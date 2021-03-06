package basic.javagently;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Display extends Frame implements ActionListener {
  /*  Display      by J M Bishop   July 1999
   **********
   is a simple class that provides facilities for
      input and output on a window.
   The data values are entered in boxes in the input section.
   Different data choices can be entered if the driving program
     asks for them.
   There is an optional integration with the Graph class
   Interface
   =========
   new Display (title)   - sets up a new Display object with a title
   println (string)      - prints a string in the output section
   prompt (label, value) - sets a box in the input section with the given label
   ready (message)       - prints a message then enables reading from the boxes
   getDouble (label)     - reads the double value that was set with that label
   getInt (label)        - gets the int value that was set with that label
   getString (label)     - reads the string value that was set with that label
   reposition (graph)    - takes a graph and places it on the bottom of the
                           input section
  */
  private String title;
  public Display (String t) {
    /* the alternative constructor -
       has a titles */
    title = t;
    initializeDisplay();
  }
  private Hashtable table = new Hashtable(10);
  private int xwidth, yheight;
  private Button okButton, closeButton;
  private TextArea outDisplay;
  private Panel inDisplay;
  private ScrollPane inPane, outPane;
  private Watcher okWatcher = new Watcher();
  private boolean graphInFront;
  private Graph graph;
  private void initializeDisplay () {
    xwidth = 640;
    yheight = 480;
    setSize(xwidth,yheight);
    setTitle(title);
    setLayout(new BorderLayout());
    Panel p = new Panel ();
      p.add(new Label("INPUT"));
      p.add(new Label("OUTPUT"));
    add(p,"North");
    p = new Panel (new FlowLayout(FlowLayout.CENTER,15,0));
      inPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
      inPane.setSize(xwidth/2 - 40, yheight - 100);
      inDisplay = new Panel(new GridLayout (0,2,10,10));
      inPane.add(inDisplay);
      p.add(inPane);
      outDisplay = new TextArea(24, 40);
      p.add(outDisplay);
   add(p,"Center");
     p = new Panel(new BorderLayout());
     okButton = new Button("Ready");
     okButton.addActionListener(this);
     okButton.setEnabled(false);
     p.add("Center",okButton);
     closeButton = new Button("Close");
     closeButton.addActionListener(this);
     closeButton.setEnabled(true);
     p.add("East",closeButton);
    add("South",p);
    addWindowListener (new WindowAdapter () {
      public void windowClosing(WindowEvent e) {
        System.exit (0);
      }
    });
    setVisible(true);
    graphInFront = false;
  }
  public void reposition (Graph g) {
    // makes the graph smaller and puts it on the bottom
    // half of the input section
    g.setLocation (30, yheight/2-30);
    g.setSize(xwidth/2 - 40, yheight/2-15);
    graphInFront = true;
    graph = g;
  }
  public void actionPerformed (ActionEvent e) {
    if (e.getSource() == okButton) {
    okWatcher.ready();
  } else
  if (e.getSource() == closeButton) {
    System.exit(0);
  }
  }
  private class Data {
    TextField field;
  String value;
  }
  private Data getEntry (String s) {
    if (table.containsKey(s))
    return (Data) table.get(s);
  else {
      outDisplay.append("\nERROR: No such input label: "+s+"\n");
    return null;
  }
  }
  public int getInt (String s) {
    Data d = getEntry(s);
    return Integer.valueOf(d.value).intValue();
  }
  public double getDouble (String s) {
    Data d = getEntry(s);
    return Double.valueOf(d.value).doubleValue();
  }
  public String getString (String s) {
    Data d = getEntry(s);
    return d.value;
  }
  private void insertPrompt(Data d, String s, TextField t) {
    Panel p;
    p = new Panel(new FlowLayout(FlowLayout.RIGHT));
      p.add(new Label(s));
      inDisplay.add(p);
    t.addActionListener(this);
      t.setEditable(true);
      p = new Panel(new FlowLayout(FlowLayout.LEFT));
      p.add(t);
      inDisplay.add(p);
    d.field = t;
  }
  public void prompt (String s, int n) {
    Data d = new Data();
    TextField t = new TextField(10);
    insertPrompt(d, s, t);
    d.value = Text.writeInt(n,0);
    t.setText(d.value);
    table.put(s, d);
  }
  public void prompt (String s, double n) {
     Data d = new Data();
   TextField t = new TextField(10);
   insertPrompt(d, s, t);
   d.value = Double.toString(n);
   t.setText(d.value);
   table.put(s, d);
  }
  public void prompt (String s, String n) {
    Data d = new Data();
    TextField t = new TextField(n.length()+2);
    insertPrompt(d, s, t);
    d.value = n;
    t.setText(d.value);
    table.put(s, d);
  }
  public void ready (String s) {
    outDisplay.append(s+"\n");
    okButton.setEnabled(true);
    setVisible(true);
    if (graphInFront) graph.toFront();
    okWatcher.watch();
    // copy all the values from the boxes to the table
    for (Enumeration e = table.keys(); e.hasMoreElements();) {
      String name = (String) e.nextElement();
      Data d = (Data) table.get(name);
      d.value = d.field.getText();
      table.put(name, d);
    }
  }
  public void ready() {
    okButton.setEnabled(true);
    setVisible(true);
    if (graphInFront) graph.toFront();
    okWatcher.watch();
    // copy all the values from the boxes to the table
    for (Enumeration e = table.keys(); e.hasMoreElements();) {
      String name = (String) e.nextElement();
      Data d = (Data) table.get(name);
      d.value = d.field.getText();
      table.put(name, d);
    }
  }
  public void println (String s) {
    outDisplay.append(s+"\n");
  }
  public void print (String s) {
    outDisplay.append(s);
  }
  class Watcher {
    private boolean ok;
    Watcher () {
    ok = false;
  }
    synchronized void watch () {
      while (!ok) {
      try {wait(500); }
      catch(InterruptedException e) {}
    }
    ok = false;
    }
    synchronized void ready () {
      ok = true;
    notify();
    }
  }
}