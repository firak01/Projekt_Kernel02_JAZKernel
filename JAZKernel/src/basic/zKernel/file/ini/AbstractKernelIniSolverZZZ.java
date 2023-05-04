package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelIniSolverZZZ  extends AbstractKernelIniTagZZZ implements IKernelZFormulaIniZZZ, IKernelIniSolverZZZ{
	private IKernelConfigSectionEntryZZZ objEntry = null;
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	public AbstractKernelIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		AbstractKernelIniSolverNew_(saFlag);
	}
		
	public AbstractKernelIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{		
		AbstractKernelIniSolverNew_(saFlag);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniSolverNew_(null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniSolverNew_(saFlag);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ{
		super(objKernelUsing);
		AbstractKernelIniSolverNew_(null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ{
		super(objKernelUsing);
		AbstractKernelIniSolverNew_(saFlag);
	}
	
	private boolean AbstractKernelIniSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der uebergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}
				}			
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	public String getValue() {
		return this.getEntry().getValue();
	}

	public void setValue(String sValue) {
		this.getEntry().setValue(sValue);
	}
	
	/**Methode ersetzt in der Zeile Variablen und Ini-Pfade.
	 * Methode Kann vom konkreten "solver" entwickelt werden.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */
	//public abstract Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);									
			if(!StringZZZ.isEmpty(sExpression)){
				
				//ZUERST: Löse ggfs. übergebene Variablen auf.
				KernelZFormulaIni_VariableZZZ objVariable = new KernelZFormulaIni_VariableZZZ(this.getKernelObject(), this.getHashMapVariable());
				while(objVariable.isExpression(sExpression)){
					sExpression = objVariable.compute(sExpression);			
				} //end while
					
								
				//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
				//Problem hier: [ ] ist auch der JSON Array-Ausdruck
				String sExpressionOld = sExpression;
				KernelZFormulaIni_PathZZZ objIniPath = new KernelZFormulaIni_PathZZZ(this.getKernelObject(), this.getFileIni());
				while(KernelZFormulaIni_PathZZZ.isExpression(sExpression)){
						sExpression = objIniPath.computeAsExpression(sExpression);	
						if(StringZZZ.isEmpty(sExpression)) {
							sExpression = sExpressionOld;
							break;
						}else{
							sExpressionOld = sExpression;							
						}
				} //end while
									
				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
				if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
				vecReturn.add(1, sExpression);
			
			} //end if sExpression = ""					
		}//end main:
		return vecReturn;
	}

	//###### Getter / Setter
	public abstract String getExpressionTagName();

	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}
	
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		return this.hmVariable;
	}
	
	public void setVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
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
	
	public String getVariable(String sKey){
		return (String) this.getHashMapVariable().get(sKey);
	}
	//#########################################

	
	
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			sReturn = (String) vecAll.get(1);
			this.setValue(sReturn);
								
		}//end main:
		return sReturn;
	}	
	
	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.IKernelIniSolverZZZ#computeAsEntry(java.lang.String)
	 */
	@Override
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
											
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			String sReturn = (String) vecAll.get(1);
			this.setValue(sReturn); //Merke: Internes Entry-Objekt nutzen. Darin wurden in den vorherigen Methoden auch Zwischenergebnisse gespeichert.
			
			objReturn = this.getEntry();			
		}//end main:
		return objReturn;
	}	
	
	@Override
	public String[] computeAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
		String[] saReturn = null; //new String[];//sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			String sDelimiter;
			if(StringZZZ.isEmpty(sDelimiterIn)) {
				sDelimiter = IniFile.sINI_MULTIVALUE_SEPARATOR; 
			}else {
				sDelimiter = sDelimiterIn;
			}
				   
			String sExpressionTotal = this.compute(sLineWithExpression); //Hole erst einmal den Kernel-Tag-Wert.
			if(!StringZZZ.isEmpty(sExpressionTotal)) {
				String[] saExpression = StringZZZ.explode(sExpressionTotal, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
				
				String sValue = null;
				ArrayListExtendedZZZ listasValue = new ArrayListExtendedZZZ();
				for(String sExpression : saExpression) {
					
					//Nur für den etwas komplizierteren Fall einer Verschachtelung ...
					if(this.isExpression(sExpression)){
						sValue = this.compute(sExpression);
					}else {
						sValue = sExpression;
					}
					listasValue.add(sValue);
				}
								
				saReturn = listasValue.toStringArray();				
			}
		}//end main:
		return saReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniTagZZZ#computeAsExpression(java.lang.String)
	 */
	@Override
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
				String sReturn = sLineWithExpression;
				main:{
					if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
					
					Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
					
					//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
					sReturn = VectorZZZ.implode(vecAll);
					this.setValue(sReturn);
					
				}//end main:
				return sReturn;
			}
	
	//### Interface aus IKernelExpressionIniSolver
	public IKernelConfigSectionEntryZZZ getEntry() {
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ();			
		}
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
}//End class