import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Chan-Ching Hsu
 *
 */
class WikiCrawler {
	List<String> disallowedSites;
	static final String BASE_URL = "https://en.wikipedia.org";
	String[] keyWords;
	int max;
	String fileName;
	boolean isWeighted;
	Set<String> visited;
	WeightedQ wq;
	
	WikiCrawler(String seedUrl, String[] keywords, int max, String fileName, boolean isWeighted) {
		getRobotsSites();
		this.keyWords = keywords;
		this.max = max;
		this.fileName = fileName;
		this.isWeighted = isWeighted;
		this.visited = new HashSet<String>();
		this.wq = new WeightedQ();
		weightedBFS(seedUrl);
		checkGraph(fileName);
	}
	
	void getRobotsSites() {
		this.disallowedSites = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		URL url;
		try {
			url = new URL(BASE_URL + "/robots.txt");
			is = url.openStream();
			br = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
			String line = br.readLine();
			while (line != null && !line.equals("User-agent: *"))
				line = br.readLine();
			while ((line = br.readLine())!= null) {
				if (line.indexOf("Disallow") > -1) {
					disallowedSites.add(line.split(":")[1].trim());
				}
			} //for (String disallowedSite : disallowedSites) System.out.println(disallowedSite);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (br != null) 
					br.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	void checkGraph(String fileName) {
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    Set<String> edgeSet = new HashSet<String>();
			String line = br.readLine();
			Set<String> vertexSet = new HashSet<String>();
		    while (line != null) {
		    	
		    	String[] edgeString = line.split("[ ]+");
		    	if (edgeString.length <= 1) {
		    		line = br.readLine();
		    		continue;
		    		
		    	}
		    	if (edgeSet.contains(line) || edgeString[0].equals(edgeString[1])) {
		    		System.out.println(line);
		    		
		    		throw new IllegalArgumentException("The graph has self loops or multiple edges.");
		    		
		    	}
		    	else {
		    		edgeSet.add(line);
		    		vertexSet.add(edgeString[0]);
		    		vertexSet.add(edgeString[1]);
		    	}
		    	line = br.readLine();
		    	
		    }
		    //System.out.println(vertexSet.size());	
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	void weightedBFS(String seedUrl) {
		int requests = 0;
		wq.add(seedUrl, (float) 0);
		visited.add(seedUrl);
		PrintWriter writer = null;
		int vertexNum = 1;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			writer.println(max);
			while (!wq.Q.isEmpty()) {
				List<Pair<String, Float>> weightedQ;
				String u = wq.extract().getFirst();
				/*if (!u.equals(seedUrl)) {//System.out.println(u + "\t" + seedUrl);
					String from = u.split(edge)[0];
					String to = u.split(edge)[1];
					u = to;
					writer.println(from + edge + to);
					vertexNum ++;
				}*/
				weightedQ = crawl(u);
				requests ++;
				if (requests % 10 == 0) { 
					Thread.sleep(ThreadLocalRandom.current().nextInt(1000000000, Integer.MAX_VALUE) / 1000000);
					//System.out.println("waited for " +
					//ThreadLocalRandom.current().nextInt(1000000000, Integer.MAX_VALUE) / 1000000 + "milliseconds");
				}
				if (weightedQ == null)
					continue;
				for (Pair<String, Float> weight : weightedQ) {
					String v = weight.getFirst();//System.out.println(v);
					if (visited.contains(v))
						writer.println(u + " " + v);
					else if (!visited.contains(v) && vertexNum < max && isAllowedSite(v)) {
						wq.add(v, weight.getSecond());
						visited.add(v);
						writer.println(u + " " + v);
						vertexNum ++;
					}
				}
			}
			if (vertexNum < max || vertexNum != visited.size()) {
				throw new IllegalArgumentException("Only " + vertexNum + " found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	boolean isAllowedSite(String site) {
		boolean isAllowed = true;
		for (String disallowedSite : disallowedSites)
			if (site.contains(disallowedSite)) {
				isAllowed = false;
				throw new IllegalArgumentException(site + "is not allowed to crawl.");
			}
		return isAllowed;
	}
	
	List<Pair<String, Float>> crawl(String nodeUrl) {
		String absoluteUrl = BASE_URL + nodeUrl;
		InputStream is = null;
		BufferedReader br = null;
		//System.out.println(nodeUrl);
		List<Pair<String, Float>> weightedQ = null;
		URL url;
		try {
			//CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			
			url = new URL(absoluteUrl);
			
			//int i;
			is = url.openStream();
			//BufferedInputStream bis = new BufferedInputStream(is);
			//InputStreamReader isr = new InputStreamReader(bis);
			br = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
			/*while ((line = br.readLine()) != null) 
				everything.append(" " + line);*/
			
			//System.out.println("crawling " + absoluteUrl);
			TopicSensitiveCrawling tsc = new TopicSensitiveCrawling(br, keyWords, isWeighted);
			//TopicSensitiveCrawling tsc = new TopicSensitiveCrawling(everything, keyWords);
			for (int i = 0; i < tsc.weights.size(); i ++) {
				if (nodeUrl.equals(tsc.weights.get(i).getFirst())) {
					tsc.weights.remove(i);
					//System.err.println("self loop: " + tsc.weights.get(i).getFirst());
				}
				//System.err.println(tsc.weights.get(i).getFirst());
			}
			weightedQ = tsc.weights;
		} catch (InterruptedIOException iioe) {
			System.err.println("Remote host timed out");
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			System.err.println("Invalid URL");
		} catch (IOException ioe) {
			System.err.println("Network I/O error - " + ioe);
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (br != null) 
					br.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return weightedQ;
		
	}
}
