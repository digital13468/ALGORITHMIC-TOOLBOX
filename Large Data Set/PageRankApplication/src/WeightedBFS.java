import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/**
 * @author Chan-Ching Hsu
 *
 */
class WeightedBFS {
	WeightedQ wq;
	Set<String> visited;
	Map<String, Set<String>> graph;
	
	WeightedBFS(String root, Map<String, Set<String>> graph) {
		this.wq = new WeightedQ();
		this.visited = new HashSet<String>();
		this.graph = graph;
		wq.add(root, (float) 0);
		visited.add(root);
		while (!wq.Q.isEmpty()) {
			//for (String key : wq.Q.keySet())
			//	System.out.println("(" + key + ", " + wq.Q.get(key) + ")\t");
			String u = wq.extract().getFirst();
			System.out.println("Processing " + u);
			if (graph.containsKey(u)) {
				for (String v : graph.get(u)) {
					if (!visited.contains(v)) {
						wq.add(v, Float.parseFloat(v));
						visited.add(v);
					}
				}
			}
		}
	}
	
	
}
