/*
 * Created on 29.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zBasic.util.abstractList;

import java.util.ArrayList;


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
		super();
		ArrayListUniqueNew_(obja);	
	}
	public ArrayListUniqueZZZ(ArrayList<T>lista) {
		super();
		ArrayListUniqueNew_(lista);
	}
	
	private boolean ArrayListUniqueNew_(ArrayList<T>lista) {
		boolean bReturn = false;
		main:{
			if(lista==null) break main;
			
			for(T obj : lista) {
				this.add(obj); //Merke: Die Abfrage, ob ein Wert schon vorhanden ist, wird in der ueberschriebenen add-Methode gemacht.
			}
			bReturn = true;
		}//end main:
				
		return bReturn;
	}//end private constructor
	
	private boolean ArrayListUniqueNew_(T[] obja){		
		boolean bReturn = false;
		main:{
			if(obja==null) break main;
			
			for(T obj : obja) {				
				this.add(obj);  //Merke: Die Abfrage, ob ein Wert schon vorhanden ist, wird in der ueberschriebenen add-Methode gemacht.
			}
			bReturn = true;
		}//end main:
				
		return bReturn;
	}//end private constructor
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(T obj){
		boolean bReturn = false;
		main:{
			if(obj==null)break main;			
			if(this.contains(obj)) break main;
			
			bReturn = super.add(obj);			
		}//end main
		return bReturn;
	}
	
	@Override
	public boolean replace(int iIndex, T obj) {
		boolean bReturn = false;
		main:{
			if(obj==null)break main;			
			if(this.contains(obj)) break main;
			
			bReturn = super.replace(iIndex, obj);			
		}//end main
		return bReturn;
	}
	
	
}//end class
