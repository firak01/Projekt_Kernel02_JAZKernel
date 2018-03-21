package basic.zBasic.util.math;

import java.util.Random;

public class RandomZZZ {
	private static final Random generator = new Random(System.currentTimeMillis());
	private RandomZZZ(){
		//Zum "Verstecken" des Konstruktors		
	}
	
	
	public static final String getRandom (int length) {
		double tenPow = Math.pow (10,length);
		double tenPowMinus = Math.pow (10,length-1);
		double d = tenPow*(generator.nextDouble());
		if (d < (tenPowMinus)) {
			d += tenPowMinus;
		}
		return ""+Math.round(d);
	}
	
	/* generiert einen <b>i</b>-stelligen ZufallsString aus a..z, A..Z, 0..9
	 * @param i
	 * @return
	 */
	public static String getRandomString(int i)
	{
	    char[] ac = {
	        'a', 'b', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
	        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
	        't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 
	        'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
	        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 
	        'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
			'6', '7', '8', '9'
	    };
	    StringBuffer stringbuffer = new StringBuffer();
	    for(int j = 0; j < i; j++) {
	        stringbuffer.append(ac[Math.abs(generator.nextInt()) % ac.length]);
	    }
	
	    return stringbuffer.toString();
	}

	public static int getRandomNumber () {
		return generator.nextInt();
	}
	
	/** 
	 * Zufallszahl zwischen <b>min</b> einschließlich und <b>max</b> einschließlich.
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int getRandomNumber (int min, int max) {
		return min + generator.nextInt (max+1);
	}
}
