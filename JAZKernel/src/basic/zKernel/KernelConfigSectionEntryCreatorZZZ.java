package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class KernelConfigSectionEntryCreatorZZZ implements IConstantZZZ{
	public static IKernelConfigSectionEntryZZZ createEntryEncrypted(String sRawValueIn, String sValueEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ();
		main:{
			if(StringZZZ.isEmpty(sRawValueIn)) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing Parameter RAW-String line with Expression", iERROR_PARAMETER_MISSING, KernelConfigSectionEntryCreatorZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			
			objReturn.setRaw(sRawValueIn);
			objReturn.setValue(sValueEncrypted);
			if(sRawValueIn.equals(sValueEncrypted)) {
				objReturn.isEncrypted(false);
			}else {
				objReturn.isEncrypted(true);
			}
		}//end main:
		return objReturn;
	}
	
	public static IKernelConfigSectionEntryZZZ createEntryEncrypted(String sRawValueIn, ICryptZZZ objCrypt) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ();
		main:{
			if(StringZZZ.isEmpty(sRawValueIn)) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing Parameter RAW-String line with Expression", iERROR_PARAMETER_MISSING, KernelConfigSectionEntryCreatorZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}						
			objReturn.setRaw(sRawValueIn);
						
			if(objCrypt==null) {
				objReturn.setValue(sRawValueIn);				
				objReturn.isEncrypted(false);
				
				objReturn.setRawEncrypted(null);				
			}else {
				String sValueEncrypted = objCrypt.encrypt(sRawValueIn);				
				objReturn = KernelConfigSectionEntryCreatorZZZ.createEntryEncrypted(sRawValueIn, sValueEncrypted, objCrypt);
			}
			 
		}//end main:
		return objReturn;
	}
	
	public static IKernelConfigSectionEntryZZZ createEntryDecrypted(String sRawValueIn, ICryptZZZ objCrypt) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ();
		main:{
			if(StringZZZ.isEmpty(sRawValueIn)) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing Parameter RAW-String line with Expression", iERROR_PARAMETER_MISSING, KernelConfigSectionEntryCreatorZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			objReturn.setRaw(sRawValueIn);
			if(objCrypt==null) {				
				objReturn = KernelConfigSectionEntryCreatorZZZ.createEntryDecrypted(sRawValueIn, null, null);				
			}else {
				objReturn.setCryptAlgorithmType(objCrypt);				
				String sDecrypted = objCrypt.decrypt(sRawValueIn);				
				objReturn = KernelConfigSectionEntryCreatorZZZ.createEntryDecrypted(sRawValueIn, sDecrypted, objCrypt);
			}
			 
		}//end main:
		return objReturn;
	}
	
	public static IKernelConfigSectionEntryZZZ createEntryDecrypted(String sRawValueIn, String sDecrypted, ICryptZZZ objCrypt) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ();
		main:{
			if(StringZZZ.isEmpty(sRawValueIn)) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing Parameter RAW-String line with Expression", iERROR_PARAMETER_MISSING, KernelConfigSectionEntryCreatorZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			objReturn.setRaw(sRawValueIn);
			if(objCrypt==null) {				
				objReturn.setValue(sDecrypted);				
				objReturn.isEncrypted(false);
				objReturn.setRawEncrypted(null);				
			}else {
				objReturn.setCryptAlgorithmType(objCrypt);
				
				objReturn.setValue(sDecrypted);
				objReturn.setRawEncrypted(sRawValueIn);
				if(sRawValueIn.equals(sDecrypted)) {
					objReturn.isEncrypted(false);				
				}else {
					objReturn.isEncrypted(true);	
				}				
			}
			 
		}//end main:
		return objReturn;
	}
	
	public static IKernelConfigSectionEntryZZZ createEntry(String sValueIn) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ();
		main:{			
			objReturn.setValue(sValueIn);				
		}//end main:
		return objReturn;
	}
}
