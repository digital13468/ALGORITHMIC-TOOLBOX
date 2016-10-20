import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
public class LSHApplication {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String folderPath;
		
		int numPermutations;
		
		//int numBands;
		double similarityThreshold;
		int rounds;
		//System.out.println("permutation functions: (" + mh.numTerms + " , " + mh.p + ")");
		//for (Integer a : mh.permutationFunctions.keySet())
		//	System.out.println(a + ", " + mh.permutationFunctions.get(a));
		//mh.buildBinaryTermFrequencyAllDocs();
		/*for (String key : mh.binaryTermFrequencyAllDocs.keySet())
			System.out.println(key + ", " + mh.binaryTermFrequencyAllDocs.get(key));
		System.out.println(mh.numTerms);
		System.out.println(mh.numDocuments);*//*
		System.out.println("=================== Check word ids ===================");
		Set<String[]> btfd = mh.buildBinaryTermFrequencyDoc(new File(testFolderPath + "baseball0.txt"));
		for (String[] pair : btfd) {
			System.out.println(pair[0] + ", " + pair[1]);
		}
		System.out.println("size: " + btfd.size());*//*
		System.out.println("=================== Check word ids ===================");
		Set<Integer> btfdd = mh.buildBinaryTermFrequencyDocc(new File(testFolderPath + "space-999.txt"));
		for (Integer pair : btfdd) {
			System.out.println(pair);
		}
		System.out.println("size: " + btfdd.size());*/
		//System.out.println("=================== Check word ids ===================");
		//Set<Integer> btfd = mh.binaryTermFrequencyDoc.get(testFolderPath + testFile);
		//System.out.println(testFolderPath + "baseball0.txt");
		//System.out.println(btfd.size());
		//for (Integer pair : btfd) {
		//	System.out.println(pair);
		//}
		//System.out.println("=================== Permutation Functions ===================");
		//for (Integer a : mh.permutationFunctions.keySet())
	//		System.out.println("a: " + a + ", b: " + mh.permutationFunctions.get(a));
		
		//for (int i = 0; i < mh.numPermutations; i ++){
		
		
		
		/*
		for (int i = 0; i < mh.numPermutations; i ++){
			for (int j = 0; j < mh.numDocuments; j ++)
				System.out.print(mh.minHashMatrix[i][j] + "\t");
			System.out.println();
		}*/
		/**
		 * MinHashAccuracy
		 *//*
		int[] permutations = {400, 600, 800};
		double[] epsilons = {0.04, 0.07, 0.09};
		rounds = 10;
		double[] avgNumDiffer = new double[permutations.length * epsilons.length];
		folderPath = "data/space/";
		long startTime = System.currentTimeMillis();	
		for (int j = 0; j < permutations.length; j ++) {
			for (int k = 0; k < epsilons.length; k ++) {
				double numDiffer = 0;
				for (int i = 0; i < rounds; i ++) {
					//System.out.println("============================= round " + i + " =============================");
					numDiffer = numDiffer + MinHashAccuracy.accuracy(folderPath, permutations[j], epsilons[k]);
				}
				avgNumDiffer[j * epsilons.length + k] = numDiffer / rounds;
				System.out.println("For " + permutations[j] + ", the average number of pairs for which approximate and exact similarities differ by more than " + epsilons[k] + ":" + avgNumDiffer[j * epsilons.length + k]);
				System.out.println();
			}
			
		}
		System.out.println("Comparing the exact Jaccard Similarity for every pair of files in the folder took " + ((System.currentTimeMillis() - startTime) / 1000F) + " seconds.");*/	
		/**
		 * MinHashTime
		 *//*
		long startTime = System.currentTimeMillis();	
		int[] permutations = {100, 350, 600};
		folderPath = "data/space/";
		rounds = 100;
		
		for (int j = 0; j < permutations.length; j ++)  {
			double[] time = {0, 0, 0, 0, 0, 0};
			System.out.println("=========================== " + permutations[j] + " permutations ===========================");
			for (int i = 0; i < rounds; i ++){
				MinHashTime mht = new MinHashTime();
				mht.timer(folderPath, permutations[j]);
				time[0] = time[0] + mht.timeForExact;
				time[1] = time[1] + mht.timeForMinHashMatrix;
				time[2] = time[2] + mht.timeForEstimated;
				time[3] = time[3] + mht.timeForMatrixPlusEstimating;
				time[4] = time[4] + mht.exactJaccardComparisons;
				time[5] = time[5] + mht.estimatedJaccardComparisons;
			}
			for (int i = 0; i < time.length; i ++)
				time[i] = time[i] / rounds;
			//for (double t : time) {
			//	System.out.println(t);
			//}
			System.out.println("Running time on computing exact similarities:" + time[0]);
			System.out.println("Running time on building the MinHash matrix: " + time[1]);
			System.out.println("Running time on estimating similarities: " + time[2]);
			System.out.println("Running time on computing both the matrix and estimated similarities: " + time[3]);
			System.out.println("Computing exact similarities took " + time[4] + " comparisons");
			System.out.println("Computing estimated similarities tool " + time[5] + " comparisons");
		}
		System.out.println("Running MinHashTime for " + rounds + " rounds took " + ((System.currentTimeMillis() - startTime) / 1000F) + " seconds.");
		*/
		/*
		for (int i = 0; i < lsh.hashTables.size(); i ++) {
			for (int j = 0; j < lsh.hashTables.get(i).size(); j ++) {
				System.out.println("band : " + i + ", " + j + " bucket");
				for (int k = 0; k < lsh.hashTables.get(i).get(j).size(); k ++){
					
					System.out.println(lsh.hashTables.get(i).get(j).get(k));
				}
				
			}
			
		}*/
		
