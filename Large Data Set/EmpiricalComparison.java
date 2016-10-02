import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
class EmpiricalComparison {
	File database;
	File diffFile;
	File testFile;
	
	EmpiricalComparison (String DBFilePath, String DFFilePath, String testFilePath) {
		this.database = new File(DBFilePath);
		this.diffFile = new File(DFFilePath);
		this.testFile = new File(testFilePath);
	}
	
	void runNaiveDifferntial () throws IOException {
		NaiveDifferential nd = new NaiveDifferential(database, diffFile);
		BufferedReader br = new BufferedReader (new FileReader(testFile));
		
		String line = null;
		long startTime = System.currentTimeMillis();
		while ((line = br.readLine()) != null) {
			//System.out.println("retreiving " + line);
			nd.retrieveRecord(line);
		}
		System.out.println(((System.currentTimeMillis() - startTime ) / 1000F)+ " seconds");
		System.out.println("Database file was queried " + nd.databaseQueryTimes + " times. Diff file was queried " + nd.DiffFileQueryTImes + " times.");
		br.close();
	}
	
	void runBloomDifferential (int setSize, int bitsPerElement, String method) throws Exception {
		
		BloomDifferential bd = new BloomDifferential(database, diffFile);
		
		bd.createFilter(setSize, bitsPerElement, method);
		BufferedReader br = new BufferedReader (new FileReader(testFile));
		
		String line = null;
		long startTime = System.currentTimeMillis();
		while ((line = br.readLine()) != null) {
			//System.out.println("retreiving " + line);
			bd.retrieveRecord(line, method);
			/*if (bd.SearchThe(diffFile, line) == null) {
				if (bd.SearchThe(database, line) != null)
					System.out.println(line + ", It is in the database.");
			}
			else
				System.out.println(line + ", It is in the DiffFile.");*/
		}
		long QTime = System.currentTimeMillis() - startTime;
		System.out.println((QTime / 1000F) + " seconds");
		if (method == "Det") {
			System.out.println("Database file was queried " + bd.databaseQueryTimes + " times. Diff file was queried " + bd.DiffFillQueryTimes + " times. Filter was queried " + (bd.BFDQueryTime / 1000F) + " seconds." + "(" + ((bd.BFDQueryTime / 1000F) / (QTime / 1000F) * 100) + "%)");
			System.out.println("The fnv64 filter was built in " + (bd.filterBuildingTime / 1000F) + " seconds.");
			System.out.println("The queries were completed in " + (bd.filesQueringTime / 1000F) + " seconds." + "(" + ((bd.filesQueringTime / 1000F) / (QTime / 1000F) * 100) + "%)");
			System.out.println("The queries on database took " + (bd.databaseQueryTime / 1000F) + " seconds (" + ((bd.databaseQueryTime / 1000F) / (QTime / 1000F) * 100) + "%). The queries on DiffFile took " + (bd.DiffFillQueryTime / 1000F) + " seconds (" + ((bd.DiffFillQueryTime / 1000F) / (QTime / 1000F) * 100) + "%).");
		}
		else {
			System.out.println("Database file was queried " + bd.databaseQueryTimes + " times. Diff file was queried " + bd.DiffFillQueryTimes + " times. Filter was queried " + (bd.BFRQueryTime / 1000F) + " seconds." + "(" + ((bd.BFRQueryTime / 1000F) / (QTime / 1000F) * 100) + "%)");
			System.out.println("The random filter was built in " + (bd.filterBuildingTime / 1000F) + " seconds.");
			System.out.println("The queries were completed in " + (bd.filesQueringTime / 1000F) + " seconds." + "(" + ((bd.filesQueringTime / 1000F) / (QTime / 1000F) * 100) + "%)");
			System.out.println("The queries on database took " + (bd.databaseQueryTime / 1000F) + " seconds (" + ((bd.databaseQueryTime / 1000F) / (QTime / 1000F) * 100) + "%). The queries on DiffFile took " + (bd.DiffFillQueryTime / 1000F) + " seconds (" + ((bd.DiffFillQueryTime / 1000F) / (QTime / 1000F) * 100) + "%).");
		}
		br.close();
	}
	
	
}
