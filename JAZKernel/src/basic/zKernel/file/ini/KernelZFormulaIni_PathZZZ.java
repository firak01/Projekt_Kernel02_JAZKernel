package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Merke: Der Path-Tag besteht aus [ oder ] ist damit kein normaler Tag 
 *         und kann also nicht aus AbstractIniTagSimple oder AbstractIniCascadedZZZ erben.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 12.07.2024, 09:26:56 
 */
public class KernelZFormulaIni_PathZZZ<T>  extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelFileIniUserZZZ, IKernelExpressionIniSolverZZZ, IKernelZFormulaIni_PathZZZ{
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
	
	
	//### Aus IParseEnabledZZZ	
		
	//### aus IKernelEntryExpressionUserZZZ
	
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
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		Vector3ZZZ<String> vecReturn = null;
		//20240919: Dummy debug
		//sExpression = "<Z:Call><Z:Java><Z:Class><Z>irgendwas</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>";			
		//sExpression = "<Z:Call><Z:Java><Z:Class><Z>irgendwas</Z></Z:Class><Z:Method><Z>nochnemethod</Z></Z:Method></Z:Java></Z:Call>";			
		String sReturn = sExpression;
		boolean bExpressionFound = false;				
		IKernelConfigSectionEntryZZZ objEntry = null;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			vecReturn = new Vector3ZZZ<String>();
			
			boolean bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION);
			if(!bUseExpression) break main;
			
			boolean bUseExpressionPath = this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH);
			if(!bUseExpressionPath) break main;
			
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
			objEntry.setRaw(sExpression);
			bExpressionFound = this.isExpression(sExpression); 
			if(!bExpressionFound)break main;
			
			//++++++++++++++++++++++++++++++++++++++
			boolean bReturnSeparators = !bRemoveSurroundingSeparators;
			String sSepLeft = this.getTagStarting();
			String sSepRight = "<";
			
			//Folgender Ausdruck findet auch etwas, wenn nur der Path ohne Einbettung in Tags vorhanden ist.
			//Also, z.B.: [Section A]Testentry1
			//also bis zum nächsten Tag, darum "<", falls kein naechster Tag vorhanden ist. 						
			vecReturn = StringZZZ.vecMidFirst(sExpression + sSepRight, sSepLeft, sSepRight, false,false);
			String sLeft = (String) vecReturn.get(0);
			
			String sMid = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sMid)) {
				sMid = this.getTagStarting()+ sMid; //Besonderheit. Wg. Weiterverarbeitung (Section holen) muss hier das Starttäg drinbleiben.
			}
			
			//Erforderliche Nacharbeiten, weil es halt besondere Tags sind:
			//1. den oben geklauten Anfangstag - des nachfolgenden Ausdrucks - wieder hinzufuegen
			//   und den zuviel gesetzten < wegnehmen am Ende.
			String sRight = (String) vecReturn.get(2);			
			if(!StringZZZ.isEmpty(sRight)) {
				sRight = StringZZZ.replaceRight(sRight, sSepRight, ""); 
				
				//UND wg. dem "<" fehlt am Anfang eben diese Zeichen. Intern wird naemlich auf Laenge gegangen.
				sRight = sSepRight + sRight;
			}
			
			
			//#########################
			vecReturn.replace(sLeft, sMid, sRight, bReturnSeparators, sSepLeft, sSepRight);

			//##########################
	
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
			//objFileIniUsed.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, false);//USEEXPRESSION muss bleiben, sonst wird nix verarbeitet
			objFileIniUsed.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);			
			//INIT=false, USEEXPRESSION_PATH=true, USEEXPRESSION=true, USEEXPRESSION_SOLVER=false
			
			//+++ Wichtig: Dieses geclonte objFileIni darf auch nicht Statuswerte frueherer Verarbeitungen im Entry-Objekt haben.
			objFileIniUsed.setEntry(null); //Durch das Null-Setzen wird ein neues Erstellen erzwungen.
			
			
			//20080109: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
			//20230316: Aber, jetzt ist es allgemeingültiger nicht eine konkrete SystemNumber vorzugeben. Darum null dafür.
			//          Dann werden alle Sections durchsucht
			
			//Merke: Es gibt: KernelZZZ.computeSectionFromSystemSection(sSystemSection)
			//                Damit wird eine Section aus abcsection!01 geholt. 
						
			String sSectionTotal = (String) vecReturn.get(1);
			
			String sSection = StringZZZ.midLeftRightback(this.getTagStarting() + sSectionTotal + this.getTagClosing(), this.getTagStarting(), this.getTagClosing());
			String sProperty = StringZZZ.right(sSectionTotal, sSection+this.getTagClosing());
			
			sReturn =  objFileIniUsed.getPropertyValueSystemNrSearched(sSection, sProperty, null).getValue();
			
			
		}//end main:
		
		vecReturn.replace(sReturn);
			
		//Z-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
		//...aber nur, wenn ein Pfad gefunden wurde.
		if(bRemoveSurroundingSeparators & bExpressionFound) {
			String sTagStart="<Z>";
			String sTagEnd="</Z>";
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
		}

		this.setValue((String) vecReturn.get(1));
		if(objEntry!=null) {
			objEntry.setValue(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
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