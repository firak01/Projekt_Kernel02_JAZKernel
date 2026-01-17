package basic.zKernel.status;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class EnumSetMappedStatusUtilZZZ implements IConstantZZZ {

	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ[] toEnumSetMappedStatusArray(IEnumSetMappedZZZ[]objaEnumSetMapped){
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
	
	
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ[] toEnumMappedStatusArray(ArrayList<?> listae) throws ExceptionZZZ{
		E[] objaeReturn = null;
		main:{
			if(ArrayListUtilZZZ.isEmpty(listae)) break main;

			//Hier ein Arra<ListZZZ.getInstanceOf() verwenden und nach der Klasse die Fallunterscheidung machen.
			//Das spart 1x dieses durchsehen der Liste....
			ArrayList<Class<?>> listaInstance = ArrayListUtilZZZ.getInstanceOfList(listae);
			if(listaInstance.contains(IEnumSetMappedZZZ.class)) {
				objaeReturn=EnumSetMappedUtilZZZ.toEnumArrayByMapped((ArrayList<IEnumSetMappedZZZ>) listae);
			}else if(listaInstance.contains(IEnumSetMappedStatusZZZ.class)) {
				objaeReturn=EnumSetMappedUtilZZZ.toEnumArrayByMappedStatus((ArrayList<IEnumSetMappedStatusZZZ>) listae);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("The type of ArrayList is not from IEnumSetMappdeZZZ", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getPositionCurrent());
				throw ez;
			}
				
			//obige Lösung ersetzt das mehrmalige interne ausrechnen der Liste.
//			if(ArrayListZZZ.isInstanceOf(listae, IEnumSetMappedZZZ.class)) {
//				objaeReturn=EnumSetMappedUtilZZZ.toEnumArrayByMapped((ArrayList<IEnumSetMappedZZZ>) listae);
//			}else if(ArrayListZZZ.isInstanceOf(listae, IEnumSetMappedStatusZZZ.class)) {
//				objaeReturn=EnumSetMappedUtilZZZ.toEnumArrayByMappedStatus((ArrayList<IEnumSetMappedStatusZZZ>) listae);
//			}else {
//				ExceptionZZZ ez = new ExceptionZZZ("The type of ArrayList is not from IEnumSetMappdeZZZ", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getPositionCurrent());
//				throw ez;
//			}
		}//end main:	
		return objaeReturn;
	}
}
