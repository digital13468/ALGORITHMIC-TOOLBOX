import java.io.IOException;
import java.util.Arrays;
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
public class NearDuplicates {
	double falsePositives;
	double falsePositiveRate;
	int numFinalDuplicates;
	double notNearDuplicates;
	MinHash mh;
	
	void clearStatistics() {
		falsePositives = 0;
		falsePositiveRate = 0;
		numFinalDuplicates = 0;
		notNearDuplicates = 0;
	}
	
	void constructMinHashMatrix(String folderName, int numPermutations) throws IOException {
		if (mh == null) {
			mh = new MinHash(folderName, numPermutations);
		//System.out.println("term num: " + mh.numTerms);
			mh.computeMinHashMatrix();
		}
		
	}
	
	Set<String> nearDuplicateDetector(String folderName, int numPermutations, int numBands, double s, String docName) throws IOException {
		constructMinHashMatrix(folderName, numPermutations);
		//System.out.println(mh);

		LSH lsh = new LSH(mh.minHashMatrix, mh.fileNames, numBands);
		
		lsh.localitySensitiveHashing();
		
		Set<String> nearDuplicates = lsh.nearDuplicatesOf(docName);
		System.out.println("Documents similar to " + docName + " are the following:");
		for (String duplicate : nearDuplicates)
			System.out.println(duplicate);
		notNearDuplicates = mh.fileNames.length - 1 - nearDuplicates.size();
		System.out.println("The false positives are the following:");
		Set<String> lessThanThresholdDocs = getLessThanThresholdDocs(mh.minHashMatrix, docName, mh.fileNames, nearDuplicates, s, mh.minHashMap);
		falsePositives = lessThanThresholdDocs.size();
		falsePositiveRate = falsePositives / nearDuplicates.size();
		nearDuplicates.removeAll(lessThanThresholdDocs);
		numFinalDuplicates = nearDuplicates.size();
		
		/*for (String duplicate : lessThanThresholdDocs) {
			System.out.println(docName + " and " + duplicate + " have exact similarity (" + exactJaccard(docName, duplicate, mh.binaryTermFrequencyDoc) + ", " + mh.exactJaccard(docName, duplicate) + ")");
			if (exactJaccard(docName, duplicate, mh.binaryTermFrequencyDoc) != mh.exactJaccard(docName, duplicate) || approximateJaccard(docName, duplicate, numPermutations, mh.minHashMatrix, mh.fileNames) != 
				approximateJaccard(docName, duplicate, numPermutations, mh.minHashMap, mh.fileNames) || mh.approximateJaccard(docName, duplicate) != approximateJaccard(docName, duplicate, numPermutations, mh.minHashMap, mh.fileNames)) {
				
				System.out.println("Look at that: ");
				
				System.out.println(docName + " and " + duplicate + " have estimated similarity (" + approximateJaccard(docName, duplicate, numPermutations, mh.minHashMap, mh.fileNames) + ", " + 
						approximateJaccard(docName, duplicate, numPermutations, mh.minHashMatrix, mh.fileNames) + ", " + mh.approximateJaccard(docName, duplicate) + ")");
				
			}
		}*/
		return nearDuplicates;
	}
	
