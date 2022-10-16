package debug.zBasic.util.start;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.start.GetOpt;

public class DebugGetOptUI {
	 public static void main(String[] saArg){
//		TODO GOON: Beim Start werden Parameter mitgegeben
			//-- ApplikationKey, -- SystemNumber, -- Konfigurationspfad
			//Den Argumentpatternstring übergeben. Dabei sind die Optionen auf 1 Zeichen beschränkt und ein Doppelpunkt besagt, dass ein Wert folgt.
		 	JFrame frame = new JFrame("Application Option Checker");
			//frame.setUndecorated(true);
//		 	frame.setLayout( new GridLayout());	
		 	BoxLayout objLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
			frame.setLayout(objLayout);  //!!! Nur den LayoutManager zu initialisieren reicht nicht. Auch wenn das Panel-Objekt mit übergeben wird.
			
			
		 
			//TODO GOON: Klasse GetOptZZZ
			GetOpt objOption = new GetOpt("k:s:p:");
			char a = objOption.getopt(saArg);
			String sOption = StringZZZ.char2String(a);			
			String sParam = objOption.optarg();
			
//			MErke: Einfaches System.out führt aber zu keiner Anzeige unter Windows...
			//Kleinen frame machen !!!
			if(StringZZZ.isEmpty(sOption.trim())){
				JLabel labelDrag = new JLabel("No option at startup provided");			
				frame.getContentPane().add(labelDrag);
			}else{
				do{
					JLabel labelDrag = new JLabel(sOption);			
					frame.getContentPane().add(labelDrag);
					if(! StringZZZ.isEmpty(sParam)){
						
							JLabel label = new JLabel(sParam);
							frame.getContentPane().add(label);
						
					}else{
						JLabel label = new JLabel("-- kein Parameter --");
						frame.getContentPane().add(label);
					}
					a = objOption.getopt(saArg);
					sOption = StringZZZ.char2String(a);			
					sParam = objOption.optarg();
				}while(!StringZZZ.isEmpty(sOption.trim()));
			}
			
			frame.pack();
			frame.setVisible(true);	
	 }
}
