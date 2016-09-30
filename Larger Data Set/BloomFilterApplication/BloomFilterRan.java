/**
 * Create a new bloom filter with random hash function.
 * The random hash function depends on the length of the filter.
 * If the length of the filter is not prime, the actual length of the bloom filter will be set to the next larger prime number found.
 */

import java.util.BitSet;
import java.util.Random;

public class BloomFilterRan extends BloomFilter{

	long[] arrayAlpha;
	long[] arrayBeta;

	
	
	
	
	BloomFilterRan (int setSize, int bitsPerElement) {
		this.setSize = setSize;
		this.bitsPerElement = bitsPerElement;
		int primeNum = nextPrime(setSize * bitsPerElement);
		this.filterSize = primeNum;
		this.hashFunNum = (int) (Math.log(2) * filterSize / setSize);	// must be an integer and we might choose a value less than optimal to reduce computational overhead
		this.arrayAlpha = new long [hashFunNum];
		this.arrayBeta = new long [hashFunNum];
		hashFunc();
		this.filter = new BitSet(filterSize);
		this.dataSize = 0;
		if (filter.size() % 64 != 0 || filter.size() < filterSize) { 
			System.out.println("filter size: " + filter.size() + ", filterSize: " + filterSize);
			System.exit(1);
		}
	}
	
	/**
	 * Generates k random hash functions for use with bloom filter bit array.
	 */
	void hashFunc () {
		Random r = new Random();
		//System.out.println("Building " + hashFunNum +" random hash functions......");
		for (int i = 0; i < hashFunNum; i ++) {
			arrayAlpha[i] = r.nextInt(filterSize);
			arrayBeta[i] = r.nextInt(filterSize);
		}
		for (int i = 0; i < hashFunNum - 1; i ++) {
			for (int j = i + 1; j < hashFunNum ; j ++) {
				if (arrayAlpha[i] == arrayAlpha[j] && arrayBeta[i] == arrayBeta[j]) {
					
					arrayAlpha[i] = r.nextInt(filterSize);
					arrayBeta[i] = r.nextInt(filterSize);
					System.out.println("Replaced " + i + " hash functions");
					i = 0;
					break;
				}
			}
		}
		/*for (int i = 0; i < hashFunNum; i ++) {
			System.out.println(arrayAlpha[i]);
			System.out.println(arrayBeta[i]);
		}*/
	}
	
	/**
	 * Finds the next prime number
	 */
	int nextPrime (int n) {
		//System.out.println("Randomly choose a prime number large enough......");
		int m = n;
		while (!isPrime(m)) {
			m += 1;
			//Random r = new Random();
			//m = r.nextInt(Integer.MAX_VALUE);
			//while (m <= n) {
			//	m = r.nextInt(Integer.MAX_VALUE);
			//}
		}
		return m;
	}
	
	/**
	 * A random hash function for strings. Requires random coefficients.
	 * Given two random coefficients generated from {0, 1, ..., p - 1}
	 * where p = prime length of bloom filter bit array, the random 
	 * hash function will be: a * x + b.  
	 */
	void add (String s) {
		long startTime = System.currentTimeMillis();
		String upperCaseS = caseInsensitive(s);
		//long x = upperCaseS.hashCode();
		//System.out.println(filter.size());
		//System.out.println(s);
		for (int i = 0; i < hashFunNum; i++) {
			//long result = arrayAlpha[i] * x + arrayBeta[i];
			
			//System.out.println(arrayAlpha[i]);
			//System.out.println(x);
			//System.out.println(arrayBeta[i]);
			//System.out.println(arrayAlpha[i] * x);
			//System.out.println(result);
			//System.out.println(filterSize);
			//System.out.println(result % filterSize);
			//filter.set((int) (result % filterSize));
			long result = 0;
			for (int j = 0; j < upperCaseS.length(); j ++) {
				//System.out.println(result);
				result ^= upperCaseS.charAt(j);
				//System.out.println(result);
				result = arrayAlpha[i] * result + arrayBeta[i];
				result = result % filterSize;
			}
			filter.set((int) (result));
			//System.out.println(result);
		}
		filterBuildingTime = filterBuildingTime + System.currentTimeMillis() - startTime;
		dataSize += 1;
	}	
	
	boolean appears (String s) {
		long startTime = System.currentTimeMillis();
		boolean appearance = true;
		String upperCaseS = caseInsensitive(s);
		//System.out.println(filter.size());
		//System.out.println(s);
		//long x = upperCaseS.hashCode();
		for (int i = 0; i < hashFunNum; i++) {
			/*long result = arrayAlpha[i] * x + arrayBeta[i];
			if (!filter.get((int) (result % filterSize))) {
				appearance = false;
				break;
			}*/
			long result = 0;
			for (int j = 0; j < upperCaseS.length(); j ++) {
				result ^= upperCaseS.charAt(j);
				result = arrayAlpha[i] * result + arrayBeta[i];
				result = result % filterSize;
				
			}
			//System.out.println(result);
			if (!filter.get((int) (result))) {
				appearance = false;
				break;
			}
		}
		queriesCompleteTime = queriesCompleteTime + System.currentTimeMillis() - startTime;
		return appearance;
	}

	/**
	 * Checks if integer is prime or not.
	 * This is based on the sieve of eratosthenes.
	 * This particular implementation is is based off information from:
	 * http://en.wikipedia.org/wiki/Primality_test
	 */
	boolean isPrime (int n) {
		if (n == 1)
			return false;
		else if (n == 2 || n == 3)
			return true;
		else if (n % 2 == 0 || n % 3 == 0)
			return false;
		else {
			for (int i = 5; i * i < n + 1; i += 6) {
				if (n % i == 0 || n % (i + 2) == 0) {
					return false;
				}
			}
			return true;
		}
	}
	

}
