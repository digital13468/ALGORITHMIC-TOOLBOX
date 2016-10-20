import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
public class MinHash {
	String folderName;
	int numPermutations;
	int[][] minHashMatrix;
	int numTerms;
	int numDocuments;
	String[] fileNames;
	Map<String, Integer> fileNameHashTable;
	double minHashMatrixComputationTime;
	Map<String, Integer> binaryTermFrequencyAllDocs;
	Map<String, Set<Integer>> binaryTermFrequencyDoc;
	Map<Integer, Integer> permutationFunctions;
	int p;
	int exactJaccardComparisons;
	int estimatedJaccardComparisons;
	Map<String, Map<Integer, Integer>> minHashMap;
	
	MinHash(String folder, int numPermutations) throws IOException {
		if (numPermutations < 1) {
			throw new IllegalArgumentException("Permutation number is " + numPermutations);
		}
		this.folderName = folder;
		this.numPermutations = numPermutations;
		this.fileNames = allDocs();
		this.numDocuments = fileNames.length;
		//this.minHashMatrix = new int[numPermutations][numDocuments];
		this.numTerms = 0;
		buildBinaryTermFrequencyAllDocs();
		PermutationFunctions pf = new PermutationFunctions(numPermutations, numTerms);
		pf.generatePermutationFunctions();
		this.permutationFunctions = pf.permutationFunctions;
		this.p = pf.p;
		
	}
	
	void buildBinaryTermFrequencyAllDocs() throws IOException {
		binaryTermFrequencyAllDocs = new HashMap<String, Integer>();
		binaryTermFrequencyDoc = new HashMap<String, Set<Integer>>();
		for (int i = 0; i < numDocuments; i ++) {
			Set<Integer> binaryTermFrequency = new HashSet<Integer>();
			//binaryTermFrequencyDoc.put(folderName + fileNames[i], binaryTermFrequency);
			BufferedReader br = new BufferedReader(new FileReader(new File(fileNames[i])));
			String line;
			while ((line = br.readLine()) != null) {
				String words[] = wordsProcessor(line);
				for (String word : words) {
					
					if (word != null && binaryTermFrequencyAllDocs.get(word) == null){
						binaryTermFrequencyAllDocs.put(word, numTerms);
						numTerms ++;
						//System.out.println(word);
					}
					if (word != null) {
						binaryTermFrequency.add(binaryTermFrequencyAllDocs.get(word));
						//binaryTermFrequencyDoc.put(folderName + fileNames[i], binaryTermFrequency);
						//System.out.println(binaryTermFrequencyAllDocs.get(word));
					}
				}
			}
			//System.out.println("size: " + binaryTermFrequency.size());
			//System.out.println(folderName + fileNames[i]);
			binaryTermFrequencyDoc.put(fileNames[i], binaryTermFrequency);
			br.close();
		}
	}
	
	String[] wordsProcessor(String line) {
		line = line.toLowerCase();
		String[] words = line.split("[ .,:;'\n\t]+");
		
		for (int i = 0; i < words.length; i ++) {
			
			if (words[i].equals("the") || words[i].length() < 3) {
				words[i] = null;
				
				if (words[i] != null) {
					throw new IllegalArgumentException(words[i]);
					
				}
			}
		}
		return words;
	}
	
