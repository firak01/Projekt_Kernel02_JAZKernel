package basic.zBasic;

/** Mit AbstractKernelConfigZZZ - Klassen koennen ja Argumente entgegen genommen werden.
 *  Diese Klasse ist dem noch vorgelagert und wird von AbstractKernelConfigZZZ verwendet.
 *  
 *  Beispielsweise ist eine Konvention, dass es in der Kommandozeile Platzhalter für Umgebungsvariablen geben kann,
 *  die dann vor dem Bauen des Konfigurationsobjekts gegen ihren Wert ausgetauscht werden.
 *  
 *  Hintergrund:
 *  Damit soll es dann möglich sein
 *  a) Launch-Konfigurationen über mehrer Eclipse Installationen gleich zu halten
 *  b) Geheime Daten, die nicht in der Launch-Konfiguration selbst stehen dürfen (z.B. weil die auch in ein Git-Repository kommen)
 *     von aussen an die Applikation zu uebergeben.
 *  
 * @author Fritz Lindhauer, 02.04.2026, 14:29:33
 * 
 */
public class ReflectLaunchArgumentZZZ implements IReflectLaunchArgumentZZZ, IConstantZZZ{
	
	private ReflectLaunchArgumentZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	public String[] replaceArgumentsWithEnvironmentValue(String[] argv) {
		
		TODOGOON20260402;//hier wenn ein Wert mit ${ beginnt und mit } endet, die entsprechende Umgebungsvariable holen
		//mit system.getEnv(...)
		//Idee: EnvironmentZZZ einbinden um den Variablennamen aus dem Platzhalter zu exctrahieren.
		//      oder neue Klasse EnvironmentBaseZZZ
	
		return argv;
	}
}
