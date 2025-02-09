import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
class BloomFilterApp {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int[] setSize = {235886, 200000, 100000, 50000};
		int[] bitsPerElement = {4, 8, 10};
		float[][][] fpe = new float[setSize.length][bitsPerElement.length][2];
		double[][][] detFullR = new double[setSize.length][bitsPerElement.length][2];
		double[][][] ranFullR = new double[setSize.length][bitsPerElement.length][2];
		double[][][] detBuildT = new double[setSize.length][bitsPerElement.length][2];
		double[][][] ranBuildT = new double[setSize.length][bitsPerElement.length][2];
		double[][][] detQueryT = new double[setSize.length][bitsPerElement.length][2];
		double[][][] ranQueryT = new double[setSize.length][bitsPerElement.length][2];
		int round = 1600;
		//long startTime = System.currentTimeMillis();
		for (int i = 0; i < round; i ++) {
			System.out.println("round " + i);
			FalsePositives fp = new FalsePositives("../web2.txt", "../web2a.txt", bitsPerElement);
			float[][][] FPE = fp.estimateFalsePositive();
			
			for (int j = 0; j < FPE.length; j ++) {
				for (int k = 0; k < FPE[0].length; k ++) {
					for (int l = 0; l < FPE[0][0].length; l ++) {
						/*if (l == 0) {
							//System.out.print("set size: " + setSize[j] + ", bits per element: " + bitsPerElement[k] + ", type : det" + ", false positive rate: " + fpe[j][k][l]);
							System.out.println(" full ratio: " + fp.detFullRatio[j][k][l] + "%, building time: " + (fp.detBuildTime[j][k][l] / 1000F) + " s, querying time: " + (fp.detQueryTime[j][k][l] / 1000F) + " s");
						}
						else {
							//System.out.print("set size: " + setSize[j] + ", bits per element: " + bitsPerElement[k] + ", type : ran" + ", false positive rate: " + fpe[j][k][l]);
							System.out.println(" full ratio: " + fp.ranFullRatio[j][k][l] + "%, building time: " + (fp.ranBuildTime[j][k][l] / 1000F) + " s, querying time: " + (fp.ranQueryTime[j][k][l] / 1000F) + " s");
						}*/
						fpe[j][k][l] = fpe[j][k][l] + FPE[j][k][l];
						detFullR[j][k][l] = detFullR[j][k][l] + fp.detFullRatio[j][k][l];
						ranFullR[j][k][l] = ranFullR[j][k][l] + fp.ranFullRatio[j][k][l];
						detBuildT[j][k][l] = detBuildT[j][k][l] + fp.detBuildTime[j][k][l];
						ranBuildT[j][k][l] = ranBuildT[j][k][l] + fp.ranBuildTime[j][k][l];
						detQueryT[j][k][l] = detQueryT[j][k][l] + fp.detQueryTime[j][k][l];
						ranQueryT[j][k][l] = ranQueryT[j][k][l] + fp.ranQueryTime[j][k][l];
						if (i == round - 1) {
							fpe[j][k][l] = fpe[j][k][l] / round; 
							detFullR[j][k][l] = detFullR[j][k][l] / round;
							ranFullR[j][k][l] = ranFullR[j][k][l] / round;
							detBuildT[j][k][l] = detBuildT[j][k][l] / round;
							ranBuildT[j][k][l] = ranBuildT[j][k][l] / round;
							detQueryT[j][k][l] = detQueryT[j][k][l] / round;
							ranQueryT[j][k][l] = ranQueryT[j][k][l] / round;
						}
					}
				}
			}
			fp = null;
		}
		//System.out.println((System.currentTimeMillis() - startTime) / 1000F);
		for (int j = 0; j < fpe.length; j ++) {
			for (int k = 0; k < fpe[0].length; k ++) {
				for (int l = 0; l < fpe[0][0].length; l ++) {
					if (l == 0) {
						System.out.print("set size: " + setSize[j] + ", bits per element: " + bitsPerElement[k] + ", type : det" + ", false positive rate: " + fpe[j][k][l]);
						System.out.println(" full ratio: " + detFullR[j][k][l] + "%, building time: " + (detBuildT[j][k][l] / 1000F) + " s, querying time: " + (detQueryT[j][k][l] / 1000F) + " s");
					}
					else {
						System.out.print("set size: " + setSize[j] + ", bits per element: " + bitsPerElement[k] + ", type : ran" + ", false positive rate: " + fpe[j][k][l]);
						System.out.println(" full ratio: " + ranFullR[j][k][l] + "%, building time: " + (ranBuildT[j][k][l] / 1000F) + " s, querying time: " + (ranQueryT[j][k][l] / 1000F) + " s");
					}
				}
			}
		}
		String database = "../database.txt";
		String difffile = "../DiffFile.txt";
		String gramFile = "../grams.txt";
		//int databaseSize = countSetSize(database);
		//int difffileSize = countSetSize(difffile);
		double[] sizeFromDB = {0.0, 0.6, 0.6, 0.6, 1.2, 6.0};
		double[] sizeFromDF = {0.6, 0.0, 0.6, 1.2, 0.6, 0.6};
		
