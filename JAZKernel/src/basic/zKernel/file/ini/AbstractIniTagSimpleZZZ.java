package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

/** Merke: Einfache Tags sind nicht verschachtelt.
 *         => 1. Bei einer compute() Berechnung wird nur der Inhalt des Tags zurückgegeben. 
 *               Sie haben also keine Formel, wohl aber eine "Expression"
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 20.07.2024, 08:03:01
 * 
 */
public abstract class AbstractIniTagSimpleZZZ<T>  extends AbstractIniTagBasicZZZ<T> implements IConvertEnabledZZZ{
	private static final long serialVersionUID = -5785934791199206030L;
	
	public AbstractIniTagSimpleZZZ() throws ExceptionZZZ{
		super();
		AbstractKernelIniTagNew_();
	}
		
	private boolean AbstractKernelIniTagNew_() throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
		
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	//### aus IConvertableZZZ
	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		return sLine;
	}	
	
	/* IDEE: convertable != parseable.
    convertable bedeutet DER GANZE STRING Wird ersetzt, also nur wenn nix davor oder dahniter steht.
    parsable würde dann lediglich den Wert aus der Mitte (s. Vector.getIndex(1) ) durch ein Leerzeichen ersetzen
	
	* 
	* (non-Javadoc)
	* @see basic.zKernel.file.ini.AbstractIniTagSimpleZZZ#isConvertRelevant(java.lang.String)
	*/
	@Override
	public boolean isConvertRelevant(String sLineWithExpression) throws ExceptionZZZ {
	//Nein return this.isParseRelevant(sStringToProof);
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
		
		bReturn = this.getEmpty().equalsIgnoreCase(sLineWithExpression);
		if(bReturn) break main;
	
		
		bReturn = XmlUtilZZZ.isSurroundedByTag(sLineWithExpression, this.getTagStarting(), this.getTagClosing());		
	}//end main
	return bReturn;
	}
	
	
	//### aus IExpressionUserZZZ
	@Override
	public String parseAsExpression() throws ExceptionZZZ {
		String sExpression = this.getValue();
		return this.parseAsExpression(sExpression);
	}	

	@Override
	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(!this.isParse(sLineWithExpression)) break main;
			
			Vector<String> vecAll = this.parseFirstVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorUtilZZZ.implode(vecAll);
			this.setValue(vecAll.get(1));
			
		}//end main:
		return sReturn;
	}	
	
//	/* (non-Javadoc)
//	 * @see basic.zKernel.file.ini.AbstractIniTagBasicZZZ#isExpression(java.lang.String)
//	 */
//	@Override
//	public boolean isExpression(String sLine) throws ExceptionZZZ{
//		return KernelConfigSectionEntryUtilZZZ.isExpression(sLine, this.getName());
//	}
	
	
	//### aus IIniTagWithExpressionZZZ (siehe auch: IExpressionUserZZZ)
	@Override
	public Vector3ZZZ<String>parseAllVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.parseFirstVectorAsExpression(sLineWithExpression);			
			
		}
		return vecReturn;
	}
	
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	@Override
	public Vector3ZZZ<String>parseFirstVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), true, false);
		}
		return vecReturn;
	}
	
	
	
	
//	@Override
//	public String[] parseAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
//		String[] saReturn = null; //new String[];//sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			String sDelimiter;
//			if(StringZZZ.isEmpty(sDelimiterIn)) {
//				sDelimiter = IniFile.sINI_MULTIVALUE_SEPARATOR; 
//			}else {
//				sDelimiter = sDelimiterIn;
//			}
//				   
//			String sExpressionTotal = this.parse(sLineWithExpression); //Hole erst einmal den Kernel-Tag-Wert.
//			if(!StringZZZ.isEmpty(sExpressionTotal)) {
//				String[] saExpression = StringZZZ.explode(sExpressionTotal, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
//				
//				String sValue = null;
//				ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>();
//				for(String sExpression : saExpression) {
//					
//					//Nur für den etwas komplizierteren Fall einer Verschachtelung ...
//					if(this.isExpression(sExpression)){
//						sValue = this.parse(sExpression);
//					}else {
//						sValue = sExpression;
//					}
//					listasValue.add(sValue);
//				}
//								
//				saReturn = listasValue.toStringArray();				
//			}
//		}//end main:
//		return saReturn;
//	}
	
	
	
	

	
}//End class