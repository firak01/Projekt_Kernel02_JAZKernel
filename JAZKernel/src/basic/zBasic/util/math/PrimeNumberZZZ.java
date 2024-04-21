package basic.zBasic.util.math;

import java.util.List;

public class PrimeNumberZZZ {

	public static void main(String[] args) {
		    int number = 84;
		    for(int i = 1; i<=10; i++) {
		    	int iPrimeSum = primePosition(number,i);
		    	System.out.println("Primsumme für " + number + " an Position\t"+i+" = " + iPrimeSum);
		    }
	}
	
	public static int primePosition(int iNumber, int iPosition) {
		int iReturn = 0;
		main:{
			int iPrime = getNthPrime(iPosition);
			iReturn = iNumber*iPrime;
		}//end main:
		return iReturn;
	}
	
	public static int computePositionValueFromPrime(int iPrimedValue, int iPosition) {
		//20240421: Experimentell... so geht das aber nicht...
		int iReturn = 0;
		main:{
			int iPrime = getNthPrime(iPosition);
			//float f = (float)iPrimedValue/(float)iPrime;		
			//System.out.println(f);
			
			iReturn = iPrimedValue % iPrime;
		}//end main:
		return iReturn;
	}
	
	public static int getNthPrime(int iPosition) {
		int iReturn = 0;
		main:{
			if(iPosition<=0)break main;
			
			int[] ia = PrimeFinderZZZ.asIntegerArray();
			if(iPosition>ia.length)break main;
			
			iReturn = ia[iPosition];
		}//end main:
		return iReturn;
	}

}
