package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Merke: Der Path-Tag besteht aus [ oder ] ist damit kein normaler Tag 
 *         und kann also nicht aus AbstractIniTagSimple oder AbstractIniCascadedZZZ erben.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 12.07.2024, 09:26:56 
 */
//public class KernelZFormulaIni_PathZZZ<T>  extends AbstractKernelUseObjectZZZ<T> implements IKernelFileIniUserZZZ, IKernelZFormulaIniZZZ{
public class KernelZFormulaIni_PathZZZ<T>  extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelFileIniUserZZZ, IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = -6403139308573148654L;
	public static String sTAG_NAME = ""; //Hier kein Tag 
		
	public KernelZFormulaIni_PathZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniPathNew_(null);
	}
	
	public KernelZFormulaIni_PathZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	public KernelZFormulaIni_PathZZZ(FileIniZZZ objFileIni,String[] saFlag) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	public KernelZFormulaIni_PathZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	public KernelZFormulaIni_PathZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	
	private boolean KernelExpressionIniPathNew_(FileIniZZZ objFileIni) throws ExceptionZZZ {
	 boolean bReturn = false;
	 main:{			 															
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}										
					
			this.setFileConfigKernelIni(objFileIni);
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	@Override
	public Vector<String>parseAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Nun die Section suchen
			Vector<String>vecSection = this.parseFirstVector(sLineWithExpression);	
								
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
									
				FileIniZZZ objFileIni = this.getFileConfigKernelIni();
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
	@Override
	public Vector<String> parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();		
		main:{
			//vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false,false);
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), "<", false,false); //also bis zum nächsten Tag!!!
			
			String sValue = vecReturn.get(2);			
			if(!StringZZZ.isEmpty(sValue)) {
				//den oben geklauten Anfangstag wieder hinzufuegen
				sValue = "<" + sValue;
			}
			
			if(vecReturn.size()>=3) vecReturn.removeElementAt(2);			
			vecReturn.add(2, sValue);
			
			
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
			if(objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//20080109: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
			//20230316: Aber, jetzt ist es allgemeingültiger nicht eine konkrete SystemNumber vorzugeben. Darum null dafür.
			//          Dann werden alle Sections durchsucht
			//String sSystemNr = this.getKernelObject().getSystemNumber();					
			//String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
			
			//Hier eine gehärtete Variante einsezten, bei der man auf jeden Fall eine Section findet durch Verdoppeln der Tags!!!			
			String sSectionTotal = vecReturn.get(1);
			String sSection = StringZZZ.midLeftRightback(this.getTagStarting() + sSectionTotal + this.getTagClosing(), this.getTagStarting(), this.getTagClosing());
			String sProperty = StringZZZ.right(sSectionTotal, sSection + this.getTagClosing());
			
			String sValuePathed =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, null).getValue();
			if(!StringZZZ.isEmpty(sValuePathed)) {
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);						
				vecReturn.add(1, sValuePathed);			
			}
		}
		return vecReturn;
	}
	
	
