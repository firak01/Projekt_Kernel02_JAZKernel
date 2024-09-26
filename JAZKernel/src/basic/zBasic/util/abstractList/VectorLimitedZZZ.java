package basic.zBasic.util.abstractList;

import java.util.Collection;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IVectorLimitedZZZ;
import basic.zBasic.ObjectUtilZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class VectorLimitedZZZ<T> extends VectorZZZ<T> implements IVectorLimitedZZZ{
	private static final long serialVersionUID = -4483450165450297325L;
	private int iSizeMax=0;
	
	
	public VectorLimitedZZZ(int iSizeMax) {
		super();
		this.setSizeMax(iSizeMax);
	}
	
	//## aus IVectorLimitedZZZ
	@Override 
	public int sizeMax() {
		return this.iSizeMax;
	}
	private void setSizeMax(int iSize) {
		this.iSizeMax = iSize;
	}
	
	@Override
	public boolean isValidSize(int iSize) throws ExceptionZZZ{
		if(iSize>=0) return true;
		if(iSize<=this.sizeMax()) return true;
		
		return false;
	}
	
	@Override
	public boolean ensureValidSize(int iSize) throws ExceptionZZZ{
		if(iSize<0) return false;
		if(this.sizeNext()<= this.sizeMax()) return true;
		
		ExceptionZZZ ez = new ExceptionZZZ("New Size is invalid: '" + iSize +"'", iERROR_PARAMETER_VALUE, ObjectUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	
	@Override
	public boolean ensureValidIndex(int iIndex) throws ExceptionZZZ{
		boolean bReturn = this.ensureValidSize(iIndex + 1);
		if(bReturn) return true;
		
		ExceptionZZZ ez = new ExceptionZZZ("New Index is invalid: '" + iIndex +"'", iERROR_PARAMETER_VALUE, ObjectUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	
	//### ueberschriebene Vector Originale
	@SuppressWarnings("unchecked")
	@Override
	public void addElement(Object obj) {
		try {
			this.ensureValidSize(this.sizeNext());
			super.addElement(obj);
		}catch(ExceptionZZZ ez) {
			try {
				this.logProtocolString(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Object obj) {
		boolean bReturn = false;
		try {
			this.ensureValidSize(this.sizeNext());
			
			bReturn = super.add(obj);		
			this.iIndexUsedLast = this.size()-1;
			
		}catch(ExceptionZZZ ez) {
			try {
				this.logProtocolString(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(int iIndex, Object obj) {
		try {
			this.ensureValidSize(this.sizeNext());
			
			super.add(iIndex, obj);
			this.iIndexUsedLast = iIndex;
			
		}catch(ExceptionZZZ ez) {
			try {
				this.logProtocolString(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection col) {
		boolean bReturn = false;
		try {
			this.ensureValidSize(this.sizeNext());
			
			bReturn = super.addAll(col);
			this.iIndexUsedLast = this.size()-1;
			
		}catch(ExceptionZZZ ez) {
			try {
				this.logProtocolString(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int iIndex, Collection col) {
		boolean bReturn = false;
		try {
			this.ensureValidSize(this.sizeNext());
			
			bReturn = super.addAll(iIndex, col);
			this.iIndexUsedLast = this.size()-1;
		}catch(ExceptionZZZ ez) {
			try {
				this.logProtocolString(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void insertElementAt(Object obj, int iIndex) {
		
		try {
			this.ensureValidSize(iIndex);
			
			super.insertElementAt(obj, iIndex);
			this.iIndexUsedLast = iIndex;
			
		}catch(ExceptionZZZ ez) {
			try {
				this.logProtocolString(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//### Aus IVectorZZZ (muss ueberschrieben werden, um die Anzahl 3 zu garantieren)
    //.... momentan noch nix hier
}