	void printJaccardSimilarities() throws IOException {
		int total = 0;
		for (int i = 0; i < fileNames.length; i ++) {
			for (int k = 0; k < numPermutations; k ++)
				if (minHashMap.get(fileNames[i]).get(k) != minHashMatrix[k][i]) {
					System.out.println("difference: " + minHashMap.get(fileNames[i]).get(k) + ", " +  minHashMatrix[k][i]);
					//System.exit(1);
				}
			for (int j = 0; j < fileNames.length; j ++) {
				
					
				total ++;
				double exactJaccard = exactJaccard(fileNames[i], fileNames[j]);
				System.out.println(fileNames[i] + " and " + fileNames[j] + ": " + exactJaccard);
				System.out.println(fileNames[i] + " and " + fileNames[j] + ": " + approximateJaccard(fileNames[i], fileNames[j]));
			}
			System.out.println("======================================================" + total);
		}
	}/*
	Set<Integer> buildBinaryTermFrequencyDocc (File doc) throws IOException {
		Set<Integer> binaryTermFrequencyDoc= new LinkedHashSet<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(doc));
		//Map<String, HashSet<String[]>> a = new HashMap<String, HashSet<String[]>>();
		String line;
		while ((line = br.readLine()) != null) {
			String words[] = wordsProcessor(line);
			for (String word : words) {
				if (word != null) {
					//String pair[] = {word, binaryTermFrequencyAllDocs.get(word).toString()};
					binaryTermFrequencyDoc.add(binaryTermFrequencyAllDocs.get(word));
				}
			}
		}
		br.close();
		return binaryTermFrequencyDoc;
	}*/
	void computeMinHashMatrix() throws IOException {
		minHashMap = new HashMap<String, Map<Integer, Integer>>();
		this.minHashMatrix = new int[numPermutations][numDocuments];
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < numDocuments; i ++) {
			int minHashSig[] = minHashSig(fileNames[i]);
			//System.out.println(i);
			for (int j = 0; j < minHashSig.length; j ++) {
				minHashMatrix[j][i] = minHashSig[j];
				
			}
		}
		minHashMatrixComputationTime = (System.currentTimeMillis() - startTime) / 1000F;
	}
	
	String[] allDocs() {
		
		File folderFiles = new File(folderName);
		File[] listOfFiles = folderFiles.listFiles();
		fileNameHashTable = new HashMap<String, Integer>();
		String[] fileNames = new String[listOfFiles.length];
		int i = 0;
		for (File file : listOfFiles){ 
			fileNames[i] = folderName + file.getName();
			fileNameHashTable.put(folderName + file.getName(), i);
			i++;
			
		}
		//if (fileNames.length != fileNameHashTable.size() || fileNameHashTable.size() != i)
		//		throw new IllegalArgumentException("???");
		return fileNames;
	}
	
	double exactJaccard(String file1, String file2) throws IOException {
		
		//BufferedReader fr1 = new BufferedReader(new FileReader(new File(folderName + file1)));
		//BufferedReader fr2 = new BufferedReader(new FileReader(new File(folderName + file2)));
		double intersection = 0;
		
		//String file1Line;
		//String file2Line;
		/*while ((file1Line = fr1.readLine()) != null) {
			file2Line = fr2.readLine();
			if (file1Line == "1"){
				union ++;
				if (file2Line == "1"){
					intersection ++;
					
				}
			}
			else{
				if (file2Line == "1"){
					
					union ++;
				}
			}
		}
		fr1.close();
		fr2.close();*/
		Set<Integer> file1Terms = binaryTermFrequencyDoc.get(file1);
		Set<Integer> file2Terms = binaryTermFrequencyDoc.get(file2);
		for (Integer termId : file1Terms) {
			exactJaccardComparisons ++;
			if (file2Terms.contains(termId))
				intersection ++;
			
		}
		double union = file1Terms.size() + file2Terms.size() - intersection;
		return intersection / union;
		
	}
	
	int[] minHashSig(String fileName) throws IOException {
		Map<Integer, Integer> minHashSigMap = new HashMap<Integer, Integer>();
		int minHashSig[] = new int[numPermutations];
		for (int i = 0; i < numPermutations; i ++) {
			//BufferedReader fr = new BufferedReader(new FileReader(new File(folderName + fileName)));
			//String line;
			minHashSig[i] = Integer.MAX_VALUE;
			//while ((line = fr.readLine()) != null) {
			//	int sig = hash(line, i);
			//	if (sig < minHashSig[i])
			//		minHashSig[i] = sig;
			//}
			//fr.close();
			for(Integer termId : binaryTermFrequencyDoc.get(fileName)) {
				int sig = minHashTerm(termId, i);
				if (sig < 0)
					throw new IllegalArgumentException("Hash Values is negative: " + sig);
				if (sig < minHashSig[i]) {
					minHashSig[i] = sig;
					minHashSigMap.put(i, sig);
				}
			}
			//System.out.println(minHashSig[i]);
		}
		minHashMap.put(fileName, minHashSigMap);
		return minHashSig;
	}
	
	int minHashTerm(Integer item, int permutationInd) {
		//if (((item * (Integer)(permutationFunctions.keySet().toArray()[permutationInd]) + (Integer)(permutationFunctions.values().toArray()[permutationInd])) % p) < 0) {
		//	System.out.println(item + "=>" +(item * (Integer)(permutationFunctions.keySet().toArray()[permutationInd]) + (Integer)(permutationFunctions.values().toArray()[permutationInd])) % p);
		//	System.out.println(item + ", " + permutationFunctions.keySet().toArray()[permutationInd] + ", " + (Integer)(permutationFunctions.values().toArray()[permutationInd]) + ", " + p);
		//}
		return ((item * (Integer)(permutationFunctions.keySet().toArray()[permutationInd]) + (Integer)(permutationFunctions.values().toArray()[permutationInd])) & 0x7fffffff) % p;
	}
	
	double approximateJaccard(String file1, String file2) throws IOException {
		//int[] file1MinHashSig = getColumn(minHashMatrix, Arrays.asList(fileNames).indexOf(file1));
		//int[] file2MinHashSig = getColumn(minHashMatrix, Arrays.asList(fileNames).indexOf(file2));
		//if (Arrays.asList(fileNames).indexOf(file1) != fileNameHashTable.get(file1) || Arrays.asList(fileNames).indexOf(file2) != fileNameHashTable.get(file2))
		//	throw new IllegalArgumentException("???");
		//int[] file1MinHashSig = getColumn(minHashMatrix, fileNameHashTable.get(file1));
		//int[] file2MinHashSig = getColumn(minHashMatrix, fileNameHashTable.get(file2));
		//if (minHash > file2MinHash)
		//	minHash = file2MinHash;
		double approximateJaccard = 0;
		//if (file1MinHash == file2MinHash){ 
		for (int i = 0; i < numPermutations; i ++){
			estimatedJaccardComparisons ++;
			if (minHashMap.get(file1).get(i).equals(minHashMap.get(file2).get(i)))
				approximateJaccard ++;
			//if (file1MinHashSig[i] != minHashMap.get(file1).get(i) || file2MinHashSig[i] != minHashMap.get(file2).get(i))
			//	throw new IllegalArgumentException("???");
				
		}
		approximateJaccard = approximateJaccard / numPermutations;
		//}
		return approximateJaccard;
	}
	
	static int[] getColumn(int[][] matrix, int columnInd) {
		int[] column = new int[matrix.length];
		for (int i = 0; i < matrix.length; i ++)
			column[i] = matrix[i][columnInd];
		return column;
	}
	
	public static int getMin(int[] array) {
		int min = Integer.MAX_VALUE;
		for (int i : array) {
			if (i < min)
				min = i;
		}
		return min;
	}
	
	int[][] minHashMatrix() {
		return minHashMatrix;
	}
	
	int numTerms() {
		return numTerms;
	}
	
	int numPermutations() {
		return numPermutations;
	}
}