//	public static boolean isExpression(String sLine){
//		boolean bReturn = false;
//		main:{
//			//Merke: Contains reicht nicht. Die Positionen sind auch wichtig.			
//			//boolean btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), false);
//			//if(btemp==false) break main;
//		
//			//btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false);
//			//if(btemp==false) break main;
//			
//			//!!! Wichtig: nur auf [ ] abzupruefen reicht nicht. Das koennte auch ein Array sein.
//			//Bei einem Path Ausdruck muss nach dem ClosingTag noch Text stehen.
//			//Also: 
//			boolean bAsTagFound = StringZZZ.containsAsTag(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false);
//			if(!bAsTagFound) break main;
//			
//			int iIndexClosing = sLine.toLowerCase().indexOf(KernelZFormulaIni_PathZZZ.getExpressionTagClosing().toLowerCase());
//			iIndexClosing=iIndexClosing+KernelZFormulaIni_PathZZZ.getExpressionTagClosing().length();
//			String sRest = sLine.substring(iIndexClosing);
//			if(StringZZZ.isEmpty(sRest)) break main; //dann kann das also keine PATH-Anweisung sein.
//			
//			bReturn = true;
//		}//end main
//		return bReturn;
//	}
	
	
	//###### Getter / Setter
	//Merke: Static Ausdruecke koennen erst ab Java 8 in ein Interface

	@Override
	public String getTagStarting() throws ExceptionZZZ{
		//Merke: Das ist nicht nur ein Zeichen, sondern gilt wirklich als ganzes Tag
		return "[";
	}
	
	@Override
	public String getTagClosing() throws ExceptionZZZ{
		//Merke: Das ist nicht nur ein Zeichen, sondern gilt wirklich als ganzes Tag
		return "]"; 
	}
	
	@Override
	public String getTagEmpty()throws ExceptionZZZ{
		//Merke: Das ist nicht nur ein Zeichen, sondern gilt wirklich als ganzes Tag
		return "[/]";
	}
		
	//### Aus Interface IKernelFileIniUserZZZ	
//	@Override
//	public void setFileConfigKernelIni(FileIniZZZ objFileIni){
//		this.objFileIni = objFileIni;
//	}
	
//	@Override
//	public FileIniZZZ getFileConfigKernelIni() throws ExceptionZZZ{
//		if(this.objFileIni==null) {
//			IKernelZZZ objKernel = this.getKernelObject();
//			if(objKernel==null) {
//				ExceptionZZZ ez = new ExceptionZZZ("FileIni and KernelObject", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}
//			return objKernel.getFileConfigKernelIni();						
//		}else {
//			return this.objFileIni;
//		}
//	}

	//### Aus Interface IKernelExpressionIniZZZ
//	@Override
//	public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
//		boolean bReturn=false;
//		
//		//Hier noch was Relevantes für die KernelExpressionIniConverter-Klasse finden.
////		if(StringZZZ.isEmpty(sToProof)){
////			bReturn = true;
////		}
//		return bReturn;
//	}
	
//	@Override
//	public boolean isParseRelevant(String sExpressionToProof)
//			throws ExceptionZZZ {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
//	/* (non-Javadoc)
//	 * @see basic.zKernel.IKernelZFormulaIniZZZ#compute(java.lang.String)
//	 */
//	@Override
//	public String parse(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = null;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
//			
//			//nun nur den Wert an Index 1 zurückgeben
//			//...ist falsch für Formeln, die "aufeinander folgen" sReturn = (String) vecAll.get(1);
//			sReturn = VectorZZZ.implode(vecAll);
//		}//end main:
//		return sReturn;
//	}
	
//	@Override
//	public String[] parseAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
//		String[] saReturn = null;
//		main:{
//			if(!this.isParseRelevant(sLineWithExpression)) break main;
//						
//			String sDelimiter;
//			if(StringZZZ.isEmpty(sDelimiterIn)) {
//				sDelimiter = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR; 
//			}else {
//				sDelimiter = sDelimiterIn;
//			}
//			
//			String[] saExpression = StringZZZ.explode(sLineWithExpression, sDelimiter);
//			
//			//Hier einfach das Leerzeichen zurückgeben
//			String sValue = null;
//			ArrayListExtendedZZZ listasValue = new ArrayListExtendedZZZ();
//			for(String sExpression : saExpression) {
//				sValue = this.parse(sExpression);
//				listasValue.add(sValue);
//			}
//			saReturn = listasValue.toStringArray();
//			
//		}//end main:
//		return saReturn;
//	}
	
//	@Override
//	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			Vector<String>vecAll = this.computeExpressionAllVector(sLineWithExpression);
//			
//			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
//			sReturn = VectorZZZ.implode(vecAll);
//			
//		}//end main:
//		return sReturn;
//	}

	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelZFormulaIni_PathZZZ.sTAG_NAME;
	}

	//### Aus IConvertable
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return false;
	}
}//End class