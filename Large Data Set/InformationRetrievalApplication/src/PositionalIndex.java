import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
class PositionalIndex {
	Map<Integer, String> documentIDToName;
	Map<String, Entry<Map<Integer, List<Integer>>, Integer>> positionalIndex;
	String folderName;
	int totalDocuments;
	int maximumDistanceBetweenTwoTerms;
	Map<String, Double> documentVectorsLength;
	
	PositionalIndex(String folderName) {
		this.folderName = folderName;
		this.totalDocuments = 0;
		this.documentIDToName = getDocumentsFromFolder();//System.out.println("document ID-Name table built");
		this.positionalIndex = getPositionalIndex();//System.out.println("positional index built");
		this.maximumDistanceBetweenTwoTerms = 17;
		this.documentVectorsLength = documentVectorsLength();
	}
	
	PositionalIndex(String folderName, int maximumDistanceBetweenTwoTerms) {
		this.folderName = folderName;
		this.totalDocuments = 0;
		this.documentIDToName = getDocumentsFromFolder();
		this.positionalIndex = getPositionalIndex();
		this.maximumDistanceBetweenTwoTerms = maximumDistanceBetweenTwoTerms;
		this.documentVectorsLength = documentVectorsLength();
	}
	
	//PositionalIndex(int maximumDistanceBetweenTwoTerms) {
	//	this.maximumDistanceBetweenTwoTerms = maximumDistanceBetweenTwoTerms;
	//}