	Set<String> getLessThanThresholdDocs(int[][] minHashMatrix, String docName, String[] docNames, Set<String> nearDuplicates, double threshold, Map<String, Map<Integer, Integer>>  minHashMap) throws IOException {
		Set<String> lessThanThresholdDocs = new HashSet<String>();
		int docInd = Arrays.asList(docNames).indexOf(docName);
		int[] docMinHashSig = MinHash.getColumn(minHashMatrix, docInd);
		//for (String doc : docNames)
		//	System.out.println(doc);
		//System.out.println(docInd);
		for (String nearDuplicate : nearDuplicates) {
			int nearDuplicateInd = Arrays.asList(docNames).indexOf(nearDuplicate);
			//System.out.println(nearDuplicateInd);
			int[] nearDuplicateMinHashSig = MinHash.getColumn(minHashMatrix, nearDuplicateInd);
			double estimatedJaccard = 0;
			//double hashMapEstimated = 0;
			for (int i = 0; i < minHashMatrix.length; i ++) {
				//System.out.print("(" + docMinHashSig[i] + "," + nearDuplicateMinHashSig[i] + ")   ");
				if (docMinHashSig[i] == nearDuplicateMinHashSig[i]) {
					estimatedJaccard ++;
				}/*
				if (minHashMap.get(docName).get(i) != docMinHashSig[i] || minHashMap.get(nearDuplicate).get(i) != nearDuplicateMinHashSig[i])
					System.out.println("[" + minHashMap.get(docName).get(i) + ", " + docMinHashSig[i] + ", " + minHashMap.get(nearDuplicate).get(i) + ", " + nearDuplicateMinHashSig[i] + "]");
				if (minHashMap.get(docName).get(i).equals(minHashMap.get(nearDuplicate).get(i)))
					hashMapEstimated ++;
				if (hashMapEstimated != estimatedJaccard){
					System.out.println("Oops! " + hashMapEstimated + ", " + estimatedJaccard);
					System.out.println("[" + minHashMap.get(docName).get(i) + ", " + minHashMap.get(nearDuplicate).get(i) + "]");
					System.out.print("(" + docMinHashSig[i] + "," + nearDuplicateMinHashSig[i] + ")   ");
				}*/
			}
			//System.out.println();
			if (estimatedJaccard / minHashMatrix.length < threshold) {
				lessThanThresholdDocs.add(nearDuplicate);
				//System.out.println(nearDuplicate + " is a false positive (" + (estimatedJaccard / minHashMatrix.length) + ", " + approximateJaccard(docName, nearDuplicate, minHashMatrix.length, minHashMatrix, docNames) + ", " + 
				//		approximateJaccard(docName, nearDuplicate, minHashMatrix.length, minHashMap, docNames) + ")");
				System.out.println(nearDuplicate);
			}
		}
		return lessThanThresholdDocs;
	}
	/*
	double exactJaccard(String file1, String file2, Map<String, Set<Integer>> binaryTermFrequencyDoc) throws IOException {
			
	
			double intersection = 0;
	
			Set<Integer> file1Terms = binaryTermFrequencyDoc.get(file1);
			Set<Integer> file2Terms = binaryTermFrequencyDoc.get(file2);
			for (Integer termId : file1Terms) {
				//exactJaccardComparisons ++;
				if (file2Terms.contains(termId))
					intersection ++;
				
			}
			double union = file1Terms.size() + file2Terms.size() - intersection;
			return intersection / union;
			
		}
	
	double  approximateJaccard(String file1, String file2, int numPermutations, Map<String, Map<Integer, Integer>>  minHashMap, String[] docNames) throws IOException {

		double approximateJaccard = 0;

		for (int i = 0; i < numPermutations; i ++){
			//System.out.print(minHashMap.get(file1).get(i) + "," +minHashMap.get(file2).get(i));
			if (minHashMap.get(file1).get(i).equals(minHashMap.get(file2).get(i)))
				approximateJaccard ++;
			//if (minHashMap.get(file1).get(i) != minHashMatrix[i][Arrays.asList(docNames).indexOf(file1)] || minHashMap.get(file2).get(i) != minHashMatrix[i][Arrays.asList(docNames).indexOf(file2)]) {
			//	System.exit(1);
			//}
			//System.out.print("(" + minHashMap.get(file1).get(i) + "," + minHashMap.get(file2).get(i)  + ")   ");
			//}
				
		}
		//System.out.println();
		//System.out.println(approximateJaccard + ", " + numPermutations);
		approximateJaccard = approximateJaccard / numPermutations;
		
		return approximateJaccard;
	}*/

}
