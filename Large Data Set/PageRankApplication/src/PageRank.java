import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



/**
 * @author Chan-Ching Hsu
 *
 */
class PageRank {
	int numEdge;
	Map<String, Set<String>> inDegree;
	Map<String, Set<String>> outDegree;
	String edgeFile;
	Map<String, Integer> inDegreeSize;
	Map<String, Integer> outDegreeSize;
	Map<String, Double> distributionVector;
	int numVertex;
	Set<String> vertices;
	double epsilon;
	double beta = 0.85;
	//double baseProbability;
	Map<String, Double> baseProbabilityVector;
	
	PageRank(String edgeFile, double epsilon) {
		this.inDegree = new HashMap<String, Set<String>>();
		this.outDegree = new HashMap<String, Set<String>>();
		this.numEdge = 0;
		this.edgeFile = edgeFile;
		this.inDegreeSize = new HashMap<String, Integer>();
		this.outDegreeSize = new HashMap<String, Integer>();
		this.distributionVector = new HashMap<String, Double>();
		this.numVertex = 0;
		this.vertices = new HashSet<String>();
		this.epsilon = epsilon;
		//this.baseProbability = (1.0 - beta) / numEdge;
		this.baseProbabilityVector = new HashMap<String, Double>();
		buildGraph();
		for(String vertex : vertices) {
			baseProbabilityVector.put(vertex, (1.0 - beta) / numVertex);
			//System.out.println(baseProbabilityVector.get(vertex));
		}
		
		simulateRandomWalk();/*
		String[] topKinD = topKInDegree(4);
		for (String kinD : topKinD)
			System.out.println(kinD);
		String[] topKoutD = topKOutDegree(4);
		for (String kOutD : topKoutD)
			System.out.println(kOutD);
		String[] topKpageR = topKPageRank(4);
		for (String kPageR : topKpageR)
			System.out.println(kPageR);*/
	}
	
	void printDistributionVector() {
		for(String vertex : vertices)
			System.out.println(vertex + ": " + distributionVector.get(vertex));
	}
	
	void simulateRandomWalk() {
		int stepNum = 0;
		initializeDistributionVector();//printDistributionVector();
		boolean converged = false;
		while (!converged) {
			Map<String, Double> update = oneStepRandomWalk();
			converged = isConverged(update);
			distributionVector = update;
			stepNum ++;//printDistributionVector();
		}
		System.out.println("It took " + stepNum + " steps.");
	}
	
	boolean isConverged(Map<String, Double> currentStep) {
		double difference = 0;
		for (String vertex : vertices) {
			difference = difference + Math.abs(currentStep.get(vertex) - distributionVector.get(vertex));
		} 
		if (difference <= epsilon)
			return true;
		else
			return false;
	}
	
	Map<String, Double> oneStepRandomWalk() {
		Map<String, Double> nextStep = new HashMap<String, Double>(baseProbabilityVector);
		for(String vertex : vertices) {
			double base = beta * distributionVector.get(vertex);
			if(outDegree.containsKey(vertex)) {
				//if (outDegreeSize.get(vertex) == 0)
				//	throw new IllegalArgumentException("Outdegree 0");
				double increment = base / outDegreeSize.get(vertex);
				for(String adjacentNode : outDegree.get(vertex)) {
					nextStep.put(adjacentNode, nextStep.get(adjacentNode) + increment);
				}
			}
			else {
				//System.out.println(vertex + " is a sink node.");
				double increment = base / numVertex;
				for(String v : vertices) {
					nextStep.put(v, nextStep.get(v) + increment);
					
				}
			}
		}
		return nextStep;
	}
	
	void initializeDistributionVector() {
		if(numVertex != vertices.size())
			throw new IllegalArgumentException("Vertex number not matched");
		double initialProbability = 1.0 / numVertex;
		for(String vertex : vertices) {
			distributionVector.put(vertex, initialProbability);
			
		}
	}
	
