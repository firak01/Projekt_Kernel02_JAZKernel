package basic.zBasic.util.file.txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.txt.TxtReaderZZZ.FLAGZ;

/**Klasse dient zum Schreiben in TextDateien. Dies ist ein Wrapper um "RandomAccessFile".
 * Bietet Komfortmethoden, wie z.B. das Einfügen einer Zeile in eine sortierte Datei.
 * 
 * @author lindhaueradmin
 *
 */
public class TxtWriterZZZ  extends TxtCommonZZZ{
	private boolean bFlagIsFileSorted = false;
	private boolean bFlagIgnoreCase = false;
	private boolean bFlagTopOfEmptyLine = false;
	private boolean bFlagTopOfCommentLine = false;
	
	public enum FLAGZ{
		IsFileSorted, IgnoreCase, TopOfEmptyLine, TopOfCommentLine;
	}
	
	//Aus IFlagZZZ, über die abstrakte TxtCommonZZZ Klasse
	@Override
	public Class getClassZ() {
		return FLAGZ.class;
	}
	
	public TxtWriterZZZ() throws ExceptionZZZ{
		String[] saFlag = {"INIT"};
		TxtWriterNew_(null, null, saFlag);
	} 
	public TxtWriterZZZ(File file) throws ExceptionZZZ{
		TxtWriterNew_(null, file, null);
	}
	
	public TxtWriterZZZ(File file, String[] saFlagcontrol) throws ExceptionZZZ{
		TxtWriterNew_(null, file, saFlagcontrol);
	}
	public TxtWriterZZZ(TxtReaderZZZ objReader, String[] saFlagControl) throws ExceptionZZZ{
		TxtWriterNew_(objReader, null, saFlagControl);
	}
	



	private boolean TxtWriterNew_(TxtReaderZZZ objReader, File file, String[] saFlagControlIn ) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
		 //setzen der übergebenen Flags	
		  if(saFlagControlIn != null){
			  for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				  String stemp = saFlagControlIn[iCount];
				  boolean btemp = setFlagZ(stemp, true);
				  if(btemp==false){						
					  ExceptionZZZ ez = new ExceptionZZZ("the flag '" + stemp + "' is not available.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  throw ez;		 
				  }
			  }
				if(this.getFlagZ("INIT")==true){
					bReturn = true;
					break main; 
				}		
		  }
		  
		  if(objReader==null){
			  if(file == null){
				  ExceptionZZZ ez = new ExceptionZZZ("File-Object", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				  throw ez;
			  }		  
			  this.setFile(file);
		  }else{
			  File fileFromReader = objReader.getFile();
			  if(fileFromReader == null){
				  ExceptionZZZ ez = new ExceptionZZZ("File-Object from reader-object.", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				  throw ez;
			  }
			  this.setFile(fileFromReader);
		  }	  
		  bReturn = true;
		}//end main:
		return bReturn;
	}



