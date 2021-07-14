package basic.zKernel.file.ini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import custom.zKernel.file.ini.FileIniZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelExpressionIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelJsonIniSolverZZZ extends KernelUseObjectZZZ implements IKerneJsonIniSolverZZZ{	
	private FileIniZZZ objFileIni=null;
	private HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	
	public KernelJsonIniSolverZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		KernelJsonIniSolverNew_(null, saFlag);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelJsonIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelJsonIniSolverNew_(objFileIni, saFlag);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonIniSolverNew_(objFileIni, saFlag);
	}
			
	private boolean KernelJsonIniSolverNew_(FileIniZZZ objFileIn, String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
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
			
				if(objFileIn==null ){
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}else{
					this.setFileIni(objFileIn);	
					if(objFileIn.getHashMapVariable()!=null){
						this.setHashMapVariable(objFileIn.getHashMapVariable());
					}
				}

	 	}//end main:
		return bReturn;
	 }//end function KernelJsonIniSolverNew_
					
	public static boolean isJson(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, KernelJsonIniSolverZZZ.getExpressionTagStarting(),false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, KernelJsonIniSolverZZZ.getExpressionTagClosing(),false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//###### Getter / Setter
	public static String getExpressionTagName(){
		return "JSON";
	}
	public static String getExpressionTagStarting(){
		return "<" + KernelJsonIniSolverZZZ.getExpressionTagName() + ">";
	}
	public static String getExpressionTagClosing(){
		return "</" + KernelJsonIniSolverZZZ.getExpressionTagName() + ">"; 
	}
	public static String getExpressionTagEmpty(){
		return "<" + KernelJsonIniSolverZZZ.getExpressionTagName() + "/>";
	}
	
	
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}
			
	public HashMap<String,String> computeHashMap(String sLineWithExpression) throws ExceptionZZZ{
		HashMap<String,String>hmReturn=new HashMap<String,String>();				
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			if(this.getFlag("useJson")==false) break main; 			
			if(this.getFlag("useJsonMap")==true){				
		
				//Dann erzeuge neues KernelJsonMapSolverZZZ - Objekt.
				KernelJsonMapIniSolverZZZ objJsonMapSolver = new KernelJsonMapIniSolverZZZ(); 
													
				//2. Ist in dem String JSON:MAP
				while(objJsonMapSolver.isExpression(sLineWithExpression)){
					
					//Der Returnwert soll eine HashMap<String,String> sein !!
					hmReturn=objJsonMapSolver.computeHashMap(sLineWithExpression);			
				}													
			}else{																	
			}						
		}//end main:
		return hmReturn;
	}
}//End class
