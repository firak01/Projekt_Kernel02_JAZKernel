package basic.zBasic.util.stream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import basic.javagently.Stream;

/** Erweiterung der Stream-Klasse um das zu verwendende CharacterSet / Encoding
 *  Klasse: Charset
 * 
 *  Hintergrund:
 *  Beim Arbeiten mit DateiUtil.java (entstanden aus Datei.java vom Buch "Kryptografie mit Java"
 *  ist mir aufgefallen, dass heutzutage es problematisch ist OHNE Encoding - Angabe einen Stream zu öffnen.
 *  
 *  siehe:
 *  https://stackoverflow.com/questions/696626/java-filereader-encoding-issue
Yes, you need to specify the encoding of the file you want to read.
Yes, this means that you have to know the encoding of the file you want to read.
No, there is no general way to guess the encoding of any given "plain text" file.
The one-arguments constructors of FileReader always use the platform default encoding which is generally a bad idea.

Since Java 11 FileReader has also gained constructors that accept an encoding: new FileReader(file, charset) and new FileReader(fileName, charset).
In earlier versions of java, you need to use new InputStreamReader(new FileInputStream(pathToFile), <encoding>).
       
 * 
 * @author Fritz Lindhauer, 09.10.2022, 09:05:49
 * 
 */
public class StreamZZZ extends Stream {
TODOGOON20221010; //In dieser erbenden Klasse das Encoding für das Einlesen/Schreiben beachten...
	public StreamZZZ(String filename, int how) throws FileNotFoundException, IOException {
		super(filename, how);
	  }
	public StreamZZZ(InputStream i) {
		super(i);
	}

}
