package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaMathSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = -6400035649490240580L;
	public static String sTAG_NAME = "Z:math";
	
	public KernelZFormulaMathSolverZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelZFormulaMathSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelExpressionMathSolverNew_();
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_();
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_();
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag)  throws ExceptionZZZ{
		super(objKernel, objFileIni, saFlag);
		KernelExpressionMathSolverNew_();
	}
	
	
	private boolean KernelExpressionMathSolverNew_() throws ExceptionZZZ {
		 boolean bReturn = false; 
		 main:{			 																
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}										
					
				bReturn = true;
		 	}//end main:
			return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	
	//###### Getter / Setter
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelZFormulaMathSolverZZZ.sTAG_NAME;
	}

	//### Andere Interfaces	
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {
		return false;
	}
	
	//### Aus ISolveEnabled		
	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public String solveParsed(String sExpression) throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, true);
	}
	
	@Override
	public String solveParsed(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	//Analog zu AbstractkernelIniSolverZZZ, nur jetzt mit MATH-Tag (vorher aber noch Pfade und ini-Variablen aufloesen)
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
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolverSuper.set(objEntry);
			sReturn = super.solveParsed(sExpressionIn, objReturnReferenceSolverSuper, bRemoveSurroundingSeparators);
			objEntry = objReturnReferenceSolverSuper.get();
			
			//Aufloesen des Math-Tags
			sReturn = this.solveParsed_Math_(sExpressionIn, objReturnReference, bRemoveSurroundingSeparators);			
			objEntry = objReturnReference.get();								
		}//end main
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	
	private String solveParsed_Math_(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		IKernelConfigSectionEntryZZZ objEntry = null;
		String sReturn = sExpression;	
		
		boolean bUseExpression=false;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			bUseExpression = this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
						
			boolean bUseSolver = this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);		
			if(!bUseFormula) break main;
			
			boolean bUseFormulaMath = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);		
			if(!bUseFormulaMath) break main;
			
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
			if(objReturnReferenceIn==null) {
				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
			}else {
				objReturnReference = objReturnReferenceIn;				
			}
						
			objEntry = objReturnReference.get();
			if(objEntry==null) {
				//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"				
				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
				                                                     //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
				objReturnReference.set(objEntry);
			}	//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			objEntry.setRaw(sExpression);
			
			//++++++++++++++++++++++++++++++
		    //Das Ziel ist, das der Operator nur mit der reinen Formel arbeitet. Da er umgebenden Text nicht herausfiltern kann.
			//Nun den z:operator Tag suchen
			String sFormulaExpression = sExpression;
			ZTagFormulaMath_OperatorZZZ<T> objOperator = new ZTagFormulaMath_OperatorZZZ<T>();
			if(objOperator.isExpression(sFormulaExpression)){
				 sExpression = objOperator.parse(sFormulaExpression);
				 sReturn = sExpression;
			}else{
				//Da gibt es wohl nix weiter auszurechen...	also die Werte als String nebeneinander setzen....
				//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
				ZTagFormulaMath_ValueZZZ<T> objValue = new ZTagFormulaMath_ValueZZZ<T>();
				
				String sExpressionOld = sFormulaExpression; 
				while(objValue.isExpression(sExpressionOld)){
					sExpression = objValue.parse(sExpressionOld);
//						String sDebug = (String) vecValue.get(1);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
					
					if(sExpressionOld.equals(sExpression)) break; //Sicherheitsmassnahme gegen Endlosschleife
					sExpressionOld = sExpression;
				}													
				 sReturn = sExpression;
			}//end if operator.isExpression(...)
			
			
		}//end main:

		//Als echten Ergebniswert aber die <Z:math>-Tags rausrechnen
		if(bRemoveSurroundingSeparators) {
			String sTagStart = this.getTagStarting();
			String sTagEnd = this.getTagClosing();
			String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);												
			sReturn = sValue;
		}			
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturn);		
		if(objEntry!=null) {		
			objEntry.setValue(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	//##########################
	//### FLAG Handling
	
	//### Aus Interface IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ) {
		return this.getFlag(objEnum_IKernelEncryptionIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ.name(), bFlagValue);
	}
	
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelEncryptionIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelEncryptionIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ:objaEnum_IKernelEncryptionIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class