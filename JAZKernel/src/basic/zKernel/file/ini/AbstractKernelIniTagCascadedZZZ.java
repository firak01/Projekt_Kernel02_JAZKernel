package basic.zKernel.file.ini;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
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
	
	//### Aus IKernelEntryExpressionUserZZZ
	@Override
	public int parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parse_(sExpression, objReturnReference, true);
	}
	
	@Override
	public int parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parse_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private int parse_(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		int iReturn = 0;
		main:{		
			IKernelConfigSectionEntryZZZ objEntry = null;
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
			if(objReturnReferenceIn==null) {	
				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			}else {
				objReturnReference = objReturnReferenceIn;
				objEntry = objReturnReference.get();
			}
			if(objEntry==null) {
				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
											 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
				objReturnReference.set(objEntry);								
			}//Achtung: Das objReturn Objekt NICHT generell versuchen mit .getEnry() und ggfs. dann darin .getEntryNew89 uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry.setRaw(sExpression);			
							
			Vector<String> vecReturn = this.parseFirstVector(sExpression, objReturnReference, bRemoveSurroundingSeparators);						
			if(vecReturn==null) break main;
						
			String sReturn = VectorUtilZZZ.implode(vecReturn);	//Merke: Implode nur bei parse(), solve() gibt vecReturn.get(1) zurueck.						
			this.setValue(sReturn);
			
			objEntry.setValue(sReturn);				
			if(!sExpression.equals(sReturn)) {
				objEntry.isParsed(true);			
				iReturn = 1;
			}
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);			
		}//end main:
		return iReturn;
	}
	
	//Die Idee ist, das die konkreten Klassen den ersten Vector parsen
	//also ggfs. überschreiben: public Vector<String>parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
	
	//### aus IParseEnabledZZZ
	@Override
	public Vector<String> parseFirstVector(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, bRemoveSurroundingSeparators, true);
	}
	
	//### Aus IKernelEntryExpressionUserZZZ	
	@Override
	public Vector<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, objReturnReference, true, true);
	}

	@Override
	public Vector<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators, true);
	}
	
	
	private Vector<String> parseFirstVector_(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators, boolean bIgnoreCase) throws ExceptionZZZ {
		Vector<String>vecReturn = new Vector<String>();
		IKernelConfigSectionEntryZZZ objEntry = null;
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;		
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
			if(objReturnReferenceIn==null) {
				objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
				                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);				
			}else {
				objReturnReference = objReturnReferenceIn;
				objEntry = objReturnReference.get();
			}
			
			if(objEntry==null) {
				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
				//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
				//objEntry = this.getEntry();
				objEntry = new KernelConfigSectionEntryZZZ<T>(this); // =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
				objReturnReference.set(objEntry);
			}	
			objEntry.setRaw(sExpression);
						
			//Bei dem cascaded Tag wird das schliessende Tag vom Ende gesucht...
			vecReturn = StringZZZ.vecMid(sExpression, this.getTagStarting(), this.getTagClosing(), !bRemoveSurroundingSeparators, !bIgnoreCase);
			if (vecReturn==null)break main;
			sReturn = vecReturn.get(1);
			
			if(vecReturn.size()==0) vecReturn.add(0, "");
			
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			if(!StringZZZ.isEmpty(sReturn)){
				vecReturn.add(1, sReturn);
			}else {
				vecReturn.add(1, "");
			}
				
			if(vecReturn.size()==2) vecReturn.add(2, "");
			
			//sReturn = super.parse(sExpression, bRemoveSurroundingSeparators);
			sReturn = VectorUtilZZZ.implode(vecReturn);
		}				
		
		this.setValue(sReturn);		
		if(objEntry!=null) {					
			objEntry.setValue(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
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
	@Override
	public Vector<String>parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = null;

		main:{
			if(sExpression==null) break main;
			
			vecReturn = new Vector<String>();
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
								
			//Bei dem cascaded Tag wird das schliessende Tag vom Ende gesucht...
			vecReturn = StringZZZ.vecMid(sExpression, this.getTagStarting(), this.getTagClosing(), true, false);
				
		}//end main:			
		return vecReturn;
	}
}// End class