	Map<String, Entry<Map<Integer, List<Integer>>, Integer>> getPositionalIndex() {
		Map<String, Entry<Map<Integer, List<Integer>>, Integer>> positionalInd = new HashMap<String, Entry<Map<Integer, List<Integer>>, Integer>>();
		for(Integer documentID : documentIDToName.keySet()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(folderName + documentIDToName.get(documentID))));
				String line = null;
				int position = 0;
				while((line = br.readLine()) != null) {//System.out.println(line);
					String[] words = textPreprocess(line);//System.out.println(Arrays.toString(words));
					for(String term : words) {
						if(term.isEmpty())
							continue;
						position ++;//System.out.println(term);
						if(positionalInd.containsKey(term)) {
							Entry<Map<Integer, List<Integer>>, Integer> dictionary = positionalInd.get(term);
							Map<Integer, List<Integer>> posting = dictionary.getKey();
							if(posting.containsKey(documentID)) {
								posting.get(documentID).add(position);
							}
							else {
								List<Integer> positionsList = new LinkedList<Integer>();
								positionsList.add(position);
								posting.put(documentID, positionsList);
								dictionary.setValue(dictionary.getValue() + 1);
							}
						}
						else {
							List<Integer> positionsList = new LinkedList<Integer>();
							positionsList.add(position);
							Map<Integer, List<Integer>> postingLists = new HashMap<Integer, List<Integer>>();
							postingLists.put(documentID, positionsList);
							Entry<Map<Integer, List<Integer>>, Integer> posting = new AbstractMap.SimpleEntry<Map<Integer, List<Integer>>, Integer>(postingLists, 1);
							positionalInd.put(term, posting);
						}
					}
					
				}
				if(br != null)
					br.close();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return positionalInd;
	}
	
	Integer getDocumentID(String fileName) {
		Integer documentID = null;
		for(Entry<Integer, String> document : documentIDToName.entrySet()) {
			if(document.getValue().equals(fileName)) {
				documentID = document.getKey();
				break;
			}
		}
		return documentID;
	}
	
	void checkDuplicatePosition() {
		Map<Integer, Set<Integer>> documentTerm = new HashMap<Integer, Set<Integer>>(); 
		for(String term : positionalIndex.keySet()) {
			for(Integer documentID : positionalIndex.get(term).getKey().keySet()) {
				if(!documentTerm.containsKey(documentID))
					documentTerm.put(documentID, new HashSet<Integer>());
				for(Integer position : positionalIndex.get(term).getKey().get(documentID)) {
					if(documentTerm.get(documentID).contains(position)) {
						throw new IllegalArgumentException(term + " has duplicate positions in " + documentIDToName.get(documentID) + " at " + position);
					}
					else
						documentTerm.get(documentID).add(position);
				}
			}
		}
	}
	
	void printDocumentAndPositionalIndex(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(folderName + fileName)));
			String line = null;
			int positionCounter = 0;
			while((line = br.readLine()) != null) {
				System.out.println(line);
				String[] words = textPreprocess(line);
				for(String word : words) {
					if(word.isEmpty())
						continue;
					positionCounter ++;
					System.out.print("(" + word + ", " + positionCounter + ")");
					System.out.print("[" + Arrays.toString(positionalIndex.get(word).getKey().get(getDocumentID(fileName)).toArray()) + "]");
					if(!positionalIndex.get(word).getKey().get(getDocumentID(fileName)).contains(positionCounter)) {
						throw new IllegalArgumentException(word + " is at position " + positionCounter + ", but not in the psoting.");
					}
					if(termFrequency(word, fileName) != positionalIndex.get(word).getKey().get(getDocumentID(fileName)).size()) {
						throw new IllegalArgumentException("The number of times " + word + " appears in " + fileName +": " + termFrequency(word, fileName) + ", " +
								positionalIndex.get(word).getKey().get(getDocumentID(fileName)).size());
					}
				}
				System.out.println();
			}
			if(br != null)
				br.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	void checkDocumentFrequency() {
		for(String term : positionalIndex.keySet()) {
			System.out.println(term + " appears in " + docFrequency(term) + " documents.");
			System.out.println(postingsList(term));
			int colonCount = 0;
			for(char c : postingsList(term).toCharArray()) {
				if(c == ':') {
					colonCount ++;
				}
			}
			if(colonCount != docFrequency(term)) {
				throw new IllegalArgumentException(term + ": " + colonCount +", " + docFrequency(term));
			}
			if(positionalIndex.get(term).getValue() != positionalIndex.get(term).getKey().keySet().size()){
				throw new IllegalArgumentException(term + ": " + positionalIndex.get(term).getValue() +", " + positionalIndex.get(term).getKey().keySet().size());
			}
		}		
	}
	
	String[] textPreprocess(String line) {//System.out.println(line);
		/*String[] textLine = line.toLowerCase().split("[ .,:;'\n\t]+");
		StringBuffer strb = new StringBuffer();
		for(int i = 0; i < textLine.length; i ++) {
			if(textLine[i].equals("the") || textLine[i].length() < 3) {
				//textLine[i] = " ";
				continue;
			}
			else
				strb.append(textLine[i] + " ");
		}//System.out.println(Arrays.toString(textLine));
		return strb.length() < 1 ? strb.substring(0).split("[ ]+") : strb.substring(0, strb.length() - 1).split("[ ]+");*/
		return queryPreprocess(line);
	}
	
	String[] queryPreprocess(String query) {
		String[] queryText = query.toLowerCase().split("[ ,\"?\\[\\]`'{}:;()\n\t]+");
		StringBuffer strb = new StringBuffer();
		Pattern p = Pattern.compile("[-]?[0-9,]*\\.[0-9]+");
		for(int i = 0; i < queryText.length; i ++) {
			String text = queryText[i];
			if(text.contains(".")) {
				Matcher m = p.matcher(text);
				if(!m.find()) {
					//queryText[i] = queryText[i].replace(".", " ");
					strb.append(queryText[i].replace(".", " ") + " ");
				}
				else
					strb.append(queryText[i] + " ");
			}
			else
				strb.append(queryText[i] + " ");
		}
		return strb.length() < 1 ? strb.substring(0).split("[ ]+") : strb.substring(0, strb.length() - 1).split("[ ]+");
	}
	
	Map<Integer, String> getDocumentsFromFolder() {
		Map<Integer, String> docIDToName = new HashMap<Integer, String>();
		File folder = new File(folderName);
		if(!folder.exists()) {
			System.err.println(folderName + " doesn't exist.");
		}
		else if(folder.listFiles().length == 0) {
			System.err.println("No files under " + folderName);
		}
		else {
			for(File file : folder.listFiles()) {
				totalDocuments ++;
				docIDToName.put(totalDocuments, file.getName());
			}
		}
		return docIDToName;
	}
	
	void printDocumentList() {
		for(Integer documentID : documentIDToName.keySet()) {
			System.out.println(documentIDToName.get(documentID) + " in the " + folderName);
		}
	}
	
	int termFrequency(String term, String Doc) {
		return positionalIndex.get(term).getKey().get(getDocumentID(Doc)).size();
	}
	
	int docFrequency(String term) {
		return positionalIndex.get(term).getValue();
	}
	
	String postingsList(String t) {
		if(!positionalIndex.containsKey(t)) {
			System.err.println(t + " is not in the built dictionary.");
			return "[]";
		}
		String postingsList = "[";
		for(Integer documentID : positionalIndex.get(t).getKey().keySet()) {
			postingsList = postingsList + "<" + documentIDToName.get(documentID) + ":";
			for(Integer pos : positionalIndex.get(t).getKey().get(documentID)) {
				postingsList = postingsList + pos.toString() + ",";
			}
			postingsList = postingsList.substring(0, postingsList.lastIndexOf(",")) + ">,";
		}
		postingsList = postingsList.substring(0, postingsList.lastIndexOf(",")) + "]";
		return postingsList;
	}
	
	void checkPostingsOrder() {
		for(String term : positionalIndex.keySet()) {
			//System.out.println(term);
			for(Integer documentID : positionalIndex.get(term).getKey().keySet()) {
				for(int i = 1; i < positionalIndex.get(term).getKey().get(documentID).size(); i ++) {
					if(positionalIndex.get(term).getKey().get(documentID).get(i).intValue() - positionalIndex.get(term).getKey().get(documentID).get(i - 1).intValue() <= 0 ) {
						throw new IllegalArgumentException("Postings is not in order." + Arrays.toString(positionalIndex.get(term).getKey().get(documentID).toArray()));
					}
				}
			}
		}
	}
	
	int getDistanceBetweenTwoTerms(String t1, String t2, Integer d) {
		int distanceBetweenTwoTerms = maximumDistanceBetweenTwoTerms;//System.out.print(t1 + " and " + t2 + " in " + documentIDToName.get(d) + "\t");
		if(!positionalIndex.containsKey(t1)) {
			System.err.println(t1 + " doesn't appear in the collection.");
		}
		else if(!positionalIndex.containsKey(t2)) {
			System.err.println(t2 + " doesn't appear in the collection.");
		}
		else {
			if(!positionalIndex.get(t1).getKey().containsKey(d)) {
				System.err.println(t1 + " doesn't appear in " + documentIDToName.get(d));
			}
			else if (!positionalIndex.get(t2).getKey().containsKey(d)){
				System.err.println(t2 + " doesn't appear in " + documentIDToName.get(d));
			}
			else {
				List<Integer> postingsT1 = positionalIndex.get(t1).getKey().get(d);
				List<Integer> postingsT2 = positionalIndex.get(t2).getKey().get(d);
				distanceBetweenTwoTerms = getMinimumElementWiseDistance(postingsT1, postingsT2);
			}
		}
		return distanceBetweenTwoTerms;
	}
	
	int getMinimumElementWiseDistance(List<Integer> p, List<Integer> r) {
		int minimumDistance = maximumDistanceBetweenTwoTerms;
		int l = 0;//System.out.print("<" + Arrays.toString(p.toArray()) + ">");
		int k = 0;//System.out.print("<" + Arrays.toString(r.toArray()) + ">");
		while(l < p.size() && k < r.size()) {
			int pl = p.get(l).intValue();
			int rk = r.get(k).intValue();
			if(pl - rk > 0) {
				k ++;//System.out.println("moving second list index");
			}
			else if(pl - rk == 0) {
				System.err.println(p.get(l) + " is equal to " + r.get(k));
			}
			else {
				if(minimumDistance > rk - pl)
					minimumDistance = rk - pl;
				l ++;
			}
		}
		if(minimumDistance > maximumDistanceBetweenTwoTerms)
			minimumDistance = maximumDistanceBetweenTwoTerms;//System.out.print("\tThe minimum distance is " + minimumDistance);
		return minimumDistance;
	}
	
	double weight(String t, String d) {
		if(!isQueryOrDocValid(t, d))
			return 0;
		Entry<Map<Integer, List<Integer>>, Integer> frequency = positionalIndex.get(t);
		//System.out.print(t + "\t");
		/*if(frequency == null)
			throw new IllegalArgumentException("1");
		else if(frequency.getValue() == null)
			throw new IllegalArgumentException("2");
		else if(frequency.getKey() == null)
			throw new IllegalArgumentException("3");*/
		//System.out.println(getDocumentID(d));
		/*if(frequency.getKey().get(getDocumentID(d)) == null)
			throw new IllegalArgumentException("5");
		else if(frequency.getKey().get(getDocumentID(d)).isEmpty())
			throw new IllegalArgumentException("4");*/
		//System.out.print("tf: " + frequency.getKey().get(getDocumentID(d)).size()+ ", df: " + frequency.getValue());
		return (Math.log(1 + frequency.getKey().get(getDocumentID(d)).size()) / Math.log(2)) * Math.log10(totalDocuments / (double) frequency.getValue());
	}
	
	double TPScore(String query, String doc) {
		if(!isQueryOrDocValid(query, doc))
			return 0;
		String[] terms = queryPreprocess(query);
		int queryLength = terms.length;
		if(queryLength == 1)
			return 0;
		else {
			double totalDistance = 0;
			for(int i = 0; i < queryLength - 1; i ++) {
				totalDistance = totalDistance + getDistanceBetweenTwoTerms(terms[i], terms[i + 1], getDocumentID(doc));//System.out.println("Total distance: " + totalDistance);
			}
			return queryLength / totalDistance;
		}
	}
	
	Map<String, Double> documentVectorsLength() {
		Map<String, Double> documentVectorsLength = new HashMap<String, Double>();
		for(String doc : documentIDToName.values()) {
			documentVectorsLength.put(doc, vectorLength(documentVector(doc)));
		}//System.out.println("document vector length: " + documentVectorsLength.toString());
		return documentVectorsLength;
	}
	
	Map<String, Double> documentVector(String doc) {
		if(!documentIDToName.values().contains(doc)) {
			System.err.println(doc + " is not in the collection.");
			return null;
		}		
		Map<String, Double> documentVector = new HashMap<String, Double>();
		for(String term : positionalIndex.keySet()) {
			if(positionalIndex.get(term).getKey().containsKey(getDocumentID(doc))) {
				documentVector.put(term, weight(term, doc));
				//System.out.println("\t" + term + " has weight " +  weight(term, doc) + " in " + doc);
			}
		}
		return documentVector;
	}
	
	Double vectorLength(Map<String, Double> vector) {
		if(vector.isEmpty()) {
			System.err.println("Vector is empty. Possibly no contents in document.");
			return (double) 0;
		}
		Double length = (double) 0;
		for(Double element : vector.values()) {
			length = length.doubleValue() + element.doubleValue() * element.doubleValue();//System.out.print(length + "\t");
		}
		return Math.sqrt(length);
	}
	
	Map<String, Entry<Map<String, Double>, Double>> documentsVector() {
		Map<String, Entry<Map<String, Double>, Double>> documentsVector = new HashMap<String, Entry<Map<String, Double>, Double>>();
		for(int i = 1; i <= totalDocuments; i ++) {
			String documentName = documentIDToName.get(i);
			Map<String, Double> documentVector = documentVector(documentName);//System.out.print(documentName + "'s vector: " + Arrays.toString(documentVector.values().toArray()) + " with length ");
			documentsVector.put(documentName, new AbstractMap.SimpleEntry<Map<String, Double>, Double>(documentVector, vectorLength(documentVector)));//System.out.println(documentsVector.get(documentName).getValue());
		}
		return documentsVector;
	}
	
	Map<String, Double> queryVector(String query) {//System.out.print("Query: " + query);
		if(query.isEmpty()) {
			System.err.println("Query is empty.");
			return null;
		}		
		Map<String, Double> vector = new HashMap<String, Double>();
		Map<String, Integer> termsCounts = new HashMap<String, Integer>();
		String[] terms = queryPreprocess(query);
		for(String term : terms) {
			if(!termsCounts.containsKey(term)) {
				termsCounts.put(term, 1);
			}
			else {
				termsCounts.put(term, termsCounts.get(term).intValue() + 1);
			}
		}//System.out.println("\tterm: " + termsCounts.toString() + ", counts: " + Arrays.toString(termsCounts.values().toArray()));
		for(String term : termsCounts.keySet()) {
			vector.put(term, (Math.log(1 + termsCounts.get(term)) / Math.log(2)) * Math.log10(totalDocuments / (double) positionalIndex.get(term).getValue()));//System.out.print(term + " appears in " + positionalIndex.get(term).getValue() + " docs.\t");
		}//System.out.println("\nterm: " + Arrays.toString(vector.keySet().toArray()) + ", weight: " + Arrays.toString(vector.values().toArray()));
		return vector;
	} 
	
	Map<Integer, Double> VSScore(String query) {
		if(query.isEmpty()) {
			System.err.println("Query is empty.");
			return null;
		}
		Map<String, Entry<Map<String, Double>, Double>> documentsVector = documentsVector();
		Map<Integer, Double> VSScore = new HashMap<Integer, Double>();
		String[] terms = queryPreprocess(query);
		Map<String, Double> queryVector = queryVector(query);
		double queryVectorLength = vectorLength(queryVector).doubleValue();
		for(String term : terms) {
			if(!positionalIndex.containsKey(term))
				continue;
			for(Integer documentID : positionalIndex.get(term).getKey().keySet()) {//System.out.print(term + " in " + documentIDToName.get(documentID) + ".\n");
				Double partialVSScore = documentsVector.get(documentIDToName.get(documentID)).getKey().get(term).doubleValue() * queryVector.get(term);//System.out.println(documentsVector.get(documentIDToName.get(documentID)).getKey().get(term).doubleValue() + " , " + queryVector.get(term));
				if(VSScore.containsKey(documentID)) {
					VSScore.put(documentID, VSScore.get(documentID).doubleValue() + partialVSScore.doubleValue());
				}	
				else {
				
					VSScore.put(documentID, partialVSScore);
				}//System.out.println("The partial score for " + documentIDToName.get(documentID) + " is " + VSScore.get(documentID));
			}
		}
		for(Integer documentID : VSScore.keySet()) {//System.out.println(documentIDToName.get(documentID) + " and query vector length: " + documentsVector.get(documentIDToName.get(documentID)).getValue().doubleValue() + " ," + queryVectorLength);
			VSScore.put(documentID, VSScore.get(documentID).doubleValue() / (documentsVector.get(documentIDToName.get(documentID)).getValue().doubleValue() * queryVectorLength));
		}//System.out.println("VSScore: " + Arrays.toString(VSScore.entrySet().toArray()));System.out.println("VSScore: " + VSScore.toString());
		return VSScore;
	}
	
	double VSScore(String query, String doc) {
		if(!isQueryOrDocValid(query, doc))
			return 0;
		double VSScore = 0;
		String[] terms = queryPreprocess(query);
		Map<String, Double> queryVector = queryVector(query);
		Integer documentID = getDocumentID(doc);
		//double documentVectorLength = 0;
		for(String term : terms) {
			if(positionalIndex.containsKey(term)) {
				if(positionalIndex.get(term).getKey().containsKey(documentID)) {//System.out.print(term + " appears in " + doc + ".\t");
					VSScore = VSScore + weight(term, doc) * queryVector.get(term).doubleValue();//System.out.print(term + " has weights " + weight(term, doc) + " and " + queryVector.get(term).doubleValue());
					//documentVectorLength = documentVectorLength + weight(term, doc) * weight(term, doc);System.out.print("\tdocument vector length: " + documentVectorLength);
				}
			}
		}//System.out.print("\tVSSCore: " + VSScore + ", query vector length: " + vectorLength(queryVector).doubleValue() + ", document vector length: " + documentVectorsLength.get(doc).doubleValue());
		if(VSScore == 0 || documentVectorsLength.get(doc).doubleValue() == 0)
			return 0;
		return VSScore / (documentVectorsLength.get(doc).doubleValue() * vectorLength(queryVector).doubleValue());
	}
	
	double Relevance(String query, String doc) {//System.out.print("TPScore: " + TPScore(query, doc) + ", VSScore: " + VSScore(query, doc));
		if(!isQueryOrDocValid(query, doc))
			return 0;
		return 0.6 * TPScore(query, doc) + 0.4 * VSScore(query, doc);
	}
	
	boolean isQueryOrDocValid(String query, String doc) {
		if(query.isEmpty()) {
			System.err.println("Query is empty.");
			return false;
		}
		else if(!documentIDToName.values().contains(doc)) {
			System.err.println(doc + " is not in the collection.");
			return false;
		}
		return true;
	}
}
