import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
public class LSH {
	String[] docNames;
	int[][] minHashMatrix;
	int bands;
	int numOfRowsPerBand;
	List<List<List<String>>>hashTables;
	Map<Integer, Integer> hashFunctions;
	int hashTableSize;
	
	LSH (int[][] minHashMatrix, String[] docNames, int bands) {
		this.bands = bands;
		this.docNames = docNames;
		this.minHashMatrix = minHashMatrix;
		this.numOfRowsPerBand = minHashMatrix.length / bands;
		this.hashTables = new ArrayList<List<List<String>>>();
		//localitySensitiveHashing();
	}
	
	void localitySensitiveHashing() {
		//int hashTableSize = PermutationFunctions.nextPrime(docNames.length);
		PermutationFunctions pf = new PermutationFunctions(numOfRowsPerBand, docNames.length);
		pf.generatePermutationFunctions();
		hashFunctions = pf.permutationFunctions;
		hashTableSize = pf.p;
		//for (Integer key : hashFunctions.keySet())
		//	System.out.println(key + ", " + hashFunctions.get(key) + ", " + hashTableSize);
		for (int i = 0; i < bands; i ++) {
			//System.out.println("band: " + i);
			hashTables.add(new ArrayList<List<String>>());
			for (int j = 0; j < hashTableSize; j ++)
				hashTables.get(i).add(new ArrayList<String>());
			for (int j = 0; j < docNames.length; j ++) {
				List<Integer> band = getBand(i, j);
				int pos = hash(band);
				if (pos < 0)
					throw new IllegalArgumentException("hash value is negative: " + pos);
				hashTables.get(i).get(pos).add(docNames[j]);
				//for (Integer r : band)
				//System.out.println("final hash value: " + pos);
			}
		}
	}
	
	int hash(List<Integer> band) {
		int hashValue = 0;
		for (int i = 0; i < numOfRowsPerBand; i ++) {
			hashValue = hashValue + (((band.get(i) * (Integer)hashFunctions.keySet().toArray()[i] + (Integer)hashFunctions.values().toArray()[i]) & 0x7fffffff) % hashTableSize);
			//System.out.println(hashValue);
			if (hashValue < 0)
				throw new IllegalArgumentException("Hash value is negative. " + hashValue + ", " + band.get(i) + ", " + (Integer)hashFunctions.keySet().toArray()[i] + ", " + (Integer)hashFunctions.values().toArray()[i] + ", " + hashTableSize);
		}
		return (hashValue & 0x7fffffff) % hashTableSize;
	}
	
	List<Integer> getBand(int bandInd, int documentInd) {
		List<Integer> band = new ArrayList<Integer>();
		for (int i = bandInd * numOfRowsPerBand; i < (bandInd + 1) * numOfRowsPerBand; i ++) {
			//if (i > minHashMatrix.length)
			//	break;
			band.add(minHashMatrix[i][documentInd]);
		}
		return band;
	}

	Set<String> nearDuplicatesOf(String docName) {
		if (hashTables.isEmpty())
			throw new IllegalArgumentException("No LSH tables built");
		Set<String> nearDuplicates = new LinkedHashSet<String>();
		int documentInd = Arrays.asList(docNames).indexOf(docName);
		for (int i = 0; i < bands; i ++) {
			List<Integer> band = getBand(i, documentInd);
			int pos = hash(band);
			nearDuplicates.addAll(hashTables.get(i).get(pos));
			
		}
		nearDuplicates.remove(docName);

		return nearDuplicates;
	}
}
