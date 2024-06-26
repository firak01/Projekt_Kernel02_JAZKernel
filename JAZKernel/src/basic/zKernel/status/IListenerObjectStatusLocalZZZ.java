package basic.zKernel.status;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface IListenerObjectStatusLocalZZZ extends IListenerObjectStatusBasicZZZ{
	//Hier wird ggfs. der empfangene Status mit einem Alias gemappt. 
	//Der Alias kann dann in der "reaction"-Methode verwendet werden um gezielt zu reagieren.
	//Merke: Ohne HashMap oder mit leerer HashMap ist jede Action relevant.
	//Merke: Es können als Action-Angabe auch verwendet werden
	//       KernelZFormulaIni_NullZZZ.getExpressionTagNull(), d.h. ist NICHT relevant, s. HashMap=NULL 
	//       KernelZFormulaIni_NullZZZ.getExpressionTagEmpty(), d.h. ist auf jeden Fall relevant, ohne spezielle Differenzierung. Wie "".
	public HashMap<IEnumSetMappedStatusZZZ,String> getHashMapStatusLocal4Reaction(); 	
	public void setHashMapStatusLocal4Reaction(HashMap<IEnumSetMappedStatusZZZ,String> hmEnumSetForReaction);	
	
	//Eine HashMap mit den StatusLocal-Enums und einem ActionAlias.
	//Hier macht es nur Sinn StatusLocal-Enums aufzunehmen von Objekten, an denen das Objekt selbst als  Listener registriert ist.
	//Die definierten ActionAliassse werden verwendet in der Methode: reactOnStatusLocalEvent4ActionCustom
	public HashMap<IEnumSetMappedStatusZZZ,String> createHashMapStatusLocal4ReactionCustom();
	
	public String getActionAliasString(IEnumSetMappedStatusZZZ enumStatus);
		
	public boolean queryReactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean queryReactOnStatusLocalEventCustom(IEventObjectStatusLocalZZZ eventStatusLocal)throws ExceptionZZZ; //Darueber wird abgeprueft, ob eine Reaktion auf ein Event erfolgen soll. Z.B. beendete Programme koennen so eine Reaktion unterbinden.
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	
	public boolean queryReactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;	
	public boolean reactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	//Hierin werden die ActionAliasse aus der HashMap createHashMapStatusLocal4ReactionCustom
	//in einer Fallunterscheidung zurm Aufruf der jeweiligen Methode verwendet.
	public boolean queryReactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	public boolean queryReactOnStatusLocal4ActionCustom(String sActionAlias, IEnumSetMappedStatusZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	public boolean reactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	public boolean reactOnStatusLocal4ActionCustom(String sActionAlias, IEnumSetMappedStatusZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	
	public boolean isEventRelevantAny(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ; //also überhaupt entweder für den eingenen Statuswechsel relevant oder fuer eine Reaction
	public boolean isEventRelevant4ReactionOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	public boolean isEventRelevant2ChangeStatusLocalByClass(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean isEventRelevant2ChangeStatusLocalByStatusLocalValue(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	//Merke: REGISTER_ON_SELF_FOR_EVENT bewirkt, dass der geworfenen Event vom Objekt selbst empfangen wird. 
	//                                           Dadurch ist die Reaktion ggfs. schneller und VOR allen anderen registrierten Objekten. 
	public enum FLAGZ{
		DUMMY,REGISTER_SELF_FOR_EVENT, STATUSLOCAL_REACT_ON_VALUEFALSE
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
