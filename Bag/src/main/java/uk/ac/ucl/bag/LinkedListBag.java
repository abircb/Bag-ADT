package uk.ac.ucl.bag;

import java.util.Iterator;

/**
This class implements Bags using a Linked List as the internal data structure.
*/

public class LinkedListBag<T extends Comparable> extends AbstractBag<T>{
  
    /** 
     * A static class to define an element- node- of the linked list.
     */
    private static class Element<E extends Comparable>{
      public E value;
      public int occurences;
      public Element<E> next;
    
      public Element(E value, int occurences, Element<E> next) {
        this.value = value;
        this.occurences = occurences;
        this.next = next;
      }
    }
    
  private int maxSize;
  private Element<T> head;
  
  public LinkedListBag() throws BagException {
    this(MAX_SIZE);
  }
  
  public LinkedListBag(int maxSize) throws BagException {
    if (maxSize > MAX_SIZE)
    {
      throw new BagException("Attempting to create a Bag with size greater than maximum");
    }
    if (maxSize < 1)
    {
      throw new BagException("Attempting to create a Bag with size less than 1");
    }
    
    this.maxSize = maxSize;
  }
  
  @Override
  public void add(T value) throws BagException {
    if(value == null) {
      System.out.println("Attempting to add a null value to the bag");
      return;
    }
    
    Element<T> currentNode = this.head;
    
    if(this.contains(value)) {
      while(currentNode != null) {
        if(currentNode.value.equals(value)) {
          currentNode.occurences++;
          return;
        }
        currentNode = currentNode.next;
      }
    }
    
    else {
      if(size() < maxSize) {
        Element<T> new_node = new LinkedListBag.Element<T>(value, 1, null);
          
        if(this.head == null) {
          this.head = new_node;
        }
          
        else {
          Element<T> last = this.head;
          while (last.next != null) last = last.next; 
          last.next = new_node; 
        }
      }
        
      else {
        throw new BagException("Bag is full");
      }
    }
  }
  
  @Override
  public void addWithOccurrences(T value, int occurences) throws BagException {
    for (int i = 0 ; i < occurences ; i++) add(value);
  }
  
  @Override
  public boolean contains(T value) {
    if(value == null) return false;
    
    Element<T> currentNode = this.head;
    
    while(currentNode != null) {
      if(currentNode.value.equals(value)) {
        return true;
      }
      currentNode = currentNode.next;
    }
    
    return false;
  }
  
  @Override
  public int countOf(T value) {
    if(value != null) {
      Element<T> currentNode = this.head;
      
      while(currentNode != null) {
        if(currentNode.value.equals(value)) {
          return currentNode.occurences;
        }
        currentNode = currentNode.next;
      }
    }
    return 0;
  }
  
  @Override
  public void remove(T value) {
    
    if(value == null) return;
    
    //Case-1: Empty list
    if(isEmpty()) {
      System.out.println("Attempting to remove from an empty bag.");
      return;
    }
    
    Element<T> temp = this.head, prev = null, next = null;
    
    //Case-2: When there is only one node in the list
    if((temp.next == null) && (temp.value.compareTo(value) == 0)) {         
      if(temp.occurences == 1) {
        temp.occurences = 0;
        this.head = null;
        return;
      }
      temp.occurences--;
      return;
    }
    
    
    //Case-3: When node to be deleted is at the head
    if((temp != null) && (temp.value.compareTo(value) == 0)) {          
      if(temp.occurences == 1) {
        temp.occurences = 0;
        this.head = temp.next;
        return;
      }
      temp.occurences--;
      return;
    }
    
    /* Finding the node
     * if found, then temp contains the node to be deleted and prev contains the 
     * previous node. Else, temp is null
     */
    while((temp != null) && (temp.value.compareTo(value)) != 0) {
      prev = temp;
      temp = temp.next;
    }
    
    //Case-4: Node is not present in the list
    if(temp == null) {
      System.out.println(value + " is not present in the bag");
      return;
    }
    
    //Case-5: Deleting node at the tail
    if(temp.next == null) {         
      if(temp.occurences == 1) {
        temp.occurences = 0;
        prev.next = null;
        temp = null;
        return;
      }
      temp.occurences--;
      return;
    }
    
    //Case-6: Deleting node in the middle of the list
    if(temp != null) {
      if(temp.occurences == 0) {
        prev.next = temp.next;
        return;
      }   
      
      temp.occurences--;
      return;
    }
  }
  
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }
  
  @Override 
  public int size() {
    int count = 0;
    Element<T> currentNode = this.head;
      
    while(currentNode != null) {
      count++;
      currentNode = currentNode.next;
    }
      
    return count;
  }
  
  /**
   *  The LinkedListBagUniqueIterator class implements the iterator interface to allow the unique 
   * values in MapBag objects to be iterated through.The iterator returns each unique value without 
   * any copies (i.e., one value for each element in the HashMap ). 
   */
  
  private class LinkedListBagUniqueIterator implements Iterator<T>{
    private boolean firstIteration = true;
    Element<T> node = head;
    
    @Override
    public boolean hasNext() {
      if(firstIteration && head != null) return true;
      return (node != null && node.next != null);
    }
    
    @Override
    public T next() {   
      if(firstIteration) {
        firstIteration = false;
        return head.value;
      }
      node = node.next;
      return node.value;
    }
  }
  
  @Override
  public Iterator<T> iterator(){
    return new LinkedListBagUniqueIterator();
  }
  
  private class LinkedListBagIterator implements Iterator<T>
    {
    private int count = 0;
    Element<T> node = head;
    
    @Override
    public boolean hasNext()
    {
      if (node != null) {
        if (count < node.occurences) return true;
        if ((count == node.occurences) && (node.next != null)) return true;
      }
      return false;
    }

    @Override
    public T next()
    {
      if (count < node.occurences)
      {
      T value = node.value;
      count++;
      return value;
      }
      count = 1;
      node = node.next;
      return node.value;
    }
    }

    @Override
    public Iterator<T> allOccurrencesIterator() {
      return new LinkedListBagIterator();
    }
}
