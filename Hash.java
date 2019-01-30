/* *************************************************************************
 * 	Student Name:    Zhuohan Xie
 *  Student ID:   	 871089
 *
 *  Description:  Provides an SSparseRec class. It has five functions
 *				  in total. 
 *				  It implements the S-Sparse Recovery scheme for the project.				  
 *
 *  Written:       2/10/2018
 *  Last updated:  12/10/2018
 *
 *
 ***************************************************************************/
import java.math.BigInteger;

public class Hash {
	
	/*
	* one long type large prime p, and k mean k-wise, long array for all 
	* polynomial cofactors and a long type range.
	*/
	
	private long p = (long)(Math.pow(2, 61) - 1);
	private long[] a;
	private long range;
	
	/*
 	* 	Constructor takes range and k number for setting all cofactors.
 	*/	
	
	public Hash(long range, int k) {
		a = new long[k];
		for(int i = 0; i < k; i++) {
			a[i] = (long)StdRandom.uniform(1, (double)p-1)+1;
		}
		this.range = range;
	}
	
	/*
 	* 	hash function takes an int number and returns a long type hashed 
 	* 	value. Polynomial calculations are worked here.
 	*/
	
	public long hash(int x) {
		BigInteger bighvalue = BigInteger.valueOf(0);
		BigInteger bigx = BigInteger.valueOf(x);
		BigInteger bigp = BigInteger.valueOf(p);
		BigInteger bigr = BigInteger.valueOf(range);
		for(int i = 0; i < a.length; i ++) {
			BigInteger bigco = BigInteger.valueOf(a[i]);
			BigInteger expo = BigInteger.valueOf(i);
			BigInteger plus = 
					bigco.multiply(bigx.modPow(expo, bigp)).mod(bigp);
			bighvalue = bighvalue.add(plus).mod(bigp);
		}
		bighvalue = bighvalue.mod(bigr);
		return bighvalue.longValue();
	}
		
		
}
