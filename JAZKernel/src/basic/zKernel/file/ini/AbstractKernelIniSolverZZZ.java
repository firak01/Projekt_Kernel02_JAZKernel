package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelIniSolverZZZ<T>  extends AbstractKernelIniTagCascadedZZZ<T> implements IKernelFileIniUserZZZ, IKernelZFormulaIniZZZ, IValueSolverZTagIniZZZ, IKernelIniSolverZZZ, IKernelConfigSectionEntryUserZZZ{
	private static final long serialVersionUID = -4816468638381054061L;
	protected FileIniZZZ objFileIni=null;
	protected HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	public AbstractKernelIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		AbstractKernelIniSolverNew_();
	}
	
	public AbstractKernelIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		AbstractKernelIniSolverNew_();
	}
		
	public AbstractKernelIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		AbstractKernelIniSolverNew_();
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniSolverNew_();
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_();
	}
	
	public AbstractKernelIniSolverZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ{
		super(objKernelUsing);
		AbstractKernelIniSolverNew_();
	}
	
	public AbstractKernelIniSolverZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ{
		super(objKernelUsing, saFlag);
		AbstractKernelIniSolverNew_();
	}
	
	private boolean AbstractKernelIniSolverNew_() throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}	
			
			
	 	}//end main:
		return bReturn;
	 }//end function AbstractKernelIniSolverNew_
		
	public String getValue() {
		return this.getEntry().getValue();
	}

	public void setValue(String sValue) {
		this.getEntry().setValue(sValue);
	}
	
	//### aus IKernelConfigSectionEntryUserZZZ
	public IKernelConfigSectionEntryZZZ getEntry() {
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ();			
		}
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
	
	/**Methode muss vom konkreten "solver" ueberschrieben werden, wenn darin keine Pfade oder Variablen ersetzt werden sollen.
	 * @param sLineWithExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ {		
             //Darin können also auch Variablen, etc. sein
			Vector<String> vecReturn = new Vector<String>();
			main:{
				if(StringZZZ.isEmpty(sLineWithExpression)) break main;
				
				vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
				String sExpression = (String) vecReturn.get(1);									
				if(!StringZZZ.isEmpty(sExpression)){
					
					//ZUERST: Löse ggfs. übergebene Variablen auf.
					ZTagFormulaIni_VariableZZZ objVariable = new ZTagFormulaIni_VariableZZZ(this.getHashMapVariable());
					while(objVariable.isExpression(sExpression)){
						sExpression = objVariable.compute(sExpression);			
					} //end while
						
									
					//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
					//Problem hier: [ ] ist auch der JSON Array-Ausdruck
					String sExpressionOld = sExpression;
					KernelZFormulaIni_PathZZZ objIniPath = new KernelZFormulaIni_PathZZZ(this.getKernelObject(), this.getFileConfigKernelIni());
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
	
	//### Aus IKernelFileIniUserZZZ
	@Override
	public void setFileConfigKernelIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	@Override
	public FileIniZZZ getFileConfigKernelIni()throws ExceptionZZZ{
		if(this.objFileIni==null) {
			IKernelZZZ objKernel = this.getKernelObject();
			if(objKernel==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIni and KernelObject", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			return objKernel.getFileConfigKernelIni();						
		}else {
			return this.objFileIni;
		}
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
	
	//### aus IKernelZFormulaIniZZZ
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#isStringForConvertRelevant(java.lang.String)
	 */
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.isConvertable(sStringToProof);
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#convert(java.lang.String)
	 */
	@Override
	public abstract String convert(String sLine) throws ExceptionZZZ;

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#isStringForComputeRelevant(java.lang.String)
	 */
	@Override
	public abstract boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ;

}//End class