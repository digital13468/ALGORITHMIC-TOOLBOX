import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
class NaiveDifferential {
	File database;
	File diffFile;
	int databaseQueryTimes;
	int DiffFileQueryTImes;
	
	NaiveDifferential (File databaseFile, File differenFile) {
		this.database = databaseFile;
		this.diffFile = differenFile;
		this.databaseQueryTimes = 0;
		this.DiffFileQueryTImes = 0;
	}
	
	String retrieveRecord (String key) {
		String record = tryDiffFile(key);
		if (record == null) {
			record = tryDataBase(key);
			if (record == null)
				System.exit(1);
			//else
			//	databaseQueryTimes += 1;
		}
		//else
		//	DiffFileQueryTImes += 1;
		return record;
	}
	
	String tryDiffFile (String key) {
		DiffFileQueryTImes += 1;
		return SearchThe(diffFile, key);
	}
	
	String tryDataBase (String key) {
		databaseQueryTimes += 1;
		return SearchThe(database, key);
	}
	
	String SearchThe (File file, String key) {
		Scanner in = null;
		String record = null;
		try {
			in = new Scanner(file);
			while (in.hasNext()) {
				String line = in.nextLine();
				if (line.startsWith(key)){
				//if (line.equals(key)) {
					//record = line.replace(key, "").trim();
					record = line;
					//System.out.flush();
					//System.out.println(record);
					break;
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println("IO Error Occurred: " + e.toString());
		}
		return record;
	}
}
