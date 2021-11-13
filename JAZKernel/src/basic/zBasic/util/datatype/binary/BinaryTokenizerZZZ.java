package basic.zBasic.util.datatype.binary;

/** Klasse zum Arbeiten mit Binären Daten, bzw. Zerlegen wie es eine Binäre Operation tut.
 *  Z.B. kann die Summe 7 für die Verarbeitung von Flags/Statuswerten stehen
 *      1* 2 hoch 0 = 1: d.h. status A gesetzt
 *      1* 2 hoch 1 = 2: d.h. Status B gesetzt
 *      1* 2 hoch 2 = 4: d.h. Status C gesetzt  
 *       
 *   dem gegenüber wäre dann die Summe 5
 *      1* 2 hoch 0 = 1: d.h. status A gesetzt
 *      0* 2 hoch 1 = 2: d.h. Status B NICHT gesetzt
 *      1* 2 hoch 2 = 4: d.h. Status C gesetzt  
 *   
 *   Anregungen aus: https://mein-javablog.de/java-dezimalzahl-in-binaere-zahl-umwandeln/
 * @author Fritz Lindhauer, 13.11.2021, 16:01:05
 * 
 */
public class BinaryTokenizerZZZ {
	
	/**Als Integer wird immer der Restwert eingetragen.
	 * @param iValue
	 * @return
	 * @author Fritz Lindhauer, 13.11.2021, 18:03:41
	 */
	public static int[] createBinaryTokensRemainder(int iValue){
		int anzahlStellen=0;//Anzahl der Stellen der Dualzahl
		int dezZahlZwei=iValue;//Kopie der Zahl, da am Ende der while-Schleife die Zahl Null ist
		
		/*
		 * While Schleife soll die Anzahl der Stellen bestimmen
		 */
		while (dezZahlZwei != 0){
			dezZahlZwei=dezZahlZwei / 2;//Zahl wird solange durch 2 dividiert bis 0 herauskommt
			anzahlStellen++;//Erhöhung der Zählvariablen
		}
		
		int zahlen[] = new int [anzahlStellen];//Array mit Länge der Zählvariablen
		
		/*
		 * For Schleife füllt das Array mit den Restwerten
		 */
		int dezZahl = iValue;
		for (int i = 0; i < anzahlStellen; i++){ 
                        zahlen[i]=dezZahl % 2; //Speichern der Restwerte im Array
                        dezZahl = dezZahl / 2; //Die Zahl wird immer wieder durch 2 dividiert
                    } 
		
		
		return zahlen;
	}

	public static boolean[] createBinaryTokens(int iValue) {
		boolean[]baReturn=null;
		main:{
			
			int[]iaReturn = BinaryTokenizerZZZ.createBinaryTokensRemainder(iValue);
			if(iaReturn==null)break main;
			baReturn = new boolean[iaReturn.length];	
			
			boolean btemp = false;
			for(int iIndex=0;iIndex <= iaReturn.length-1; iIndex++) {
				
				//Interpretiere den Wert... wenn was gefunden, dann true				
				if(iaReturn[iIndex]>0) {
					btemp=true;
				}else {
					btemp=false;
				}
				baReturn[iIndex] = btemp;
			}//end for													
		}//end main:
		return baReturn;
		
	}
}
