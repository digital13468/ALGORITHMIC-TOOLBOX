import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
class FalsePositives {
	File file1;
	File file2;
	int[] bitsPerElement;
	int largestSize;
	int[] setSize;
	double[][][] detFullRatio;
	double[][][] ranFullRatio;
	double[][][] detBuildTime;
	double[][][] ranBuildTime;
	double[][][] detQueryTime;
	double[][][] ranQueryTime;
	
	FalsePositives (String file1Path, String file2Path, int[] bitsPerElement) throws Exception {
		this.file1 = new File(file1Path);
		this.file2 = new File(file2Path);
		this.bitsPerElement = bitsPerElement;
		this.largestSize = countSetSize();
		//setSize = new int [largestSize / 50000];		
		//for (int i = setSize.length - 1; i > -1; i --) {
		//	setSize[i] = 50000 * (i + 1);
		//}
		setSize = new int[] {235886, 200000, 100000, 50000};
		//setSize = new int[] {500000};
		//this.largestSize = 500000;
		detFullRatio = new double[setSize.length][bitsPerElement.length][2];
		ranFullRatio = new double[setSize.length][bitsPerElement.length][2];
		detBuildTime = new double[setSize.length][bitsPerElement.length][2];
		ranBuildTime = new double[setSize.length][bitsPerElement.length][2];
		detQueryTime = new double[setSize.length][bitsPerElement.length][2];
		ranQueryTime = new double[setSize.length][bitsPerElement.length][2];
	}
	
	int countSetSize () throws IOException {
		int setSize = 0;
		BufferedReader br = new BufferedReader (new FileReader(file1));
		String line = br.readLine();
		while (line != null) {
			setSize += 1;
			line = br.readLine();
		}
		br.close();
		return setSize;
	}
	
	float[] readFile1ToBuildFilter (int setSize, int bitsPerElement, int stringNum) throws IOException {
		BloomFilterDet BFDet = new BloomFilterDet(setSize, bitsPerElement);
		BloomFilterRan BFRan = new BloomFilterRan(setSize, bitsPerElement);
		BufferedReader br = new BufferedReader (new FileReader(file1));
		System.out.println("Building filters...");	
		String line = null;
		if (stringNum == largestSize) {
			
			while ((line = br.readLine()) != null) {
				
				BFDet.add(line);
				BFRan.add(line);
			}
			
			
		}
		else {
			
			List<String> lines = new ArrayList<String>();
			System.out.println("Start reading file1...");
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			System.out.println("Finish reading file1");

			Random r = new Random();
			for (int i = 0; i < stringNum; i ++) {
				String randomString = lines.get(r.nextInt(largestSize));
				//System.out.println("adding string \"" + randomString + "\"......");
				//System.out.println("with deterministic hash functions");
				BFDet.add(randomString);
				//System.out.println("with random hash functions");
				BFRan.add(randomString);
			}
		}
		br.close();
		
		int setSizeIndex = -1;
		for (int i = 0; i < this.setSize.length; i ++){
			if (this.setSize[i] == stringNum)
				setSizeIndex = i;
		}
		int bitsPerElementIndex = -1;
		for (int i = 0; i < this.bitsPerElement.length; i ++){
			if (this.bitsPerElement[i] == bitsPerElement)
				bitsPerElementIndex = i;
		}
		//System.out.println(setSizeIndex);
		//System.out.println(bitsPerElementIndex);
		detFullRatio[setSizeIndex][bitsPerElementIndex][0] = BFDet.totalBitsSetPercentage();
		ranFullRatio[setSizeIndex][bitsPerElementIndex][1] = BFRan.totalBitsSetPercentage();
		detBuildTime[setSizeIndex][bitsPerElementIndex][0] = BFDet.filterBuildingTime;
		ranBuildTime[setSizeIndex][bitsPerElementIndex][1] = BFRan.filterBuildingTime;
		System.out.println("fnv64 Hash Function Bloom Filter");
		System.out.println("------------------");
		System.out.println("m = " + BFDet.filterSize + ", n = " + stringNum + ", k = " + BFDet.hashFunNum);
		System.out.println("Bits per element: " + BFDet.bitsPerElement);
		System.out.println("Total items: " + BFDet.dataSize);
		System.out.println("Total bits set: " + BFDet.totalBitsSet() + "(" + BFDet.totalBitsSetPercentage() + "%)");
		System.out.println("Creating the filter took " + (BFDet.filterBuildingTime / 1000F) + " seconds.");
		System.out.println("Random Hash Function Bloom Filter");
		System.out.println("------------------");
		System.out.println("m = " + BFRan.filterSize + ", n = " + stringNum + ", k = " + BFRan.hashFunNum);
		System.out.println("Bits per element: " + BFRan.bitsPerElement);
		System.out.println("Total items: " + BFRan.dataSize);
		System.out.println("Total bits set: " + BFRan.totalBitsSet() + "(" + BFRan.totalBitsSetPercentage() + "%)");
		System.out.println("Creating the filter took " + (BFRan.filterBuildingTime / 1000F) + " seconds.");
		return readFile2ToTestFilter(BFDet, BFRan);
	}
	
