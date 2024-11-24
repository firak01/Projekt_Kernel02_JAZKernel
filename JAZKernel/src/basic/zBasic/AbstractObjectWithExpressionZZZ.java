package basic.zBasic;

import java.util.Vector;

import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;

//MUSS FLAGS FUER DIE Expression-VERARBEITUNG SETZEN KOENNEN
//Merke: Arrays erst in ini-Tag behandeln, da es dafuer Separatorn in der Zeile geben muss
public abstract class AbstractObjectWithExpressionZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IObjectWithExpressionZZZ{
	private static final long serialVersionUID = 4049221887081114236L;
		
	//IValueSolvedUserZZZ
	protected VectorDifferenceZZZ<String> vecRaw = null;
	
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
	
	//### Aus IObjectWithExpression
	@Override
	public boolean isExpressionEnabledGeneral() throws ExceptionZZZ{
		return this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 	
	}
}
