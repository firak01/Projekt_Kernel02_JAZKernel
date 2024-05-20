package basic.zBasic;

public interface IReflectCodeZZZ{
	public static  final int iPOSITION_STACKTRACE_CURRENT = 2;
	public static  final int iPOSITION_STACKTRACE_CALLING = 3;
	
	public static final String sCLASS_METHOD_SEPERATOR = ".";
	public static final String sPACKAGE_SEPERATOR = ".";
	
	public static final String sPOSITION_MESSAGE_SEPARATOR = "# "; //Merke: Damit kein unnoetiges Leerzeichen entsteht 2 Variablen. Einmal Separator und einmal Idetntifier.
	public static final String sPOSITION_MESSAGE_IDENTIFIER = "" +IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;    //Dieser String wird hinter der Ermittelten Position ausgegeben.
															   //Merke: Mit der neuen "justify"-Methode in LogStringZZZ wird das eh buendig gemacht. Darum keine Tabs mehr notwendig
	
	public static final String sPOSITION_FILE_SEPARATOR = " ~";    //Merke: Damit wird eine ggfs. errechnete Position im Code abgegrenzt.
	public static final String sPOSITION_FILE_IDENTIFIER = IReflectCodeZZZ.sPOSITION_FILE_SEPARATOR + "";    //Merke: Falls dies wieder z.B. Buendig gemacht werden soll, oder so.

	public static final String sPOSITION_METHOD_SEPARATOR = " @";    //Merke: Damit wird eine ggfs. errechnete Position im Code abgegrenzt.
	public static final String sPOSITION_METHOD_IDENTIFIER = IReflectCodeZZZ.sPOSITION_METHOD_SEPARATOR + "";    //Merke: Falls dies wieder z.B. Buendig gemacht werden soll, oder so.	
}
