package basic.zBasic.util.abstractList;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;

public interface IVectorZZZ<T> extends IOutputDebugNormedZZZ, IConstantZZZ, ICollectionConstantZZZ {
	
	int getIndexOfString(String string, boolean ignoreCase, int fromIndex);
	public T getEntryLast();
	public int getIndexUsedLast();

	public T getEntryLow();
	public T getEntryHigh();
	
	public int getIndexHigh();
	public int getIndexNext();
	
	public int indexOfString(String string);
	public int indexOfString(String string, int fromIndex);
	public int indexOfString(String string, boolean ignoreCase);
	public int indexOfString(String string, boolean ignoreCase, int fromIndex);
	
	public boolean addSorted(Integer intNew, int iSortDirectionAscDesc) throws ExceptionZZZ;
	
	public Vector rightOfString(String string, boolean ignoreCase, int fromIndex) throws ExceptionZZZ;
	public boolean containsString(String string);
	public boolean containsString(String string, boolean ignoreCase);	
	
	public boolean hasAnyElement();
	public boolean isLastElementGreaterThan(Integer intToCompare) throws ExceptionZZZ;
	public int compareToLastElement(Integer intToCompare) throws ExceptionZZZ;
	
	public T getElementByIndex(int iIndex); //zur Vereinheitlichung, weil das in HashMapExtendedZZZ auch verwendet wird.
	
	//eigentlich aus Vector
	public boolean isEmpty();
	public Object get(int iIndex);
	public int size();

}
