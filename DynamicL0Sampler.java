/* *************************************************************************
 * 	Student Name:    Zhuohan Xie
 *  Student ID:   	 871089
 *
 *  Description:  Provides an DynamicL0Sampler class. It has four functions
 *				  in total. 
 *				  It implements the DynamicL0Sampler scheme for the project.				  
 *
 *  Written:       2/10/2018
 *  Last updated:  12/10/2018
 *
 *
 ***************************************************************************/
import java.util.HashMap;


public class DynamicL0Sampler implements Sampler<Integer> {
	
	/*
	* one universe number n, the error rate delta and a SSparseRec array 
	* stores all and a hash function chosen from k-wise independent family.
	*/
	
    // size of universe
    private int n;
    
    private double delta = 0.01;

    private SSparseRec[] recovery;

    // selected from k-wise independent family of hash functions with k >= s/2,
    // I set k = s/2 for simplicity.    
    private Hash hash;
    
    /*
 	* 	Constructor takes the universe number n, and set subset number subsets
 	*  	and sparsity number s and set recovery arrays. I use hash to the range
 	*   of n^2 to make sure it will be within a long type.
 	*/	
    
    public DynamicL0Sampler(int n) {
        this.n = n;
        int subsets = (int)Math.ceil(Math.log(n)/Math.log(2));
        int s = (int)(4 *Math.ceil(Math.log(1/delta)/Math.log(2)));
        recovery = new SSparseRec[subsets];
        for(int i = 0; i < subsets; i++) {
        	recovery[i] = new SSparseRec(4, s);
        }
        hash = new Hash((long)n*(long)n, s/2);
        
    }
    
    /*
 	* 	add function takes one <index, value> pair and hash the index 
 	* 	using the k-wise hash function, and send this pair to different
 	* 	subsets based on the hash value. 
 	*/

    public void add(Integer index, int value) {
    	long hindex = hash.hash(index);
    	for(int j = 0; j < recovery.length; j++) {
    		if(hindex < (double)n*n/Math.pow(2, j))
    		recovery[j].add(index, value);
    	}
    }
    
    /*
 	* 	output function outputs the sampled item when one subset successfully
 	* 	recovered, otherwise, return null, which means it fails in this 
 	* 	process.
 	*/
    
    public Integer output() {
        
    	for(int i = 0; i < recovery.length; i++) {
    		if(recovery[i].isSSparse()) {
    			HashMap<Integer, Integer> hashmap = recovery[i].getvalues();
    			long minhvalue = (long)n * (long)n;
    			int result = 0;
    			for(HashMap.Entry<Integer, Integer> entry: 
    				hashmap.entrySet()) {
    				if(hash.hash(entry.getKey()) < minhvalue) {
    					minhvalue = hash.hash(entry.getKey());
    					result = entry.getKey();
    				}
    			}
    			return result;
    		}
    	}
    	
        return null;
    }

}
