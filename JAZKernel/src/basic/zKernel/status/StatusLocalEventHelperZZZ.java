package basic.zKernel.status;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_NullZZZ;

public class StatusLocalEventHelperZZZ  implements IConstantZZZ{
	public static boolean isEventRelevant4ReactionOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalReact, HashMap<IEnumSetMappedStatusZZZ,String>hmStatusLocal4Reaction, ReferenceArrayZZZ<String>objReturnReference) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sLog;
			
			if(hmStatusLocal4Reaction==null) {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+ "ReactionHashMap NULL => jeder Status ist relevant.";
//				objReturnReference.add(sLog);
				bReturn=true;
				break main;			
			}
			if(hmStatusLocal4Reaction.isEmpty()) {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap Empty => jeder Status ist relevant.";
//				objReturnReference.add(sLog);
				bReturn = true;
				break main;	
			}

		
			//Hole den vermeintlichen ActionAlias aus der HashMap
			IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocalReact.getStatusEnum();
			
			ReferenceArrayZZZ<String> objReturnReferenceTemp = new ReferenceArrayZZZ<String>();
			String sActionAlias = StatusLocalEventHelperZZZ.getActionAliasString4Reaction(enumStatus, hmStatusLocal4Reaction, objReturnReferenceTemp);
			String[] saLog = objReturnReferenceTemp.get(); 
			if(!ArrayUtilZZZ.isNull(saLog)) {
				objReturnReference.add(saLog);//also Temp weiter nach aussen geben...
			}
			
			//Status nicht gepflegt, wenn durchaus andere Statuseintraege vorhanden sind oder gepflegt und ActionAlias Leer => NICHT relevant 
			if(StringZZZ.isEmpty(sActionAlias)) {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat Leerstring fuer den Status '" + enumStatus.getName() + "'=> ist NICHT relevant.";
//				objReturnReference.add(sLog);
				bReturn = false;
				break main;
			}
			
	
			bReturn = true;
		}//end main:			
		return bReturn;
	}
	
	public static String getActionAliasString4Reaction(IEnumSetMappedStatusZZZ enumStatus, HashMap<IEnumSetMappedStatusZZZ,String>hmEnum, ReferenceArrayZZZ<String>objReturnReferenceLog) throws ExceptionZZZ{
		return StatusLocalEventHelperZZZ.getActionAliasString4Reaction(enumStatus, hmEnum, objReturnReferenceLog, true);
	
	}
	public static String getActionAliasString4Reaction(IEnumSetMappedStatusZZZ enumStatus, HashMap<IEnumSetMappedStatusZZZ,String>hmEnum, ReferenceArrayZZZ<String>objReturnReferenceLog, boolean bConvertImmediate) throws ExceptionZZZ{
		String sReturn = null;
		main:{
		
			if(enumStatus==null) {				 
				 ExceptionZZZ ez = new ExceptionZZZ( "EnumStatus-Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName()); 
				 throw ez;
			}
				
			if(hmEnum==null) {
				 ExceptionZZZ ez = new ExceptionZZZ("HashMapEnum", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				 throw ez;
			}
			
			String sLog=null;
			
			String sActionAlias = hmEnum.get(enumStatus);			
			if(StringZZZ.isEmptyTrimmed(sActionAlias)) {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat Leerstring fuer den Status '" + enumStatus.getName();
//				objReturnReferenceLog.add(sLog);
				break main;
			}
			if(bConvertImmediate) {
				if(KernelConfigEntryUtilZZZ.isConvertable(sActionAlias)){
					
					//TODOGOON20240418;//Nutze einen KernelExpressionIniSolverZZZ, den es erst noch zu schreiben gilt.
					//                   Der ohne IniFile nur fuer eine Zeile gilt.
					//                   s. KernelJavaCallIniSolverZZZ
					if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) {
		//				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat '"+ KernelZFormulaIni_NullZZZ.getExpressionTagEmpty() + "' fuer den Status '" + enumStatus.getName() + "'";
		//				objReturnReferenceLog.add(sLog);				
						break main;
					}		
					if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) {
		//				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat '"+ KernelZFormulaIni_EmptyZZZ.getExpressionTagEmpty() + "' fuer den Status '" + enumStatus.getName() + "'";
		//				objReturnReferenceLog.add(sLog);
						break main;
					}				
				}
			}
			
			sReturn = sActionAlias;
		}//end main:
		return sReturn;
	}	
	
	
	public static String getActionAliasString4Reaction(String sStatusName, HashMap<IEnumSetMappedStatusZZZ,String>hmEnum, ReferenceArrayZZZ<String>objReturnReferenceLog) throws ExceptionZZZ{
		String sReturn = null;
		main:{
		
			if(StringZZZ.isEmpty(sStatusName)) {				 
				 ExceptionZZZ ez = new ExceptionZZZ( "EnumStatus-String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName()); 
				 throw ez;
			}
				
			if(hmEnum==null) {
				 ExceptionZZZ ez = new ExceptionZZZ("HashMapEnum", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				 throw ez;
			}
			
			String sLog=null;

			//#######################################################################################
			//Fuer die Weiterverarbeitung fehlt noch das IEnumSetMappedStatusZZZ Objekt 
			ReferenceArrayZZZ<String>objReferenceLog02 = new ReferenceArrayZZZ<String>();
			IEnumSetMappedStatusZZZ enumStatus = StatusLocalEventHelperZZZ.getEnumStatusMapped(sStatusName, hmEnum, objReferenceLog02);
			String[] saLog02 = objReferenceLog02.get();
			if(!ArrayUtilZZZ.isNull(saLog02)) {
				objReturnReferenceLog.add(saLog02);				
			}
			if(enumStatus==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat kein IEnumSetMappedStatusZZZ-Objekt als Schluessel fuer den Status '" + sStatusName + "'";
				objReturnReferenceLog.add(sLog);
				break main;
			}
			
			//Mit dem IEnumSetMappedStatusZZZ als Key kann man nun den Action Alias suchen.
			ReferenceArrayZZZ<String>objReferenceLog = new ReferenceArrayZZZ<String>();
			String sActionAlias = StatusLocalEventHelperZZZ.getActionAliasString4Reaction(enumStatus, hmEnum, objReferenceLog);
			String[] saLog = objReferenceLog.get();
			if(!ArrayUtilZZZ.isNull(saLog)) {
				objReturnReferenceLog.add(saLog);				
			}
			
			sReturn = sActionAlias;
		}//end main:
		return sReturn;
	}	

	public static IEnumSetMappedStatusZZZ getEnumStatusMapped(String sStatusName, HashMap<IEnumSetMappedStatusZZZ,String>hmEnum, ReferenceArrayZZZ<String>objReturnReference) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sStatusName)) {				 
				 ExceptionZZZ ez = new ExceptionZZZ( "EnumStatus-String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName()); 
				 throw ez;
			}
			
			if(hmEnum==null) {
				 ExceptionZZZ ez = new ExceptionZZZ("HashMapEnum", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
				 throw ez;				
			}
			
				
			//Gehe das KeySet der HashMap durch.
			Set<IEnumSetMappedStatusZZZ> setEnumMapped = hmEnum.keySet();
			Iterator<IEnumSetMappedStatusZZZ> itEnumMapped = setEnumMapped.iterator();
			while(itEnumMapped.hasNext()) {
				IEnumSetMappedStatusZZZ objEnumMapped = itEnumMapped.next();
				String sEnumMapped = objEnumMapped.getName();
				if(sStatusName.equals(sEnumMapped)) {
					objReturn = objEnumMapped;
					break main;
				}				
			}						
		}//end main:
		return objReturn;
	}
	
}
