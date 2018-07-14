package basic.zKernel.file.ini;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import custom.zKernel.file.ini.FileIniZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;

/**Diese Klasse verarbeitet ggf. Ausdr�cke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelExpressionIniSolverZZZ extends KernelUseObjectZZZ{
	public enum FLAGZ{
		USEFORMULA_MATH
	}
	
	private FileIniZZZ objFileIni=null;
	
	public KernelExpressionIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionIniSolverNew_(null, saFlag);
	}
	
	public KernelExpressionIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, null);
	}
	
	public KernelExpressionIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniSolverNew_(objFileIni, saFlag);
	}
	
	public KernelExpressionIniSolverZZZ(KernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniSolverNew_(objFileIni, saFlag);
	}
	
	
	private boolean KernelExpressionIniSolverNew_(FileIniZZZ objFileIn, String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der �bergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}
				}
			
				if(objFileIn==null ){
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}else{
					this.setFileIni(objFileIn);
				}
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
			if(this.getFlag("useFormula_math")==true){
				//Erst den Vector der "übersetzten" Werte zusammensetzen
				String sLineWithExpression2Check = VectorZZZ.implode(vecAll);
			
				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
				KernelExpressionMathSolverZZZ objMathSolver = new KernelExpressionMathSolverZZZ(); 
													
				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
				if(objMathSolver.isExpression(sLineWithExpression2Check)){
					String sValueMath = objMathSolver.compute(sLineWithExpression2Check);
					sReturn = sValueMath;
				}else{
					sReturn = sLineWithExpression2Check;
				}
			}else{									
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturn = VectorZZZ.implode(vecAll);
			}
		}//end main:
		return sReturn;
	}
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			
			//TODO: Mehrere Ausdr�cke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			String sExpression = (String) vecReturn.get(1);
			if(!StringZZZ.isEmpty(sExpression)){
					
				//Nun die Section suchen
				Vector vecSection = StringZZZ.vecMidFirst(sExpression, "[", "]", false);
				String sSection = (String) vecSection.get(1);
				String sProperty = (String) vecSection.get(2);
				if(!(StringZZZ.isEmpty(sSection) || StringZZZ.isEmpty(sProperty))){
				FileIniZZZ objFileIni = this.getFileIni();
				if(objFileIni==null){
					ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
//				20080109 FGL: Falls es eine Section gibt, so muss die Aufl�sung der Section über eine Suche über die Systemnummer erfolgen
				//String sValue =  objFileIni.getPropertyValue(sSection, sProperty);
				String sSystemNr = this.getKernelObject().getSystemNumber();
				String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr);
				
				//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
				vecReturn.removeElementAt(1);
				vecReturn.add(1, sValue);
				
//				TODO: Verschachtelung der Ausdrücke. Dann muss das jeweilige "Vector Element" des ExpressionFirst-Vectors erneut mit this.computeExpressionFirstVector(...) zerlegt werden.
								
				}
			}				
		}//end main:
		return vecReturn;
	}
	
	/** Gibt einen Vector zur�ck, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelExpressionIniSolverZZZ.getExpressionTagStarting(), KernelExpressionIniSolverZZZ.getExpressionTagClosing(), false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelExpressionIniSolverZZZ.getExpressionTagStarting());
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelExpressionIniSolverZZZ.getExpressionTagClosing());
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "Z";
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelExpressionIniSolverZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelExpressionIniSolverZZZ.getExpressionTagName() + ">"; 
	}
	
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}
	
	
}//End class
