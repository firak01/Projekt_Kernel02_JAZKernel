package base.md5;
//package de.his.core.base.md5;
/*
Java port of the GNU C Library md5_crypt function.
Ported to Java by Ryan Mack <rmack@mackman.net>, 2001.

Original copyright notice follows:

One way encryption based on MD5 sum.
Copyright (C) 1996, 1997, 1999, 2000 Free Software Foundation, Inc.
This file is part of the GNU C Library.
Contributed by Ulrich Drepper <drepper@cygnus.com>, 1996.

The GNU C Library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

The GNU C Library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with the GNU C Library; if not, write to the Free
Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.  */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* Hasht ein Passwort mit Salt.
*
* @author http://www.extecher.net/code/MD5Crypt.java
*/
public class MD5Crypt {
   /* Define our magic string to mark salt for MD5 "encryption"
    replacement.  This is meant to be the same as for other MD5 based
    encryption implementations.  */

   private byte[] salt_prefix = { (byte) '$', (byte) '1', (byte) '$' };
   //    private static Logger logger = Logger.getLogger("MD5Crypt");

   public MD5Crypt(String saltPrefix) {
       salt_prefix = saltPrefix.getBytes();
   }

   public MD5Crypt() {
       // use default salt_prefix
   }

   /**
    * Funktion zur Erstellung eines htpasswd2 (Apache Group) konformen
    * MD5-gehashtes Passwort mit Salt.
    *
    * @param key Bytearray des Passwortes
    * @param salt Bytearray des Salts
    * @return buffer Bytearray des MD5-gehashten Passworts mit Salt
    */
   public byte[] crypt(byte[] key, byte[] salt) throws NoSuchAlgorithmException {
       final MessageDigest md5 = MessageDigest.getInstance("md5");
       final MessageDigest alt_md5 = MessageDigest.getInstance("md5");

       /* Find beginning of salt string.  The prefix should normally always
        be present.  Just in case it is not.  */
       int salt_len;
       if (startsWith(salt, salt_prefix)) {
           /* Skip salt prefix.  */
           salt_len = salt.length - salt_prefix.length;
           final byte[] copied_salt = new byte[salt_len];
           System.arraycopy(salt, salt_prefix.length, copied_salt, 0, salt_len);
           salt = copied_salt;
       }

       salt_len = Math.min(indexOf(salt, (byte) '$'), 8);
       int cp;

       if (salt_len == -1) {
           salt_len = 8;
       }
       final int key_len = key.length;
       byte[] alt_result;
       int cnt;

       //Fehlerkorrektur salt_prefix.length + salt_len + 1 + 22 nach
       //                salt_prefix.length + salt_len + 2 + 22
       final byte[] buffer = new byte[salt_prefix.length + salt_len + 2 + 22];

       /* Prepare for the real work. */
       md5.reset();

       /* Add the key string. */
       md5.update(key, 0, key_len);

       /* Because the SALT argument need not always have the salt prefix we
        add it separately.  */
       md5.update(salt_prefix, 0, salt_prefix.length);

       /*
        * The last part is the salt string. This must be at most 8 characters
        * and it ends at the first `$' character (for compatibility which
        * existing solutions).
        */
       md5.update(salt, 0, salt_len);

       /* Compute alternate MD5 sum with input KEY, SALT, and KEY.  The
        final result will be added to the first context.  */
       alt_md5.reset();

       /* Add key. */
       alt_md5.update(key, 0, key_len);

       /* Add salt. */
       alt_md5.update(salt, 0, salt_len);

       /* Add key again. */
       alt_md5.update(key, 0, key_len);

       /* Now get result of this (16 bytes) and add it to the other
        context.  */
       alt_result = alt_md5.digest();

       /* Add for any character in the key one byte of the alternate sum. */
       for (cnt = key_len; cnt > 16; cnt -= 16) {
           md5.update(alt_result, 0, 16);
       }
       md5.update(alt_result, 0, cnt);

       /* For the following code we need a NUL byte. */
       alt_result[0] = (byte) '\000';

       /* The original implementation now does something weird: for every 1
        bit in the key the first 0 is added to the buffer, for every 0
        bit the first character of the key.  This does not seem to be
        what was intended but we have to follow this to be compatible.  */
       for (cnt = key_len; cnt > 0; cnt >>= 1) {
           md5.update((cnt & 1) != 0 ? alt_result : key, 0, 1);
       }

       /* Create intermediate result. */
       alt_result = md5.digest();

       /* Now comes another weirdness.  In fear of password crackers here
        comes a quite long loop which just processes the output of the
        previous round again.  We cannot ignore this here.  */
       for (cnt = 0; cnt < 1000; ++cnt) {
           /* New context.  */
           md5.reset();

           /* Add key or last result. */
           if ((cnt & 1) != 0) {
               md5.update(key, 0, key_len);
           } else {
               md5.update(alt_result, 0, 16);
           }

           /* Add salt for numbers not divisible by 3. */
           if (cnt % 3 != 0) {
               md5.update(salt, 0, salt_len);
           }

           /* Add key for numbers not divisible by 7. */
           if (cnt % 7 != 0) {
               md5.update(key, 0, key_len);
           }

           /* Add key or last result. */
           if ((cnt & 1) != 0) {
               md5.update(alt_result, 0, 16);
           } else {
               md5.update(key, 0, key_len);
           }

           /* Create intermediate result. */
           alt_result = md5.digest();
       }

       /*
        * Now we can construct the result string. It consists of three parts.
        */
       System.arraycopy(salt_prefix, 0, buffer, 0, salt_prefix.length);
       cp = salt_prefix.length;

       System.arraycopy(salt, 0, buffer, cp, salt_len);
       cp += salt_len;

       buffer[cp++] = (byte) '$';
       //logger.debug("alt_result="+new String(alt_result) + "  cp=" + cp );
       //logger.debug("buffern1="+new String(buffer) + "  cp=" + cp );

       cp += b64_from_24bit(buffer, cp, alt_result[0], alt_result[6], alt_result[12], 4);

       //logger.debug("buffern2="+new String(buffer) + "  cp=" + cp );

       cp += b64_from_24bit(buffer, cp, alt_result[1], alt_result[7], alt_result[13], 4);

       //logger.debug("buffern3="+new String(buffer)+ "  cp=" + cp );
       cp += b64_from_24bit(buffer, cp, alt_result[2], alt_result[8], alt_result[14], 4);

       //logger.debug("buffern4="+new String(buffer)+ "  cp=" + cp );
       cp += b64_from_24bit(buffer, cp, alt_result[3], alt_result[9], alt_result[15], 4);

       //logger.debug("buffern5="+new String(buffer)+ "  cp=" + cp );
       cp += b64_from_24bit(buffer, cp, alt_result[4], alt_result[10], alt_result[5], 4);

       //logger.debug("buffern6="+new String(buffer)+ "  cp=" + cp );
       cp += b64_from_24bit(buffer, cp, (byte) 0, (byte) 0, alt_result[11], 2);

       //logger.debug("buffern7="+new String(buffer)+ "  cp=" + cp );
       //logger.debug("buffer.length neu="+buffer.length);
       buffer[cp] = (byte) '\000';

       return buffer;
   }

