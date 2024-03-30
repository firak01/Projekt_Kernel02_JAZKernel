package basic.zKernel.status;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_NullZZZ;

public class StatusLocalEventHelperZZZ {
	public static boolean isEventRelevant4ReactionOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalReact, HashMap<IEnumSetMappedStatusZZZ,String>hmStatusLocal4Reaction, ReferenceZZZ<String>objReturnReferenceLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sLog;
			
			if(hmStatusLocal4Reaction==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+ "ReactionHashMap NULL => jeder Status ist relevant.";
				objReturnReferenceLog.set(sLog);
				break main;			
			}
			if(hmStatusLocal4Reaction.isEmpty()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap Empty => jeder Status ist relevant.";
				objReturnReferenceLog.set(sLog);
				break main;	
			}

		
			//Hole den vermeintlichen ActionAlias aus der HashMap
			IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocalReact.getStatusEnum();			
			String sActionAlias = hmStatusLocal4Reaction.get(enumStatus);
		
			
			//Status nicht gepflegt, wenn durchaus andere Statuseintraege vorhanden sind oder gepflegt und ActionAlias Leer => NICHT relevant 
			if(StringZZZ.isEmptyTrimmed(sActionAlias)) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat Leerstring fuer den Status '" + enumStatus.getName() + "'=> ist NICHT relevant.";
				objReturnReferenceLog.set(sLog);
				bReturn = false;
				break main;
			}
			if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat "+ KernelZFormulaIni_NullZZZ.getExpressionTagEmpty() + " fuer den Status '" + enumStatus.getName() + "'=> ist NICHT relevant.";
				objReturnReferenceLog.set(sLog);
				bReturn = false;
				break main;
			}		
			if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ReactionHashMap hat "+ KernelZFormulaIni_EmptyZZZ.getExpressionTagEmpty() + " fuer den Status '" + enumStatus.getName() + "'=> ist NICHT relevant.";
				objReturnReferenceLog.set(sLog);
				bReturn = false;
				break main;
			}	
	
			bReturn = true;
		}//end main:			
		return bReturn;
	}
}
