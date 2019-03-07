package basic.zBasic.util.datatype.counter;

import basic.zBasic.IConstantZZZ;

public abstract class AbstractCounterByCharacterAsciiZZZ implements IConstantZZZ, ICounterStringZZZ{
	private String sSuffix="";
	private String sPrefix="";
	private String sCurrent="";
	
	//### Aus Interfasce
	public String getSuffix(){
		return this.sSuffix;
	}
	public void setSuffix(String sSuffix){
		this.sSuffix = sSuffix;
	}
	
	public String getPrefix(){
		return this.sPrefix;
	}
	public void setPrefix (String sPrefix){
		this.sPrefix=sPrefix;
	}
	
	public String current() {
		return this.sCurrent;
	}
	protected void setCurrent(String sCurrent){
		this.sCurrent=sCurrent;
	}
}
