
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author Chan-Ching Hsu
 *
 */
class WeightedQ {
	Map<String, Float> Q;
	/**
	 * 
	 */
	WeightedQ() {
		this.Q = new LinkedHashMap<String, Float>();
	}
	
	void add(String x, Float n) {
		if (Q.containsKey(x) == false)
			Q.put(x, n);
		else
			System.out.println(x + " exists already.");
	}
	
	Pair<String, Float> extract() {
		Pair<String, Float> tuple;
		if (Q.isEmpty())
			tuple = new Pair<String, Float>("None", Float.MIN_VALUE);
		else {
			Map.Entry<String, Float> highestWeightEntry = getHighestWeightEntry();
			tuple = new Pair<String, Float>(highestWeightEntry.getKey(), highestWeightEntry.getValue());
			Q.remove(highestWeightEntry.getKey());
		}
		return tuple;
	}
	
	Map.Entry<String, Float>getHighestWeightEntry() {
		Map.Entry<String, Float> highestEntry = Q.entrySet().iterator().next();
		for (Map.Entry<String, Float> entry : Q.entrySet()) {
			if (entry.getValue().compareTo(highestEntry.getValue()) > 0) {
				highestEntry = entry;
			} 
		}
		return highestEntry;
	}
}
