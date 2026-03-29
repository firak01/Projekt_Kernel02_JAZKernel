package basic.myutilities;

import java.io.*;
import basic.javagently.*;

public class Filer {

  /* The Filer class           by J M Bishop  June 2000
   * ----------------------    Java 1.2
   *                           based on the FileMan class
   *                           from June 1997
   *
   * Provides for a file to be opened, with five tries at a
   * correct file name.
   * Illustrates the use of exceptions in for-try loops..
   */

 public static Stream open (String filename) throws Exception {

    Stream in = new Stream (System.in);

    for (int count = 0; count < 5; count ++) {
      try {
        return new Stream(filename, Stream.READ);
      } catch (FileNotFoundException e) {
          System.out.println(filename+" does not exist.");
          if (count < 4) {
            System.out.println("Try again");
          }
          filename = in.readString();
      }
    }
    throw new FileNotFoundException ();
  }
}