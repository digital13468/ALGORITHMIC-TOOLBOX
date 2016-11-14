import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/**
 * 
 */

/**
 * @author CHan-Ching Hsu
 *
 */
class PageRankApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//weightedQTest();
		//weightedBFSTest();
		//topicSensitiveCrawlingTest();
		
		// MyWikiRanker - pop music
		/*String[] topics = {"album", "formula", "funk", "gospel music", "hit", "pop song", "rapper", "single", "soul music", "verse"};
		WikiCrawler w = new WikiCrawler("/wiki/Pop_music", topics, 100, "WikiPopMusicGraph.txt", true);
		PageRank pg = new PageRank("WikiPopMusicGraph.txt", 0.01);
		String[] topKpageR = pg.topKPageRank(10);
		System.out.println("Top 10 pages with highest page rank: ");
		new WikiTennisRanker().topK(topKpageR, new HashSet<String>());*/
		
		// MyWikiRanker - pop music
		String[] topics = {"system", "mass", "velocity", "collision", "speed", "inertia", "kinetic energy", "momentum"};
		WikiCrawler w = new WikiCrawler("/wiki/Physics", topics, 100, "WikiPhysicsGraph.txt", true);
		PageRank pg = new PageRank("WikiPhysicsGraph.txt", 0.01);
		String[] topKpageR = pg.topKPageRank(10);
		System.out.println("Top 10 pages with highest page rank: ");
		new WikiTennisRanker().topK(topKpageR, new HashSet<String>());
				
		// WikiTennisRanker
		/*WikiTennisRanker wtr = new WikiTennisRanker();
		wtr.run();*/
	}
	
	static void topicSensitiveCrawlingTest() {
		URL url;
		InputStream is = null;
		BufferedReader br;
		//String line;
		String[] keyWords = {"tennis", "grand slam", "french open", "australian open", "wimbledon", "us open", "masters"};
		//StringBuilder everything = new StringBuilder();
		try {
			//CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			
			url = new URL("https://en.wikipedia.org/wiki/french_open");
			
			//int i;
			is = url.openStream();
			//BufferedInputStream bis = new BufferedInputStream(is);
			//InputStreamReader isr = new InputStreamReader(bis);
			br = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
			/*while ((line = br.readLine()) != null) 
				everything.append(" " + line);
			
			System.out.println(everything.toString());*/
			TopicSensitiveCrawling tsc = new TopicSensitiveCrawling(br, keyWords, true);
			//TopicSensitiveCrawling tsc = new TopicSensitiveCrawling(everything, keyWords);
			for (Pair<String, Float> weight : tsc.weights)
				weight.printPair();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			System.err.println("Invalid URL");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.err.println("Network I/O error - " + ioe);
		} finally {
			try {
				if (is != null)
					is.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
	}
	
	static void weightedBFSTest() {
		
		Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
		graph.put("1", new HashSet<String>(Arrays.asList("2", "3", "4")));
		graph.put("2", new HashSet<String>(Arrays.asList("5", "6")));
		graph.put("5", new HashSet<String>(Arrays.asList("9", "10")));
		graph.put("7", new HashSet<String>(Arrays.asList("11", "12")));
		graph.put("4", new HashSet<String>(Arrays.asList("7", "8")));
		
		for (String i : graph.keySet()) {
			System.out.print(i + ": ");
			for (String j : graph.get(i))
				System.out.print(j + "\t");
			System.out.println();
		}
		WeightedBFS wbfs = new WeightedBFS("1", graph);
	}
	
	static void weightedQTest() {
		WeightedQ wq = new WeightedQ();
		wq.add("1", (float) 5);
		wq.add("2", (float) 3);
		wq.add("5", (float) 7);
		wq.add("21", (float) 5);
		wq.add("36", (float) 4);
		Pair<?, ?> tuple = wq.extract();
		System.out.println("extract (" + tuple.getFirst() + ", " + tuple.getSecond() + ")");
		tuple = wq.extract();
		System.out.println("extract (" + tuple.getFirst() + ", " + tuple.getSecond() + ")");
		wq.add("21", (float) 9);
		System.out.println("remaining:");
		for (String key : wq.Q.keySet())
			System.out.println("(" + key + ", " + wq.Q.get(key) + ")\t");
	}
}