	void buildGraph() {
		try (BufferedReader br = new BufferedReader(new FileReader(edgeFile))) {
			numVertex = Integer.parseInt(br.readLine());
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] edge = line.split(" ");
				if (edge.length <= 1)
					throw new IllegalArgumentException("Unable to capture nodes from line: " + line);
				addDegree(inDegree, edge[1], edge[0], inDegreeSize);
				addDegree(outDegree, edge[0], edge[1], outDegreeSize);
				numEdge ++;
				vertices.add(edge[0]);
				vertices.add(edge[1]);
			} 
			//printGraph();
			checkDegreeSize();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find file: " + edgeFile);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	void checkDegreeSize() {
		for (String from : outDegree.keySet()) {
			//System.out.println(from + " has " + outDegree.get(from).size() + " outdegree.");
			if (outDegree.get(from).size() != outDegreeSize.get(from)) 
				throw new IllegalArgumentException("Outdegree not matched: " + outDegree.get(from).size() + ", " + outDegreeSize.get(from));
		}
		for (String to : inDegree.keySet()) {
			//System.out.println(to + " has " + inDegree.get(to).size() + " indegree.");
			if (inDegree.get(to).size() != inDegreeSize.get(to))
				throw new IllegalArgumentException("Indegree not matched: " + inDegree.get(to).size() + ", " + inDegreeSize.get(to));
		}
	}
	
	void printGraph() {
		System.out.println("There are " + numEdge() + " edges.");
		System.out.println("Outdegree: ");
		int count = 0;
		for (String from : outDegree.keySet()) {
			for (String to : outDegree.get(from)) {
				System.out.println(from + "->" + to);
				count ++;
			}
			System.out.println(from + " has " + outDegree.get(from).size() + " outdegree.");
		}
		System.out.println("There are " + count + "edges.");
		System.out.println("Indegree: ");
		count = 0;
		for (String to : inDegree.keySet()) {
			for (String from : inDegree.get(to)) {
				System.out.println(to + "<-" + from);
				count ++;
			}
			System.out.println(to + " has " + inDegree.get(to).size() + " indegree.");
		}
		System.out.println("There are " + count + " edges.");
	}
	
	void addDegree(Map<String, Set<String>> degree, String addTo, String toAdd, Map<String, Integer> degreeSize) {
		if (degree.containsKey(addTo)){
			degree.get(addTo).add(toAdd);
			
		}
		else {
			Set<String> toAddSet = new HashSet<String>();
			toAddSet.add(toAdd);
			degree.put(addTo, toAddSet);
			degreeSize.put(addTo, 0);
		}
		degreeSize.put(addTo, degreeSize.get(addTo) + 1);
	}
	
	double pageRankof(String vertexName) {
		double pageRank = 0;
		return pageRank;
	}
	
	int outDegreeOf(String vertexName) {
		return outDegree.get(vertexName).size();
	}
	
	int inDegreeOf(String vertexName) {
		return inDegree.get(vertexName).size();
	}
	
	int numEdge() {
		return numEdge;
	}
	
	String[] topKPageRank(int k) {
		List<String> kPageRank = new LinkedList<String>();
		for(Entry<String, Double> entry : entriesSortedByValues(distributionVector).subList(0, k)) {
			kPageRank.add(entry.getKey());
		}
		return kPageRank.toArray(new String[kPageRank.size()]);
	}
	
	String[] topKInDegree(int k) {
		List<String> kInDegree = new LinkedList<String>();  
		for(Entry<String, Integer> entry : entriesSortedByValues(inDegreeSize).subList(0, k)) {
			kInDegree.add(entry.getKey());
			//System.out.println(entry.getKey() + " has " + entry.getValue() + " indegree.");
		}
		return kInDegree.toArray(new String[kInDegree.size()]);
	}
	
	String[] topKOutDegree(int k) {
		List<String> kOutDegree = new LinkedList<String>();
		for(Entry<String, Integer> entry : entriesSortedByValues(outDegreeSize).subList(0, k)) {
			kOutDegree.add(entry.getKey());
		}
		return kOutDegree.toArray(new String[kOutDegree.size()]);
	}
	
	<V> List<Entry<String, V>> entriesSortedByValues(Map<String, V> map) {
		List<Entry<String, V>> sortedEntries = new LinkedList<Entry<String, V>>(map.entrySet());
		if(sortedEntries.get(0).getValue() instanceof Integer) {
			Collections.sort(sortedEntries, new Comparator<Entry<String, V>>() {
				public int compare(Entry<String, V> e1, Entry<String, V> e2) {
					return ((Integer) e2.getValue()).compareTo((Integer) e1.getValue());
				}
			});
		}
		else if(sortedEntries.get(0).getValue() instanceof Double) {
			Collections.sort(sortedEntries, new Comparator<Entry<String, V>>() {
				public int compare(Entry<String, V> e1, Entry<String, V> e2) {
					return ((Double) e2.getValue()).compareTo((Double) e1.getValue());
				}
			});
		}
		return sortedEntries;
	}
}
