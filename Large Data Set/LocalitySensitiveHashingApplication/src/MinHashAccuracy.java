import java.io.IOException;


public class MinHashAccuracy {
	
	
	MinHashAccuracy() {
		
	}
	
	static int accuracy(String folderName, int numPermutations, double error) throws IOException {
		int differMoreThanError = 0;
		MinHash mh = new MinHash(folderName, numPermutations);
		mh.computeMinHashMatrix();
		//for (String i : mh.fileNames)
		//	System.out.println(mh.folderName);
		for (int i = 0; i < mh.fileNames.length - 1; i ++) {
			for (int j = i + 1; j < mh.fileNames.length; j ++) {
				double exactJaccard = mh.exactJaccard(mh.fileNames[i], mh.fileNames[j]);
				double estimatedJaccard = mh.approximateJaccard(mh.fileNames[i], mh.fileNames[j]);
				//System.out.println("The exact and estimated Jaccard similarities between " + mh.fileNames[i] + " and " + mh.fileNames[j] + ": " + exactJaccard + ", " + estimatedJaccard + ", " + Math.abs(exactJaccard - estimatedJaccard));
				if (Math.abs(exactJaccard - estimatedJaccard) > error){
					differMoreThanError ++;
					
				}
				//System.out.println("For " + numPermutations + " permuations and " + error + " as the threshold, the number of pairs: " + differMoreThanError);
			}
		}
		return differMoreThanError;
	}
}
