package basic.zBasic.util.datatype.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 01.06.2024, 08:36:49
 * 
 * Ursprungsidee:
 * https://stackoverflow.com/questions/3522454/how-to-implement-a-tree-data-structure-in-java
 * 
 * Code aus:
 * https://github.com/gt4dev/yet-another-tree-structure
 */
public class TreeNodeZZZ<T> implements Iterable<TreeNodeZZZ<T>> {

	public T data;
	public TreeNodeZZZ<T> parent;
	public List<TreeNodeZZZ<T>> children;
	
	//FGL: Erweiterung, Tags auf gleicher Ebene, fuer ROOT
	public List<TreeNodeZZZ<T>> sibling;

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	private List<TreeNodeZZZ<T>> elementsIndex;
	
	public TreeNodeZZZ(T data) {
		this.data = data;
		if(this.children ==null) {
			this.children = new LinkedList<TreeNodeZZZ<T>>();
		}
		
		//!!! nur im Root den Index aller Elemente haben
		if(this.isRoot()) {		//Merke: Da Parent erst spaeter definiert wird, ist zu diesem Zeitpunkt jedes Element ROOT	
			if(this.sibling==null) {
				this.sibling = new LinkedList<TreeNodeZZZ<T>>();
			}
			this.sibling.add(this);
		
			if(this.elementsIndex==null) {
				this.elementsIndex = new LinkedList<TreeNodeZZZ<T>>();				
			}
			this.elementsIndex.add(this);
		} else {
			TreeNodeZZZ<T> objRootNode = this.searchRoot();
			objRootNode.elementsIndex.add(this);
		}		
	}

	/** Ergaenzung zu der Loesung aus dem Web
	 * @param sibling
	 * @return
	 * @author Fritz Lindhauer, 01.06.2024, 15:36:00
	 */
	public TreeNodeZZZ<T> addSibling(T sibling) {
		TreeNodeZZZ<T> siblingNode = new TreeNodeZZZ<T>(sibling);
		siblingNode.parent = this.parent;
		if(this.parent!=null) {
			this.parent.children.add(siblingNode);
		}else {
			//ROOT!
			if(this.sibling==null) {
				this.sibling = new LinkedList<TreeNodeZZZ<T>>();
			}
			this.sibling.add(siblingNode);
		}
				
		this.registerSiblingForSearch(siblingNode);
		return siblingNode;		
	}
	
	/** Ergaenzung zu der Loesung aus dem Web
	 * @param sibling
	 * @return
	 * @author Fritz Lindhauer, 01.06.2024, 15:36:00
	 */
	public TreeNodeZZZ<T> addSiblingAsFirst(T sibling) {
		return this.addSibling(0, sibling);
	}
	
	/** Ergaenzung zu der Loesung aus dem Web
	 * @param sibling
	 * @return
	 * @author Fritz Lindhauer, 01.06.2024, 15:36:00
	 */
	public TreeNodeZZZ<T> addSibling(int iIndex, T sibling) {
		TreeNodeZZZ<T> siblingNode = new TreeNodeZZZ<T>(sibling);
		siblingNode.parent = this.parent;
		if(this.parent!=null) {
			//NICHT MEHR ROOT!
			this.parent.children.add(iIndex, siblingNode); //Gefahr, wird jetzt Text for einem 2ten Knoten auf der Ebene ganz nach vorne gesetzt?
		}else {
			//ROOT!
			if(this.sibling==null) {
				this.sibling = new LinkedList<TreeNodeZZZ<T>>();
			} 
		}
		this.sibling.add(iIndex, siblingNode);
				
		this.registerSiblingForSearch(siblingNode);
		return siblingNode;		
	}
	
	
	
	
	
	public TreeNodeZZZ<T> addChild(T child) {
		TreeNodeZZZ<T> childNode = new TreeNodeZZZ<T>(child);
		childNode.parent = this;
		this.children.add(childNode);
		this.registerChildForSearch(childNode);
		return childNode;
		
		TODOGOON20240602; //In dem neuen Child nun alle siblings hinzufügen
		                  //und in allen Siblings dieses Child als weiteren Sibling hinzufuegen.
	}

	public int getLevel() {
		if (this.isRoot())
			return 0;
		else
			return parent.getLevel() + 1;
	}
	
	public TreeNodeZZZ<T>searchRoot(){
		if(this.isRoot()) {
			return this;
		}else {
			return parent.searchRoot();
		}
	}

	
	/**Erweiterung fuer Knoten auf gleicher Ebene
	 * Es wird aber alles in eine ElementIndex-Speicher geworfen
	 * Falls die Suchstrategie eine andere ermöglichen soll
	 * (z.B. erst die Ebene durchsuchen, dann die Kindebene, etc.)
	 * Dann muss hier ein anderer Index gewaehlt werden.
	 * @param node
	 * @author Fritz Lindhauer, 01.06.2024, 15:43:58
	 */
	private void registerSiblingForSearch(TreeNodeZZZ<T> node) {
		TreeNodeZZZ rootNode = this.searchRoot();
		rootNode.elementsIndex.add(node);
	}
	
	private void registerChildForSearch(TreeNodeZZZ<T> node) {
		TreeNodeZZZ rootNode = this.searchRoot();
		rootNode.elementsIndex.add(node);
		
		elementsIndex.add(node);
		if (parent != null)
			parent.registerChildForSearch(node);
	}

	public TreeNodeZZZ<T> findTreeNode(Comparable<T> cmp) {
		for (TreeNodeZZZ<T> element : this.elementsIndex) {
			T elData = element.data;
			if (cmp.compareTo(elData) == 0)
				return element;
		}

		return null;
	}

	@Override
	public String toString() {
		return data != null ? data.toString() : "[data null]";
	}

	@Override
	public Iterator<TreeNodeZZZ<T>> iterator() {
		TreeNodeIteratorZZZ<T> iter = new TreeNodeIteratorZZZ<T>(this);
		return iter;
	}

}
