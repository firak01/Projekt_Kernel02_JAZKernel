package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
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
public class KernelZFormulaIni_PathZZZ<T>  extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelFileIniUserZZZ, IKernelZFormulaIni_PathZZZ{
	private static final long serialVersionUID = -6403139308573148654L;
	public static String sTAG_NAME = ""; //Hier kein Tag-Name
		
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
	//public Vector<String>parse(String sLineWithExpression) throws ExceptionZZZ{
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		Vector<String>vecReturn = null; 
		main:{
			if(sLineWithExpression==null) break main;
			vecReturn = new Vector<String>(); //Merke: vecReturn wird am Schluss zu einem String zusammengefasst.			
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
				//Dann ist das nicht (mehr!) ein richtig konfigurierter Pfad, also unverändert zurueckgeben.
				vecReturn = vecSection;
				break main;
			}

				//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
//				String sMathValueTag = KernelZFormulaMath_ValueZZZ.computeExpressionTagClosing(KernelZFormulaMath_ValueZZZ.sTAG_NAME);
//				if(StringZZZ.contains(sRest, sMathValueTag)){
//					sBefore = (String) vecSection.get(0);
//					sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
//					//sProperty = StringZZZ.left(sProperty, sMathValueTag);												
//				}
									
				FileIniZZZ<T> objFileIni = this.getFileConfigKernelIni();
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
		sReturn = VectorZZZ.implode(vecReturn);
		return sReturn;
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
			//Folgender Ausdruck findet auch etwas, wenn nur der Path ohne Einbetting in Tags vorhanden ist.
			//Also, z.B.: [Section A]Testentry1
			vecReturn = StringZZZ.vecMidFirst(this.getTagStarting() + sLineWithExpression + "<", this.getTagStarting(), "<", false,false); //also bis zum nächsten Tag!!!
			
			String sValue = vecReturn.get(2);			
			if(!StringZZZ.isEmpty(sValue)) {
				//den oben geklauten Anfangstag - des nachfolgendne Ausdrucks - wieder hinzufuegen
				sValue = "<" + sValue;
			}
			
			if(vecReturn.size()>=3) vecReturn.removeElementAt(2);			
			vecReturn.add(2, sValue);
			
			//Problem: Parsen und solven sind hier zusammen...
			//         Es wird also beim Nachsehen in der INI - Datei ein weiterer Solver gestartet.
			//         Der schreibt dann den gefundenen Wert als "Gesamtwert" in den Entry... 
			//         DAS IST FALSCH.
			//GRUND DAFUER IST, DAS FUER
			//getPropertyValueDirectLookup_(String sSection, String sProperty, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
			//ZWAR EIN NEUES ENTRY-OBJEKT VERWENDET WIRD
			//ABER MIT .setEntry(..) Das Objekt uebernommen wird.
			//DARUM NIEMALS das Entry Objekt einfach so uebernehmen. Also Kommentar:
			//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			
			//Das reicht aber nicht....
			//Loesungsansatz: Arbeite mit einer nicht weiter verwendeten Kopie des Ini-Objekts
			
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
			if(objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			FileIniZZZ objFileIniUsed = (FileIniZZZ) objFileIni.clonez();
			
			
			//20080109: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
			//20230316: Aber, jetzt ist es allgemeingültiger nicht eine konkrete SystemNumber vorzugeben. Darum null dafür.
			//          Dann werden alle Sections durchsucht
			//String sSystemNr = this.getKernelObject().getSystemNumber();					
			//String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
			
			//Hier eine gehärtete Variante einsezten, bei der man auf jeden Fall eine Section findet durch Verdoppeln der Tags!!!			
			String sSectionTotal = vecReturn.get(1);
			String sSection = StringZZZ.midLeftRightback(this.getTagStarting() + sSectionTotal + this.getTagClosing(), this.getTagStarting(), this.getTagClosing());
			String sProperty = StringZZZ.right(sSectionTotal, sSection + this.getTagClosing());
			
			String sValuePathed =  objFileIniUsed.getPropertyValueSystemNrSearched(sSection, sProperty, null).getValue();
			if(!StringZZZ.isEmpty(sValuePathed)) {
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);						
				vecReturn.add(1, sValuePathed);			
			}
		}
		return vecReturn;
	}
	
	
	@Override
	public boolean isExpression(String sLine) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Merke: Contains reicht nicht. Die Positionen sind auch wichtig.			
			//boolean btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), false);
			//if(btemp==false) break main;
		
			//btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false);
			//if(btemp==false) break main;
			
			//!!! Wichtig: nur auf [ ] abzupruefen reicht nicht. Das koennte auch ein Array sein.
			//Bei einem Path Ausdruck muss nach dem ClosingTag noch Text stehen.
			//Also: 
			//boolean bAsTagFound = StringZZZ.containsAsTag(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false);
			boolean bAsTagFound = StringZZZ.containsAsTag(sLine, this.getTagStarting(), this.getTagClosing(), false);
			if(!bAsTagFound) break main;
			
			int iIndexClosing = sLine.toLowerCase().indexOf(this.getTagClosing().toLowerCase());
			iIndexClosing=iIndexClosing+this.getTagClosing().length();
			String sRest = sLine.substring(iIndexClosing);
			if(StringZZZ.isEmpty(sRest)) break main; //dann kann das also keine PATH-Anweisung sein.
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
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
	
	
	//########################################################
	//### FLAG Handling
	
	//### aus IKernelZFormulaIni_PathZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ) {
		return this.getFlag(objEnum_IKernelZFormulaIni_PathZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_PathZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIni_PathZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ:objaEnum_IKernelZFormulaIni_PathZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class