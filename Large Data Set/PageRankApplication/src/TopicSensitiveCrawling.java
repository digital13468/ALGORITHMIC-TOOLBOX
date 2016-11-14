import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Chan-Ching Hsu
 *
 */
class TopicSensitiveCrawling {
	List<Pair<String, Float>> weights;
	String delims = "[ ]+";
	String href = "<a href=";
	String hrefBetween = "\"";
	String anchorTextGoesAfter = ">";
	String anchorTextFollowedBy = "</a>";
	String anchorTextBetween = "@@";
	Set<String> linksAppeared;
	int maxDistBetweenWordAndLink = 20;
	String compressTextBy = " ";
	boolean isWeighted;
	String[] specialLinks = {};/*{"/wiki/Wikipedia:Citation_needed", "wikipedia.org/wiki/", "action=edit",
			"https://en.wiktionary.org/wiki/", "/wiki/Wikipedia:NOTRS", "/wiki/Category:", "/wiki/Wikipedia:Please_clarify",
			"/wiki/Wikipedia:Avoid_weasel_words", "/wiki/File:", "/wiki/Wikipedia:Verifiability", "/wiki/Special:BookSources",
			"/wiki/Wikipedia:Manual_of_Style/Dates_and_numbers", "//upload.wikimedia.org/wikipedia/commons/", "/wiki/Help:",
			"https://en.wikisource.org/wiki", "/wiki/Wikipedia:Vagueness", "https://wikimediafoundation.org/", "//www.mediawiki.org/",
			"/wiki/Portal", "wikimediafoundation.org/", "/wiki/Book:", "https://en.wikiquote.org/wiki/Special:"};*/
	
	TopicSensitiveCrawling(BufferedReader br, String[] keyWords, boolean isWeighted) {
		this.weights = new ArrayList<Pair<String, Float>>();
		this.linksAppeared = new HashSet<String>();
		this.isWeighted = isWeighted;
		assignWeightsToLinks(br, keyWords);
	}
	
