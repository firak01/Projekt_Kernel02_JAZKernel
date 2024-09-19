package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
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
	
	/**Methode ersetzt in der Zeile alle CALL Werte.
	 * Methode überschreibt den abstrakten "solver", weil erst einmal keine Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */
	@Override
	public Vector<String>solveFirstVector(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		return this.solveFirstVector_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private Vector<String>solveFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector<String> vecReturn = null;
		String sReturn = sExpressionIn;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {				
		}else {
			objEntry = objReturnReferenceIn.get();
		}
		if(objEntry==null) {
			objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.				
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		objEntry.setRaw(sExpressionIn);
			
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			vecReturn = new Vector<String>();
			
			boolean bAnyCall = false; boolean bAnyJavaCall = false; boolean bAnyJavaClass = false; boolean bAnyJavaMethod = false;
						
			//Fuer den abschliessenden Aufruf selbst.
			String sClassnameWithPackage=null; String sMethodname=null;
			
			//Nein: Nicht die Parse-Methoden der Klasse aufrufen, ... die machen ja auch solve...
//			vecReturn = this.parseFirstVector(sExpression);//Alle Z:Call Ausdruecke ersetzen						
//			String sExpression1 = (String) vecReturn.get(1);
//			if(!StringZZZ.isEmpty(sExpression1)& !sExpression.equals(sExpression1)) {
//				bAnyCall = true;
//			}
			
			//Statt dessen "Elternsolver" aufrufen.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSuper = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSuper.set(objEntry);
			Vector<String>vecValue = super.solveFirstVector(sExpression, objReturnReferenceSuper,bRemoveSurroundingSeparators); 
			if(vecValue==null) break main;
		
			//Nun das Aufloesen dieses konkreten Solvers... der CallIniSolverZZZ
			String sExpression1 = vecValue.get(1);
			if(!StringZZZ.isEmpty(sExpression1)& !sExpression.equals(sExpression1)) {
				sReturn = sExpression1;
			}
			
			//Dazwischen müsste eigentlich noch ein KernelJavaCallIniSolver stehen
            //Aber jetzt entfernen wir das einfach so:
			//Bei dem verschachtelten Tag werden die äußeren Tags genommen...
			Vector<String>vecReturn2 = StringZZZ.vecMid(sExpression1, "<Z:Java>", "</Z:Java>", false, false);//Alle z:Java Ausdruecke ersetzen
			String sExpression2= vecReturn2.get(1);
			if(!StringZZZ.isEmpty(sExpression2)& !sExpression1.equals(sExpression2)) {							
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
						sReturn = XmlUtilZZZ.computeTagEmpty(KernelZFormulaIni_EmptyZZZ.sTAG_NAME);
					}					
				}				
			} //end if bAnyJavaCall
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
		if(vecReturn!=null) {
			if(vecReturn.size()==0) vecReturn.add("");
			
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			if(!StringZZZ.isEmpty(sReturn)){
				vecReturn.add(1, sReturn);
			}else {
				vecReturn.add(1, "");
			}
			
			if(vecReturn.size()<=2) vecReturn.add("");
		}
		this.setValue(sReturn);
		//nein, solve liefert nur 1 Wert zurueck if(objEntry!=null) objEntry.setValue(VectorZZZ.implode(vecReturn));
		if(objEntry!=null) objEntry.setValue(sReturn);
		if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		return vecReturn;	
	
	}
	

	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}

	@Override
	public String convert(String sLine) throws ExceptionZZZ {			
		return null;
	}

	@Override
	public boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ {			
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
