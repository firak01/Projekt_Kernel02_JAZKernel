package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelIniSolverZZZ  extends KernelUseObjectZZZ implements IKernelZFormulaIniZZZ, IKernelIniSolverZZZ{
	private String sValue;
	
	public AbstractKernelIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		AbstractKernelIniTagNew_(saFlag);
	}
		
	public AbstractKernelIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{		
		AbstractKernelIniTagNew_(saFlag);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniTagNew_(null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniTagNew_(saFlag);
	}
	
	
	private boolean AbstractKernelIniTagNew_(String[] saFlagControlIn) throws ExceptionZZZ {
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
		return this.sValue;
	}

	public void setValue(String sValue) {
		this.sValue = sValue;
	}
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			//TODO: Mehrere Ausdrücke ineinander verschachtelt. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			
			//... und vielleicht erneut den Math-Solver auf den Rest ansetzen.
			
		}
		return vecReturn;
	}
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getExpressionTagStarting(), this.getExpressionTagClosing(), false, false);
		}
		return vecReturn;
	}
	
	
	public boolean isExpression(String sLine) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, this.getExpressionTagStarting(),false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, this.getExpressionTagClosing(),false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public abstract String getExpressionTagName();
	
	public String getExpressionTagStarting() throws ExceptionZZZ{
		return AbstractKernelIniSolverZZZ.computeExpressionTagStarting(this.getExpressionTagName());		
	}
	public String getExpressionTagClosing() throws ExceptionZZZ{
		return AbstractKernelIniSolverZZZ.computeExpressionTagClosing(this.getExpressionTagName());		
	}	
	public String getExpressionTagEmpty()throws ExceptionZZZ{
		return AbstractKernelIniSolverZZZ.computeExpressionTagEmpty(this.getExpressionTagName());		
	}
	
	public static String computeExpressionTagStarting(String sTagName) throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractKernelIniSolverZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "<" + sTagName + ">";
	}
	
	public static String computeExpressionTagClosing(String sTagName)throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractKernelIniSolverZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "</" + sTagName + ">"; 
	}
	
	public static String computeExpressionTagEmpty(String sTagName)throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractKernelIniSolverZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "<" + sTagName + "/>";
	}
	

	//### Aus Interface IKernelExpressionIniZZZ
	public abstract boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ;
	public abstract String convert(String sLine) throws ExceptionZZZ;		
	
	public abstract boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ;
			
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
	
	
	@Override
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
					
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			TODOGOON20230426;//Hier die Methode abwandeln und objReturn ueber ein Referenzobjekt hineingeben. Damit können darin zwischenergebnisse abgelegt werden.
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression, objReturnReference);
			
			String sReturn = (String) vecAll.get(1);
			this.setValue(sReturn);
			
			objReturn = objReturnReference.get();
			objReturn.setValue(sReturn);
			
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
}//End class