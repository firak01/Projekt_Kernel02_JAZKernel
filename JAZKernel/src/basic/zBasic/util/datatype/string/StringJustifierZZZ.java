package basic.zBasic.util.datatype.string;

import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class StringJustifierZZZ extends AbstractObjectZZZ implements IStringJustifierZZZ {
	private static final long serialVersionUID = 1931006668388552859L;
	
	//Zum Buendig machen
	protected volatile String sPositionSeparator = null;	  //Ggfs. individuelle Positions-Trenner Zeichen.
	protected volatile String sPositionIdentifier = null;     //Merke: Identifier und Separator sind voneinander abhaengig.
	protected volatile int iInfoPartBoundLeft=-1;             //Die aktuelle linke Grenze, an der der InfoPart beginnt.
	
	public StringJustifierZZZ() {			
	}
	
	//### Hilfsmethoden zum Buendig machen des Informationsteils im Log ueber meherer Zeilen ########################
	@Override
	public String getPositionSeparatorDefault() {
		return ReflectCodeZZZ.sPOSITION_SEPARATOR;
	}
	
	@Override
	public String getPositionSeparator() {
		if(this.sPositionSeparator==null) {
			this.sPositionSeparator = this.getPositionSeparatorDefault();
		}
		return this.sPositionSeparator;
	}
	
	@Override
	public void setPositionSeparator(String sPositionSeparator) {
		this.sPositionSeparator = sPositionSeparator;
	}
	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public String getPositionIdentifierDefault() {
		return ReflectCodeZZZ.sPOSITION_IDENTIFIER;
	}
	
	@Override
	public String getPositionIdentifier() {
		if(this.sPositionIdentifier==null) {
			this.sPositionIdentifier = this.getPositionIdentifierDefault();
		}
		return this.sPositionIdentifier;
	}
	
	@Override
	public void setPositionIdentifier(String sPositionIdentifier) {
		this.sPositionIdentifier = sPositionIdentifier;
	}
    //+++++++++++++++++++++++++++++++++
	
	@Override
	public int getInfoPartBoundLeftBehindCurrent() {
		return this.iInfoPartBoundLeft;
	}
	
	@Override
	public void setInfoPartBoundLeftBehindCurrent(int iIndex) {
		this.iInfoPartBoundLeft = iIndex;
	}
	
	@Override 
	public int indexOfInfoPartBoundLeftBehind(String sLog) {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionIdentifier();
			iReturn = StringJustifierZZZ.indexOfInfoPartBoundLeftBehindStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override 
	public int indexOfInfoPartBoundLeft(String sLog) {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionIdentifier();
			iReturn = StringJustifierZZZ.indexOfInfoPartBoundLeftStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override
	public int getInfoPartBoundLeftBehind2use(String sLog) {
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
			
			//hole die bisherige, aktuelle Grenze
			int iBoundLeftBehindCurrent = this.getInfoPartBoundLeftBehindCurrent();
						
			//ermittle die Grenze aus dem Logstring
			//int iBoundLeft2use = this.getInfoPartBoundLeft2use(sLog);
			int iBoundLeftBehind = this.indexOfInfoPartBoundLeftBehind(sLog);
			
			//Differenz vorhanden			
			if(iBoundLeftBehind == iBoundLeftBehindCurrent) {				
				sReturn = sReturn  + "-DEBUG01: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
			}
			if(iBoundLeftBehind < iBoundLeftBehindCurrent) {
				//aufsplitten und auffuellen
				//... aber vor dem \t aufsplitten
				int iBoundLeft = this.indexOfInfoPartBoundLeft(sLog);
	

				int iDifference = iBoundLeftBehindCurrent - iBoundLeftBehind;
				
				//Nicht mehr auf Tabs konzentrieren, sondern nur noch die echten Zeichen
				if(iDifference>=1) {
					String sLeft = StringZZZ.left(sLog, iBoundLeft);
					String sRight = StringZZZ.rightback(sLog, iBoundLeft);
								
					//String sMid = StringZZZ.repeat("\t", iTabs); //Die Anzahl der Tabs selbst auszugeben, macht es schwierig.
					//Daher nur die Anzahl der Zeichen als Leerzeichen.
					String sMid = StringZZZ.repeat(" ", iDifference);
								
					//wieder zusamensetzen
					sReturn = sLeft + sMid + sRight + "-DEBUG02a: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
				}else {
					sReturn = sReturn + "-DEBUG02b: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
				}
				
				//Es werden keine TABS mehr verwendet, sondern nur noch die wirklichen Zeichen. Auffuellzeichen ist " ".
//					//falls ja: Berechne die Anzahl er Tabs aus der Differenz				
//					int iTabs = iDifference / 7;//7 Leerzeichen entsprechen 1 Tab???
//					
//					if(iTabs>=1) {
//						String sLeft = StringZZZ.left(sLog, iBoundLeft);
//						String sRight = StringZZZ.rightback(sLog, iBoundLeft);
//									
//						//String sMid = StringZZZ.repeat("\t", iTabs); //Die Anzahl der Tabs selbst auszugeben, macht es schwierig.
//						//Daher nur die Anzahl der Zeichen als Leerzeichen.
//						String sMid = StringZZZ.repeat(" ", iDifference);
//									
//						//wieder zusamensetzen
//						sReturn = sLeft + sMid + sRight + "-DEBUG03: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
//					}
				
				
				
			}//end if(iBoundLeftBehind < iBoundLeftBehindCurrent) {
			
			//Merke diesen Fall zum Schluss, da hier iBoundLeftBehind neu gesetzt wird
			if(iBoundLeftBehind > iBoundLeftBehindCurrent) { 
				this.setInfoPartBoundLeftBehindCurrent(iBoundLeftBehind);
				sReturn = sReturn  + "-DEBUG03: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
			}		
		}//end main:
		return sReturn;
	}
		
	
	//### STATIC METHODEN
	public static int indexOfInfoPartBoundLeftStatic(String sLog, String sPositionSeparator) {
		return StringZZZ.indexOfFirst(sLog, sPositionSeparator);				
	}
	
	public static int indexOfInfoPartBoundLeftBehindStatic(String sLog, String sPositionSeparator) {
		return StringZZZ.indexOfFirstBehind(sLog, sPositionSeparator);				
	}

	
		
}
