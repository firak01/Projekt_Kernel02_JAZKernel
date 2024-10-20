package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelCallIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelCallIniSolverZZZ, IKernelJavaCallIniSolverZZZ{
	private static final long serialVersionUID = -8017698515311079738L;
	public static String sTAG_NAME = "Z:Call";
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	
	public KernelCallIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelCallIniSolverNew_(null);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, String[]saFlag) throws ExceptionZZZ{		
		super(objKernel,saFlag);
		KernelCallIniSolverNew_(null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelCallIniSolverNew_(objFileIni);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(), saFlag);
		KernelCallIniSolverNew_(objFileIni);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelCallIniSolverNew_(objFileIni);
	}
			
	private boolean KernelCallIniSolverNew_(FileIniZZZ<T> objFileIn) throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{ 		
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			FileIniZZZ<T> objFile=null;
			if(objFileIn==null ){
				objFile = this.getKernelObject().getFileConfigKernelIni();
				if(objFile==null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
			}else {
				objFile = objFileIn;
			}
			
			this.setFileConfigKernelIni(objFile);	
			if(objFile.getHashMapVariable()!=null){
				this.setHashMapVariable(objFile.getHashMapVariable());			
			}
	 	}//end main:
		return bReturn;
	 }//end function KernelJsonIniSolverNew_
					
	//###### Getter / Setter
	@Override
	public String getNameDefault(){
		return KernelCallIniSolverZZZ.sTAG_NAME;
	}
	
	@Override
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	
	@Override
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		return this.hmVariable;
	}
	
	@Override
	public String parse(String sExpression) throws ExceptionZZZ {
		return this.parse_(sExpression);
	}
	
	private String parse_(String sExpression) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)==false) break main;
			
			//1.+ 2. Kein Versuch als HashMap oder ArrayList
			
			//3. Versuch als Einzelwert
			if(this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA)==false) break main;
			
			//WICHTIG: DIE FLAGS VERERBEN !!!
			KernelJavaCallIniSolverZZZ<T> init4FlagLookup = new KernelJavaCallIniSolverZZZ<T>();
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, init4FlagLookup, true);
			
			//FileIniZZZ objFileIni = this.getFileIni();
			IKernelZZZ objKernel = this.getKernelObject();
			
			//Dann erzeuge neues KernelJavaCallSolverZZZ - Objekt.				
			KernelJavaCallIniSolverZZZ<T> objJavaCallSolver = new KernelJavaCallIniSolverZZZ<T>(objKernel, saFlagZpassed); 
			sReturn=objJavaCallSolver.parse(sExpression);		
										
		}
		return sReturn;
	}
				
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				objReturn = super.parseAsEntryNew(sExpression);	
				
				//Speichere nun das USECALL - Ergebnis auch ab.
				String sReturn = objReturn.getValue();
				objReturn.setValue(sReturn);
				objReturn.setValueCallSolvedAsExpression(sReturn);				
			}else {
				objReturn.setValue(sExpression);
				objReturn.setValueCallSolvedAsExpression(null);
			}									
		}//end main:
		return objReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeAsExpression(java.lang.String)
	 */
	@Override
	public String parseAsExpression(String sExpression) throws ExceptionZZZ{
		String sReturn = sExpression;
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				sReturn = super.parseAsExpression(sExpression);
			}else {
				sReturn = sExpression;
			}									
		}//end main:
		return sReturn;
	}
		
	//### Andere Interfaces
	
	//### Aus ISolveEnabled	
	//Analog zu KernelJavaCallIniSolverZZZ, KernelJavaCallIniSolverZZZ, KernelJsonMapInisolver, KernelZFormulaMathSolver aufbauen... Der code ist im Parser
	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	//Analog zu AbstractkernelIniSolverZZZ, nur jetzt mit Call-Tag (vorher aber noch Pfade und ini-Variablen aufloesen)
	/**Methode ersetzt in der Zeile alle CALL Werte.
	 * Methode überschreibt den abstrakten "solver", weil erst einmal Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		String sReturn = sExpressionIn; //Darin können also auch Variablen, etc. sein
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objEntry.setRaw(sExpressionIn);
			
		main:{			
			//Aufloesen von Pfaden und ini-Variablen
			//2021012: Passiert jetzt nur noch im KernelExpressionHandler
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			objReturnReferenceSolverSuper.set(objEntry);
//			sReturn = super.solveParsed(sExpressionIn, objReturnReferenceSolverSuper, bRemoveSurroundingSeparators);
//			objEntry = objReturnReferenceSolverSuper.get();
			
			//Aufloesen des CALL-Tags
			sReturn = this.solveParsed_Call_(sExpressionIn, objReturnReference, bRemoveSurroundingSeparators);			
			objEntry = objReturnReference.get();								
		}//end main
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objEntry.isSolved(true);
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	private String solveParsed_Call_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		String sReturn = sExpressionIn;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objEntry.setRaw(sExpressionIn);
			
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
		
			boolean bAnyJavaCall = false; boolean bAnyJavaClass = false; boolean bAnyJavaMethod = false;
						
			//Fuer den abschliessenden Aufruf selbst.
			String sClassnameWithPackage=null; String sMethodname=null;
							
			//Dazwischen müsste eigentlich noch ein KernelJavaCallIniSolver stehen
            //Aber jetzt entfernen wir das einfach so:
			//Bei dem verschachtelten Tag werden die äußeren Tags genommen...
			Vector3ZZZ<String>vecReturn2 = StringZZZ.vecMid(sExpression, "<Z:Java>", "</Z:Java>", false, false);//Alle z:Java Ausdruecke ersetzen
			String sExpression2= (String) vecReturn2.get(1);
			if(!StringZZZ.isEmpty(sExpression2)& !sExpression.equals(sExpression2)) {							
				bAnyJavaCall = true;
				sReturn = sExpression2;
			}
			
			//Darin sind einfache Tags, die dürfen geparsed werden...., sie machen kein solve()
			if(bAnyJavaCall){
				this.getEntry().isCall(true);
				this.getEntry().isJavaCall(true);
				
				//++++++++++++++++++++++++++++++++++++++++++++				
				sExpression = sReturn;
				String sExpressionOld="";
				KernelJavaCall_ClassZZZ<T> objClassname = new KernelJavaCall_ClassZZZ<T>();
				while(objClassname.isExpression(sExpression)&& !sExpressionOld.equals(sExpression)){
						IKernelConfigSectionEntryZZZ objEntryClassname = objClassname.parseAsEntryNew(sExpression2);	
						sExpression = objEntryClassname.getValue();
						if(StringZZZ.isEmpty(sExpression)) {							
							break;
						}else{
							sExpressionOld = sExpression;
							sClassnameWithPackage = sExpression;							
							bAnyJavaClass=true;
						}
				} //end while
			
			
				//Darin sind einfache Tags, die dürfen geparsed werden...., sie machen kein solve()
				String sExpression3 = sReturn;			
				if(!StringZZZ.isEmpty(sExpression3)){									
					sExpression = sExpression3;
					sExpressionOld = "";
					KernelJavaCall_MethodZZZ<T> objMethodname = new KernelJavaCall_MethodZZZ<T>();
					while(objMethodname.isExpression(sExpression)&& !sExpressionOld.equals(sExpression)){
							IKernelConfigSectionEntryZZZ objEntryMethod = objMethodname.parseAsEntryNew(sExpression);
							sExpression = objEntryMethod.getValue();
							if(StringZZZ.isEmpty(sExpression)) {								
								break;
							}else{
								sExpressionOld = sExpression;
								sMethodname = sExpression;							
								bAnyJavaMethod=true;
							}
					} //end while											
				}//end if sExpression3 = ""

				if(bAnyJavaClass) this.getEntry().setCallingClassname(sClassnameWithPackage);
				if(bAnyJavaMethod)this.getEntry().setCallingMethodname(sMethodname);
				
				if(bAnyJavaClass && bAnyJavaMethod) {
					//Abschliessend die Berechung durchführen.
					Object objValue = ReflectUtilZZZ.invokeStaticMethod(sClassnameWithPackage, sMethodname);
					if(objValue!=null) {
						sReturn = objValue.toString();					
					}else {
						sReturn = XmlUtilZZZ.computeTagNull();
					}					
				}				
			} //end if bAnyJavaCall
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objEntry.isSolved(true);
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}
	
	//###############################
	//### FLAG Handling
	
	//### aus IKernelCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### Aus Interface IKernelJavaCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelJavaCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ.name(), bFlagValue);
	}
			
	@Override
	public boolean[] setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ[] objaEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelJavaCallIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelJavaCallIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ:objaEnum_IKernelJavaCallIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelJavaCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class
