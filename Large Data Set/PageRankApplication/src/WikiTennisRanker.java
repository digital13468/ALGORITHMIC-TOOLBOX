import java.util.HashSet;
import java.util.Set;



/**
 * @author Chan-Ching Hsu
 *
 */
class WikiTennisRanker {
	
	void run() {
		Set<String> topInDegree = new HashSet<String>();
		Set<String> topOutDegree = new HashSet<String>();
		Set<String> topPageRank = new HashSet<String>();
		String[] topKinD = null;
		String[] topKoutD = null;
		String[] topKpageR = null;
		PageRank pg1 = new PageRank("wikiTennis.txt", 0.01);
		System.out.println("======================= epsilon = 0.01 =======================");
		topKinD = pg1.topKInDegree(10);
		System.out.println("Top 10 pages with highest in-degree: ");
		topK(topKinD, topInDegree);
		System.out.println("Top 10 pages with highest out-degree: ");
		topKoutD = pg1.topKOutDegree(10);
		topK(topKoutD, topOutDegree);
		topKpageR = pg1.topKPageRank(10);
		System.out.println("Top 10 pages with highest page rank: ");
		topK(topKpageR, topPageRank);
		System.out.println("Similarity between in-degree and out-degree: " + computeJaccardSimilarity(topInDegree, topOutDegree));
		System.out.println("Similarity between in-degree and page rank: " + computeJaccardSimilarity(topInDegree, topPageRank));
		System.out.println("Similarity between out-degree and page rank: " + computeJaccardSimilarity(topOutDegree, topPageRank));
		topInDegree.clear();
		topOutDegree.clear();
		topPageRank.clear();
		topKinD = pg1.topKInDegree(100);
		System.out.println("Top 100 pages with highest in-degree: ");;
		topK(topKinD, topInDegree);
		System.out.println("Top 100 pages with highest out-degree: ");
		topKoutD = pg1.topKOutDegree(100);
		topK(topKoutD, topOutDegree);
		System.out.println("Top 100 pages with highest page rank: ");
		topKpageR = pg1.topKPageRank(100);
		topK(topKpageR, topPageRank);
		System.out.println("Similarity between in-degree and out-degree: " + computeJaccardSimilarity(topInDegree, topOutDegree));
		System.out.println("Similarity between in-degree and page rank: " + computeJaccardSimilarity(topInDegree, topPageRank));
		System.out.println("Similarity between out-degree and page rank: " + computeJaccardSimilarity(topOutDegree, topPageRank));
		topInDegree.clear();
		topOutDegree.clear();
		topPageRank.clear();
		System.out.println("======================= epsilon = 0.005 =======================");
		PageRank pg2 = new PageRank("wikiTennis.txt", 0.005);
		topKinD = pg2.topKInDegree(10);
		System.out.println("Top 10 pages with highest in-degree: ");
		topK(topKinD, topInDegree);
		topKoutD = pg2.topKOutDegree(10);
		System.out.println("Top 10 pages with highest out-degree: ");
		topK(topKoutD, topOutDegree);
		topKpageR = pg2.topKPageRank(10);
		System.out.println("Top 10 pages with highest page rank: ");
		topK(topKpageR, topPageRank);
		System.out.println("Similarity between in-degree and out-degree: " + computeJaccardSimilarity(topInDegree, topOutDegree));
		System.out.println("Similarity between in-degree and page rank: " + computeJaccardSimilarity(topInDegree, topPageRank));
		System.out.println("Similarity between out-degree and page rank: " + computeJaccardSimilarity(topOutDegree, topPageRank));
		topInDegree.clear();
		topOutDegree.clear();
		topPageRank.clear();
		topKinD = pg2.topKInDegree(100);
		System.out.println("Top 100 pages with highest in-degree: ");;
		topK(topKinD, topInDegree);
		topKoutD = pg2.topKOutDegree(100);
		System.out.println("Top 100 pages with highest out-degree: ");
		topK(topKoutD, topOutDegree);
		topKpageR = pg2.topKPageRank(100);
		System.out.println("Top 100 pages with highest page rank: ");
		topK(topKpageR, topPageRank);
		System.out.println("Similarity between in-degree and out-degree: " + computeJaccardSimilarity(topInDegree, topOutDegree));
		System.out.println("Similarity between in-degree and page rank: " + computeJaccardSimilarity(topInDegree, topPageRank));
		System.out.println("Similarity between out-degree and page rank: " + computeJaccardSimilarity(topOutDegree, topPageRank));
	}
	
	void topK(String[] topKString, Set<String> topKSet) {
		for (String k : topKString) {
			System.out.println(k);
			topKSet.add(k);
		}
	}
	
	double computeJaccardSimilarity(Set<String> docA, Set<String> docB) {
		
		double duplicates = 0;
		for(String term : docA)
			if(docB.contains(term))
				duplicates ++;
		return duplicates / (docA.size() + docB.size() - duplicates);
	}
}
