package basic.zBasic.util.string.justifier;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerXmlZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;

public abstract class AbstractStringJustifierZZZ extends AbstractObjectWithExceptionZZZ implements IStringJustifierZZZ {
	private static final long serialVersionUID = 1931006668388552859L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static IStringJustifierZZZ objStringJustifierINSTANCE;

	
	//Zum Buendig machen
	protected volatile String sPositionSeparator = null;	  //Ggfs. individuelle Positions-Trenner Zeichen.
	protected volatile String sPositionIdentifier = null;     //Merke: Identifier und Separator sind voneinander abhaengig.
	protected volatile int iInfoPartBoundLeft=-1;             //Die aktuelle linke Grenze, an der der InfoPart beginnt.
	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractStringJustifierZZZ() throws ExceptionZZZ{
		super();
	}
	
	//##########################################################
	//### METHODEN ##########
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(this.iInfoPartBoundLeft==-1) break main;
			this.iInfoPartBoundLeft=-1;
					
			bReturn = true;
		}//end main:
		return bReturn;
		
	}
	
	//### Hilfsmethoden zum Buendig machen des Informationsteils im Log ueber meherer Zeilen ########################
	@Override
	public abstract String getPositionSeparatorDefault() throws ExceptionZZZ;
	
	@Override
	public String getPositionSeparator() throws ExceptionZZZ {
		if(this.sPositionSeparator==null) {
			this.sPositionSeparator = this.getPositionSeparatorDefault();
		}
		return this.sPositionSeparator;
	}
	
	@Override
	public void setPositionSeparator(String sPositionSeparator) throws ExceptionZZZ {
		this.sPositionSeparator = sPositionSeparator;
	}
	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public abstract String getPositionIdentifierDefault() throws ExceptionZZZ; 
//	public String getPositionIdentifierDefault() {
//		return ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER;
//	}
	
	@Override
	public String getPositionIdentifier() throws ExceptionZZZ {
		if(this.sPositionIdentifier==null) {
			this.sPositionIdentifier = this.getPositionIdentifierDefault();
		}
		return this.sPositionIdentifier;
	}
	
	@Override
	public void setPositionIdentifier(String sPositionIdentifier) throws ExceptionZZZ {
		this.sPositionIdentifier = sPositionIdentifier;
	}
    //+++++++++++++++++++++++++++++++++
	
	@Override
	public synchronized int getInfoPartBoundLeftBehindCurrent() throws ExceptionZZZ {
		return this.iInfoPartBoundLeft;
	}
	
	@Override
	public synchronized void setInfoPartBoundLeftBehindCurrent(int iIndex) throws ExceptionZZZ {
		this.iInfoPartBoundLeft = iIndex;
	}
	
	@Override
	public void setInfoPartBoundLeftBehindIncreased(int iIndexMayIncrease) throws ExceptionZZZ {
		main:{
			if(iIndexMayIncrease<=0) break main;
			
			//hole die bisherige, aktuelle Grenze
			int iBoundLeftBehindCurrent = this.getInfoPartBoundLeftBehindCurrent();
			
			//Keine Differenz vorhanden			
			if(iIndexMayIncrease == iBoundLeftBehindCurrent) break main;				
			
			//Differenz vorhanden und groesser.
			//Merke: Nun zum Schluss, da hier iBoundLeftBehind neu gesetzt wird			
			if(iIndexMayIncrease > iBoundLeftBehindCurrent) { 
				this.setInfoPartBoundLeftBehindCurrent(iIndexMayIncrease);
				break main;
			}
		}//end main:
	}
	
				
	
		
	
	@Override 
	public int indexOfInfoPartBoundLeftBehind(String sLog) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionIdentifier();
			iReturn = AbstractStringJustifierZZZ.indexOfInfoPartBoundLeftBehindStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override 
	public int indexOfInfoPartBoundLeft(String sLog) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionIdentifier();
			iReturn = AbstractStringJustifierZZZ.indexOfInfoPartBoundLeftStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override
	public int getInfoPartBoundLeftBehind2use(String sLog) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			int itemp = this.indexOfInfoPartBoundLeftBehind(sLog);
			iReturn = this.getInfoPartBoundLeftBehindCurrent(); 
			if(itemp>iReturn) {
				//nein, nicht setzen. Das justify überlassen this.setInfoPartBoundLeftCurrent(itemp);
				iReturn = itemp;
			}			
		}//end main:
		return iReturn;
	}
	
	@Override
	public synchronized String justifyInfoPart(String sLog) throws ExceptionZZZ {
		String sReturn = sLog;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;

			//ermittle die Grenze aus dem Logstring
			int iBoundLeftBehind = this.indexOfInfoPartBoundLeftBehind(sLog);
			if(iBoundLeftBehind<=-1) break main; //Dann gibt es diese Grenzmarkierung noch nicht, z.B. beim anfänglichen Datumsstring.

			//erhoehte ggfs. die Grenze
			this.setInfoPartBoundLeftBehindIncreased(iBoundLeftBehind);
			
			//hole die nun aktuelle Grenze, die es zu verwenden gilt
			int iBoundLeftBehindToUse = this.getInfoPartBoundLeftBehindCurrent();
						
			//Differenz vorhanden und kleiner
			//Merke: Nun aufsplitten und auffuellen
			if(iBoundLeftBehind < iBoundLeftBehindToUse) {

				int iBoundLeft = this.indexOfInfoPartBoundLeft(sLog);
				int iDifference = iBoundLeftBehindToUse - iBoundLeftBehind;
				
				if(iDifference>=1) {
					String sLeft = StringZZZ.left(sLog, iBoundLeft);
					String sRight = StringZZZ.rightback(sLog, iBoundLeft);
					String sMid = StringZZZ.repeat(" ", iDifference);
								
					//wieder zusamensetzen
					sReturn = sLeft + sMid + sRight;// + "-DEBUG02a: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
				}else {
					break main;
				}				
			}//end if(iBoundLeftBehind < iBoundLeftBehindCurrent) {	
		}//end main:
		return sReturn;
	}
		
	
	//### STATIC METHODEN
	public static int indexOfInfoPartBoundLeftStatic(String sLog, String sPositionSeparator) throws ExceptionZZZ {
		return StringZZZ.indexOfFirst(sLog, sPositionSeparator);				
	}
	
	public static int indexOfInfoPartBoundLeftBehindStatic(String sLog, String sPositionSeparator) throws ExceptionZZZ {
		return StringZZZ.indexOfFirstBefore(sLog, sPositionSeparator);				
	}

	
		
}
