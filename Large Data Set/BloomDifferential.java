import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
class BloomDifferential {
	File database;
	File diffFile;
	BloomFilterDet BFD;
	BloomFilterRan BFR;
	int databaseQueryTimes;
	int DiffFillQueryTimes;
	double BFDQueryTime;
	double BFRQueryTime;
	double filterBuildingTime;
	double filesQueringTime;
	double databaseQueryTime;
	double DiffFillQueryTime;
	
	BloomDifferential (File databaseFile, File differenFile) {
		this.database = databaseFile;
		this.diffFile = differenFile;
		this.databaseQueryTimes = 0;
		this.DiffFillQueryTimes = 0;
		this.BFDQueryTime = 0.0;
		this.BFRQueryTime = 0.0;
		this.filterBuildingTime = 0.0;
		this.filesQueringTime = 0.0;
		this.databaseQueryTime = 0.0;
		this.DiffFillQueryTime = 0.0;
	}
	
	void createFilter (int setSize, int bitsPerElement, String method) throws Exception {
		if (method == "Det") {
			long startTime = System.currentTimeMillis();
			BFD = new BloomFilterDet(setSize, bitsPerElement);
			BufferedReader br = new BufferedReader (new FileReader(diffFile));
			String line = null;		
			while ((line = br.readLine()) != null) {
				String str = line;
				int index = 0;
				for (int j = 0; j < 4; j ++) {
					int offset = str.indexOf(" ") + 1;
					index = index + offset;
					str = str.substring(offset);
					//System.out.println(secondStr);
				}
				//System.out.println("adding " + line.substring(0, index));
				BFD.add(line.substring(0, index));
			}
			br.close();
			filterBuildingTime = filterBuildingTime + System.currentTimeMillis() - startTime;
		}
		else if (method == "Ran") {
			long startTime = System.currentTimeMillis();
			BFR = new BloomFilterRan(setSize, bitsPerElement);
			BufferedReader br = new BufferedReader (new FileReader(diffFile));
			String line = null;		
			while ((line = br.readLine()) != null) {	
				String str = line;
				int index = 0;
				for (int j = 0; j < 4; j ++) {
					int offset = str.indexOf(" ") + 1;
					index = index + offset;
					str = str.substring(offset);
					//System.out.println(secondStr);
				}
				//System.out.println("adding " + line.substring(0, index));
				BFR.add(line.substring(0, index));
			}
			br.close();
			filterBuildingTime = filterBuildingTime + System.currentTimeMillis() - startTime;
		}
		else {
			
			System.out.println("hash function method unrecognized");
			System.exit(0);
		}

	}
	
	String retrieveRecord (String key, String method) {
		String record = null;
		
		if (method == "Det") {
			long startTime = System.currentTimeMillis();
			boolean contains = BFD.appears(key);
			BFDQueryTime = BFDQueryTime + System.currentTimeMillis() - startTime;
			startTime = System.currentTimeMillis();
			if (contains == false) {
				//System.out.println("not in the filter");
				record = tryDataBase(key);
				if (record == null)
					System.exit(1);
			//	else
			//		databaseQueryTimes += 1;
			}
			else {
				//System.out.println("in the filter");
				record = tryDiffFile(key);
				if (record == null){
					record = tryDataBase(key);
					//System.out.println("not in DiffFile");
					if (record == null) 
						System.exit(1);
			//		else
			//			databaseQueryTimes += 1;
				}
			//	else
			//		DiffFillQueryTimes += 1;
			}
			filesQueringTime = filesQueringTime + System.currentTimeMillis() - startTime;
		}
		else if (method == "Ran") {
			long startTime = System.currentTimeMillis();
			boolean contains = BFR.appears(key);
			BFRQueryTime = BFRQueryTime + System.currentTimeMillis() - startTime;
			startTime = System.currentTimeMillis();
			if (contains == false){
				//System.out.println("not in the filter");
				record = tryDataBase(key);
				if (record == null)
					System.exit(1);
			//	else
			//		databaseQueryTimes += 1;
			}
			else {
				//System.out.println("in the filter");
				record = tryDiffFile(key);
				if (record == null) {
					record = tryDataBase(key);
					//System.out.println("not in DiffFile");
					if (record == null) 
						System.exit(1);
			//		else
			//			databaseQueryTimes += 1;
				}
			//	else
			//		DiffFillQueryTimes += 1;
			}
			filesQueringTime = filesQueringTime + System.currentTimeMillis() - startTime;
		}
		else {
			System.out.println("hash function method unrecognized");
			System.exit(0);
		}
		return record;
	}
	
	String tryDiffFile (String key) {
		DiffFillQueryTimes += 1;
		long startTime = System.currentTimeMillis();
		String record = SearchThe(diffFile, key);
		DiffFillQueryTime = DiffFillQueryTime + System.currentTimeMillis() - startTime;
		return record;
	}
	
	String tryDataBase (String key) {
		databaseQueryTimes += 1;
		long startTime = System.currentTimeMillis();
		String record = SearchThe(database, key);
		databaseQueryTime = databaseQueryTime + System.currentTimeMillis() - startTime;
		return record;
	}
	
	String SearchThe (File file, String key) {
		Scanner in = null;
		String record = null;
		try {
			in = new Scanner(file);
			while (in.hasNext()) {
				String line = in.nextLine();
				if (line.startsWith(key)){
				//if (line.equals(key)){
					//record = line.replace(key, "").trim();
					record = line;
					//System.out.flush();
					//System.out.println(record);
					break;
				}
			}
			in.close();
		} 
		catch (IOException e) {
			System.out.println("IO Error Occurred: " + e.toString());
		}
		return record;
	}
}
