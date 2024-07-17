package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Warum diese Klasse existiert:
 *  Beispielsweise NULL-Wert in der Ini-Datei-Konfiguarition bedeutet "Eclipse Projekteordner".
 *  Man könnte den Eintrag auch weglassen, aber dann verliert man:
 *  a) Die Möglichkeit, dass ein "Unter"-System den Pfadeintrag der Hauptkonfigurationsdatei überschreiben kann.
 *  b) Den Performancegewinn, der dadurch erzielt wird, dass bei der Suche nach dem konfigurierten Parameter 
 *     auch ein NULL - Wert gefunden wird (halt der <z:Null/> Tag). Wenn etwas gefunden wird, dann wird auch diese Parametersuche beendet.   
 * @param <T>
 */
//public class ZTagFormulaIni_NullZZZ<T>  extends AbstractKernelUseObjectZZZ<T> implements IKernelZFormulaIniZZZ{
public class ZTagFormulaIni_NullZZZ<T>  extends AbstractKernelIniTagCascadedZZZ<T>{ // implements IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = -3773890882498236252L;
	public static String sTAG_NAME = "z:Null"; 
	private FileIniZZZ objFileIni=null;
		
	public ZTagFormulaIni_NullZZZ() throws ExceptionZZZ{	
		super("init");
		KernelExpressionIniNullNew_(null);
	}
	
	public ZTagFormulaIni_NullZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	public ZTagFormulaIni_NullZZZ(FileIniZZZ objFileIni,String[] saFlag) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	public ZTagFormulaIni_NullZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	public ZTagFormulaIni_NullZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	
	private boolean KernelExpressionIniNullNew_(FileIniZZZ objFileIni) throws ExceptionZZZ {
	 boolean bReturn = false; 
	 main:{			 																
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}										
				
			this.setFileIni(objFileIni);
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
			
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Nun die Section suchen
			Vector vecSection = this.computeExpressionFirstVector(sLineWithExpression);	
								
			String sSection = (String) vecSection.get(1);
			String sProperty = (String) vecSection.get(2);
			String sBefore = "";
			String sRest = "";
						
			if(!(StringZZZ.isEmpty(sSection) || StringZZZ.isEmpty(sProperty))){

					//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
					String sMathValueTag = XmlUtilZZZ.computeTagPartClosing(ZTagFormulaMath_ValueZZZ.sTAG_NAME);
					if(StringZZZ.contains(sProperty, sMathValueTag)){
						sBefore = (String) vecSection.get(0);
						sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
						sProperty = StringZZZ.left(sProperty, sMathValueTag);												
					}
										
					FileIniZZZ objFileIni = this.getFileIni();
					if(objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					//20080109 FGL: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
					String sSystemNr = this.getKernelObject().getSystemNumber();
					String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
					
					//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
					if(sValue!=null){
						
						//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
						if(!StringZZZ.isEmpty(sBefore)){
							if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
							vecReturn.add(0, sBefore);
						}else{
							vecReturn.add(0,"");
						}
																
						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
						vecReturn.add(1, sValue);
						
						if(vecReturn.size()>=3) vecReturn.removeElementAt(2); //Immer den Namen der Property löschen....
						if(!StringZZZ.isEmpty(sRest)){							
							vecReturn.add(2, sRest); //Fallls vorhanden einen Restwert eintragen.
						}else{
							vecReturn.add(2,"");
						}		
				}//end if sValue!=null
									
			}//end if isempty(sSection)
			
		}//end main:
		return vecReturn;
	}
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			vecReturn = StringZZZ.vecMid(sLineWithExpression, XmlUtilZZZ.computeTagPartStarting(ZTagFormulaIni_NullZZZ.sTAG_NAME), XmlUtilZZZ.computeTagPartClosing(ZTagFormulaIni_NullZZZ.sTAG_NAME), false,false);
		}
		return vecReturn;
	}
	
	//###### Getter / Setter
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface
//	public static String getExpressionTagName(){
//		return ZTagFormulaIni_NullZZZ.sTAG_NAME;
//	}
//	public static String getExpressionTagStarting(){
//		return "<" + ZTagFormulaIni_NullZZZ.getExpressionTagName() + ">";
//	}
//	public static String getExpressionTagClosing(){
//		return "</" + ZTagFormulaIni_NullZZZ.getExpressionTagName() + ">"; 
//	}	
//	public static String getExpressionTagEmpty(){
//		return "<" + ZTagFormulaIni_NullZZZ.getExpressionTagName() + "/>";
//	}
	
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}

	
	//### Aus Interface IKernelExpressionIniZZZ
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		boolean bReturn=false;
		if(sStringToProof==null) bReturn = true;
		return bReturn;
	}
	
	@Override
	public boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
		if(StringZZZ.isEmptyTrimmed(sExpressionToProof)) break main;
		if(ZTagFormulaIni_NullZZZ.getExpressionTagEmpty().equalsIgnoreCase(sExpressionToProof)){
			bReturn = true;
			break main;
		}
	}//end main
		return bReturn;
	}
	
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(!this.isStringForComputeRelevant(sLineWithExpression)) break main;
			
			//Hier einfach NULL zurückgeben
			return null;
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public String[] computeAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(!this.isStringForComputeRelevant(sLineWithExpression)) break main;
						
			String sDelimiter;
			if(StringZZZ.isEmpty(sDelimiterIn)) {
				sDelimiter = IniFile.sINI_MULTIVALUE_SEPARATOR; 
			}else {
				sDelimiter = sDelimiterIn;
			}
			
			String[] saExpression = StringZZZ.explode(sLineWithExpression, sDelimiter);
			
			//Hier einfach das Leerzeichen zurückgeben
			String sValue = null;
			ArrayListExtendedZZZ listasValue = new ArrayListExtendedZZZ();
			for(String sExpression : saExpression) {
				sValue = this.compute(sExpression);
				listasValue.add(sValue);
			}
			saReturn = listasValue.toStringArray();
			
		}//end main:
		return saReturn;
	}
	
	@Override
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(!this.isStringForComputeRelevant(sLineWithExpression)) break main;
			
			//Hier einfach eine leere Expression zurückgeben
			sReturn = "<Z/>";
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public String convert(String sLineWithoutExpression) throws ExceptionZZZ{
		String sReturn = sLineWithoutExpression;
		main:{
			if(!this.isStringForConvertRelevant(sLineWithoutExpression)) break main;
			
			//Hier einfach den Leeren-Tag zurückgeben
			sReturn = ZTagFormulaIni_NullZZZ.getExpressionTagEmpty();
						
		}//end main
		return sReturn;
	}


	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return ZTagFormulaIni_NullZZZ.sTAG_NAME;
	}
}//End class