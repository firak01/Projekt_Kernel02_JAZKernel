package basic.zBasic.util.datatype.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;

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
public class TreeNodeZZZ<T> implements ITreeNodeZZZ<T> {
	protected volatile String sDebugKeyDelimiterUsed = null; //zum Formatieren einer Debug Ausgabe
	protected volatile String sDebugEntryDelimiterUsed = null;
	
	
	public T data=null;
	public ITreeNodeZZZ<T> parent;
	public List<ITreeNodeZZZ<T>> children;
	
	//FGL: Erweiterung, Tags auf gleicher Ebene, fuer alle Knoten
	public List<ITreeNodeZZZ<T>> sibling;	
	
	//Das ist der Suchindex, nur im Root gefuellt
	protected List<ITreeNodeZZZ<T>> elementsIndex;
	
	public TreeNodeZZZ() {
		//Damit man auch einen leeren Knoten erzeugen kann.
		//Ggfs. als Root. 
		//Damit soll das analog zu einem Vector oder einer HashMap sein, naemlich auch ohne Werte als Objekt vorhanden.
	}

	public TreeNodeZZZ(T data) {
		main:{
			if(data==null)break main;
			
			this.setData(data);
			
			if(this.getSiblings()==null) {
				this.setSiblings(new LinkedList<ITreeNodeZZZ<T>>());
			}
			this.getSiblings().add(this);
			
			//!!! Merke: Nur im Root den Index aller Elemente halten	
		}//end main:
	}
	
	//#####################################
	
	@Override
	public List<ITreeNodeZZZ<T>> getChildren(){
		return this.children;
	}
	
	@Override
	public void setChildren(List<ITreeNodeZZZ<T>> listChildren) {
		this.children = listChildren;
	}
	
	@Override
	public List<ITreeNodeZZZ<T>> getSiblings(){
		return this.sibling;
	}
	
	@Override
	public void setSiblings(List<ITreeNodeZZZ<T>> listSibling) {
		this.sibling = listSibling;
	}
	
	@Override
	public List<ITreeNodeZZZ<T>> getRootElementsIndex(){
		return this.elementsIndex;
	}
	
	@Override
	public void setRootElementsIndex(List<ITreeNodeZZZ<T>> listElement) {
		this.elementsIndex = listElement;
	}
	
	@Override
	public ITreeNodeZZZ<T> getParent() {
		return this.parent;
	}

	@Override
	public void setParent(ITreeNodeZZZ<T> parentNode) {
		this.parent = parentNode;
	}

	@Override
	public T getData() {
		return this.data;
	}

	@Override
	public void setData(T obj) {
		this.data = obj;
	}
	
	
	//###########################################################################
	
