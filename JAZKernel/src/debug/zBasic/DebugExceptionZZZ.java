package debug.zBasic;

import basic.zBasic.ExceptionZZZ;

public class DebugExceptionZZZ {

	/** TODO What the method does.
	 * @param args
	 * 
	 * lindhauer; 19.03.2008 09:49:37
	 */
	public static void main(String[] args) {
		try{
			ExceptionZZZ ez = new ExceptionZZZ("das ist ein test");
			throw ez;
		}catch(Throwable t){
			//String sResult = ExceptionZZZ.computeStringFromStackTrace(t);
			String sResult = ExceptionZZZ.computeHtmlFromStackTrace(t);
			System.out.println(sResult);
		}
	}

}