   private final static byte[] b64t = { (byte) '.', (byte) '/', (byte) '0',
                                        (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5',
                                        (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'A',
                                        (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F',
                                        (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K',
                                        (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P',
                                        (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
                                        (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z',
                                        (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
                                        (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j',
                                        (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o',
                                        (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't',
                                        (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y',
                                        (byte) 'z' };

   private static int b64_from_24bit(byte[] buffer, int cp, byte B2, byte B1,
                                     byte B0, int N) {
       // Fehlerkorrektur: B0 zu (((int)B0) & 0xFF)
       int w = (B2 & 0xFF) << 16 | (B1 & 0xFF) << 8 | B0 & 0xFF;
       // logger.debug("w=" +w );
       int n = N;
       while (n > 0) {

           // logger.debug("n=" +n);
           n = n - 1;
           buffer[cp] = b64t[w & 0x3f];
           // logger.debug(new String(buffer));
           cp = cp + 1;

           w = w >> 6;
       }
       return N;
   }

   /**
    * Prueft, ob ein byte-Array genauso anf&auuml;ngt wie ein anderes
    *
    * @param ar zu testendes Array
    * @param pre zu suchender Anfang
    * @return true, wenn ar mit pre anf&auml;ngt, sonst false
    */
   private static boolean startsWith(byte[] ar, byte[] pre) {
       if (ar.length < pre.length) {
           return false;
       }
       for (int i = 0; i < pre.length; i++) {
           if (ar[i] != pre[i]) {
               return false;
           }
       }
       return true;
   }

   /**
    * Sucht ein byte in einem byte-Array (&uuml;beraschender R&uuml;ckgabewert).
    *
    * @param ar byte-Array
    * @param b  byte
    * @return erster Index des Bytes, oder <b>ar.length, wenn es nicht existiert</b>.
    */
   private static int indexOf(byte[] ar, byte b) {
       for (int i = 0; i < ar.length; i++) {
           if (ar[i] == b) {
               return i;
           }
       }
       return ar.length;
   }
}
