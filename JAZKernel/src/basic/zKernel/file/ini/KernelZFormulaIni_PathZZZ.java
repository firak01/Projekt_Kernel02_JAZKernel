package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Merke: Der Path-Tag besteht aus [ oder ] ist damit kein normaler Tag 
 *         und kann also nicht aus AbstractIniTagSimple oder AbstractIniCascadedZZZ erben.
 *         
 *  Merke2: Stand jetzt 20250225 braucht man aber irgendeinen umgebenden Tag, der beim Parsen einen Wert bekommt. 
 *          Dieser Wert ist dann die Grundlage für das Auflösen des Pfads.
 *          
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 12.07.2024, 09:26:56 
 */
public class KernelZFormulaIni_PathZZZ<T>  extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelFileIniUserZZZ, IKernelExpressionIniSolverZZZ, IKernelZFormulaIni_PathTagZZZ{
	private static final long serialVersionUID = -6403139308573148654L;
	
	//Hier kein Tag-Name sondern eine RegEx. Der Tag ist definiert als [section]property
	//Merke: In RegEx Ausdruechen muessen eckige Klammern mit Backslash escaped werden. 
	//public static String sTAG_NAME = ".*<Z>.*[\\[]*[\\]].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text
	//public static String sTAG_NAME = ".*<Z>.*\\[[^\"].*[^\"]\\].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text UND auf keine Fall nach der offenen eckigen Klammer ein Hochkomma (was fuer Java escaped ist); dito fuer die geschlossenen eckige Klammer
	//public static String sTAG_NAME = ".*<[^/].*>.*\\[[^\"].*[^\"]\\].*<[/].*>.*"; //finde einen Ausdruck in eckigen Klammern mit Tags (erstes Tag ohne Slash) drumherum und ggfs. Text UND auf keine Fall nach der offenen eckigen Klammer ein Hochkomma (was fuer Java escaped ist); dito fuer die geschlossenen eckige Klammer
	public static String sTAG_NAME = ".*\\{\\[.*[^\"']\\].*\\}.*"; //finde einen Ausdruck in geschweiften Klammern drumherum PLUS finde einen Ausdruck in eckigen Klammern und ggfs. Text, aber OHNE Hochkommata (was hier im Ausdruck fuer Java escaped ist), einfaches Hochkommata etc.
	
	public static String sSECTION_OPENING = "[";
	public static String sSECTION_CLOSING = "]";
	public static String sTAGPART_OPENING = "{" + sSECTION_OPENING;	
	public static String sTAGPART_CLOSING = "}";//Merke: Hinter der Section kommt noch der Property-Text, der hat keine Trenner.
	
	
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
	
	
//	@Override
//	//public Vector<String>parse(String sLineWithExpression) throws ExceptionZZZ{
//	public String parse(String sLineWithExpression) throws ExceptionZZZ{
//		return this.parse(sLineWithExpression, true);
//	}
//	
//	
//	@Override
//	//public Vector<String>parse(String sLineWithExpression) throws ExceptionZZZ{
//	public String parse(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		String sReturn = null;
//		Vector<String>vecReturn = null; 
//		main:{
//			if(sLineWithExpression==null) break main;
//			vecReturn = new Vector<String>(); //Merke: vecReturn wird am Schluss zu einem String zusammengefasst.			
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
//			
//			//Nun die Section suchen
//			Vector<String>vecSection = this.parseFirstVector(sLineWithExpression, bRemoveSurroundingSeparators);	
//			
////			TODOGOON 20240821;
//			//Wenn der Pfad aufgeloest ist nicht weiter machen.
//			//also: .isExpression pruefen
//			
////			String sBefore = (String) vecSection.get(0);			
////			String sSection = (String) vecSection.get(1);
////			String sRest = (String) vecSection.get(2);//Zu diesem Zeitpunkt eigentlich der Rest...
//			
//			
//			//TODOGOON 20240821: Diese Werte Ausrechnerei, die folgt ist ggfs. nicht mehr relevant
//			//                   und stoert sogar.
//			
////			String sProperty = "";
////			if(StringZZZ.contains(sRest,"</Z>")) {
////				sProperty = StringZZZ.left(sRest, "</Z>");
////			}else {
////				sProperty = sRest;
////			}
////			
////			
////			if(StringZZZ.contains(sProperty,"</Z:val>")) {      //Wenn der Pfad Bestandteil einer Mathematischen Formel ist, also eine Section mit einem "Wert".
////				sProperty = StringZZZ.left(sProperty, "</Z:val>");
////			}
////			
//
////			sRest = StringZZZ.right(sRest, sProperty);
////						
//
////			if(StringZZZ.isEmpty(sSection) | StringZZZ.isEmpty(sProperty)){
//				String sValue = VectorZZZ.implode(vecSection);
//				
//				if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//				vecReturn.add(0, "");
//				
//				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
//				vecReturn.add(1, sValue);
//				
//				if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
//				vecReturn.add(2, "");
//				
//				//Z-Tags "aus der Mitte entfernen"... Wichtig z.B. für Z:JavaCall Tags
//				if(bRemoveSurroundingSeparators) {
//					String sTagStart="<Z>";
//					String sTagEnd="</Z>";
//					KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
//				}
//				
//				break main;
////			}

			
			
//#####################################################################################			
				//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
//				String sMathValueTag = KernelZFormulaMath_ValueZZZ.computeExpressionTagClosing(KernelZFormulaMath_ValueZZZ.sTAG_NAME);
//				if(StringZZZ.contains(sRest, sMathValueTag)){
//					sBefore = (String) vecSection.get(0);
//					sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
//					//sProperty = StringZZZ.left(sProperty, sMathValueTag);												
//				}
									
//				FileIniZZZ<T> objFileIni = this.getFileConfigKernelIni();
//				if(objFileIni==null){
//					ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				
//				//20080109: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
//				//20230316: Aber, jetzt ist es allgemeingültiger nicht eine konkrete SystemNumber vorzugeben. Darum null dafür.
//				//          Dann werden alle Sections durchsucht
//				//String sSystemNr = this.getKernelObject().getSystemNumber();					
//				//String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
//				String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, null).getValue();
//				
//				//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
//				if(!StringZZZ.isEmpty(sValue)){
//					if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//					vecReturn.add(0, sBefore);
//					
//					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
//					vecReturn.add(1, sValue);
//					
//					if(vecReturn.size()>=3) vecReturn.removeElementAt(2);
//					vecReturn.add(2, sRest);
//					
//					//Z-Tags "aus der Mitte entfernen"... Wichtig z.B. für Z:JavaCall Tags
//					String sTagStart="<Z>";
//					String sTagEnd="</Z>";
//					KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
//					
//				}//end if sValue!=null
//		}//end main:
//		sReturn = VectorZZZ.implode(vecReturn);
//		return sReturn;
//	}
	

