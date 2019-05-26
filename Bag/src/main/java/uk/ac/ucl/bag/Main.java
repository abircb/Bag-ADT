package uk.ac.ucl.bag;

import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * Example code illustrating the use of Bag objects. Run to see all methods.
 */
public class Main
{
  private BagFactory<String> factory = BagFactory.getInstance();

  public void print(Bag<String> bag)
  {
    boolean first = true;
    System.out.print("{");
    for (String value : bag)
    {
      if (!first) { System.out.print(" , "); }
      first = false;
      System.out.print(value);
    }
    
    System.out.println("}");
  }
  

  public void printAll(Bag<String> bag)
  {
    boolean first = true;
    System.out.print("{");
    Iterator<String> allIterator = bag.allOccurrencesIterator();
    while (allIterator.hasNext())
    {
      if (!first) { System.out.print(" , "); }
      first = false;
      System.out.print(allIterator.next());
    }
    System.out.println("}");
  }

  /**
   * Run this method to see all added methods.
   * @throws Exception
   */
  public void go() throws Exception
  {
    factory.setBagClass("LinkedListBag");

    try
    {
      Bag<String> bag1;
      Bag<String> bag2;
      Bag<String> bag3;

      bag1 = factory.getBag();
      bag1.add("abc");
      bag1.add("def");
      bag1.add("hij");
      System.out.print("bag1 all unique:             ");
      print(bag1);
      System.out.print("bag1 all:                    ");
      printAll(bag1);
      System.out.println("bag1 toString: " + bag1.toString() + "\n");
     
      bag2 = factory.getBag();
      bag2.addWithOccurrences("def", 3);
      System.out.println("bag2 size: " + bag2.size());
      System.out.print("bag2 all unique:             ");
      print(bag2);
      System.out.print("bag2 all:                    ");
      printAll(bag2);
      System.out.println("bag2 toString: " + bag2.toString() + "\n");

      bag3 = factory.getBag();
      bag3.addWithOccurrences("xyz", 5);
      bag3.add("opq");
      bag3.addWithOccurrences("123", 3);
      System.out.println("bag3 size: " + bag3.size());
      System.out.print("bag3 all unique:             ");
      print(bag3);
      System.out.print("bag3 all:                    ");
      printAll(bag3);
      System.out.println("bag3 toString: " + bag3.toString() + "\n");
      
      System.out.print("createMergedAllOccurrences (bag1 and bag3):  ");
      Bag<String> bag4 = bag1.createMergedAllOccurrences(bag3);
      printAll(bag4);

      System.out.print("createMergedAllUnique(bag1 and bag3):       ");
      Bag<String> bag5 = bag1.createMergedAllUnique(bag3);
      print(bag5);
      
      System.out.println("\nQuestion4:");
      System.out.println("Removing all copies in bag3:");
      bag3.removeAllCopies();
      System.out.println("bag3 toString: " + bag3.toString() + "\n");
      
      System.out.println("Question5:");
      System.out.println("Subtracting bag2 from bag1: ");
      bag1 = bag1.subtract(bag2);
      System.out.println("bag1 toString: " + bag1.toString() + "\n");
      
      System.out.println("Question 6:");
      Bag<Boolean> bag6 = BagFactory.getInstance().getBag();
      bag6.addWithOccurrences(true, 4);
      bag6.addWithOccurrences(false, 2);
      bag6.add(true);
      System.out.println("bag6 toString: " + bag6.toString() + "\n");
     
      System.out.println("Adding bag6 to file");
      bag6.toFile();
      
      System.out.println("\nRecreating bag7 from bag6 text-file");
      System.out.println("Enter the filename you entered for the bag6 text-file");
      String fileName = new Scanner(System.in).nextLine() + ".txt";
      File bag6_File = new File(fileName);
      Bag<?> bag7 = BagFactory.getInstance().getBag().readFile(bag6_File);
      System.out.println("bag7 toString: " + bag7.toString() + "\n");
     
      System.out.println("Recreating bag object from existing file- bag8.txt");
      File bag8_file = new File("./bag8.txt");
      Bag<?> bag8 = BagFactory.getInstance().getBag().readFile(bag8_file);
      System.out.println("bag8 toString: " + bag8.toString() + "\n");
      
     for(int i = 123; i < 127; i++) {
    	 System.out.print("[");
     }
    }

    catch (BagException e)
    {
      System.out.println("====> Bag Exception thrown...");
    }
  }

  public static void main(String[] args) throws Exception
  {	
    new Main().go();
  }
}