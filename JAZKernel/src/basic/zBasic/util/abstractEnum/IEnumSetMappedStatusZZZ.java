package basic.zBasic.util.abstractEnum;

public interface IEnumSetMappedStatusZZZ extends IEnumSetMappedZZZ{
	public static String sENUMNAME="STATUSLOCAL";
	
	//weitere Erweiterungen, speziell für LocalStatus
	public String getStatusMessage();		
	public int getStatusGroupId();
}
