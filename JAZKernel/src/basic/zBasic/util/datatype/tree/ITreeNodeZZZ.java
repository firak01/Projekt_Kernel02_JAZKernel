package basic.zBasic.util.datatype.tree;

import java.util.List;

import basic.zBasic.IOutputDebugNormedWithKeyZZZ;

public interface ITreeNodeZZZ<T> extends Iterable<ITreeNodeZZZ<T>>, IOutputDebugNormedWithKeyZZZ{
	
	public ITreeNodeZZZ<T> getParent();
	public void setParent(ITreeNodeZZZ<T> parentNode);
	
	public List<ITreeNodeZZZ<T>> getChildren();
	public void setChildren(List<ITreeNodeZZZ<T>> listChildren);
	
	public List<ITreeNodeZZZ<T>> getSiblings();
	public void setSiblings(List<ITreeNodeZZZ<T>> listSibling);
	
	public List<ITreeNodeZZZ<T>> getRootElementsIndex();
	public void setRootElementsIndex(List<ITreeNodeZZZ<T>> listSibling);
	
	public ITreeNodeZZZ<T> getElementByIndex(int iIndex);
	
	
	public ITreeNodeZZZ<T> addChild(T childNode);
	public ITreeNodeZZZ<T> addSibling(int iIndex, T siblingNode);
	public ITreeNodeZZZ<T> addSibling(T siblingNode);
	public ITreeNodeZZZ<T> addSiblingAsFirst(T siblingNode);
	
	public ITreeNodeZZZ<T> findTreeNode(Comparable<T> cmp);
	public ITreeNodeZZZ<T>searchRoot();
	
	public boolean isEmptyNode();
	public boolean isRoot();
	
	//zugriff auf die gespeicheten Elemente
	public T getData();
	public void setData(T obj);
	
	//### Analog zu Map implementierte Methoden
	public boolean isEmpty();	
	public int size();
	
	
	//### war schon vorher drin
	public int getLevel();
	
	
	
	
}
