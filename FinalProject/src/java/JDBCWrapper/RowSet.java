package JDBCWrapper;

import java.util.*;

public class RowSet
{
  private Vector vector = new Vector();

  public RowSet() {
  }

  public void add( Row row ) {
    vector.addElement( row );
  }

  public int length() {
    return vector.size();
  }

  public Row get( int which ) {
    return (Row)vector.elementAt( which );
  }
  
  public void mergeRowSet(RowSet rows)
  {
  	for(int i=0; i<rows.length(); i++) {
  		
  		vector.addElement(rows.get(i));
  	}
  }

  public void dump() {
    for (Enumeration e=vector.elements(); e.hasMoreElements();) {
      ((Row)e.nextElement()).dump();
    }
  }
}
