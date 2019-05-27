package uk.ac.ucl.bag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class implements methods common to all concrete bag implementations
 * but does not represent a complete bag implementation.<br />
 *
 * New bag objects are created using a BagFactory, which can be configured in the application
 * setup to select which bag implementation is to be used.
 */

public abstract class AbstractBag<T extends Comparable> implements Bag<T>
{   
  
  private BagFactory factory = BagFactory.getInstance();

    @Override
    public String toString() {
    if(this.isEmpty()) return "[Empty Bag]";
    
    boolean first = true;
    String result = "[";
      
    for(T value : this) {
      if(!first) result += " , ";
      first = false;
      result += (value + ": " + this.countOf(value) + " occurence");
      
      if(this.countOf(value) != 1) result += "s";
    }
    return result + "]";
  }
  
  /**
   * removes all copies/duplicates from the bag
   */
  public void removeAllCopies() {
    for(T value : this) {
      if(this.countOf(value) > 1) {
        while(this.countOf(value) != 1) this.remove(value);
      }
  }
  
  public Bag<T> createMergedAllOccurrences(Bag<T> b) throws BagException {
      Bag<T> result = BagFactory.getInstance().getBag();
      for (T value : this)
      {
        result.addWithOccurrences(value, this.countOf(value));
      }
      for (T value : b)
      {
        result.addWithOccurrences(value, b.countOf(value));
      }
      return result;
   }

  public Bag<T> createMergedAllUnique(Bag<T> b) throws BagException {
      Bag<T> result = BagFactory.getInstance().getBag();
      for (T value : this)
      {
        if (!result.contains(value)) result.add(value);
      }
      for (T value : b)
      {
        if (!result.contains(value)) result.add(value);
      }
      return result;
  }
  
  public Bag<T> subtract(Bag<T> bag) throws BagException {
    if(this.isEmpty()) return this;
    Bag<T> result = this.getCopy();
    
    for(T value : result) {
      int count = 0;
      if(result.contains(value) && bag.contains(value)) {
        count = result.countOf(value) - bag.countOf(value);
        if(count >= 0) {
          for(int i = result.countOf(value); i > count; i--) result.remove(value);
        }
        else {
          for(int j = result.countOf(value); j > 0; j--) result.remove(value);
        }
      }
    }
    return result;
  }
  
  /**
   * @return a copy of <i> this </i> bag
   * @throws BagException
   */
  private Bag<T> getCopy() throws BagException{
    Bag<T> bagCopy = factory.getBag();    
    for(T value : this) bagCopy.addWithOccurrences(value, this.countOf(value));
    return bagCopy;
  }
  
  public void toFile() throws IOException {
    System.out.println("Enter the filename (without the .txt extension)");
    String filename = new Scanner(System.in).nextLine();
    File file = createFile(filename);
    
    if(file.createNewFile()) System.out.println("File succesfully created.");
    else {
      System.out.println("A problem occured- file name already exists.");
      return;
    }
    
    FileWriter writer = new FileWriter(file);
    
    //Only loops through once to write the file type
    for(T value : this) {
      
           if(value instanceof Character) writer.write("Character" + newline);
      else if(value instanceof String)    writer.write("String"  + newline); 
      else if(value instanceof Byte)      writer.write("Byte"    + newline);
      else if(value instanceof Short)     writer.write("Short"   + newline);
      else if(value instanceof Integer)   writer.write("Integer" + newline); 
      else if(value instanceof Long)      writer.write("Long"    + newline);
      else if(value instanceof Float)     writer.write("Float" + newline);
      else if(value instanceof Double)    writer.write("Double" + newline); 
      else if(value instanceof Boolean)   writer.write("Boolean" + newline);
      else {
        System.out.println("Invalid ");
        return;
      }
      
      break;
    }
    
    for(T value : this) {
      for(int i = 0; i < this.countOf(value); i++) writer.write(value.toString() + newline);
    }
    writer.close();
  }
  
  /**
   * A private method used to create a file
   * @param fileName
   * @return file of the name- fileName
   * @throws IOException
   */
  private File createFile(String fileName) throws IOException {
    File file = new File(fileName + ".txt");
    return file;
  }
  
  public Bag<?> readFile(File file) throws IOException, BagException {
    String line;
    String path = file.getAbsolutePath();
    Bag<?> fileBag = factory.getBag();

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String type = br.readLine();
      line = br.readLine();
      
      switch(type) {
        case "Character": {
          Bag<Character> bag = factory.getBag();
          while(line != null) {
            bag.add(line.charAt(0));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "String": {
          Bag<String> bag = factory.getBag();
          while(line != null) {
            bag.add(line);
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "Byte": {
          Bag<Byte> bag = factory.getBag();
          while(line != null) {
            bag.add(Byte.parseByte(line));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "Short": {
          Bag<Short> bag = factory.getBag();
          while(line != null) {
            bag.add(Short.parseShort(line));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "Integer": {
          Bag<Integer> bag = factory.getBag();
          while(line != null) {
            bag.add(Integer.parseInt(line));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "Long": {
          Bag<Long> bag = factory.getBag();
          while(line != null) {
            bag.add(Long.parseLong(line));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "Float": {
          Bag<Float> bag = factory.getBag();
          while(line != null) {
            bag.add(Float.parseFloat(line));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "Double": {
          Bag<Double> bag = factory.getBag();
          while(line != null) {
            bag.add(Double.parseDouble(line));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        case "Boolean": {
          Bag<Boolean> bag = factory.getBag();
          while(line != null) {
            bag.add(Boolean.parseBoolean(line));
            line = br.readLine();
          }
          fileBag = bag;
          break;
        }
        
        default: return null;
      }
    }
    
    catch (IOException ioe) {
            System.out.println(ioe.getMessage());
    }
    catch(BagException e) {
      System.out.println(new BagException("Attempting to create a bag with greater than " 
                        + MAX_SIZE + "elements").getMessage());
    }
    return fileBag;
  }
}
