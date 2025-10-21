/*
 * Created on 29.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zBasic.util.abstractList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ArrayListUniqueZZZ<T> extends AbstractArrayListZZZ<T> implements IArrayListExtendedZZZ{
	private static final long serialVersionUID = 2859619907770188881L;
	
	/**
	 * @param iCapacityInitial
	 */
	public ArrayListUniqueZZZ(int iCapacityInitial) {
		super(iCapacityInitial);			
	}
	public ArrayListUniqueZZZ(){		
		super();
	}
	public ArrayListUniqueZZZ(T[]obja) {	
		ArrayListUniqueNew_(obja);
	
	}
	
	private boolean ArrayListUniqueNew_(T[] obja){		
		boolean bReturn = false;
		main:{
			if(obja==null) break main;
			
			for(T obj : obja) {
				this.add(obj);
			}
			bReturn = true;
		}//end main:
				
		return bReturn;
	}//end private constructor
	
	
	@SuppressWarnings("unchecked")
	public boolean add(Object obj){
		boolean bReturn = false;
		main:{
			if(obj==null)break main;			
			if(this.contains(obj)) break main;
			
			bReturn = super.add((T) obj);			
		}//end main
		return bReturn;
	}
	
	
	}//end class
