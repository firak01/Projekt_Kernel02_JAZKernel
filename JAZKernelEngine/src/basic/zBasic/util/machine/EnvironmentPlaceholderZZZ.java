package basic.zBasic.util.machine;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Platzhalter fuer Umgebungsvariablen
 *  Es gilt folgende Syntax:
 		// Der Platzhalter endet mit }
 		// 
   		// Es gibt ggfs. einen Wunschwert hinter einem Pipe | für den Fall, dass der Umgebungsvariablenwert leer ist
		// 
		// TODOGOON:
		// Der Einleitungsteil des Platzhalters definiert wie mit Leerwerten (ohne Wunschwert) umgegangen wird.
		// Er beginnt mit  ${ ... wenn leer dann gib den Platzhalternamen mit vorangestelltem $ aus oder den Wunschwert
		//                $.{ ... wenn leer, dann gib den Platzhalternamen aus oder den Wunschwert
		//                $!{ ... wenn leer, dann gib einen Leerstring aus oder den Wunschwert
		//     $.!{ oder $!.{ ... wenn leer, dann gib den Plathalternamen mit vorangestelltem $ aus oder den Wunschwert
		//
		// also wenn system.getEnv(blabla) leer ist
		// $.{blabla}        ==> blabla
		// $.{blabla|toll1}  ==> toll1
		//
		// $!{blabla}        ==> Leerstring
		// $!{blabla|toll2}  ==> toll2
		//
		// $!.{blabla}       ==> $blabla
		// $!.{blabla|toll3} ==> toll3
		//
		// $.! siehe $!.
		//
		// Merke: ${blabla} führt unter Eclipse zu einem Fehler in der .launch-Konfiguration,
		//        wenn die Umgebungsvariable nicht definiert ist. Darum dort $. oder $!, etc.
		// 
		// TODOGOON:
		// Merke: Der Wunschwert kann wiederum ein Platzhalter sein.
		//        Die Wertermittlung wird durch eine Schleife gejagt, und somit koennen alternative Umgebungsvariablen abgefragt werden.
	    //        Also:
	    //        $.{blabla|$!.{alternativvariable|alternativer alternativvariablenwert}}  ==> ............
 * 
 * @author Fritz Lindhauer
 *
 */
public class EnvironmentPlaceholderZZZ implements IEnvironmentPlaceholderZZZ, IConstantZZZ {
	private String sPlaceholder=null;
	private String sContent=null;
	private String sName=null;
	private String sValue=null;
	
	public EnvironmentPlaceholderZZZ() throws ExceptionZZZ {
		EnvironmentPlaceholderNew_(null);
	}
	
	public EnvironmentPlaceholderZZZ(String sArgument) throws ExceptionZZZ {
		EnvironmentPlaceholderNew_(sArgument);
	}
	
	private boolean EnvironmentPlaceholderNew_(String sArgumentIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sArgumentIn)) break main;
			if(!StringZZZ.startsWith(sArgumentIn, IEnvironmentPlaceholderZZZ.sPREFIX)) break main;
			
			this.setPlaceholder(sArgumentIn); //Weitere Werte werden dann später errechnet
		}//end main:
		return bReturn;
	}
	
	@Override
	public String getPlaceholder() throws ExceptionZZZ{
		return this.sPlaceholder;
	}
	
	@Override
	public void setPlaceholder(String sPlaceholder) throws ExceptionZZZ{
		this.sPlaceholder = sPlaceholder;
	}
	
	@Override
	public String getContent() throws ExceptionZZZ{
		if(this.sContent==null) {
			String sPlaceholder = this.getPlaceholder();
			this.sContent = EnvironmentPlaceholderZZZ.readContent(sPlaceholder);
		}
		return this.sContent;
	}
	
	@Override
	public void setContent(String sContent) throws ExceptionZZZ{		
		this.sContent = sContent;
	}
	
	@Override 
	public String getValue() throws ExceptionZZZ{
		if(this.sValue==null) {
			String sPlaceholder = this.getPlaceholder();
			this.sValue = EnvironmentPlaceholderZZZ.readValue(sPlaceholder);
		}
		return this.sValue;
	}
	
	@Override 
	public void setValue(String sValue) throws ExceptionZZZ{
		this.sValue = sValue;
	}
	
	@Override 
	public String getName() throws ExceptionZZZ{
		if(this.sName==null) {
			String sPlaceholder = this.getPlaceholder();
			this.sName = EnvironmentPlaceholderUtilZZZ.readPlaceholderName(sPlaceholder);
		}
		return this.sName;
	}
	
	@Override 
	public void setName(String sName) throws ExceptionZZZ{
		this.sName = sName;
	}
	
	//#####################
	//Statische Methoden
	public static String readContent(String sArgumentIn) throws ExceptionZZZ{
		return EnvironmentPlaceholderUtilZZZ.readPlaceholderContent(sArgumentIn);
	}
	
	public static String readValue(String sArgumentIn) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sPlaceholdername = EnvironmentPlaceholderUtilZZZ.readPlaceholderName(sArgumentIn);
			if(StringZZZ.isEmpty(sPlaceholdername)) break main;
			
			sReturn = System.getenv(sPlaceholdername);
			if(sReturn==null) {
				sReturn = EnvironmentPlaceholderUtilZZZ.readPlaceholderAlternative(sArgumentIn);
			}
		}//end main:
		return sReturn;
		
	}
	
}
