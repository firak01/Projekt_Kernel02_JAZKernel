package basic.zBasic.util.abstractList;

public interface ICircularbufferForStatusLocalMessageZZZ {
	
	 //+++++++++++++++++++++++++++++++++
    //+++ Die Message mit einem besonderen Wert ueberbschreiben
    public boolean replaceLastWith(String sMessage);
    public boolean replacePreviousWith(String sMessage);
    public boolean replacePositionWith(int iIndex, String sMessage);
}
