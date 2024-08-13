package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Merke: Es gibt auch solver, die unabhaengig von einem ini-File funktionieren sollen.
 *        Darum 2 Kategorien bei den Konstruktoren
 * @author fl86kyvo
 *
 * @param <T>
 */
public abstract class AbstractKernelIniSolverZZZ<T>  extends AbstractKernelIniTagCascadedZZZ<T> implements IKernelFileIniUserZZZ, IKernelZFormulaIniZZZ, IKernelIniSolverZZZ, IKernelConfigSectionEntryUserZZZ{
	private static final long serialVersionUID = -4816468638381054061L;
	protected HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
			
	public AbstractKernelIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		AbstractKernelIniSolverNew1_(null);
	}
		
	public AbstractKernelIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		AbstractKernelIniSolverNew1_(null);
	}
			
	public AbstractKernelIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		AbstractKernelIniSolverNew1_(null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniSolverNew1_(null);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(null, null);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig) throws ExceptionZZZ{
		super(objFileIniKernelConfig); //Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, null);		
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, null);
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIniKernelConfig, null);//Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, hmVariable);		
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, hmVariable);		
	}
	
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objKernel, null);
		AbstractKernelIniSolverNew_(objFileIni, null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objKernel, null);
		AbstractKernelIniSolverNew_(objFileIni, hmVariable);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(objFileIni, null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(objFileIni, hmVariable);
	}
		
	private boolean AbstractKernelIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
		
	boolean bReturn = false;		 
		main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
			if(objFileIn==null ){
				ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez; 
			}else{
				this.setFileConfigKernelIni(objFileIn);	
				
				if(hmVariable!=null){				
					this.setVariable(hmVariable);					
				}else if(hmVariable == null && objFileIn.getHashMapVariable() !=null){
					this.setHashMapVariable(objFileIn.getHashMapVariable());
				}else {
					//soll zu den Variablen aus dem Ini-File hinzuaddieren, bzw. ersetzen
					//TODOGOON20240806; //HashMapCaseInsensitive PLus HashMapCaseInsensitive..
				}
			}
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	}//AbstractKernelIniSolverNew_
	
	
	private boolean AbstractKernelIniSolverNew1_(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {		
		boolean bReturn = false;		 
		main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
	
			if(hmVariable!=null){				
				this.setVariable(hmVariable);									
			}
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	}//AbstractKernelIniSolverNew2_
		
	//### Aus IValueSolverZTagIniZZZ
	@Override
	public String solve(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
				
			IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			
			//Bei einfachen Tags den Ersten Vektor holen
			//dabei werden Variablen und Pfade aufgeloest
			Vector<String> vecAll = this.solveFirstVector(sLineWithExpression, objReturnReference);
			
			//Rueckgabewert
			sReturn = (String) vecAll.get(1);
			this.setValue(sReturn);
			
			//Rueckgabestring
			String sExpressionImploded = VectorZZZ.implode(vecAll);
			sReturn = sExpressionImploded; //Der zurückgegebene Wert unterscheide sich also von dem Wert des Tags!!!
			
			//Nun noch parsen..
			sReturn = this.parse(sReturn);
			this.setValue(sReturn);						
		}//end main:
		return sReturn;
	}
	
	@Override
	public int solve(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ{
		int iReturn=-1;
		String sReturn=null;
		
		IKernelConfigSectionEntryZZZ objReturn=objReturnReferenceIn.get();		
		if(objReturn==null) {
			objReturn = this.getEntry(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			objReturnReferenceIn.set(objReturn);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		main:{
			//Solver haben immer die Aufgabe einen IKernelConfigSectionEntryZZZ zu fuellen. Das ueber ein Referenzobjekt loesen.
			//Darin dann neue Zustaende fuellen isPathSolved, isVariableReplaced
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			Vector<String> vecAll = this.solveFirstVector(sLineWithExpression, objReturnReference);
			
			//Zwischenstand zurückgeben. Implode, weil: Es koennen ja einfache String vor- bzw. nachstehend sein.
			objReturn = objReturnReference.get();
			this.setEntry(objReturn);
			
			sReturn = VectorZZZ.implode(vecAll);
			this.setValue(sReturn);
			
			//Nach dem Aufloesen der Variablen, etc. ... Parsen.
			//Solver parsen und fuellen ein internes entry-Objekt
			String sExpressionImploded = sReturn;			
			iReturn = this.parse(sExpressionImploded, objReturnReference);			
			objReturn = objReturnReference.get();
			this.setEntry(objReturn);			
			
			//Wichtig: Reference nach aussen zurueckgeben.
			objReturnReferenceIn.set(objReturn);
		}//end main;
		return iReturn;
	}
	
	
	/**Methode muss vom konkreten "solver" ueberschrieben werden, wenn darin keine Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public Vector<String> solveFirstVector(String sLineWithExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
             //Darin können also auch Variablen, etc. sein
			Vector<String> vecReturn = null;
			
			IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
			if(objReturn==null) {
				objReturn = this.getEntry(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
				objReturnReference.set(objReturn);
			}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			main:{
				if(sLineWithExpression==null) break main;
				vecReturn = new Vector<String>();
				if(StringZZZ.isEmpty(sLineWithExpression))break main;
								
				vecReturn = this.parseFirstVector(sLineWithExpression);			
				String sExpression = (String) vecReturn.get(1);
				String sExpressionOld = sExpression;
				if(!StringZZZ.isEmpty(sExpression)){
					
					//!!! WICHTIG: BEI DIESEN AUFLOESUNGEN NICHT DAS UEBERGEORNETE OBJENTRY VERWENDEN, SONDERN INTERN EIN EIGENES!!! 
					
					//ZUERST: Löse ggfs. übergebene Variablen auf.
					ZTagFormulaIni_VariableZZZ<T> objVariable = new ZTagFormulaIni_VariableZZZ<T>(this.getHashMapVariable());
					while(objVariable.isExpression(sExpression)){
						sExpression = objVariable.parse(sExpression);			
					} //end while
					if(sExpression!=sExpressionOld) {
						objReturn.isVariableSolved(true);
					}
						
									
					//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
					//Problem hier: [ ] ist auch der JSON Array-Ausdruck
					sExpressionOld = sExpression;
					String sExpressionTemp = sExpression;
					KernelZFormulaIni_PathZZZ<T> objIniPath = new KernelZFormulaIni_PathZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni());
					while(objIniPath.isExpression(sExpression)){
							sExpressionTemp = objIniPath.parse(sExpression);	
							if(StringZZZ.isEmpty(sExpressionTemp)) {
								break;
							}else if(sExpression.equals(sExpressionTemp)) {
								break;
							}else{
								sExpression = sExpressionTemp;							
							}
					} //end while
					if(sExpression!=sExpressionOld) {
						objReturn.isPathSolved(true);
					}
					
										
					//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
					vecReturn.add(1, sExpression);
				
				} //end if sExpression = ""					
			}//end main:
			return vecReturn;			
	}
	
	//###### Getter / Setter
	
	//### Aus IValueVariableUserZZZ
	@Override
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{				
		this.hmVariable = hmVariable;
	}
	
	@Override
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable() throws ExceptionZZZ{
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapCaseInsensitiveZZZ<String, String>();
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		if(this.hmVariable==null){
			this.hmVariable = hmVariable;
		}else{
			if(hmVariable==null){
				//nix....
			}else{
				//füge Werte hinzu.
				Set<String> sSet =  this.hmVariable.keySet();
				for(String sKey : sSet){
					this.hmVariable.put(sKey, (String)hmVariable.get(sKey));
				}
			}
		}	
	}
		
	@Override
	public String getVariable(String sKey) throws ExceptionZZZ{
		return (String) this.getHashMapVariable().get(sKey);
	}
	//#########################################	
	
//	@Override
//	public String[] parseAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
//		String[] saReturn = null; //new String[];//sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			String sDelimiter;
//			if(StringZZZ.isEmpty(sDelimiterIn)) {
//				sDelimiter = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR; 
//			}else {
//				sDelimiter = sDelimiterIn;
//			}
//				   
//			String sExpressionTotal = this.parse(sLineWithExpression); //Hole erst einmal den Kernel-Tag-Wert.
//			if(!StringZZZ.isEmpty(sExpressionTotal)) {
//				String[] saExpression = StringZZZ.explode(sExpressionTotal, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
//				
//				String sValue = null;
//				ArrayListExtendedZZZ listasValue = new ArrayListExtendedZZZ();
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
	
	
	
	//### aus IKernelZFormulaIniZZZ
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#isStringForConvertRelevant(java.lang.String)
	 */
	
	//### aus IConvertableZZZ
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.isConvertable(sStringToProof);
	}
}//End class