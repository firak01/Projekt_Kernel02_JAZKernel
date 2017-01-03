package basic.zBasic.util.datatype.integer;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;


public class IntegerArrayZZZ implements IConstantZZZ {
	private Integer[] iaIntern;
	private boolean bIsInteger = false;

	//Konstruktoren
	public IntegerArrayZZZ(Integer args[]) throws ExceptionZZZ{
		if(args==null){
			ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		this.iaIntern = args;
		this.bIsInteger = true;
	}
	
	
public static Integer[] plus(Integer[] iaInteger, int iValue) throws ExceptionZZZ{
	Integer[] objReturn = null;
	main:{
	if(iValue==0){
		objReturn = new Integer[iaInteger.length];
		System.arraycopy(iaInteger,0,objReturn, 0, iaInteger.length);
		break main;
	}
	
	int iSize = iaInteger.length;
	objReturn = new Integer[iSize];
	
		for(int icount = 0; icount<=iSize-1;icount++){
			int itemp = iaInteger[icount].intValue() + iValue;
			objReturn[icount]=new Integer(itemp);
		}
		break main;
	
	}//End main:
	return objReturn;
}
}
