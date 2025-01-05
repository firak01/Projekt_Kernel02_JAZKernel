package basic.zKernel.file.ini;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/*
 * In dieser Klasse wird das Ende Tag "von hinten" gesucht.
 * D.h. auch verschachtelte Tags funktionieren <Z> ... ... <Z> .. </Z> ... ... </Z>
 */
public abstract class AbstractKernelIniTagCascadedZZZ<T> extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelUserZZZ, IKernelFileIniUserZZZ{
	private static final long serialVersionUID = -3319737210584524888L;
	
	public AbstractKernelIniTagCascadedZZZ() throws ExceptionZZZ {
		super("init");
		AbstractKernelIniTagCascadedNew_();
	}

	public AbstractKernelIniTagCascadedZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);		
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ {
		super(objKernel, saFlag);
		AbstractKernelIniTagCascadedNew_();
	}

	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(objKernelUsing);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(objKernelUsing,saFlag);
		AbstractKernelIniTagCascadedNew_();
	}
	
	
	/** Wg. Interface IKernelUserZZZ notwendige Redundanz zu 
	 *  AbstractKernelUseObjectZZZ
	 *  und dem dortigen Konstruktor
	 *  
	 * @param objKernel
	 * @param objKernelUsing
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.07.2024, 07:10:15
	 */
	private boolean AbstractKernelIniTagCascadedNew_() throws ExceptionZZZ {
		boolean bReturn = false;		
		main: {
			
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
			
			//+++++++++++++++++++++++++++++++						
			bReturn = true;
		}//end main;
		return bReturn;
	}
	
	//#####################################################
	
	//### aus IParseEnabledZZZ
	//Die Idee ist, das die konkreten Klassen den ersten Vector parsen
	//also ggfs. überschreiben: public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, true, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, bRemoveSurroundingSeparators, true);
	}
	
	//### Aus IKernelEntryExpressionUserZZZ	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, objReturnReference, true, true);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators, true);
	}
	
	/**Methode wird z.B. vom AbstractKernelIniSolver ueberschrieben **/
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators, boolean bIgnoreCase) throws ExceptionZZZ {
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>(); Vector3ZZZ<String>vecReturnTag = new Vector3ZZZ<String>();
		String sReturn = sExpressionIn; String sReturnTag = null;
		boolean bUseExpression = false; boolean bUseParse = false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
		if(objReturnReferenceIn==null) {
			objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();															
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}	
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);
		objEntry.isParseCalled(true); //weil parse ausgefuehrt wird. Merke: isParseChangedValue kommt am Schluss.
		
		main:{
			String sExpression = sExpressionIn;
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			//Zentrale Stelle, um den String/Entry als Expression zu kennzeichnen.
			if(XmlUtilZZZ.containsTag(sExpression, "Z", false)){
				objEntry.isExpression(true);
			}	
			 
			//20241023 Erweiterungsarbeiten, Ini-Pfade und Variablen "substituieren"
			//Wichtig hier die Z-Tags drin lassen, nur dann funktioniert die RegEx-Expression für Pfadangabe.
		    //Ausserdem wird so vecReturn "initialisiert"
			vecReturn = StringZZZ.vecMidKeepSeparatorCentral(sExpression, this.getTagStarting(), this.getTagClosing(), !bIgnoreCase);
			if (vecReturn!=null) sReturnTag = (String) vecReturn.get(1);  //Merke: Das ist dann der Wert es Tags, wenn der Parser nicht aktiviert ist.
						
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
			//Merke: Darum vorher vecReturn schon initialisieren.
			bUseParse = this.isParserEnabledThis();
		    if(!bUseParse) break main;
		    			
			sExpression = sReturnTag;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSubstitute= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSubstitute.set(objEntry);
			sReturnTag = this.substituteParsed(sExpression, objReturnReferenceSubstitute, bRemoveSurroundingSeparators);
			this.setValue(sReturnTag);//Hier ist erst einmal das Setzend des Werts der Substitution erlaubt.
			objEntry = objReturnReferenceSubstitute.get();			
			
			if (vecReturn!=null) vecReturn.replace(sReturnTag); //da noch weiter verarbeitet werden muss.			
			
				
			//+++ Der endgueltige Wert der Zeile und eigenen Wert setzen 
			//Als echten Ergebniswert aber die <Z>-Tags und den eigenen Tag rausrechnen, falls gewuenscht
			vecReturn = this.parseFirstVectorPost(vecReturn, objReturnReference, bRemoveSurroundingSeparators);
			sReturnTag = this.getValue();
		}//end main:			
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		//Nicht ersetzen. Beim Parsen bleibt der Zeilenwert nahezu unveraendert. Der Wert des Tags wurde schon gespeichert. if(vecReturn!=null && sReturnTag!=null) vecReturn.replace(sReturnTag); //BEIM PARSEN NICHT UEBERNEHMEN IN VEC(1).
		this.setValue(sReturnTag);
		if(objEntry!=null) {
			if(!bUseExpression) {
				objEntry.setValue(sReturn);
			}else {
				if(vecReturn!=null) sReturn  = VectorUtilZZZ.implode(vecReturn);
				objEntry.setValue(sReturn);
				objEntry.isParsed(true);
				if(sExpressionIn!=null) {							
					if(!sExpressionIn.equals(sReturn)) objEntry.isParsedChanged(true); //zur Not nur, weil die Z-Tags entfernt wurden.									
				}		
				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
				this.adoptEntryValuesMissing(objEntry);
			}
		}
		return vecReturn;
	}
	
	//### aus IExpressionUserZZZ	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
//	@Override
//	public Vector3ZZZ<String>parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
//		Vector3ZZZ<String> vecReturn = null;
//
//		main:{
//			if(sExpression==null) break main;
//			
//			vecReturn = new Vector3ZZZ<String>();
//			if(StringZZZ.isEmpty(sExpression)) break main;
//			
//			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//								
//			//Bei dem cascaded Tag wird das schliessende Tag vom Ende gesucht...
//			vecReturn = StringZZZ.vecMid(sExpression, this.getTagStarting(), this.getTagClosing(), true, false);
//				
//		}//end main:			
//		return vecReturn;
//	}
}// End class