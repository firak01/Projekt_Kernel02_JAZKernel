package basic.zBasic.util.abstractList;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class ArrayListZZZ extends ObjectZZZ {

public static ArrayList unique(ArrayList lista){
	ArrayList listaReturn = null;
	main:{
		check:{
			if(lista==null)break main;			
		}//END check:
	
	listaReturn=new ArrayList();
	for(int icount=0; icount < lista.size(); icount++ ){
		if(! listaReturn.contains(lista.get(icount))) listaReturn.add(lista.get(icount));
	}	
	}//End main:
	return listaReturn;
}

public static ArrayList join(ArrayList lista1, ArrayList lista2, boolean bFlagUnique){
	ArrayList listaReturn = null;
	main:{
		check:{
			if(lista1==null && lista2 ==null) break main;
		}//END check:
	
	if(bFlagUnique==false){
		//Wenn nicht ''uniqued' werden soll, dann kann man sofort in der Return-liste joinen
		listaReturn = new ArrayList();
		if(lista1!=null){
			for(int icount=0; icount < lista1.size(); icount++){
				listaReturn.add(lista1.get(icount));
			}
		}//lista1!=null
		if(lista2!=null){
			for(int icount=0; icount < lista2.size(); icount ++){
				listaReturn.add(lista2.get(icount));
			}
		}		
		break main;
		
	}else{
		//Wenn 'uniqued' werden soll, dann erst in eine temporäre Liste joinen
		ArrayList listaTemp = new ArrayList();
		if(lista1!=null){
			for(int icount=0; icount < lista1.size(); icount++){
				listaTemp.add(lista1.get(icount));
			}
		}//lista1!=null
		if(lista2!=null){
			for(int icount=0; icount < lista2.size(); icount ++){
				listaTemp.add(lista2.get(icount));
			}
		}		
		
		listaReturn = ArrayListZZZ.unique(listaTemp);		
		break main;
	}//End if (bFlagUnique ....
	
	}//END main:
	return listaReturn;
}


public static boolean isSameSize(ArrayList objAL1, ArrayList objAL2) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(objAL1== null){
			ExceptionZZZ ez = new ExceptionZZZ("ArrayList1 to compare'", iERROR_PARAMETER_MISSING,  HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
			throw ez;	
		  }
		if(objAL2== null){
			ExceptionZZZ ez = new ExceptionZZZ("ArrayList2 to compare'", iERROR_PARAMETER_MISSING,   HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
			throw ez;	
		  }
		//###################
		int iSize1 = objAL1.size();
		int iSize2 = objAL2.size();
		
		if (iSize1 == iSize2) bReturn = true;
	}//end main:
	return bReturn;
}

}//END class
