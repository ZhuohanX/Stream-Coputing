/* *************************************************************************
 * 	Student Name:    Zhuohan Xie
 *  Student ID:   	 871089
 *
 *  Description:  Provides an InsertL0Sampler class. It has three functions
 *				  in total. 
 *				  It implements the InsertL0Sampler scheme for the project.				  
 *
 *  Written:       2/10/2018
 *  Last updated:  12/10/2018
 *
 *
 ***************************************************************************/


public class InsertL0Sampler implements Sampler<Integer> {
    
	/*
	* one universe number n, the minimum hash value so far and the sampled
	* index, and one hash function.
	*/
	
    private long range;
    private long minhashvalue;
    private int index;

    private Hash hash;
    
    /*
 	* 	Constructor takes the universe number n, and set the range to n^2 to 
 	* 	fit into a long type and construct a hash function from k-wise hash 
 	* 	families.
 	*/	

    public InsertL0Sampler(int n) {
       
        range = (long)n * (long)n;
        minhashvalue = range;
        hash = new Hash(range, 3);
        // fill in

    }
    
    /*
 	* 	add function takes one <index, value> pair and hash the index 
 	* 	using the k-wise hash function, and compare the hash calue with
 	* 	the current minimum one, if smaller, then update.
 	*/

    public void add(Integer index, int value) {
        
    	long hashvalue = hash.hash(index);
    	if(hashvalue < minhashvalue) {
    		minhashvalue = hashvalue;
    		this.index = index;
    	}

    }
    
    /*
 	* 	output function outputs the stored sampled item so far
 	*/

	public Integer output() {

        return index;
    }

}
