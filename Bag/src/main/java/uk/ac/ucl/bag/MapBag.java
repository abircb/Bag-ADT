package uk.ac.ucl.bag;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;


/*
This class implements Bags using a HashMap as the internal data structure.
*/

public class MapBag<V extends Comparable> extends AbstractBag<V>{
	
	private static class Element<Value extends Comparable>{
		
		public int count;
		public Value value;
		//Key will be auto-generated
		
		public Element(int count, Value value) {
			this.count = count;
			this.value = value;
		}
	}
	
	/* Keys used for the HashMap are positive integers assigned to each entry in the order they were 
	 * entered in. A single dimension array is used to store the keys and its size is the size
	 * provided by the constructor- int maxSize
	 * */
	
	private int maxSize;
	private HashMap <Integer, Element<V>> contents; 
	private int[] keys;								
	private int lastKeyUsed;
	
	
	public MapBag() throws BagException{
		this(MAX_SIZE);
	}
	
	public MapBag(int maxSize) throws BagException{
		if (maxSize > MAX_SIZE) {
			throw new BagException("Attempting to create a bag with size greater than maximum");
		}
		
		if (maxSize < 1) {
			throw new BagException("Attempting to create a bag with less than one element");
		}
		
		this.maxSize = maxSize;
		this.contents = new HashMap<>();
		this.keys = new int[maxSize];
		for(int i = 0; i < maxSize; i++) {
			this.keys[i] = i;
		}
		this.lastKeyUsed = 0;
	
	}
	@Override
	public void add(V value) throws BagException{
		
		if(value == null) {
			System.out.println("Attempting to add a null value to the bag");
			return;
		}
	
		int key = 0;
			
		for (Map.Entry<Integer, Element<V>> entry : contents.entrySet()) {
			if(entry.getValue().value.compareTo(value) == 0) {
				contents.get(key).count++;
				return;
			}
			key ++;
		}
			
		if (contents.size() < maxSize)
		   {
			key = this.lastKeyUsed++;
			contents.put(key, new MapBag.Element<V>(1, value));
		   }
			
	    else
		   {
		    throw new BagException("Bag is full");
		   }
	}
	
	@Override
	public void addWithOccurrences(V value, int occurrences) throws BagException{
	    for (int i = 0 ; i < occurrences ; i++) {
	      add(value);
	    }
	}
	
	@Override
	public boolean contains(V value) {
		
		if(value == null) return false;
		
		for (Map.Entry<Integer, Element<V>> entry : contents.entrySet()) {
			if(entry.getValue().value.compareTo(value) == 0) return true;
		}
		return false;
	}
	
	@Override
	public int countOf(V value) {
		if(value != null) {
			int key = 0;
			for (Map.Entry<Integer, Element<V>> entry : contents.entrySet()) {
				if(entry.getValue().value.compareTo(value) == 0) {
					return contents.get(key).count;
				}
				key ++;
			}
		}
		return 0;
	}
	
	@Override
	public void remove(V value)
	  {
		if(value == null) return;
		boolean found = false;
		
	    for (int i = 0 ; i < contents.size() ; i++)
	    {
	      Element<V> element = contents.get(i);
	      if (element.value.compareTo(value) == 0)
	      {
	    	found = true;
	        element.count--;
	        if (element.count == 0)
	        {
	          contents.remove(element);
	          return;
	        }
	      }
	    }
	    
	    if(!found) {
			System.out.println(value + " is not present in the bag");
	    }
	    
	  }
	
	@Override
	public boolean isEmpty() {
	    return size() == 0;
	}
	
	@Override
	public int size() {
	    return contents.size();
	
	}
	
	private class MapBagUniqueIterator implements Iterator<V>
	  {
	    private int key = 0;

		@Override
	    public boolean hasNext()
	    {
	      if (key < contents.size()) return true;
	      return false;
	    }
	    
		@Override
	    public V next()
	    {
	      return contents.get(key++).value;
	    }
	  }

	/**
	 *  The MapBagUnique Iterator class implements the iterator interface to allow the unique values 
	 * in MapBag objects to be iterated through.The iterator returns each unique value without 
	 * any copies (i.e., one value for each element in the HashMap ). 
	 */
	  @Override
	  public Iterator<V> iterator()
	  {
	    return new MapBagUniqueIterator();
	  }
	
	private class MapBagIterator implements Iterator <V>{
		
		private int key =   0;
		private int count = 0;
		
		@Override
		public boolean hasNext()
	    {
	        if (key < contents.size()) {
		        if (count < contents.get(key).count) return true;
		        if ((count == contents.get(key).count) && ((key + 1) < contents.size())) return true;
	        }
	      return false;
	    }
		
		@Override 
		public V next() {
	      if (count < contents.get(key).count)
	      {
	        V value = contents.get(key).value;
	        count++;
	        return value;
	      }
	      count = 1;
	      key++;
	      return contents.get(key).value;
	    }
		
	}
	
	@Override
	public Iterator<V> allOccurrencesIterator() {
		return new MapBagIterator();
	}	
}
