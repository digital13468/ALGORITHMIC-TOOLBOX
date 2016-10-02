/**
 * 
 */

/**
 * @author Chan-Ching Hsu
 *
 */
import java.util.BitSet;

public class BloomFilter {
	int setSize;
	int bitsPerElement;
	int filterSize;
	int hashFunNum;
	double filterBuildingTime;
	BitSet filter;
	int dataSize;
	double queriesCompleteTime;
	
	
	

	
	String caseInsensitive (String s) {
		return s.toUpperCase();
	}
	
	int totalBitsSet () {
		return filter.cardinality();
	}
	
	double totalBitsSetPercentage () {
		return totalBitsSet() * 1.0 / filterSize * 100; 
	}
	
	int filterSize () {
		return filterSize;
	}
	
	int dataSize () {
		return dataSize;
	}
	
	int numHashes () {
		return hashFunNum;
	}
}