		/**
		 * LSH
		 */
		folderPath = "data/F16PA2/";
		Random r = new Random();
		numPermutations = 1000;
		int[] bands = {100, 20, 40};
		similarityThreshold = 0.9;
		double[] falsePositives = new double[bands.length];
		double[] numDuplicates = new double[bands.length];
		double[] finalFalsePositives = new double [bands.length];
		double[] falseNegatives = new double [bands.length];
		double[] falsePositiveRates = new double [bands.length];
		double[] falseNegativeRates = new double [bands.length];
		rounds = 700;
		File folderFiles = new File(folderPath);
		File[] listOfFiles = folderFiles.listFiles();
		Map<Integer, String> fileNames = new HashMap<Integer, String>();
		long startTime = System.currentTimeMillis();
		int fileID = 0;
		for (File file : listOfFiles){ 
			//fileNames[i] = folderName + file.getName();
			fileNames.put(fileID, folderPath + file.getName());
			fileID ++;
			
		}
		NearDuplicates nd = new NearDuplicates();
		for (int j = 0; j < bands.length; j ++) {
			System.out.println("Bands: " + bands[j] + ", Permutations: " + numPermutations + ", Similarity: " + similarityThreshold);
			System.out.println(Math.pow((1.0 / bands[j]), (1.0 / (numPermutations / bands[j]))));
			falsePositives[j] = 0;
			numDuplicates[j] = 0;
			finalFalsePositives[j] = 0;		
			falseNegatives[j] = 0;
			falsePositiveRates[j] = 0;
			falseNegativeRates[j] = 0;
			
			for (int i = 0; i < rounds; i ++) { 
				int fileInd = r.nextInt(fileNames.size());
				//Set<String> nearDuplicates = lsh.nearDuplicatesOf(mh.fileNames[fileInd]);
				System.out.println("File No. " + (i + 1));
				nd.clearStatistics();
				Set<String> duplicates = nd.nearDuplicateDetector(folderPath, numPermutations, bands[j], similarityThreshold, fileNames.get(fileInd));
				numDuplicates[j] = numDuplicates[j] + nd.numFinalDuplicates;
				falsePositives[j] = falsePositives[j] + nd.falsePositives;
				falseNegatives[j] = falseNegatives[j] + (7 - nd.numFinalDuplicates);
				falsePositiveRates[j] = falsePositiveRates[j] + nd.falsePositiveRate;
				falseNegativeRates[j] = falseNegativeRates[j] + ((7 - nd.numFinalDuplicates) / nd.notNearDuplicates);
				System.out.println("Taking out the above false positives, the final near-duplicate list is the following:");
				for (String duplicate : duplicates) {
					
					System.out.println(duplicate);
					if (!duplicate.contains(fileNames.get(fileInd).subSequence(0, fileNames.get(fileInd).indexOf('t')))) {
						System.out.println("not similar documents");
						finalFalsePositives[j] ++;
					}
				}
				System.out.println();
			}
			falsePositives[j] = falsePositives[j] / rounds;
			numDuplicates[j] = numDuplicates[j] / rounds;
			falseNegatives[j] = falseNegatives[j] / rounds;
			finalFalsePositives[j] = finalFalsePositives[j] / rounds;
			falsePositiveRates[j] = falsePositiveRates[j] / rounds;
			falseNegativeRates[j] = falseNegativeRates[j] / rounds;
			System.out.println();
		}
		System.out.println("false positives:");
		for (double falsePositive : falsePositives)
			System.out.print(falsePositive + "\t");
		System.out.println("\nfalse positive rates:");
		for (double falsePositiveRate : falsePositiveRates)
			System.out.print(falsePositiveRate + "\t");
		System.out.println("\nfalse negatives:");
		for (double falseNegative : falseNegatives)
			System.out.print(falseNegative + "\t");
		System.out.println("\nfalse negative rates:");
		for (double falseNegativeRate : falseNegativeRates)
			System.out.print(falseNegativeRate + "\t");
		System.out.println("\nnumber of duplicates:");
		for (double numDuplicate : numDuplicates)
			System.out.print(numDuplicate + "\t");
		System.out.println("\nfinal false positives:");
		for (double finalFalsePositive : finalFalsePositives)
			System.out.print(finalFalsePositive + "\t");
		System.out.println();
		System.out.println("Running LSH took " + ((System.currentTimeMillis() - startTime) / 1000F) + " seconds.");
	}
	

}
