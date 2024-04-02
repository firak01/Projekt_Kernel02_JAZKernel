package basic.zKernel.status;

import java.util.ArrayList;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public class EnumSetMappedStatusUtilZZZ {

	public static <E extends IEnumSetMappedStatusZZZ> E[] toEnumSetMappedStatusArray(IEnumSetMappedZZZ[]objaEnumSetMapped){
		E[] enumaReturn = null;
		main:{
			if(objaEnumSetMapped==null)break main;
			
			//Ein einfaches Casten fuehrt zu einer CAST Exception.
			//enumaReturn = (E[]) enumaReturnTemp;
			
			//Lösung: In Schleife durchgehen und alles einzeln casten.
			ArrayList<IEnumSetMappedStatusZZZ>listaStatus=new ArrayList<IEnumSetMappedStatusZZZ>();
			for(IEnumSetMappedZZZ objEnumSetMapped:objaEnumSetMapped) {
				IEnumSetMappedStatusZZZ objStatus = (IEnumSetMappedStatusZZZ) objEnumSetMapped;
				listaStatus.add(objStatus);
			}
			enumaReturn = (E[]) listaStatus.toArray(new IEnumSetMappedStatusZZZ[listaStatus.size()]);
		}//end main
		return enumaReturn;
	}
}