	void assignWeightsToLinks(BufferedReader br, String[] keyWords) {
		
		
		
		
		//String anchorEnd = "</a>";
		try {
			String line = br.readLine();
			while (line != null && !line.contains("<p>")) {
				line = br.readLine();
			}
			while (line != null) {
				line = line.trim();//System.out.println(line);
				//if (line.contains("<p>") || line.contains("<dt>") || line.contains("<dd>") || line.contains("<div") ||
				//		line.startsWith(href) || (line.matches("[a-zA-Z](.*)")) || line.contains("<li>" + href) || line.contains("<th")) {
					
					
					//if (line.contains(href)) {
						//String[] tokens = line.split(delims);
					int hrefIndex = 0;
					//int substringStart = 0;
					//int titleIndex = 0;
					//while (line.substring(hrefIndex).contains(href)) {
					while ((hrefIndex = line.indexOf(href, hrefIndex)) != -1) {
						//hrefIndex = line.indexOf(href, hrefIndex);
						int linkStartAt = line.indexOf(hrefBetween, hrefIndex) + 1;
						//System.out.println(line.indexOf("\"", hrefIndex));
						//System.out.println(line.indexOf("\"", hrefIndex + 1));
						String link = line.substring(linkStartAt, line.indexOf(hrefBetween, linkStartAt));
						
						hrefIndex = hrefIndex + link.length() + href.length() + 2 * hrefBetween.length();
						if (!line.contains(anchorTextFollowedBy))
							continue;
						String text = line.substring(line.indexOf(anchorTextGoesAfter, hrefIndex) + 1,
								line.indexOf(anchorTextFollowedBy, hrefIndex));
						//text = text.replaceAll("\\<[^>]*>", "");
						
						hrefIndex = hrefIndex + text.length() + anchorTextGoesAfter.length() + anchorTextFollowedBy.length();
						text = specialAnchorTextRetrieve(text).replaceAll("\\<[^>]*>", "");
						if (link.contains("#") || link.contains(":") || linksAppeared.contains(link) || isSpecialLink(link) || !link.startsWith("/wiki/"))
								//|| !link.startsWith("/wiki"))
							// || link.contains("/wiki/Wikipedia:Citation_needed"))// || linksAppeared.contains(text))
							continue;
						isValid(link);
						/*int poundAt;
						if ((poundAt = link.indexOf("#")) != -1)
							link = link.substring(0, poundAt);*/
						//System.out.print(link + "\t");
						//System.out.println(text);
						linksAppeared.add(link);
						//linksAppeared.add(text);
						if (keyWords.length == 0) {
							this.weights.add(new Pair<String, Float>(link, (float) 0));
							continue;
						}
						if (!isWeighted) {
							this.weights.add(new Pair<String, Float>(link, (float) 0));
							continue;
						}
						boolean containWord = false;
						for (String keyWord : keyWords) {
							String[] lowercaseKeyWords = keyWord.toLowerCase().split("[ ]+");
							for (String keyword : lowercaseKeyWords) {
								if (link.toLowerCase().contains(keyword) || text.toLowerCase().contains(keyword)) {
									//System.out.println(link + ", " + text + ", " + keyword);
									
									containWord = true;
									//break;
								}
								else
									containWord = false;
							}
							if (containWord == true) {
								this.weights.add(new Pair<String, Float>(link, (float) 1));
								break;
							}
						}
						if (!containWord) {
							String[] lineWithoutTags = removeTagsInLines(line);
							//String anchorTextStartsWith = lineWithoutTags.toString().indexOf(anchorTextBetween);
							boolean anchorTextFound = false;
							int anchorTextAt = Integer.MIN_VALUE;
							for (int i = 0; i < lineWithoutTags.length; i ++) {
								//System.out.print(lineWithoutTags[i] + " ");
								//System.out.println(text.replace(compressTextBy, ""));
								if (lineWithoutTags[i].contains(text.replace(compressTextBy, ""))) {
									anchorTextAt = i;
									anchorTextFound = true;
									break;
								}
							}
							if (!anchorTextFound) {
								//System.out.println("\n" + text.replace(compressTextBy, "") + "\n");
								throw new IllegalArgumentException("Didn't find the anchor text.");
							}
							//System.out.println();
							//System.out.println(line);
							int distanceB = distanceBefore(lineWithoutTags, anchorTextAt, keyWords);
							int distanceA = distanceAfter(lineWithoutTags, anchorTextAt, keyWords);
							//System.out.println(distanceB + ", " + distanceA);
							if (distanceB >= distanceA) {
								if (distanceA > 20)
									this.weights.add(new Pair<String, Float>(link, (float) 0));
								else
									this.weights.add(new Pair<String, Float>(link, 1 / (float) (distanceA + 2)));
							}
							else {
								if (distanceB > 20)
									this.weights.add(new Pair<String, Float>(link, (float) 0));
								else
									this.weights.add(new Pair<String, Float>(link, 1 / (float) (distanceB + 2)));
							}
						}
					}
					
				//}
				line = br.readLine();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	void isValid(String link) {
		if (!link.startsWith("/wiki/") || (link.contains(":") && !link.contains("_")) || link.contains("#")) {
			/*System.out.println(link);
			try {
				System.in.read();
			} catch (Exception e) {
				
			}*/
			throw new IllegalArgumentException("Not a valid wiki link to crawl: " + link); 
		}
	}
	
	boolean isSpecialLink(String link) {
		boolean isSpecial = false;
		for (String specialLink : specialLinks)
			if (link.contains(specialLink))
				isSpecial = true;
		return isSpecial;
	}
	
	String specialAnchorTextRetrieve(String anchorText) {
		//String [] specialTags = {"<span title", "<img alt"};
		//for (String specialTag : specialTags) {
		int specialTagStarts = -1;
		while ((specialTagStarts = anchorText.indexOf("<span title")) > -1) {
			int anchorTextStarts = anchorText.indexOf('>', specialTagStarts) + 1;
			int anchorTextEnds = anchorText.indexOf("</span>", anchorTextStarts);
			String anchorTextRetrieved = anchorText.substring(anchorTextStarts, anchorTextEnds);
			String originalAnchorText = anchorText.substring(specialTagStarts, anchorTextEnds + 7);
			anchorText = anchorText.replace(originalAnchorText, anchorTextRetrieved);
		}
		//}
		while ((specialTagStarts = anchorText.indexOf("<img alt")) > -1) {
			int anchorTextStarts = anchorText.indexOf("\"", specialTagStarts) + 1;
			int anchorTextEnds = anchorText.indexOf("\"", anchorTextStarts);
			String anchorTextRetrieved = anchorText.substring(anchorTextStarts, anchorTextEnds);
			String originalAnchorText = anchorText.substring(specialTagStarts, anchorText.indexOf("/>", anchorTextEnds) + 2);
			anchorText = anchorText.replace(originalAnchorText, anchorTextRetrieved);
		}
		return anchorText;
	}
	
	int distanceAfter(String[] noTagsLine, int linkAt, String[] keyWords) {
		int distance = Integer.MAX_VALUE;
		int furtherestWordAt = linkAt + maxDistBetweenWordAndLink + 1;
		int noTagsLineLen = noTagsLine.length;
		if (furtherestWordAt >= noTagsLineLen)
			furtherestWordAt = noTagsLineLen - 1;
		boolean containWord = false;
		for (int i = linkAt + 1; i <= furtherestWordAt; i++) {
			for (String keyWord : keyWords) {
				String[] keywords = keyWord.split("[ ]+");
				int keywordsNum = keywords.length;
				if (keywordsNum > 1 && (keywordsNum + i - 1 <= furtherestWordAt)) {
					for (int j = 0; j < keywordsNum; j ++) {
						//System.out.println(i + ", " + j + ", " + noTagsLineLen); System.out.println(noTagsLine[i + j]); System.out.println(keywords[j]);
						if (noTagsLine[i + j].toLowerCase().contains(keywords[j].toLowerCase()))
							containWord = true;
						else {
							containWord = false;
							break;
						}
					}
					if (containWord)
						distance = i - linkAt - 1;
				}
				else if (keywordsNum == 1){
					if (noTagsLine[i].toLowerCase().contains(keyWord.toLowerCase())) {
						distance = i - linkAt - 1;
						containWord = true;//System.out.println(noTagsLine[i] + ", " + keyWord + ", " + i + ", " + linkAt);
					}
				}
				if (containWord)
					break;
			}
			if (containWord) 
				break;
		}
		return distance;
	}
	
	int distanceBefore(String[] noTagsLine, int linkAt, String[] keyWords) {
		//Float weightB = Float.MAX_VALUE;
		int distance = Integer.MAX_VALUE;
		int furtherestWordAt = linkAt - maxDistBetweenWordAndLink - 1;
		if (furtherestWordAt < 0)
			furtherestWordAt = 0;
		boolean containWord = false;
		for (int i = linkAt - 1; i >= furtherestWordAt; i --) {
			
			for (String keyWord : keyWords) {
				String[] keywords = keyWord.split("[ ]+");
				int keywordsNum = keywords.length;
				if (keywordsNum > 1 && (i - keywordsNum + 1 >= furtherestWordAt)) {
					
					
					for (int j = keywordsNum - 1; j >= 0; j--) {
						//System.out.println(i + ", " + j); System.out.println(keyWord); System.out.println(keywords[j]);
						if (noTagsLine[i - (keywordsNum - j - 1)].toLowerCase().contains(keywords[j].toLowerCase())) {
							containWord = true;
							
						}
						else {
							containWord = false;
							break;
						}
						
					}
					if (containWord) {
						distance = linkAt - i - 1;
					}
				}
				else if (keywordsNum == 1) {
					if (noTagsLine[i].toLowerCase().contains(keyWord.toLowerCase())) {
						distance = linkAt - i - 1;
						containWord = true;//System.out.println(noTagsLine[i] + ", " + keyWord);
					}
				}
				if (containWord)
					break;
			}
			if (containWord)
				break;
		}
		return distance;
	}
	
	String[] removeTagsInLines(String line) {
		String contentToProcess = line;
		contentToProcess = specialAnchorTextRetrieve(contentToProcess);
		contentToProcess = contentToProcess.replaceAll("\\<a [^>]*>", anchorTextBetween);
		
		contentToProcess = contentToProcess.replaceAll("\\</a>", anchorTextBetween);//System.out.println(contentToProcess);
		int left = 0;
		while ((left = contentToProcess.indexOf(anchorTextBetween, left)) > -1) {
			
			
			int right = contentToProcess.indexOf(anchorTextBetween, left + anchorTextBetween.length());
			if (right < 0) {
				//System.out.println(contentToProcess.substring(0, left));
				throw new IllegalArgumentException(contentToProcess.substring(left, contentToProcess.length()));
			}
			String anchorTextToReplace = contentToProcess.substring(left, right);
			String anchorTextReplaceWith = anchorTextToReplace.replaceAll(compressTextBy, "");
			//System.out.println(anchorTextToReplace);	
			//System.out.println(anchorTextReplaceWith);
			//System.out.println(contentToProcess);
			contentToProcess = contentToProcess.replace(anchorTextToReplace, anchorTextReplaceWith);
			if (!contentToProcess.contains(anchorTextReplaceWith)) {
				throw new IllegalArgumentException("couldn't find the text");
			}
			left = right + anchorTextBetween.length() - anchorTextToReplace.length() + anchorTextReplaceWith.length();
			
			
		}
		return contentToProcess.replaceAll("\\<[^>]*>", "").split(delims);
		
	}
}
