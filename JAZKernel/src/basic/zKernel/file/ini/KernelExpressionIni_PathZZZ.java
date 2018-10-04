package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelExpressionIni_PathZZZ  extends KernelUseObjectZZZ{
//	public enum FLAGZ{
//		USEFORMULA_MATH
//	}
	
	private FileIniZZZ objFileIni=null;
		
	public KernelExpressionIni_PathZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelExpressionIniPathNew_(null, saFlag);
	}
	
	public KernelExpressionIni_PathZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniPathNew_(objFileIni, null);
	}
	
	public KernelExpressionIni_PathZZZ(FileIniZZZ objFileIni,String[] saFlag) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniPathNew_(objFileIni, saFlag);
	}
	
	public KernelExpressionIni_PathZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniPathNew_(objFileIni, null);
	}
	
	public KernelExpressionIni_PathZZZ(KernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniPathNew_(objFileIni, saFlag);
	}
	
	
	private boolean KernelExpressionIniPathNew_(FileIniZZZ objFileIni, String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{		
	 			//setzen der übergebenen Flags	
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
				this.setFileIni(objFileIni);
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorZZZ.implode(vecAll);
			
		}//end main:
		return sReturn;
	}
	
	public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Nun die Section suchen
			Vector vecSection = this.computeExpressionFirstVector(sLineWithExpression);	
								
			String sSection = (String) vecSection.get(1);
			String sProperty = (String) vecSection.get(2);
			String sBefore = "";
			String sRest = "";
						
			if(!(StringZZZ.isEmpty(sSection) || StringZZZ.isEmpty(sProperty))){

					//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
					String sMathValueTag = KernelExpressionMath_ValueZZZ.getExpressionTagClosing();
					if(StringZZZ.contains(sProperty, sMathValueTag)){
						sBefore = (String) vecSection.get(0);
						sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
						sProperty = StringZZZ.left(sProperty, sMathValueTag);												
					}
										
					FileIniZZZ objFileIni = this.getFileIni();
					if(objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					//20080109 FGL: Falls es eine Section gibt, so muss die Aufl�sung der Section über eine Suche über die Systemnummer erfolgen
					String sSystemNr = this.getKernelObject().getSystemNumber();
					String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr);
					
					//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
					if(sValue!=null){
						
						//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
						if(!StringZZZ.isEmpty(sBefore)){
							if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
							vecReturn.add(0, sBefore);
						}else{
							vecReturn.add(0,"");
						}
																
						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
						vecReturn.add(1, sValue);
						
						if(vecReturn.size()>=3) vecReturn.removeElementAt(2); //Immer den Namen der Property löschen....
						if(!StringZZZ.isEmpty(sRest)){							
							vecReturn.add(2, sRest); //Fallls vorhanden einen Restwert eintragen.
						}else{
							vecReturn.add(2,"");
						}		
				}//end if sValue!=null
									
			}//end if isempty(sSection)
			
		}//end main:
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
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, KernelExpressionIni_PathZZZ.getExpressionTagStarting(), KernelExpressionIni_PathZZZ.getExpressionTagClosing(), false,false);
		}
		return vecReturn;
	}
	
	
	public static boolean isExpression(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelExpressionIni_PathZZZ.getExpressionTagStarting(), false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelExpressionIni_PathZZZ.getExpressionTagClosing(), false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "";  //Hier kein Tag
	}
	public static String getExpressionTagStarting(){
		return "[";
	}
	public static String getExpressionTagClosing(){
		return "]"; 
	}		
	
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}
}//End class