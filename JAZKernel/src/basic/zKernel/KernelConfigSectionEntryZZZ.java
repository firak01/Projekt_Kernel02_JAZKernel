package basic.zKernel;

public class KernelConfigSectionEntryZZZ implements IKernelConfigSectionEntryZZZ {
	private String sSection = null;
	private String sProperty = null;
	private String sRaw = null;
	private String sValue = null;
	private boolean bAnyValue = false;
	
	@Override
	public String getSection() {
		return this.sSection;
	}

	@Override
	public void setSection(String sSection) {
		this.sSection=sSection;
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
		this.sValue = sValue;
	}

	@Override
	public boolean hasAnyValue() {
		return this.bAnyValue;
	}

	@Override
	public void hasAnyValue(boolean bAnyValue) {
		this.bAnyValue=bAnyValue;
	}

}
