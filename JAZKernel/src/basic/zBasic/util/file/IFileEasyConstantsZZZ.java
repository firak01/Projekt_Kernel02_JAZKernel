package basic.zBasic.util.file;

import java.io.File;

public interface IFileEasyConstantsZZZ {

	String sDIRECTORY_CURRENT = ".";
	String sDIRECTORY_PARENT = "..";
	String sDIRECTORY_SEPARATOR = File.separator; //z.B. Backslash in Windows
	char cDIRECTORY_SEPARATOR = File.separatorChar;
	String sDIRECTORY_SEPARATOR_WINDOWS ="\\";
	String sDIRECTORY_SEPARATOR_UNIX ="/";
	
	//https://stackoverflow.com/questions/51494579/regex-windows-path-validator
	String sDIRECTORY_VALID_WINDOWS_REGEX="^(?:[a-z]:)?[\\/\\\\]{0,2}(?:[.\\/\\\\ ](?![.\\/\\\\\\n])|[^<>:\"|?*.\\/\\\\ \\n])+$";
	/* So the regex is as follows:
	
	    ^ - Start of the string.
	    (?:[a-z]:)? - Drive letter and a colon, optional.
	    [\/\\]{0,2} - Either a backslash or a slash, between 0 and 2 times.
	    (?: - Start of the non-capturing group, needed due to the + quantifier after it.
	        [.\/\\ ] - The first alternative.
	        (?![.\/\\\n]) - Negative lookahead - "forbidden" chars.
	    | - Or.
	        [^<>:"|?*.\/\\ \n] - The second alternative.
	    )+ - End of the non-capturing group, may occur multiple times.
	    $ - End of the string.
	
	If you attempt to match each path separately, use only i option.
	
	But if you have multiple paths in separate rows, and match them globally in one go, add also g and m options.
	
	For a working example see https://regex101.com/r/4JY31I/1
	 */
	String sFILE_ENDING_SEPARATOR = ".";
	String sFILE_ABSOLUT_REGEX="^[A-Za-z]:[\\\\/]"; //Merke: Um 1 Backslash auszukommentieren 4 verwenden zum ausmaskieren.
	String sFILE_VALID_WINDOWS_REGEX="^(?>[a-z]:)?(?>\\|/)?([^\\/?%*:|\"<>\r\n]+(?>\\|/)?)+$";
	String sDIRECTORY_CONFIG_SOURCEFOLDER="src";//Dient zur Unterscheidung, ob die Applikation auf deinem Server oder lokal läuft. Der Ordner ist auf dem Server nicht vorhanden (Voraussetzung)!!!
	String sDIRECTORY_CONFIG_TESTFOLDER="test";//FÜR DIE AUSFÜHRUNG VON TESTKLASSEN
	String sDIRECTORY_CONFIG_TRYOUTFOLDER="tryout";//FÜR DIE DEBUG und TRYOUTKLASSEN

}
