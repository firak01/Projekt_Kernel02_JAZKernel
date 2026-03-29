package basic.zBasic.util.abstractList;

import java.util.Vector;

/** Dieser Vector Typ prueft beim Hinzufuegen ab, ob der vorherige Wert genauso ist.
 *  Der neue Wert wird nur hinzugefuegt, wenn er sich vom vorherigen unterscheidet.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 28.06.2024, 09:28:49
 * 
 */
public class VectorDifferenceZZZ<T> extends VectorZZZ<T> implements IVectorDifferenceZZZ<T>{
	private static final long serialVersionUID = -5014357017632523225L;

	public VectorDifferenceZZZ(Vector initVector) {
		super(initVector);
	}

	public VectorDifferenceZZZ() {
	}
	
	
	//### Ueberschriebene Originalmethoden, um noch weitere Funktionen zu bieten
	@Override
	public void addElement(Object obj) {
		if(this.isEmpty()) {
			super.addElement(obj);
		}else {	
			if(this.getEntryHigh()==null) {
				if(obj==null) {
					//Mache halt nix
				}else {
					super.addElement(obj);
				}
			}else if(this.getEntryHigh().equals(obj)) {
				//Mache halt nix
			}else {
				super.addElement(obj);
			}
		}		
	}
	
	@Override
	public boolean add(Object obj) {
		if(this.isEmpty()) {
			return super.add(obj);
		}else {
			if(this.getEntryHigh()==null) {
				if(obj==null) {
					//Mache halt nix
					return false;
				}else {
					return super.add(obj);
				}
			}else if(this.getEntryHigh().equals(obj)) {
				//Mache halt nix
				return false;
			}else {
				return super.add(obj);
			}
		}		
	}
	
	@Override
	public void add(int iIndex, Object obj) {
		if(this.isEmpty()) {
			super.add(iIndex, obj);
		}else {	
			if(this.getElementByIndex(iIndex)==null) {
				if(obj==null) {
					//Mache halt nix
				}else {
					super.add(iIndex,obj);
				}
			}else if(this.getElementByIndex(iIndex).equals(obj)) {
				//Mache halt nix
			}else {
				super.add(iIndex, obj);
			}
		}			
	}
	
	@Override
	public void insertElementAt(Object obj, int iIndex) {
		if(this.isEmpty()) {
			super.insertElementAt(obj, iIndex);
		}else {	
			if(this.getElementByIndex(iIndex)==null) {
				if(obj==null) {
					//Mache halt nix
				}else {
					super.insertElementAt(obj, iIndex);
				}
			}else if(this.getElementByIndex(iIndex).equals(obj)) {
				//Mache halt nix
			}else {
				super.insertElementAt(obj, iIndex);
			}
		}	
	}
	
}
