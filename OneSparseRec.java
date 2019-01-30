/* *************************************************************************
 * 	Student Name:    Zhuohan Xie
 *  Student ID:   	 871089
 *
 *  Description:  Provides an OneSparseRec class. It has five functions
 *				  in total. 
 *				  It implements the 1-Sparse Recovery scheme for the project.				  
 *
 *  Written:       2/10/2018
 *  Last updated:  12/10/2018
 *
 *
 ***************************************************************************/

import java.math.BigInteger;

public class OneSparseRec {
	
	/*
	* phi is sum of weights and iota indicates weighted sum of item identifiers 
	* p is a suitably large prime and z is randomly chosen from [0...p-1]
	* tau indicates sum of weights times z to the power of item identifier mod p
	* notations all come from paper 
	*/
	
    private long phi = 0;
    private long iota = 0;
    private int p;
    private int z;
    private long tau = 0;
    
    /*
 	* 	Constructor takes no argument and set the prime number for p and
 	* 	z chooses uniformly randomly from [0...p-1]
 	*/

    public OneSparseRec() {
       // fill in
    	p = 6619;
        z = StdRandom.uniform(p);
    }
    
    /*
 	* 	add function takes one <index, value> pair and updates stored
 	* 	values, iota, phi and tau. 
 	*/

    public void add(int index, int value) {
    	iota += (long)index * (long)value;
    	BigInteger bigindex = BigInteger.valueOf(index);
    	BigInteger bigz = BigInteger.valueOf(z);
    	BigInteger bigp = BigInteger.valueOf(p);
    	BigInteger bigvalue = bigz.modPow(bigindex, bigp);
    	long secondvalue = bigvalue.longValue();
    	long plus;
    	plus = (long)value * secondvalue % (long)p;
    	if(plus < 0) plus += (long)p;
    	tau += plus;
    	tau %= (long)p;
    	phi += (long)value;
    	
    }
    
    /*
 	* 	isOneSparse function checks the sparsity using the equation in the 
 	* 	paper. 
 	*/

    public boolean isOneSparse() {
    	if(phi == 0) {
    		return false;
    	}
    	BigInteger bigz = BigInteger.valueOf(z);
    	BigInteger bigp = BigInteger.valueOf(p);
    	BigInteger exponent = BigInteger.valueOf(iota / phi);
    	BigInteger bigvalue = bigz.modPow(exponent, bigp);
    	long value = bigvalue.longValue();
    	value = (phi  * value) % (long)p;
    	if(value < 0) value += (long)p; 
        if(tau == value) return true;
        else return false;
    	
    }
    
    /*
 	* 	oneSparseRecTest function returns the expected the result
 	*/

    public String oneSparseTest() {
        if(isOneSparse()) return +iota/phi +" "+ phi;
        else if(phi == 0 && iota == 0 && tau == 0) return "zero";
        else return "more";
    }
    
    /*
 	* 	getSparse function returns number as result, for convience
 	* 	of SSparseRec Class 
 	*/
    
    public int getSparse() {
    	if(isOneSparse()) return 1;
        else if(phi == 0 && iota == 0 && tau == 0) return 0;
        else return 2;
    }
    
    /*
 	* 	getPhi function returns stored phi.
 	*/
    
    public long getPhi() {
    	return this.phi;
    }
    
    /*
 	* 	getIota function returns stored iota.
 	*/
    
    public long getIota() {
    	return this.iota;
    }
    
}
