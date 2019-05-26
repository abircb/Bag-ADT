# Bag ADT
A [Bag](http://pages.cs.wisc.edu/~hasti/cs367/examples/BagADT/BagADThandout.html) data structure stores an unordered collection of values and allows multiple copies of the same value. Bags know how many occurrences (i.e., count) of each distinct value are in its collection. Adding a member increases the number of instances in the bag by one and removing a member decreases the number of instances in the bag by one. The member is deleted from the bag when it has no instances.

In this implementation of a Bag ADT, a bag is of a `Comparable` type and is homogenous. Its operations include:

<ol>
  <li><code>add()</code></li>
  <li><code>addWithOccurences()</code></li>
  <li><code>contains()</code></li>
  <li><code>remove()</code></li>
  <li><code>size()</code></li>
  <li><code>isEmpty()</code></li>
  <li><code>iterator()</code></li>
  <li><code>toString()</code></li>
  <li><code>removeAllCopies()</code></li>
  <li><code>subtract()</code></li>
</ol>

The Bag has been implemented using a dictionary(HashMap), a Linked list and an array. 

## Usage
The final build is a Maven project. Clone this repo and use your IDE to process the Maven .pom file. This may take a little while as the IDE will download all the dependencies needed. Experiment with the ADT and run `main` to understand its current functionality.