	/** Ergaenzung zu der Loesung aus dem Web
	 * @param sibling
	 * @return
	 * @author Fritz Lindhauer, 01.06.2024, 15:36:00
	 */
	public ITreeNodeZZZ<T> addSibling(T sibling) {
		if(sibling==null) return null;
		
		ITreeNodeZZZ<T> siblingNode = new TreeNodeZZZ<T>(sibling);		
		ITreeNodeZZZ<T> parentNode = this.getParent();
		siblingNode.setParent(parentNode);
		if(parentNode!=null) {
			
			parentNode.getChildren().add(siblingNode);
			
			//Die Geschwister dem neuen Knoten hinzufuegen
			siblingNode.setSiblings(parentNode.getChildren());
			
		}else {
			//ROOT!
			List<ITreeNodeZZZ<T>> listSibling = this.getSiblings();
			if(listSibling==null) {
				this.setSiblings(new LinkedList<ITreeNodeZZZ<T>>());
			}
			this.getSiblings().add(siblingNode);
			
			//Die Geschwister dem neuen Knoten hinzufuegen
			siblingNode.setSiblings(listSibling);
		}
		
		//Den neuen Konten dem Suchindex hinzufuegen
		this.registerSiblingForSearch(siblingNode);
		
		
		//In andern siblings nun den neuen Konten hinzufuegen
		List<ITreeNodeZZZ<T>> listSibling = this.getSiblings();
		for(ITreeNodeZZZ<T> siblingNodeTemp : listSibling) {
			if(siblingNodeTemp.getSiblings()==null) {
				siblingNodeTemp.setSiblings(new LinkedList<ITreeNodeZZZ<T>>());
				siblingNodeTemp.getSiblings().add(siblingNode);
			}else {
				if(!(siblingNodeTemp.getSiblings().contains(siblingNode) || siblingNodeTemp.equals(siblingNode) || siblingNodeTemp.equals(this))) {
					siblingNodeTemp.getSiblings().add(siblingNode);
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
	@Override
	public ITreeNodeZZZ<T> addSiblingAsFirst(T sibling) {
		return this.addSibling(0, sibling);
	}
	
	/** Ergaenzung zu der Loesung aus dem Web
	 * @param sibling
	 * @return
	 * @author Fritz Lindhauer, 01.06.2024, 15:36:00
	 */
	@Override
	public ITreeNodeZZZ<T> addSibling(int iIndex, T sibling) {
		if(sibling==null) return null;
		
		ITreeNodeZZZ<T> siblingNode = new TreeNodeZZZ<T>(sibling);		
		if(this.parent!=null) {			
			//NICHT MEHR ROOT!			
			siblingNode.setParent(this.getParent());
			this.getParent().getChildren().add(iIndex, siblingNode); //Gefahr, wird jetzt Text for einem 2ten Knoten auf der Ebene ganz nach vorne gesetzt?
			this.sibling = this.getParent().getChildren();
		}else {
			//Merke: Der Root hat keine siblings, nur childs.
		}
		
		 //Die Geschwister dem neuen Knoten hinzufuegen
		siblingNode.setSiblings(this.getSiblings());
		
		//Den neuen Knoten dem Suchindex hinzufuegen
		//ABER: entsprechend der Indexposition weiter nach vorne
		//      Da aber iIndex auf die Sibblings bezogen ist:
		//      Versuch den neuen Index in der Suchliste auszurechnen
		ITreeNodeZZZ<T>root = this.searchRoot();
		int iSearchIndexSize = root.getRootElementsIndex().size();
		int iSearchIndexOffset = (this.getSiblings().size() -1) - iIndex;
		int iIndexForSearch = iSearchIndexSize - iSearchIndexOffset; 
		this.registerSiblingForSearch(iIndexForSearch, siblingNode);
		
		
		//Den neuen sibling nun alle anderen siblings hinzufuegen
		List<ITreeNodeZZZ<T>> listSibling = this.getSiblings();
		for(ITreeNodeZZZ<T> siblingNodeTemp : listSibling) {
			if(siblingNodeTemp.getSiblings()==null) {
				siblingNodeTemp.setSiblings(new LinkedList<ITreeNodeZZZ<T>>());
				siblingNodeTemp.getSiblings().add(siblingNode);
			}else {
				if(!(siblingNodeTemp.getSiblings().contains(siblingNode) || siblingNodeTemp.equals(siblingNode) || siblingNodeTemp.equals(this))) {
					siblingNodeTemp.getSiblings().add(iIndex, siblingNode);
				}
			}
		}
		
		return siblingNode;		
	}
	
	@Override
	public ITreeNodeZZZ<T> addChild(T child) {
		if(child==null) return null;
		
		ITreeNodeZZZ<T> childNode = new TreeNodeZZZ<T>(child);
		childNode.setParent(this);
		if(this.getChildren()==null) {
			this.setChildren(new LinkedList<ITreeNodeZZZ<T>>());				
		}
		this.getChildren().add(childNode);
		this.registerChildForSearch(childNode);
					
		//In dem neuen Child nun alle siblings hinzufuegen
		//und in allen Siblings dieses Child als weiteren Sibling hinzufuegen.
		List<ITreeNodeZZZ<T>> listChildren = this.getChildren();
		for(ITreeNodeZZZ<T> siblingNode : listChildren) {
			if(childNode.getSiblings()==null) {
				childNode.setSiblings(new LinkedList<ITreeNodeZZZ<T>>());
				childNode.getSiblings().add(siblingNode);
			}else {
				if(!(childNode.getSiblings().contains(siblingNode)||childNode.equals(siblingNode)||childNode.equals(this))) {
					childNode.getSiblings().add(siblingNode);
				}
			}
		}
		return childNode;
	}
	//#########################################################################################
	//#########################################################################################
	public ITreeNodeZZZ<T> getElementByIndex(int iIndex){
		ITreeNodeZZZ<T> objReturn = null;
		main:{
			if(this.getRootElementsIndex()==null) break main;
			if(iIndex<=-1)break main;
			if(iIndex>this.getRootElementsIndex().size()-1) break main;
			
			objReturn = this.getRootElementsIndex().get(iIndex);
			
		}//end main:
		return objReturn;
	}
	
	public int getLevel() {
		if (this.isRoot())
			return 0;
		else
			return this.getParent().getLevel() + 1;
	}
	
	public boolean isRoot() {
		//Merke: Im Konstruktor ist das ggfs. noch nicht eindeutig, also noch data hinzunehmen als Kriterium
		boolean bReturn = false;
		main:{
			bReturn = this.getParent() == null;
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
	@Override
	public boolean isEmptyNode() {
		return data==null;
	}
	
	@Override
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
	
	@Override
	public int size() {
		int iReturn = 0;
		main:{
			List<ITreeNodeZZZ<T>> listElement = this.getRootElementsIndex();
			if(listElement==null) break main;
			
			iReturn = listElement.size();		
		}//end main:
		return iReturn;
	}
	
	@Override
	public ITreeNodeZZZ<T>searchRoot(){
		if(this.isRoot()) {
			return this;
		}else {
			if(this.getParent()==null) return null;
			return this.getParent().searchRoot();
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
	private void registerSiblingForSearch(ITreeNodeZZZ<T> node) {
		ITreeNodeZZZ<T>rootNode = this.searchRoot();
		if(rootNode.getRootElementsIndex()==null) {
			rootNode.setRootElementsIndex(new LinkedList<ITreeNodeZZZ<T>>());				
		}
		rootNode.getRootElementsIndex().add(node);
	}
	
	/*Das Index hinzufuegen wird z.B. bei einem <text> Tag gemacht, der vor dem Node steht
	 */
	private void registerSiblingForSearch(int iIndex, ITreeNodeZZZ<T> node) {		
		main:{
			ITreeNodeZZZ<T>rootNode = this.searchRoot();
			if(rootNode.getRootElementsIndex()==null) {
				rootNode.setRootElementsIndex(new LinkedList<ITreeNodeZZZ<T>>());
				rootNode.getRootElementsIndex().add(node);
			}else {
				if(iIndex<=-1)break main;
				if(iIndex>rootNode.getRootElementsIndex().size()-1) {
					rootNode.getRootElementsIndex().add(node);
				}else {
					rootNode.getRootElementsIndex().add(iIndex, node);
				}
				
			}		
		}//end main:
	}
	
	private void registerChildForSearch(ITreeNodeZZZ<T> node) {
		ITreeNodeZZZ<T>rootNode = this.searchRoot();
		if(rootNode.getRootElementsIndex()==null) {
			rootNode.setRootElementsIndex(new LinkedList<ITreeNodeZZZ<T>>());				
		}
		rootNode.getRootElementsIndex().add(node);
	}

	@Override
	public ITreeNodeZZZ<T> findTreeNode(Comparable<T> cmp) {
		if(this.getRootElementsIndex()==null) return null;
				
		List<ITreeNodeZZZ<T>> listElement = this.getRootElementsIndex();
		for (ITreeNodeZZZ<T> element : listElement) {
			T elData = element.getData();
			if (cmp.compareTo(elData) == 0)
				return element;
		}

		return null;
	}

	@Override
	public String toString() {
		return this.getData() != null ? this.getData().toString() : "[data null]";
	}

	@Override
	public Iterator<ITreeNodeZZZ<T>> iterator() {
		Iterator<ITreeNodeZZZ<T>> iter = new TreeNodeIteratorZZZ<T>(this);
		return iter;
	}
	
	//#######################################################
	//### aus IOutputNormedZZ
	@Override
	public String getDebugEntryDelimiter() {
		String sEntryDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sDebugEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}
	
	@Override
	public void setDebugEntryDelimiter(String sEntryDelimiter) {
		this.sDebugEntryDelimiterUsed = sEntryDelimiter;
	}
	
	public String getDebugKeyDelimiter() {
		String sKeyDelimiter;
		if(this.sDebugKeyDelimiterUsed==null){
			sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
		}else{
			sKeyDelimiter = this.sDebugKeyDelimiterUsed;
		}
		return sKeyDelimiter;
	}
	
	@Override
	public void setDebugKeyDelimiter(String sEntryDelimiter) {
		this.sDebugKeyDelimiterUsed = sEntryDelimiter;
	}
	
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 * @throws ExceptionZZZ 
	 */
	public String computeDebugString() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			
			String sEntryDelimiter = this.getDebugEntryDelimiter();
			String sKeyDelimiter = this.getDebugKeyDelimiter();
			sReturn = TreeNodeZZZ.computeDebugString((TreeNodeZZZ<ITagSimpleZZZ>) this, sKeyDelimiter, sEntryDelimiter);
			
		}//end main
		return sReturn;
	}
	

	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 *  Merke: Beim Arbeiten mit der Scanner-Klasse um z.B. Eingaben entgegenzunehmen, sollte man 
	 *         dies verwenden, sonst wird nach jedem Wort ein Zeilenumbruch (Sprich eine neue Eingabe gemacht).
	 * @param sKeyDelimiterIn
	 * @param sEntryDelimiterIn
	 * @return
	 * @author Fritz Lindhauer, 21.10.2022, 09:56:44
	 * @throws ExceptionZZZ 
	 */
	@Override
	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ {
		String sKeyDelimiter = this.getDebugKeyDelimiter();
		return this.computeDebugString(sKeyDelimiter, sEntryDelimiter);
	}
	
	public String computeDebugString(String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			
			sReturn = TreeNodeZZZ.computeDebugString((TreeNodeZZZ<ITagSimpleZZZ>) this, sKeyDelimiterIn, sEntryDelimiterIn);
			
		}//end main
		return sReturn;
	}
	
	public static String computeDebugString(TreeNodeZZZ objTree) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{		
			sReturn = TreeNodeZZZ.computeDebugString(objTree, null, null);
		}//end main
		return sReturn;
	}
	
	public static String computeDebugString(TreeNodeZZZ<ITagSimpleZZZ> objTree, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(objTree==null) break main;
			
			sReturn = new String("");
			if(objTree.size()==0 && objTree.getData()==null) break main;
			
			String sEntryDelimiter;			
			if(sEntryDelimiterIn==null){
				sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			}else {
				sEntryDelimiter = sEntryDelimiterIn;
			}
						
			String sKeyDelimiter;
			if(sKeyDelimiterIn==null){
				sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
			}else{
				sKeyDelimiter = sKeyDelimiterIn;
			}
			
			ITagSimpleZZZ objData = objTree.getData();
			if(objData!=null) {//Null ist z.B. beim Root der Fall
				String sData = objData.getTagStarting() + objData.getValue() + objData.getTagClosing();
				String sOffset = StringZZZ.repeat(sKeyDelimiter, (objTree.getLevel()));
				sReturn = sOffset + sData + sEntryDelimiter;
			}else {
				if(objTree.isRoot()) {
					sReturn = "[root}" + sEntryDelimiter;;
				}else if(objTree.isEmpty()) {
					sReturn = "[empty]" + sEntryDelimiter;;
				}
			}
						
			List<ITreeNodeZZZ<ITagSimpleZZZ>>listChild = objTree.children;
			if(listChild!=null) {
				Iterator<ITreeNodeZZZ<ITagSimpleZZZ>> itChild = listChild.iterator();
				while(itChild.hasNext()) {				
					ITreeNodeZZZ<ITagSimpleZZZ> objChild = itChild.next();											
					sReturn = sReturn + objChild.computeDebugString();				
				}
			}
			
			
									
		}//end main
		return sReturn;
	}
}
