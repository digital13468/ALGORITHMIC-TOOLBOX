/**
 * 
 */

import java.math.BigInteger;
/**
 * @author Chan-Ching Hsu
 *
 */
import java.util.BitSet;

public class BloomFilterDet extends BloomFilter{


	
	
	
	BloomFilterDet (int setSize, int bitsPerElement) {
		this.setSize = setSize;
		this.bitsPerElement = bitsPerElement;
		this.filterSize = setSize * bitsPerElement;
		this.hashFunNum = (int) (Math.log(2) * filterSize / setSize);	// must be an integer and we might choose a value less than optimal to reduce computational overhead
		this.filter = new BitSet(filterSize);
		this.dataSize = 0;
		if (filter.size() % 64 != 0 || filter.size() < filterSize) { 
			System.out.println("filter size: " + filter.size() + ", filterSize: " + filterSize);
			System.exit(1);
		}
		this.filterBuildingTime = 0.0;
		this.queriesCompleteTime = 0.0;
	}
	
	/** See:
	 * "Less Hashing, Same Performance: Building a Better Bloom Filter"
	 * 2005, Adam Kirsch, Michael Mitzenmacher
	 * http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.72.2442
	 */
	void add (String s) {
		long startTime = System.currentTimeMillis();
		String upperCaseS = caseInsensitive(s);
		BigInteger h0 = hash64(upperCaseS);
		BigInteger h1 = h0.shiftRight(32);
		BigInteger h2 = h0.xor(new BigInteger("ffffffff", 16));
		//System.out.println(filter.size());
		//System.out.println(s);
		//long h0 = hash64(upperCaseS);
		//long h1 = h0 >> 32;
		//long h2 = h0 & 0xFFFFFFFF;
		for (int i = 0; i < hashFunNum; i++) {
			/*System.out.println(s);
			System.out.println(upperCaseS);
			System.out.println(h0);
			System.out.println(h1);
			System.out.println(h2);
			System.out.println(i);
			System.out.println(filterSize);
			System.out.println(new BigInteger(Integer.toString(i)));
			System.out.println(h2.multiply(new BigInteger(Integer.toString(i))));
			System.out.println(h1.add(h2.multiply(new BigInteger(Integer.toString(i)))));
			System.out.println(h1.add(h2.multiply(new BigInteger(Integer.toString(i)))).mod(new BigInteger(Integer.toString(filterSize))));
			System.out.println(h1.add(h2.multiply(new BigInteger(Integer.toString(i)))).mod(new BigInteger(Integer.toString(filterSize))).intValue());*/
			filter.set(h1.add(h2.multiply(new BigInteger(Integer.toString(i)))).mod(new BigInteger(Integer.toString(filterSize))).intValue());
			//filter.set((int) ((h1 + h2 * i) % filterSize));
			//System.out.println(h1.add(h2.multiply(new BigInteger(Integer.toString(i)))).mod(new BigInteger(Integer.toString(filterSize))).intValue());
		}
		filterBuildingTime = filterBuildingTime + System.currentTimeMillis() - startTime;
		dataSize += 1;
	}	
	
	boolean appears (String s) {
		long startTime = System.currentTimeMillis();
		boolean appearance = true;
		String upperCaseS = caseInsensitive(s);
		BigInteger h0 = hash64(upperCaseS);
		BigInteger h1 = h0.shiftRight(32);
		BigInteger h2 = h0.xor(new BigInteger("ffffffff", 16));
		//System.out.println(filter.size());
		//System.out.println(s);
		//long h0 = hash64(upperCaseS);
		//long h1 = h0 >> 32;
		//long h2 = h0 & 0xFFFFFFFF;
		for (int i = 0; i < hashFunNum; i++) {
			//System.out.println(h1.add(h2.multiply(new BigInteger(Integer.toString(i)))).mod(new BigInteger(Integer.toString(filterSize))).intValue());
			if (!filter.get(h1.add(h2.multiply(new BigInteger(Integer.toString(i)))).mod(new BigInteger(Integer.toString(filterSize))).intValue())) {
			//if (!filter.get((int) ((h1 + h2 * i) % filterSize))) {
				
				appearance = false;
				break;
			}
		}
		queriesCompleteTime = queriesCompleteTime +System.currentTimeMillis() - startTime;
		return appearance;
	}
	
	/**
	 * 64 bit Java port of http://www.isthe.com/chongo/src/fnv/hash_64a.c
	 */
	BigInteger hash64 (String k) {
		BigInteger INIT64 = new BigInteger("cbf29ce484222325", 16);
		BigInteger PRIME64 = new BigInteger("100000001b3", 16);
		BigInteger hash = INIT64;
		BigInteger m = new BigInteger("2").pow(64);
		//long FNV1_64_INIT = 0xcbf29ce484222325L;
		//long FNV1_PRIME_64 = 0x100000001b3L;
		//long rv = FNV1_64_INIT;
		//int len = k.length();
		for (int i = 0; i < k.length(); i++) {
			hash = hash.xor(BigInteger.valueOf((int) k.charAt(i)));
			hash = hash.multiply(PRIME64).mod(m);
			//rv ^= k.charAt(i);
			//rv *= FNV1_PRIME_64;
		}
		return hash;
		//return rv;
	}


}
