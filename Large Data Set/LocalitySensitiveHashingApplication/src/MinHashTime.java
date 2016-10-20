import java.io.IOException;


public class MinHashTime {
	double timeForExact;
	double timeForEstimated;
	double timeForMinHashMatrix;
	double timeForMatrixPlusEstimating;
	int exactJaccardComparisons;
	int estimatedJaccardComparisons;
	MinHashTime() {

	} 
	
	void timer(String folderName, int numPermutations) throws IOException {
		MinHash mh = new MinHash(folderName, numPermutations);
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < mh.numDocuments - 1; i ++)
			for (int j = i + 1; j < mh.numDocuments; j ++) {
				mh.exactJaccard(mh.fileNames[i], mh.fileNames[j]);
				//System.out.println(mh.fileNames[i] + " and " + mh.fileNames[j] + ": " + exactJaccard);
			}
		timeForExact = ((System.currentTimeMillis() - startTime) / 1000F);
		//System.out.println("Computing the exact Jaccard Similarity for every pair of files in the folder took " + timeForExact + " seconds.");
		//ystem.out.println("Comparison times are " + mh.exactJaccardComparisons + " for exact Jaccard similarities.");
		exactJaccardComparisons = mh.exactJaccardComparisons;
		mh.computeMinHashMatrix();
		timeForMinHashMatrix = mh.minHashMatrixComputationTime;
		//System.out.println("Computing the MinHashMatrix took " + mh.minHashMatrixComputationTime + " seconds.");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < mh.numDocuments - 1; i ++)
			for (int j = i + 1; j < mh.numDocuments; j ++) {
				//int file1MinHash = MinHash.getMin(getDocHash(mh.minHashMatrix, i));
				//int file2MinHash = MinHash.getMin(getDocHash(mh.minHashMatrix, j));
				mh.approximateJaccard(mh.fileNames[i], mh.fileNames[j]);
				//if (file1MinHash == file2MinHash)
				//	for (int k = 0; k < numPermutations; k ++) {
				//		if (mh.minHashMatrix[i][k] == file1MinHash && mh.minHashMatrix[j][k] == file2MinHash){
				//			estimatedJaccard ++;
				//		}
				//	}
				//estimatedJaccard = estimatedJaccard / numPermutations;
				//System.out.println(mh.fileNames[i] + " and " + mh.fileNames[j] + ": " + estimatedJaccard);
			}
		timeForEstimated = ((System.currentTimeMillis() - startTime) / 1000F);
		//System.out.println("Estimating the Jaccard Similarity for every pair of files in the folder took " + timeForEstimated + " seconds.");
		timeForMatrixPlusEstimating = timeForMinHashMatrix + timeForEstimated;
		//System.out.println("Buidling MinHash Matrix and estimating similarity for every pair of files in the folder took " + timeForMatrixPlusEstimating + " seconds.");
		//System.out.println("Comparison times are " + mh.estimatedJaccardComparisons + " for estimated Jaccard similarities.");
		estimatedJaccardComparisons = mh.estimatedJaccardComparisons;
	}
	/*
	public static int[] getDocHash(int[][] minHashMatrix, int docInd) {
		int[] docHash = new int[minHashMatrix.length];
		for (int i = 0; i < minHashMatrix.length; i ++) {
			docHash[i] = minHashMatrix[i][docInd];
		}
		return docHash;
	}*/
}
