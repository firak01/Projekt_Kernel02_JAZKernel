package basic.zBasic;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;

//MUSS FLAGS FUER DIE Expression-VERARBEITUNG SETZEN KOENNEN
//Merke: Arrays erst in ini-Tag behandeln, da es dafuer Separatorn in der Zeile geben muss
public abstract class AbstractObjectWithExpressionZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IObjectWithExpressionZZZ{
	private static final long serialVersionUID = 4049221887081114236L;
		
	//+++ fuer IValueBufferedUserZZZ
	protected VectorExtendedDifferenceZZZ<String> vecValue = new VectorExtendedDifferenceZZZ<String>();
	protected boolean bAnyValue = false;
	protected boolean bNullValue = false;
	
	//IValueSolvedUserZZZ
	protected VectorExtendedDifferenceZZZ<String> vecRaw = new VectorExtendedDifferenceZZZ<String>();
	
	public AbstractObjectWithExpressionZZZ() throws ExceptionZZZ{
		this("init");
		AbstractObjectWithExpressionNew_();
	}
	
	public AbstractObjectWithExpressionZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		AbstractObjectWithExpressionNew_();
	}
	
	public AbstractObjectWithExpressionZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		AbstractObjectWithExpressionNew_();
	}
	
	//Merke: Da in diesem Vererbungsstring erstmalig Flags vorgesehen sind, diese hier komplett setzen wie bei AbstractObjectWithFlagZZZ
	private boolean AbstractObjectWithExpressionNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{	
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
				
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function AbstractObjectWithExpressionNew_
	//++++++++++++++++++++++++
		
	//### Aus IValueBufferedUserZZZ
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueVector() throws ExceptionZZZ{
		return this.vecValue;
	}
	
	//Merke: Muss wg. dem Vector als Buffer ueberschrieben werden
	@Override
	public String getValue() throws ExceptionZZZ {
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			return null; //wenn die Section nicht existiert, dann auch kein Wert.			
		}else {
			return this.getValueVector().getEntryHigh();
		}
	}

	//Merke: Muss wg. dem Vector als Buffer ueberschrieben werden
	@Override
	public void setValue(String sValue) throws ExceptionZZZ {
		
		this.hasAnyValue(true);
		this.getValueVector().add(sValue);	
		if(sValue!=null){		
			this.hasNullValue(false);
		}else{
			this.hasNullValue(true);
		}		
	}

	@Override
	public boolean hasAnyValue() throws ExceptionZZZ {
		return this.bAnyValue;
	}	
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasAnyValue(boolean bAnyValue) throws ExceptionZZZ {
		this.bAnyValue=bAnyValue;
	}
	
	@Override
	public boolean hasNullValue() throws ExceptionZZZ {
		return this.bNullValue;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar, 
	//daher protected. Was nicht im Interface definierbar ist.
	@Override
	public void hasNullValue(boolean bNullValue) {
		this.bNullValue=bNullValue;
	}
	
	//####################################################
	//### Aus IValueComputedBufferedUserZZZ
	@Override 
	public VectorExtendedDifferenceZZZ<String> getRawVector() throws ExceptionZZZ{
		return this.vecRaw;
	}
	
	@Override
	public String getRaw() throws ExceptionZZZ {
		return this.getRawVector().getEntryLow();//anders als bei den anderen Strings und Vectoren hier den .Lows() zurueckgeben
	}

	@Override
	public void setRaw(String sRaw) throws ExceptionZZZ {
		this.getRawVector().add(sRaw);
	}
}
