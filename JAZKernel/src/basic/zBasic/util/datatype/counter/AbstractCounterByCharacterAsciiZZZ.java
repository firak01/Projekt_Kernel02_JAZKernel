package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

/**
 *  
 * 
 * @author Fritz Lindhauer, 08.03.2019, 07:40:07
 * 
 * Lösung basiert darauf, dass man mit Character-Zeichen  rechnen kann.
 * 
	 Eine einfacheres Beispiel ist: 	 https://forum.chip.de/discussion/832166/in-while-schleife-alphabet-hochzaehlen

		//Characters können wie Integer ebenfalls hochgezählt werden,somit entfällt die Suche nach dem Ascii-Wert...so müsste es in etwa dann aussehen 
		public static void main (String args[]) {
		
		   char anfang='a';
		   char ende='z';
		
		   String AbisZ="";
		
		   while (anfang<=ende) {
		   
		      AbisZ=AbisZ+anfang;
		      anfang++;
		
		    }

 * 
 */
public abstract class AbstractCounterByCharacterAsciiZZZ implements IConstantZZZ, ICounterStringZZZ{
	private String sSuffix="";
	private String sPrefix="";	
	private int iValueCurrent=-1; //Die berechneten Strings nicht speichern. Nur den Wert. Die Umrechnung wird dann den jeweiligen Static-Methoden überlassen.
	
	
	public AbstractCounterByCharacterAsciiZZZ(){		
	}
	
	public AbstractCounterByCharacterAsciiZZZ(int iStartingValue){
		this.setValueCurrent(iStartingValue);
	}
	
	//### Aus Interface
	public abstract String getStringFor(int iValue) throws ExceptionZZZ;	
	
	
	public int getValueCurrent(){
		return this.iValueCurrent;
	}
	public void setValueCurrent(int iValue){
		this.iValueCurrent=iValue;
	}
	

	public String getStringSuffix(){
		return this.sSuffix;
	}
	public void setStringSuffix(String sSuffix){
		this.sSuffix = sSuffix;
	}
	
	public String getStringPrefix(){
		return this.sPrefix;
	}
	public void setStringPrefix (String sPrefix){
		this.sPrefix=sPrefix;
	}
	public String getString() throws ExceptionZZZ{
		return this.getStringPrefix() + this.current() + this.getStringSuffix();
	}
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++
	public String current() throws ExceptionZZZ {
		int iValueCurrent = this.getValueCurrent();
		return this.getStringFor(iValueCurrent);
	}
	public String increased() throws ExceptionZZZ{
		//Gibt den wert wie er sein würde, ohne den Zähler zu erhöhen
		int iValueCurrent = this.getValueCurrent();
		iValueCurrent++;
		return this.getStringFor(iValueCurrent);
	}
	public String next() throws ExceptionZZZ{
		//Gibt den wert mit dem Zähler erhöht
		int iValueCurrent = this.getValueCurrent();
		iValueCurrent++;
		this.setValueCurrent(iValueCurrent);
		return this.current();	
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	public String getStringNext() throws ExceptionZZZ{
		//DAS ERHÖHT DEN ZÄHLER
		int iCurrent = this.getValueCurrent();
		iCurrent++;
		this.setValueCurrent(iCurrent);
		
		String s= this.getString();
		return s;		
	}
	
	public String getStringIncreased() throws ExceptionZZZ{
		return this.getStringPrefix() + this.increased() + this.getStringSuffix();
	}
	
	//+++
		public String toString(){
			String s = this.getClass().getName();
			try {
				s+=" : " + this.getString();
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return s;
		}
		//+++
		

	
}
