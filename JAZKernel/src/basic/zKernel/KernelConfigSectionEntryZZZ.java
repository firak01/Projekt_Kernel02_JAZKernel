package basic.zKernel;

public class KernelConfigSectionEntryZZZ implements IKernelConfigSectionEntryZZZ {
	private String sSection = null;
	private String sProperty = null;
	private String sRaw = null;
	private String sValue = new String("");
	private boolean bSectionExists = false;
	private boolean bAnyValue = false;
	private boolean bExpression = false;
	private boolean bFormula = false;
	
	@Override
	public String getSection() {
		return this.sSection;
	}

	@Override
	public void setSection(String sSection) {
		if(sSection!=null){
			this.sSection=sSection;
			this.sectionExists(true);
		}else{
			this.sectionExists(false);
		}
	}

	@Override
	public String getProperty() {
		return this.sProperty;
	}

	@Override
	public void setProperty(String sProperty) {
		this.sProperty=sProperty;
	}

	@Override
	public String getRaw() {
		return this.sRaw;
	}

	@Override
	public void setRaw(String sRaw) {
		this.sRaw=sRaw;
	}

	@Override
	public String getValue() {
		return this.sValue;
	}

	@Override
	public void setValue(String sValue) {
		if(sValue!=null){
			this.sValue = sValue;
			this.hasAnyValue(true);
		}else{
			this.hasAnyValue(false);
		}
	}

	@Override
	public boolean hasAnyValue() {
		return this.bAnyValue;
	}

	@Override
	public void hasAnyValue(boolean bAnyValue) {
		this.bAnyValue=bAnyValue;
	}
	
	@Override
	public boolean isExpression(){
		return this.bExpression;
	}
	
	@Override
	public void isExpression(boolean bExpression){
		this.bExpression = bExpression;
	}

	@Override
	public boolean isFormula() {
		return this.bFormula;
	}

	@Override
	public void isFormula(boolean bFormula) {
		this.bFormula = bFormula;
	}
	
	@Override
	public boolean sectionExists() {
		return this.bSectionExists;
	}

	@Override
	public void sectionExists(boolean bSectionExists) {
		this.bSectionExists = bSectionExists;
	}

}