		generateTestFiles(gramFile, sizeFromDB, difffile, sizeFromDF, 1000);//1000000);
		//generateTestFiles("database.txt", sizeFromDB, "DiffFile.txt", sizeFromDF, 1000000);
		String directory = "./test files/";
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()){
				System.out.println("Running on the test file: " + file.getName());
				//EmpiricalComparison ec = new EmpiricalComparison("database.txt", "DiffFile.txt", file.getName());
				EmpiricalComparison ec = new EmpiricalComparison(database, difffile, directory + file.getName());
				System.out.print("Naive-differential method took ");
				ec.runNaiveDifferntial();
				//int setSize = countSetSize("DiffFile.txt");
				int difffileSize = countSetSize(difffile);
				for (int i = 0; i < bitsPerElement.length; i++) {
					System.out.println("Bits per element is set to " + bitsPerElement[i]);
					System.out.print("Deterministic-hash-function method took ");
					ec.runBloomDifferential(difffileSize, bitsPerElement[i], "Det");
					System.out.print("Random-hash-function method took ");
					ec.runBloomDifferential(difffileSize, bitsPerElement[i], "Ran");
					System.out.println("");
				}
				System.out.println("");
			}
		}
	}
	
	static int countSetSize (String diffFileName) throws IOException {
		int setSize = 0;
		BufferedReader br = new BufferedReader (new FileReader(new File(diffFileName)));
		String line = br.readLine();
		while (line != null) {
			setSize += 1;
			line = br.readLine();
		}
		br.close();
		return setSize;
	}
	
	static void generateTestFiles (String database, double[] sizeFromDB, String diffFile, double[] sizeFromDF, int unit) throws Exception {
		BufferedReader dbr = new BufferedReader (new FileReader(new File(database)));
		FileWriter fw;
		String dir = "./test files/";
		for (int i = 0; i < sizeFromDB.length; i ++){
			//String testFileName = ".\test files\testword_" + (sizeFromDB) + "M_" + (sizeFromDF) + "M.txt";
			//fw = new FileWriter(dir + "testword_" + (sizeFromDF[i]) + "M_" + (sizeFromDB[i]) + "M.txt");
			fw = new FileWriter(dir + "testword_" + (sizeFromDB[i]) + "K_" + (sizeFromDF[i]) + "K.txt");
			fw.close();
		}
		
		String line = null;
		Random r = new Random();
		List<String> lines = new ArrayList<String>();
		int databaseSize = 0;	
		while ((line = dbr.readLine()) != null) {
			lines.add(line);
			databaseSize += 1;
		}
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		//System.out.println(databaseSize);
		for (File file : listOfFiles) {
			String fileName = file.getName();
			fw = new FileWriter(dir + fileName, true);
			//for (int i = 0; i < Double.parseDouble(fileName.substring(fileName.indexOf('_') + 1, fileName.indexOf('M'))) * unit; i ++)
			for (int i = 0; i < Double.parseDouble(fileName.substring(fileName.indexOf('_') + 1, fileName.indexOf('K'))) * unit; i ++)
				fw.write(lines.get(r.nextInt(databaseSize)) + " \n");
			fw.close();
			//fileIndex += 1;
		}
		dbr.close();
		lines.clear();
		
		int diffFileSize = 0;
		BufferedReader dfr = new BufferedReader (new FileReader(new File(diffFile)));
		while ((line = dfr.readLine()) != null) {
			lines.add(line);
			diffFileSize += 1;
		}
		//fileIndex = 0;
		for (File file : listOfFiles) {
			String fileName =file.getName();
			fw = new FileWriter(dir + file.getName(), true);
			//for (int i = 0; i < Double.parseDouble(fileName.substring(fileName.lastIndexOf('_') + 1, fileName.lastIndexOf('M'))) * unit; i ++) {
			for (int i = 0; i < Double.parseDouble(fileName.substring(fileName.lastIndexOf('_') + 1, fileName.lastIndexOf('K'))) * unit; i ++) {
				String str = lines.get(r.nextInt(diffFileSize));
				String secondStr = str;
				int index = 0;
				for (int j = 0; j < 4; j ++) {
					int offset = secondStr.indexOf(" ") + 1;
					index = index + offset;
					secondStr = secondStr.substring(offset);
					//System.out.println(secondStr);
				}
				fw.write(str.substring(0, index) + "\n");
				
			}
			fw.close();
			//fileIndex += 1;
		}
		
		dfr.close();
		
	}
}
