package JDBCWrapper;

import java.util.*;

public class Row
{
  private Vector ordering = new Vector();
  private Hashtable hashtable = new Hashtable();

  public Row() {
  }

  public void put( String name, String value ) {
    if (!hashtable.containsKey( name))
      ordering.addElement( name );
    hashtable.put( name, value );
  }

  public int length() {
    return hashtable.size();
  }

  public String get( String name ) {
    return (String)hashtable.get( name );
  }

  public String get( int which ) {
    String key = (String)ordering.elementAt( which );
    return (String)hashtable.get( key );
  }

  public String getKey( int which ) {
    String key = (String)ordering.elementAt( which );
    return key;
  }

  public void dump() {
    for (Enumeration e=hashtable.keys(); e.hasMoreElements();) {
      String name = (String)e.nextElement();
      String value = (String)hashtable.get( name );
      System.out.print( name+"="+value+", " );
    }
    System.out.println( "" );
  }
}
