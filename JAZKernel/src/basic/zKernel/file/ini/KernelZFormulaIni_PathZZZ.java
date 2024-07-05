package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaIni_PathZZZ<T>  extends AbstractKernelUseObjectZZZ<T> implements IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = -6403139308573148654L;
	public static String sTAG_NAME = ""; //Hier kein Tag 
	private FileIniZZZ objFileIni=null;
		
	public KernelZFormulaIni_PathZZZ() throws ExceptionZZZ{		
		KernelExpressionIniPathNew_(null, null);
	}
	
	public KernelZFormulaIni_PathZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniPathNew_(objFileIni, null);
	}
	
	public KernelZFormulaIni_PathZZZ(FileIniZZZ objFileIni,String[] saFlag) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniPathNew_(objFileIni, saFlag);
	}
	
	public KernelZFormulaIni_PathZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniPathNew_(objFileIni, null);
	}
	
	public KernelZFormulaIni_PathZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniPathNew_(objFileIni, saFlag);
	}
	
	
	private boolean KernelExpressionIniPathNew_(FileIniZZZ objFileIni, String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{		
	 			//setzen der übergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}														
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}										
				}	
				this.setFileIni(objFileIni);
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	
	public Vector<String>computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Nun die Section suchen
			Vector<String>vecSection = this.computeExpressionFirstVector(sLineWithExpression);	
								
			String sSection = (String) vecSection.get(1);
			String sRest = (String) vecSection.get(2);//Zu diesem Zeitpunkt eigentlich der Rest...
			String sProperty = "";
			if(StringZZZ.contains(sRest,"</Z>")) {
				sProperty = StringZZZ.left(sRest, "</Z>");
			}else {
				sProperty = sRest;
			}
			
			
			if(StringZZZ.contains(sProperty,"</Z:val>")) {      //Wenn der Pfad Bestandteil einer Mathematischen Formel ist, also eine Section mit einem "Wert".
				sProperty = StringZZZ.left(sProperty, "</Z:val>");
			}
			
			String sBefore = (String) vecSection.get(0);
			sRest = StringZZZ.right(sRest, sProperty);
						
			if(StringZZZ.isEmpty(sSection) | StringZZZ.isEmpty(sProperty)){
				//Dann ist das nicht ein richtig konfigurierter Pfad, also unverändert zurueckgeben.
				vecReturn.add(0,"");
				vecReturn.add(1,sLineWithExpression);
				vecReturn.add(2,"");
				break main;
			}

				//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
//				String sMathValueTag = KernelZFormulaMath_ValueZZZ.computeExpressionTagClosing(KernelZFormulaMath_ValueZZZ.sTAG_NAME);
//				if(StringZZZ.contains(sRest, sMathValueTag)){
//					sBefore = (String) vecSection.get(0);
//					sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
//					//sProperty = StringZZZ.left(sProperty, sMathValueTag);												
//				}
									
				FileIniZZZ objFileIni = this.getFileIni();
				if(objFileIni==null){
					ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//20080109: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
				//20230316: Aber, jetzt ist es allgemeingültiger nicht eine konkrete SystemNumber vorzugeben. Darum null dafür.
				//          Dann werden alle Sections durchsucht
				//String sSystemNr = this.getKernelObject().getSystemNumber();					
				//String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
				String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, null).getValue();
				
				//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
				if(!StringZZZ.isEmpty(sValue)){
					if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
					vecReturn.add(0, sBefore);
					
					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
					vecReturn.add(1, sValue);
					
					if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
					vecReturn.add(2, sRest);
					
					//Z-Tags "aus der Mitte entfernen"... Wichtig z.B. für Z:JavaCall Tags
					String sTagStart="<Z>";
					String sTagEnd="</Z>";
					KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
					
				}//end if sValue!=null
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
	public Vector<String> computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();		
		main:{
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false,false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return KernelZFormulaIni_PathZZZ.sTAG_NAME;
	}
	public static String getExpressionTagStarting(){
		return "[";
	}
	public static String getExpressionTagClosing(){
		return "]"; 
	}
	public static String getExpressionTagEmpty(){
		return "[/]";
	}
	
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}

	//### Aus Interface IKernelExpressionIniZZZ
	@Override
	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
		boolean bReturn=false;
		
		//Hier noch was Relevantes für die KernelExpressionIniConverter-Klasse finden.
//		if(StringZZZ.isEmpty(sToProof)){
//			bReturn = true;
//		}
		return bReturn;
	}
	
	@Override
	public boolean isStringForComputeRelevant(String sExpressionToProof)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#compute(java.lang.String)
	 */
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//nun nur den Wert an Index 1 zurückgeben
			//...ist falsch für Formeln, die "aufeinander folgen" sReturn = (String) vecAll.get(1);
			sReturn = VectorZZZ.implode(vecAll);
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
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			Vector<String>vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorZZZ.implode(vecAll);
			
		}//end main:
		return sReturn;
	}

	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	
}//End class