	//### aus IExpressionUserZZZ
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
			boolean bAsTagFound = StringZZZ.containsAsTag(sLine, this.getTagPartOpening(), this.getTagPartClosing(), false);
			if(!bAsTagFound) break main;
			
			int iIndexClosing = sLine.toLowerCase().indexOf(this.getTagPartClosing().toLowerCase());
			iIndexClosing=iIndexClosing+this.getTagPartClosing().length();
			String sRest = sLine.substring(iIndexClosing);
			if(StringZZZ.isEmpty(sRest)) break main; //dann kann das also keine PATH-Anweisung sein.
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//### Aus IParseEnabledZZZ		
	@Override
	public boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ {
		//Ueberschreibt den Standard, da der Name nicht so ist wie ein normales Tag
		return ExpressionIniUtilZZZ.isParseRegEx(sExpressionToProof, this.getName(), false);
	}
		
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	//### aus IKernelEntryExpressionUserZZZ
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn = null; String sReturnTag = null; String sReturnLine = null; 
		boolean bExpressionFound = false;
		
		//20240919: Dummy debug mit diesen statischen Werten
		//sExpression = "<Z:Call><Z:Java><Z:Class><Z>irgendwas</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>";			
		//sExpression = "<Z:Call><Z:Java><Z:Class><Z>irgendwas</Z></Z:Class><Z:Method><Z>nochnemethod</Z></Z:Method></Z:Java></Z:Call>";					
				
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell mit .getEntry() und darin ggfs. .getEntryNew() versuchen zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); //Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturnReference.set(objEntry);
		}			
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);	
		//20250312 objEntry.isParseCalled(true);
		this.updateValueParseCalled();
		this.updateValueParseCalled(objEntry);
		sReturnLine = sExpressionIn;
		sReturnTag = this.getValue();
		sReturn = sReturnLine;
		vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;			
			String sExpression = sExpressionIn;
			
			boolean bUseExpression = this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION);
			if(!bUseExpression) break main;
			
			boolean bUseExpressionPath = this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH);
			if(!bUseExpressionPath) break main;

			bExpressionFound = this.isExpression(sExpressionIn); 
			if(!bExpressionFound)break main;
			
			//++++++++++++++++++++++++++++++++++++++
			boolean bReturnSeparators = !bRemoveSurroundingSeparators;
			
			//Die PATH Anweisung soll zwischen jedem Tag liegen koennen oder auch einfach so darstehen
			String sTagXPathStarting = this.getTagPartOpening();
			String sSepLeft = XmlUtilZZZ.findFirstTagPartPrevious(sExpression, sTagXPathStarting);
			String sSepRight = XmlUtilZZZ.findFirstTagPartNext(sExpression, sTagXPathStarting);
		
			vecReturn = StringZZZ.vecMidFirstKeep(sExpression, sSepLeft, sSepRight, false);
	
			//Loesungsansatz: Arbeite mit einer nicht weiter verwendeten Kopie des Ini-Objekts		
			FileIniZZZ<T> objFileIni = this.getFileConfigKernelIni();
			if(objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//+++ ACHTUNG: Mit dem einfache Clonen kopiert man die internen Variablen, ohne die Referenzen zu aendern.
			//             Mit clonez() werden eine echte Kopien der HashMaps erstellt.
			//             Das bewirkt, das sich Aenderungen an den Flags NICHT auch an das Ursprungsobjekt uebertragen!!!
			FileIniZZZ<T> objFileIniUsed = (FileIniZZZ<T>) objFileIni.clonez();
			
			//+++ Wichtig: Diese geclonte objFileIni nur fuer eine direkte Suche nutzen.
			//    Daher unbedingt darin die Flags so setzen, dass keine Aufloesung von Expressions passiert.			
			objFileIniUsed.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);
			objFileIniUsed.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false);
			objFileIniUsed.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);			
			
			//+++ Wichtig: Dieses geclonte objFileIni darf auch nicht Statuswerte frueherer Verarbeitungen im Entry-Objekt haben.
			objFileIniUsed.setEntry(null); //Durch das Null-Setzen wird ein neues Erstellen erzwungen.
			
			
			//20080109: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
			//20230316: Aber, jetzt ist es allgemeingültiger nicht eine konkrete SystemNumber vorzugeben. Darum null dafür.
			//          Dann werden alle Sections durchsucht
			
			//Merke: Es gibt: KernelZZZ.computeSectionFromSystemSection(sSystemSection)
			//                Damit wird eine Section aus abcsection!01 geholt. 
						
			String sTagValueTotal = (String) vecReturn.get(1);
			Vector3ZZZ<String> vecTagValueTotal = StringZZZ.vecMidFirstKeepSeparatorCentral(sTagValueTotal, this.getTagPartOpening(), this.getTagPartClosing(), false);
			
			String sTagValue = (String) vecTagValueTotal.get(1);
			String sSection = StringZZZ.midLeftRightback(sTagValue, this.getTagPartOpening(), this.getSectionClosing());
			String sProperty = StringZZZ.midLeftRightback(sTagValue, this.getSectionClosing(), this.getTagPartClosing());
									
			//Zu einfach, was tun wenn kein Wert gefunden wird... einfach so belassen
			//sReturnTag =  objFileIniUsed.getPropertyValueSystemNrSearched(sSection, sProperty, null).getValue();
			IKernelConfigSectionEntryZZZ objEntrySearch = objFileIniUsed.getPropertyValueSystemNrSearched(sSection, sProperty, null);
			if(objEntrySearch.hasAnyValue()) {
				sReturnTag = objEntrySearch.getValue();
			}else {
				sReturnTag = sTagValue; //also unveraendert lassen.
			}
			
			vecTagValueTotal.replace(sReturnTag);						
			sReturnTag = VectorUtilZZZ.implode(vecTagValueTotal);
			this.setValue(sReturnTag);
			
			
			vecReturn.replace(sReturnTag); //Übernimm den ersetzten Wert mit ggfs. vorhandenen Zeichen drumherum in die Rückgabe
			
			//+++ Der endgueltige Wert der Zeile und eigenen Wert setzen 
			//Als echten Ergebniswert aber die <Z>-Tags rausrechnen, falls gewuenscht. BESONDERHEIT: Hier nicht versuchen den einenen Path-Tag rauszurechnen [...]
			vecReturn = this.parseFirstVectorPost(vecReturn, bRemoveSurroundingSeparators, false);
			sReturnTag = this.getValue();			
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
		}//end main:		
		 
		//Aus AbstractKernelIniTagSimpleZZZ
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			if(sExpressionIn!=null) {								 						
				if(!sExpressionIn.equals(sReturnLine)) {
					//objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.
					this.updateValueParsedChanged();
					this.updateValueParsedChanged(objEntry);
				}
			}			
			this.adoptEntryValuesMissing(objEntry);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}		
		return vecReturn;
	}
	
	
	//###### Getter / Setter
	
	//### aus IKernelZFormulaIni_PathTagZZZ
	//Merke: Static Ausdruecke koennen erst ab Java 8 in ein Interface
	@Override
	public String getSectionOpening() {
		return "[";
	}
	
	@Override
	public String getSectionClosing() {
		return "]";
	}
	
	//#### aus IBasicTagSimpleZZZ
	//Merke: Static Ausdruecke koennen erst ab Java 8 in ein Interface		
	@Override
	public String getTagPartOpening() throws ExceptionZZZ{
		return KernelZFormulaIni_PathZZZ.sTAGPART_OPENING;
	}
	
	@Override
	public String getTagPartClosing() throws ExceptionZZZ{
		return KernelZFormulaIni_PathZZZ.sTAGPART_CLOSING; 
	}
	
	@Override
	public String getTagEmpty()throws ExceptionZZZ{
		//Merke: Das ist nicht nur ein Zeichen, sondern gilt wirklich als ganzes Tag
		return KernelZFormulaIni_PathZZZ.sTAGPART_OPENING + KernelZFormulaIni_PathZZZ.sSECTION_CLOSING + "/" + KernelZFormulaIni_PathZZZ.sTAGPART_CLOSING;
	}
		

	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return sTAG_NAME;
	}

	//### Aus IConvertable
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {
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

	
	//### aus IKernelExpressionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ) {
		return this.getFlag(objEnum_IKernelZFormulaIni_PathZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_PathZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIni_PathZZZ.length];
				int iCounter=-1;
				for(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ:objaEnum_IKernelZFormulaIni_PathZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class