	float[] readFile2ToTestFilter (BloomFilterDet BFDet, BloomFilterRan BFRan) throws IOException {
		BufferedReader br = new BufferedReader (new FileReader(file2));
		int[] errors = new int[2]; 
		String line = null;
		float stringCount = 0;
		while ((line = br.readLine()) != null) {
			stringCount += 1;
			if (BFDet.appears(line)) {
				errors[0] += 1;
			}
			if (BFRan.appears(line)) {
				errors[1] += 1;
			}
		}
		br.close();
		System.out.println("test file has " + stringCount + " items.");
		float[] errorRates = new float[2];
		errorRates[0] = errors[0] / stringCount;
		errorRates[1] = errors[1] / stringCount;
		System.out.println("fnv64 hash function total false positives: " + errors[0] + "(" + (errorRates[0] * 100) + "%)");
		System.out.println("Random hash function total false positives: " + errors[1] + "(" + (errorRates[1] * 100) + "%)");
		System.out.println("Completing the queries on the fnv-64 filter took " + (BFDet.queriesCompleteTime / 1000F) + " seconds.");
		System.out.println("Completing the queries on the random filter took " + (BFRan.queriesCompleteTime / 1000F) + " seconds.");
		int setSizeIndex = -1;
		for (int i = 0; i < this.setSize.length; i ++){
			if (this.setSize[i] == BFDet.dataSize)
				setSizeIndex = i;
		}
		int bitsPerElementIndex = -1;
		for (int i = 0; i < this.bitsPerElement.length; i ++){
			if (this.bitsPerElement[i] == BFDet.bitsPerElement)
				bitsPerElementIndex = i;
		}
		detQueryTime[setSizeIndex][bitsPerElementIndex][0] = BFDet.queriesCompleteTime;
		ranQueryTime[setSizeIndex][bitsPerElementIndex][1] = BFRan.queriesCompleteTime;
		System.out.println("\n");
		return errorRates;
	}
	
	float[][][] estimateFalsePositive () throws Exception {
		float[][][] fps = new float[setSize.length][bitsPerElement.length][2];
		
		for (int j = 0; j < setSize.length; j ++) {
			for (int i = 0; i < bitsPerElement.length; i++){
				System.out.println("Testing deterministic (fnv64) and random hash functions");
				System.out.println("Total items: " + setSize[j] + ", Bits per element: " + bitsPerElement[i]);
				float fpr[] = readFile1ToBuildFilter(largestSize, bitsPerElement[i], setSize[j]);
				fps[j][i][0] = fpr[0];
				fps[j][i][1] = fpr[1];
			}
		}
		return fps;
	}
}
