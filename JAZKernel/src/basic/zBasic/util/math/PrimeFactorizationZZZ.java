package basic.zBasic.util.math;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.util.abstractList.ArrayListUtilZZZ;

public class PrimeFactorizationZZZ {

    public static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();

        main:{
        	if(n==0) break main;
        	if(n==1) {
        		factors.add(1);
        		break main;
        	}
        	
	        // Zerlege die Zahl in ihre Primfaktoren
	        while (n % 2 == 0) {
	            factors.add(2);
	            n /= 2;
	        }
	
	        for (int i = 3; i <= Math.sqrt(n); i += 2) {
	            while (n % i == 0) {
	                factors.add(i);
	                n /= i;
	            }
	        }
	
	        if (n > 2) {
	            factors.add(n);
	        }
	        }//end main:
        return factors;
    }
    
    public static int[] primeFactorsAsIntArray(int n) {
    	List<Integer>lista=primeFactors(n);
    	return ArrayListUtilZZZ.toIntArray((ArrayList<Integer>) lista);
    }

    public static void main(String[] args) {
       // int iNumber = 84;
    	for(int iNumber = 1;iNumber<=30;iNumber++) {
            List<Integer> primeFactors = primeFactors(iNumber);
            System.out.println("Primfaktoren von " + iNumber + ":\t" + primeFactors);
    	}

    }
}

