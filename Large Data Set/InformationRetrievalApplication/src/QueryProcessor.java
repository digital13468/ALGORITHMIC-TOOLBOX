import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
class QueryProcessor {
	PositionalIndex pi;
	
	QueryProcessor(String folderName) {
		this.pi = new PositionalIndex(folderName);
	}
	
	List<String> topKDocs(String q, int k) {
		Map<String, Double> relevance = new HashMap<String, Double>();
		List<String> topKDocs = new ArrayList<String>();
		for(String documentName : pi.documentIDToName.values()) {
			relevance.put(documentName, pi.Relevance(q, documentName));//System.out.println(documentName + " has relevance " + pi.Relevance(q, documentName));
		}//System.out.println(relevance.toString());
		for(Entry<String, Double> entry : entriesSortedByValue(relevance).subList(0, k)) {
			topKDocs.add(entry.getKey());
			//topKDocs.add(entry.getKey() + " has " + pi.TPScore(q, entry.getKey()) + ", " + pi.VSScore(q, entry.getKey()) + ", " + entry.getValue() + " as TPScpre, VSScore and Relevance Score, respectively.");
		}
		return topKDocs;
	}
	
	List<Entry<String, Double>> entriesSortedByValue(Map<String, Double> map) {
		List<Entry<String, Double>> sortedEntries = new ArrayList<Entry<String, Double>>(map.entrySet());
		Collections.sort(sortedEntries, new Comparator<Entry<String, Double>>() {
			public int compare(Entry<String, Double> e1, Entry<String, Double> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});
		return sortedEntries;
	}
	
	List<String> alternative(String q, int k) {
		Map<String, Double> relevance = new HashMap<String, Double>();
		List<String> topKDocs = new ArrayList<String>();
		Map<Integer, Double> VSScore = pi.VSScore(q);
		for(String documentName : pi.documentIDToName.values()) {
			if(VSScore.containsKey(pi.getDocumentID(documentName))) {
				relevance.put(documentName, 0.6 * pi.TPScore(q, documentName) + 0.4 * VSScore.get(pi.getDocumentID(documentName)));//System.out.println(documentName + " has relevance " + (0.6 * pi.TPScore(q, documentName) + 0.4 * VSScore.get(pi.getDocumentID(documentName))));
			}
		}//System.out.println(Arrays.toString(relevance.values().toArray()));
		for(Entry<String, Double> entry : entriesSortedByValue(relevance).subList(0, k)) {
			topKDocs.add(entry.getKey());
		}
		return topKDocs;
	}
}
