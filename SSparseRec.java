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
import java.util.HashMap;

public class SSparseRec {
	
	/*
	* one hash function array hashFamily stores all hash functions for each
	* copy, net is a 2d-array which stores all one-sparse scheme. hashmap is
	* used to store recovered <item, frequency> 
	*/

    private int r, s;
    private Hash[] hashFamily;
    private OneSparseRec[][] net;
    private HashMap<Integer, Integer> hashmap;
    
    /*
 	* 	Constructor takes a copy number r and a sparsity as input, 
 	* 	and use these two to construct all data structure employed in this 
 	* 	algorithm.
 	*/	

    public SSparseRec(int r, int sparsity){
        this.r = r;
        this.s = sparsity;
        
        initialise();
    }
    
    /*
 	* 	initialise function initialises all original data, hash function 
 	* 	and one sparse scheme.
 	*/	

    private void initialise() {
        // fill in
    	hashFamily = new Hash[r];
    	net = new OneSparseRec[r][2*s];
    	hashmap = new HashMap<Integer, Integer>();
    	for(int i = 0; i < r; i++) {
    		hashFamily[i] = new Hash((long)2*s, 3);
    	}
    	for(int i = 0; i < r; i++) {
    		for(int j = 0; j < 2*s; j++) {
    			net[i][j] = new OneSparseRec();
    		}
    	}
    	
    }
    
    /*
 	* 	add function takes one <index, value> pair and hash the index 
 	* 	from different hash functions to different columns of copies. 
 	*/

    public void add(int index, int value) {
       // fill in
    	int column;
    	for(int i = 0; i < r; i++) {
    		column = (int)hashFamily[i].hash(index);
    		net[i][column].add(index, value);
    	}
    }
    
    /*
 	* 	isSparse function calls sparseRecTest(), when the result is 
 	* 	s-sparse recovery, return true, otherwise, return false. 
 	*/

    public boolean isSSparse() {
        // fill in
    	String result = sparseRecTest();
    	if(result.equals("more")||result.equals("zero"))
    			return false;
    	else return true;
    }
    
    /*
 	* 	sparseRecTest function go through all copies, it will return
 	* 	false directly when one copy has one cell returns more.
 	* 	When no cell returns more in one copy, count the pair we store
 	* 	to determine how many coordinates they are.
 	*/
    
    public String sparseRecTest() {
    	String result = "";
    	long phi, iota, index;
    	for(int i = 0; i < r; i++) {
    		boolean onemore = false;
    		for(int j = 0; j < 2*s; j++) {
    			if(net[i][j].getSparse() == 1) {
    				phi = net[i][j].getPhi();
					iota = net[i][j].getIota();
					index = iota / phi;
    				hashmap.put((int)index, (int)phi);
    			}
    			else if(net[i][j].getSparse() == 2) {
    				hashmap.clear();
    				onemore = true;
    				break;
    			}
    		}
    		if(!onemore) {
    	    	if(hashmap.size() == 0) return "zero";
    	    	else if(hashmap.size() > s) return "more";
    	    	else {
    	    		for(HashMap.Entry<Integer, Integer> entry:
    	    			hashmap.entrySet()) {
    	    			result += (entry.getKey() + " " 
    	    			+ entry.getValue()+'\n');
    	    		}
    	    		result = result.substring(0, result.length()-1);
    	    		return result;
    	    	}
    		}
    	
    }
    	return "more";
    }
    
    /*
 	* 	getvalues function return the all pairs stored in the scheme, it's 
 	* 	for convience of DynamicL0Sampler Class
 	*/
   
    public HashMap<Integer, Integer> getvalues(){
    	return this.hashmap;
    }


}
