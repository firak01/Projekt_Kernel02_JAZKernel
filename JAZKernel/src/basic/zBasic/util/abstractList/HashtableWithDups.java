package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * A Hashtable that allows duplicate keys.
 * It needs to be fleshed out with some constructors for
 * actual use. This version is not thread-safe.
 * The values you store may not be arrays or ArrayLists.
 * To bypass that restriction, you would need private container classes, which
 * would get rid of the need for chained instanceofs.
 */
public class HashtableWithDups extends Hashtable
   {

   /**
    * Associate yet another value with a key in a Hashtable that allows duplicates.
    * Also use to put the first key/value.
    *
    * @param key Key to lookup by
    * @param value yet another associated value for this key.
    */
   public void putDuplicate ( Object key , Object value )
      {
      // duplicate values are stored as single objects, pairs in an array or
      // multiples in an ArrayList
      // Why not just use ArrayLists for everything? To conserve RAM.
      Object existing = get( key );
      if ( existing == null )
         {
         put ( key, value );
         }
      else if ( existing instanceof Object[] )
         {
         // was a pair in array, make into an ArrayList of 3.
         ArrayList a = new ArrayList();
         a.addAll ( Arrays.asList( (Object[]) existing ));
         a.add ( value );
         // the entire ArrayList goes into the Hashtable as a single key
         // replacing the array
         put ( key, a );
         }
      else if ( existing instanceof ArrayList )
         {
         // just add to tail end of existing ArrayList
         ( (ArrayList)existing ).add( value );
         }
      else
         {
         /* was a single object. Make into a pair, replacing original single */
         put ( key, new Object[] { existing, value } );
         }
      }

/**
* Get from a Hashtable that allows duplicates
*
* @param key Key to lookup by
* @return array of values associated with this key.
* Note the result is an Object[] array not String[]
* array, even if contents were Strings.
* Null if none found.
* Returns values in same order they were inserted.
*/
   public Object[] getDuplicates ( Object key )
      {
      // values are stored as single objects, pairs in an array or
      // multiples in an ArrayList
      Object match = get( key );
      if ( match == null )
         {
         return null;
         }
      else if ( match instanceof Object[] )
         {
         return (Object[])match;
         }
      else if ( match instanceof ArrayList )
         {
         ArrayList a = (ArrayList) match;
         return a.toArray ( new Object[a.size()] );
         }
      else
         {
         /* was a single object */
         return new Object[] { match };
         }
      }
   }