	/** Gibt die BytePosition, in die eingefügt wurde zurück.
	* @param sToBeInserted
	* @return
	* 
	* lindhaueradmin; 10.02.2007 14:34:44
	 * @throws ExceptionZZZ 
	 */
	public long insertLine(String sToBeInserted) throws ExceptionZZZ{
		long lReturn=-1;  //Merke: Selbst wenn es noch keinen Inhalt gibt, dann sollte dieser Wert auf 0 geändert werden.
		main:{
			try{
			if(StringZZZ.isEmpty(sToBeInserted)==true) break main;
			
			RandomAccessFile rwFile = this.getRandomAccessFileObject();
			
			
			boolean bFlagSorted = this.getFlagZ("IsFileSorted");
			//Falls man davon ausgehen kann, dass die Datei sortiert ist ...
			if(bFlagSorted==true){
				boolean bInserted = false;
				boolean bFlagIgnoreCommentLine = this.getFlagZ("TopOfCommentLine");
				boolean bFlagIgnoreCase = this.getFlagZ("IgnoreCase");
				boolean bFlagTopOfEmptyLine = this.getFlagZ("TopOfEmptyLine");
				
				rwFile.seek(0);
				
				String sLineForCompare;
				String sLine = rwFile.readLine();			
				while(sLine!=null){
					boolean bTopOfLine = false;
					
					currentLine:{
						//ggf. prüfen, ob eine Komentarzeile
						if(bFlagIgnoreCommentLine==true){
							if(this.isCommentLine(sLine)){
								bTopOfLine = true;
								break currentLine;
							}
						}	
						if(bFlagTopOfEmptyLine==true){
							if(this.isEmptyLine(sLine)){
								bTopOfLine = true;
								break currentLine;
							}
						}
						
						sLineForCompare = sLine; //nachdem einige Zeilen ignoriert wurden, kann nun die "Vergleichszeile" gesetzt werden.
						int iProof;
						if(bFlagIgnoreCase==false){
							iProof = sLineForCompare.compareTo(sToBeInserted);
						}else{
							iProof = sLineForCompare.compareToIgnoreCase(sToBeInserted);
						}
						
						
						if(iProof >= 0){
							//der neue String ist grösser oder gleich
							
							//TODO den Rest der Datei irgendwo zwischenspeichern
							//          Erste Alternative: ArrayList mit den restlichen Zeilen füllen
							//          Ggf. mal in eine temporäre Datei speichern. Das ist aber nur bei SEHR grossen Dateine sinnvoll.
							TxtReaderZZZ objReader = new TxtReaderZZZ(this, null);
																				
							Vector vec = objReader.readVectorStringByByte(lReturn);
							
							//Nun an der alten Stelle einfügen
							if(lReturn == rwFile.length()){
								//a) Am Ende der Datei
								if(lReturn<=-1) lReturn = 0;
								rwFile.seek(lReturn);
								sToBeInserted = "\r\n" + sToBeInserted;
								rwFile.writeBytes(sToBeInserted);												
								break main;
								
							}else{
								//b) Mitten in der Datei
								if(lReturn<=-1) lReturn = 0;
								rwFile.seek(lReturn);
								sToBeInserted = sToBeInserted + "\r\n";
								rwFile.writeBytes(sToBeInserted);
								
								//Den Speicher wieder auslesen und alle fehlenden Datensätze schreiben. 
								this.appendVectorString(rwFile.getFilePointer(), vec);								
								break main;
								
							}												
						}
						//lReturn = rwFile.getFilePointer();  //die alte File Position festhalten
					}//end currentLine:
					if(bTopOfLine==false){
						lReturn = rwFile.getFilePointer();  //die alte File Position festhalten
					}
					sLine = rwFile.readLine();		
				}//end while
				if(bInserted==false){
					lReturn = rwFile.length();
					sToBeInserted = sToBeInserted + "\r\n";
					rwFile.writeBytes(sToBeInserted);
				}
				
			}else{		
				//... falls es eine unsortierte Datei ist. Einfach anhängen.
				lReturn = rwFile.length();
				if(lReturn<=-1) lReturn = 0;
				rwFile.seek(lReturn);
				sToBeInserted = sToBeInserted + "\r\n";
				rwFile.writeBytes(sToBeInserted);	
			}
			}catch(IOException ie){
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return lReturn;
	}
	
	public long removeLineFirst(String sToBeRemoved, long lPositionIn) throws ExceptionZZZ{
		long lReturn = -1;
		main:{
		try{
			if(StringZZZ.isEmpty(sToBeRemoved)==true) break main;
			long lPosition;
			if(lPositionIn <= -1){
				lPosition = 0;
			}else{
				lPosition = lPositionIn;
			}
			
			RandomAccessFile rwFile = this.getRandomAccessFileObject();
			rwFile.seek(lPosition);
			
			boolean bFlagSorted = this.getFlagZ("IsFileSorted");
			boolean bFlagIgnoreCase = this.getFlagZ("IgnoreCase");
			
			String sLine = rwFile.readLine();	
			
			//Falls man davon ausgehen kann, dass die Datei sortiert ist ...
			if(bFlagSorted==true){												
				while(sLine!=null){				
					currentLine:{								
						int iProof;
						if(bFlagIgnoreCase==false){
							iProof = sLine.compareTo(sToBeRemoved);
						}else{
							iProof = sLine.compareToIgnoreCase(sToBeRemoved);
						}
						
						
						if(iProof == 0){
							//Die alte Stelle merken
							lReturn = lPosition;
						
							
							//TODO den Rest der Datei irgendwo zwischenspeichern
							//          Erste Alternative: ArrayList mit den restlichen Zeilen füllen
							//          Ggf. mal in eine temporäre Datei speichern. Das ist aber nur bei SEHR grossen Dateine sinnvoll.
							TxtReaderZZZ objReader = new TxtReaderZZZ(this, null);		
							lPosition = rwFile.getFilePointer();
												
							//Nun an der alten Stelle einfügen
							if(lPosition == rwFile.length()){
								//a) am Ende der Date nix machen, ausser die Dateilänge zu verkürzen
								rwFile.setLength(lReturn);
								
							}else{
								//b) Mitten in der Datei
							
								//Speicher mit den restlichen Zeilen füllen
								Vector vec = objReader.readVectorStringByByte(lPosition);
															
								//Den Speicher wieder auslesen und alle fehlenden Datensätze schreiben. 
								this.appendVectorString(lReturn, vec);								
								
	//							Die Dateilänge entsprechend verlürzen
								lPosition = rwFile.getFilePointer();
								rwFile.setLength(lPosition);
								
								break main;							
							}												
						}else if(iProof >= 1){
	//						Bei einer sortierten Datei kann es nun ein Abbruchkriterium geben.
							//System.out.println(ReflectionZZZ.getMethodCurrentName() + "# Abbruch der Suche, neue Zeile kann in sortierter Datei nicht mehr der gesuchten Zeile entsprechen");
							break main;
						}
					}//end currentLine:
					lPosition = rwFile.getFilePointer();
					sLine = rwFile.readLine();	
				}//end while
				
			}else{		
				while(sLine!=null){				
					currentLineUnsorted:{								
						int iProof;
						if(bFlagIgnoreCase==false){
							iProof = sLine.compareTo(sToBeRemoved);
						}else{
							iProof = sLine.compareToIgnoreCase(sToBeRemoved);
						}
	
						if(iProof == 0){
							//Die alte Stelle merken
							lReturn = lPosition;
					
													
							//TODO den Rest der Datei irgendwo zwischenspeichern
							//          Erste Alternative: ArrayList mit den restlichen Zeilen füllen
							//          Ggf. mal in eine temporäre Datei speichern. Das ist aber nur bei SEHR grossen Dateine sinnvoll.
							TxtReaderZZZ objReader = new TxtReaderZZZ(this, null);		
							lPosition = rwFile.getFilePointer();
													
							//Nun an der alten Stelle einfügen
							if(lPosition == rwFile.length()){
								//a) am Ende der Date nix machen, auser die Datei zu verkürzen
								rwFile.setLength(lReturn);
							}else{
								//b) Mitten in der Datei
								
								//Speicher mit den restlichen Zeilen füllen
								Vector vec = objReader.readVectorStringByByte(lPosition);
								
								//Den Speicher wieder auslesen und alle fehlenden Datensätze schreiben. 
								this.appendVectorString(lReturn, vec);		
								
								//Die Dateilänge entsprechend verlürzen
								lPosition = rwFile.getFilePointer();
								rwFile.setLength(lPosition);
								
								break main;							
							}												
						}else{
							lPosition = rwFile.getFilePointer();
						}
					}//end currentLineUnsorted:
					sLine = rwFile.readLine();	
				}//end while
			}
			}catch(IOException ie){
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return lReturn;
	}
	
	public void appendVectorString(Vector vec) throws ExceptionZZZ{
		try {
			RandomAccessFile raFile = this.getRandomAccessFileObject();
			long lPosition = raFile.length();
			this.appendVectorString(lPosition, vec);
		}catch(IOException ie){
			ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	}
	
	/** Anhängen von Datensätzen ab der bestimtten Position 
	 *  !!! Dabei werden aber ggf. folgende Bytes überschrieben !!!
	 *  
	 *  !!! Auch bei sortierten Dateien wird (noch nicht ?) neu sortiert.
	* @param lPositionin
	* @param vec
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 11.02.2007 15:16:41
	 */
	public void appendVectorString(long lPositionin, Vector vec) throws ExceptionZZZ{
		main:{
			try{
				if(vec == null) break main;
				if(vec.size()==0) break main;
				long lPosition;
				if(lPositionin <= -1){
					lPosition = 0;
				}else{
					lPosition = lPositionin;
				}
				
				RandomAccessFile raFile = this.getRandomAccessFileObject();
				raFile.seek(lPosition);
				
				Iterator it = vec.iterator();
				while(it.hasNext()){
					String sLine = (String) it.next();
					//Problem, wie fragt man die vorherige Zeile ab.   if(lPosition>=1), darum wird \r\n angehängt und nicht vorangestellt
					sLine = sLine + "\r\n";
					raFile.writeBytes(sLine);
					lPosition = raFile.getFilePointer();
				}
			}catch(IOException ie){
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
	}
	
	
	
	/** Gibt den Mode für das RandomAccessFile zurück.
	 *   Es muss sowohl lesend als auch schreibend auf die Datei zugegriffen werden.
	* @return
	* 
	* 
	* Value
	
	Meaning
	"r" 	Open for reading only. Invoking any of the write methods of the resulting object will cause an IOException to be thrown.
	"rw" 	Open for reading and writing. If the file does not already exist then an attempt will be made to create it.
	"rws" 	Open for reading and writing, as with "rw", and also require that every update to the file's content or metadata be written synchronously to the underlying storage device.
	"rwd"   	Open for reading and writing, as with "rw", and also require that every update to the file's content be written synchronously to the underlying storage device.
	
	* lindhaueradmin; 10.02.2007 15:09:59
	 */
	public String getMode(){
		return "rw";
	}
	
	
//	/* (non-Javadoc)
//	 * @see basic.zBasic.IFunctionZZZ#getFlag(java.lang.String)
//	 */
//	public boolean getFlag(String sFlagName) {
//		boolean bFunction = false;
//		main:{
//			if(StringZZZ.isEmpty(sFlagName)) break main;
//		
//			// hier Superclass aufrufen, Klasse selbst ist ja nicht ObjectZZZ
//			bFunction = super.getFlag(sFlagName);
//			if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("topofcommentline")){
//				bFunction = this.bFlagTopOfCommentLine;
//				break main;		
//			}else if(stemp.equals("topofemptyline")){
//				bFunction = this.bFlagTopOfEmptyLine;
//				break main;
//			}else if(stemp.equals("isfilesorted")){
//				bFunction = this.bFlagIsFileSorted;
//				break main;
//			}else if(stemp.equals("ignorecase")){
//				bFunction = this.bFlagIgnoreCase;
//				break main;	
//			}else{
//				bFunction = false;
//			}		
//		}	// end main:
//		
//		return bFunction;	
//	}
//	
//	/** Function can set the flags of this class or the super-class.
//	 * The following new flags are supported:
//	 * --- debug
//	* @see basic.zBasic.IFunctionZZZ#setFlag(java.lang.String, boolean)
//	*/
//	public boolean setFlag(String sFlagName, boolean bFlagValue){
//		boolean bFunction = true;
//		main:{
//			if(StringZZZ.isEmpty(sFlagName)) break main;
//			
//			// hier Superclass aufrufen, Klasse selbst ist ja nicht ObjectZZZ
//			bFunction = super.setFlag(sFlagName, bFlagValue);
//			if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("topofcommentline")){
//				this.bFlagTopOfCommentLine = bFlagValue;
//				bFunction = true;
//				break main;		
//			}else if(stemp.equals("topofemptyline")){
//				this.bFlagTopOfEmptyLine = bFlagValue;
//				bFunction = true;
//				break main;
//			} else if(stemp.equals("isfilesorted")){
//				this.bFlagIsFileSorted = bFlagValue;
//				bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag überhaupt gibt !!!
//				break main;
//			}else if(stemp.equals("ignorecase")){
//				this.bFlagIgnoreCase = bFlagValue;
//				bFunction = true;
//				break main;
//			}else{
//				bFunction = false;
//			}	
//			
//		}	// end main:
//		
//		return bFunction;	
//	}
}//end class
