package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.AbstractIniTagSimpleZZZ;
import basic.zKernel.flag.event.ISenderObjectFlagZsetZZZ;

//MUSS FLAGS FUER DIE FORMELVERARBEITUNG SETZEN KOENNEN
public abstract class AbstractObjectWithFormulaZZZ<T> extends AbstractIniTagSimpleZZZ<T> implements IObjectWithFormulaZZZ{
	private static final long serialVersionUID = 4049221887081114236L;
	
	//+++ fuer die Flags
	protected volatile HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	protected volatile HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>(); //Neu 20210402
	protected volatile HashMap<String, Boolean>hmFlagLocal = new HashMap<String, Boolean>(); //Neu 20220720
		
	protected volatile ISenderObjectFlagZsetZZZ objEventFlagZBroker = null;//Das Broker Objekt, an dem sich andere Objekte regristrieren können, um ueber Aenderung eines Flags per Event informiert zu werden.
	
	public AbstractObjectWithFormulaZZZ() throws ExceptionZZZ{
		this("init");
	}
	
	public AbstractObjectWithFormulaZZZ(String sFlag) throws ExceptionZZZ{
		super();
		String[] saFlag = new String[0];
		saFlag[0] = sFlag;	
		AbstractObjectWithFormulaNew_(saFlag);
	}
	
	public AbstractObjectWithFormulaZZZ(String[] saFlag) throws ExceptionZZZ{
		super();
		AbstractObjectWithFormulaNew_(saFlag);
	}
	
	//Merke: Da in diesem Vererbungsstring erstmalig Flags vorgesehen sind, diese hier komplett setzen wie bei AbstractObjectWithFlagZZZ
	private boolean AbstractObjectWithFormulaNew_(String[] saFlag) throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{	
			 if(saFlag!=null){
					if(saFlag.length>=1){
						String sLog;
						for(int icount =0; icount <= saFlag.length-1; icount++){
							if(!StringZZZ.isEmpty(saFlag[icount])){						
								boolean bFound = this.setFlag(saFlag[icount], true);
								if(!bFound) {
									sLog = ReflectCodeZZZ.getPositionCurrent()+"Flag not available: '" + saFlag[icount] +"'";
									this.logProtocolString(sLog);							
								}
							}
						}
					}
				}
			 
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
				
				
		 	}//end main:
			return bReturn;
		 }//end function AbstractObjectWithFormulaNew_
	
	
	
	
	//####################################################
		
	
	//### aus IValueSolvedUserZZZ

	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() {
		return this.getEntry().getValueVector();
	}

	@Override
	public String getValue() {
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) {
		this.getEntry().setValue(sValue);
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() {
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() {
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) {
		this.getEntry().setValue(sRaw);
	}
	//##############################
	
}
