

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class PermutationFunctions {

	Map<Integer, Integer> permutationFunctions;
	int numPermutations;
	int p;
	
	
	PermutationFunctions (int numPermutations, int numTerms) {
		this.numPermutations = numPermutations;
		this.p = nextPrime(numTerms);
		this.permutationFunctions = new LinkedHashMap<Integer, Integer>();
		//generatePermutationFunctions();
		
	}
	
	/**
	 * Generates k random permutation functions.
	 */
	void generatePermutationFunctions () {
		
		int i = 0;
		while (i < numPermutations) {
			Random r = new Random();
			int a = r.nextInt(p);
			int b = r.nextInt(p);
			if (checkDuplicatePermutationFunctions(a, b) == false){
				permutationFunctions.put(a, b);
				i ++;
				//System.out.println(a + ", " + b);
			}
		}


	}
	
	boolean checkDuplicatePermutationFunctions(int a, int b) {
	
		for (Integer key : permutationFunctions.keySet()) {
			
			if ( key.intValue() == a || permutationFunctions.get(key).intValue() == b) {
				return true;
				
			}
		}
		return false;
	}
	/**
	 * Finds the next prime number
	 */
	static int nextPrime (int n) {
		
		int m = n;
		while (!isPrime(m)) {
			m += 1;

		}
		return m;
	}
	
	

	/**
	 * Checks if integer is prime or not.
	 * This is based on the sieve of eratosthenes.
	 * This particular implementation is is based off information from:
	 * http://en.wikipedia.org/wiki/Primality_test
	 */
	static boolean isPrime (int n) {
		if (n < 0)
			throw new IllegalArgumentException("Initial value is negative: " + n);
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
