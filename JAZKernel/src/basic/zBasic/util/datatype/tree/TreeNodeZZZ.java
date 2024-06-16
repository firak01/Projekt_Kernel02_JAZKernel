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

	public T data=null;
	public TreeNodeZZZ<T> parent;
	public List<TreeNodeZZZ<T>> children;
	
	//FGL: Erweiterung, Tags auf gleicher Ebene, fuer alle Knoten
	public List<TreeNodeZZZ<T>> sibling;	
	
	//Das ist der Suchindex, nur im Root gefuellt
	protected List<TreeNodeZZZ<T>> elementsIndex;
	
	public TreeNodeZZZ() {
		//Damit man auch einen leeren Knoten erzeugen kann.
		//Ggfs. als Root. 
		//Damit soll das analog zu einem Vector oder einer HashMap sein, naemlich auch ohne Werte als Objekt vorhanden.
	}

	public TreeNodeZZZ(T data) {
		main:{
			if(data==null)break main;
			
			this.data = data;
			
			if(this.sibling==null) {
				this.sibling = new LinkedList<TreeNodeZZZ<T>>();
			}
			this.sibling.add(this);
			
			//!!! Merke: Nur im Root den Index aller Elemente halten	
		}//end main:
	}
	
	/** Ergaenzung zu der Loesung aus dem Web
	 * @param sibling
	 * @return
	 * @author Fritz Lindhauer, 01.06.2024, 15:36:00
	 */
	public TreeNodeZZZ<T> addSibling(T sibling) {
		if(sibling==null) return null;
		
		TreeNodeZZZ<T> siblingNode = new TreeNodeZZZ<T>(sibling);
		siblingNode.parent = this.parent;
		if(this.parent!=null) {
			this.parent.children.add(siblingNode);
			this.sibling = this.parent.children;
		}else {
			//ROOT!
			if(this.sibling==null) {
				this.sibling = new LinkedList<TreeNodeZZZ<T>>();
			}
			this.sibling.add(siblingNode);
		}
		
		//Die Geschwister dem neuen Knoten hinzufuegen
		siblingNode.sibling=this.sibling;
		
		//Den neuen Konten dem Suchindex hinzufuegen
		this.registerSiblingForSearch(siblingNode);
		
		//In andern siblings nun den neuen Konten hinzufuegen
		for(TreeNodeZZZ<T> siblingNodeTemp : this.sibling) {
			if(siblingNodeTemp.sibling==null) {
				siblingNodeTemp.sibling = new LinkedList<TreeNodeZZZ<T>>();
				siblingNodeTemp.sibling.add(siblingNode);
			}else {
				if(!(siblingNodeTemp.sibling.contains(siblingNode) || siblingNodeTemp.equals(siblingNode) || siblingNodeTemp.equals(this))) {
					siblingNodeTemp.sibling.add(siblingNode);
				}
			}
		}
		
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
		if(sibling==null) return null;
		
		TreeNodeZZZ<T> siblingNode = new TreeNodeZZZ<T>(sibling);		
		if(this.parent!=null) {			
			//NICHT MEHR ROOT!			
			siblingNode.parent = this.parent;
			this.parent.children.add(iIndex, siblingNode); //Gefahr, wird jetzt Text for einem 2ten Knoten auf der Ebene ganz nach vorne gesetzt?
			this.sibling = this.parent.children;
		}else {
			//Merke: Der Root hat keine siblings, nur childs.
		}
		
		 //Die Geschwister dem neuen Knoten hinzufuegen
		siblingNode.sibling=this.sibling;
		
		//Den neuen Knoten dem Suchindex hinzufuegen
		//ABER: entsprechend der Indexposition weiter nach vorne
		//      Da aber iIndex auf die Sibblings bezogen ist:
		//      Versuch den neuen Index in der Suchliste auszurechnen
		TreeNodeZZZ<T>root = this.searchRoot();
		int iSearchIndexSize = root.elementsIndex.size();
		int iSearchIndexOffset = (this.sibling.size() -1) - iIndex;
		int iIndexForSearch = iSearchIndexSize - iSearchIndexOffset; 
		this.registerSiblingForSearch(iIndexForSearch, siblingNode);
		
		
		//Den neuen sibling nun alle anderen siblings hinzufuegen
		for(TreeNodeZZZ<T> siblingNodeTemp : this.sibling) {
			if(siblingNodeTemp.sibling==null) {
				siblingNodeTemp.sibling = new LinkedList<TreeNodeZZZ<T>>();
				siblingNodeTemp.sibling.add(siblingNode);
			}else {
				if(!(siblingNodeTemp.sibling.contains(siblingNode) || siblingNodeTemp.equals(siblingNode) || siblingNodeTemp.equals(this))) {
					siblingNodeTemp.sibling.add(iIndex, siblingNode);
				}
			}
		}
		
		return siblingNode;		
	}
	
	
	
	
	
	public TreeNodeZZZ<T> addChild(T child) {
		if(child==null) return null;
		
		TreeNodeZZZ<T> childNode = new TreeNodeZZZ<T>(child);
		childNode.parent = this;
		if(this.children==null) {
			this.children = new LinkedList<TreeNodeZZZ<T>>();				
		}
		this.children.add(childNode);
		this.registerChildForSearch(childNode);
					
		//In dem neuen Child nun alle siblings hinzufuegen
		//und in allen Siblings dieses Child als weiteren Sibling hinzufuegen.
		for(TreeNodeZZZ<T> siblingNode : this.children) {
			if(childNode.sibling==null) {
				childNode.sibling = new LinkedList<TreeNodeZZZ<T>>();
				childNode.sibling.add(siblingNode);
			}else {
				if(!(childNode.sibling.contains(siblingNode)||childNode.equals(siblingNode)||childNode.equals(this))) {
					childNode.sibling.add(siblingNode);
				}
			}
		}
		return childNode;
	}

	public TreeNodeZZZ<T> getElementByIndex(int iIndex){
		TreeNodeZZZ<T> objReturn = null;
		main:{
			if(this.elementsIndex==null) break main;
			if(iIndex<=-1)break main;
			if(iIndex>this.elementsIndex.size()-1) break main;
			
			objReturn = this.elementsIndex.get(iIndex);
			
		}//end main:
		return objReturn;
	}
	
	public int getLevel() {
		if (this.isRoot())
			return 0;
		else
			return parent.getLevel() + 1;
	}
	
	public boolean isRoot() {
		//Merke: Im Konstruktor ist das ggfs. noch nicht eindeutig, also noch data hinzunehmen als Kriterium
		boolean bReturn = false;
		main:{
			bReturn = parent == null;
			if(!bReturn) break main;
			
			bReturn = data == null;
			if(!bReturn) break main;
		}//end main;
		return bReturn;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}
	
	//FGL: Erweiterung, Frage nach leerem Knoten.
	public boolean isEmptyNode() {
		return data==null;
	}
	
	public boolean isEmpty() {
		boolean bReturn = true;
		main:{
			bReturn = this.isEmptyNode();
			if(!bReturn) break main;
			
			//aber ggfs. gibt es noch Kindelemente
			if(this.children!=null) {
				bReturn = this.children.isEmpty();
				if(!bReturn) break main;
			}
		}//end main:
		return bReturn;
	}
	
	
	public TreeNodeZZZ<T>searchRoot(){
		if(this.isRoot()) {
			return this;
		}else {
			if(this.parent==null) return null;
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
		TreeNodeZZZ<T>rootNode = this.searchRoot();
		if(rootNode.elementsIndex==null) {
			rootNode.elementsIndex = new LinkedList<TreeNodeZZZ<T>>();				
		}
		rootNode.elementsIndex.add(node);
	}
	
	/*Das Index hinzufuegen wird z.B. bei einem <text> Tag gemacht, der vor dem Node steht
	 */
	private void registerSiblingForSearch(int iIndex, TreeNodeZZZ<T> node) {		
		main:{
			TreeNodeZZZ<T>rootNode = this.searchRoot();
			if(rootNode.elementsIndex==null) {
				rootNode.elementsIndex = new LinkedList<TreeNodeZZZ<T>>();
				rootNode.elementsIndex.add(node);
			}else {
				if(iIndex<=-1)break main;
				if(iIndex>rootNode.elementsIndex.size()-1) {
					rootNode.elementsIndex.add(node);
				}else {
					rootNode.elementsIndex.add(iIndex, node);
				}
				
			}		
		}//end main:
	}
	
	private void registerChildForSearch(TreeNodeZZZ<T> node) {
		TreeNodeZZZ<T>rootNode = this.searchRoot();
		if(rootNode.elementsIndex==null) {
			rootNode.elementsIndex = new LinkedList<TreeNodeZZZ<T>>();				
		}
		rootNode.elementsIndex.add(node);
	}

	public TreeNodeZZZ<T> findTreeNode(Comparable<T> cmp) {
		if(this.elementsIndex==null) return null;
				
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
