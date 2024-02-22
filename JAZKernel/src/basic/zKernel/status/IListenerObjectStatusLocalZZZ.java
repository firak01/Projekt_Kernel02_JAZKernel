package basic.zKernel.status;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_NullZZZ;
import basic.zKernel.status.IStatusLocalMessageUserZZZ.FLAGZ;

public interface IListenerObjectStatusLocalZZZ extends IListenerObjectStatusBasicZZZ{
	//Hier wird ggfs. der empfangene Status mit einem Alias gemappt. 
	//Der Alias kann dann in der "reaction"-Methode verwendet werden um gezielt zu reagieren.
	//Merke: Ohne HashMap oder mit leerer HashMap ist jede Action relevant.
	//Merke: Es können als Action-Angabe auch verwendet werden
	//       KernelZFormulaIni_NullZZZ.getExpressionTagNull(), d.h. ist NICHT relevant, s. HashMap=NULL 
	//       KernelZFormulaIni_NullZZZ.getExpressionTagEmpty(), d.h. ist auf jeden Fall relevant, ohne spezielle Differenzierung. Wie "".
	public HashMap<IEnumSetMappedStatusZZZ,String> getHashMapStatusLocalReaction(); 
	
	public void setHashMapStatusLocalReaction(HashMap<IEnumSetMappedStatusZZZ,String> hmEnumSetForReaction);	
	public HashMap<IEnumSetMappedStatusZZZ,String> createHashMapStatusLocalReactionCustom();
	public String getActionAliasString(IEnumSetMappedStatusZZZ enumStatus);
	
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	public boolean isEventRelevant(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
