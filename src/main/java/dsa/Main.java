package dsa;

import dsa.lab01.exercises.Array;
import dsa.lab01.exercises.StringArray;
import dsa.lab02.exercises.SinglyLinkedList;
import dsa.lab03.exercises.DynamicArray;
import dsa.lab04.exercises.ArrayMap;


public class Main
{

  @SuppressWarnings({"unchecked", "RedundantSuppression"})
  public static void main(String[] args)
  {
    /*------------------------------------------------------------------------*
     | You can add code below to experiment with the data structures and      |
     | algorithms in this module when implementing and/or understanding them. |
     |                                                                        |
     | If you click the 'Run Main.java' button in the top-right - or press    |
     | Shift+F10 - IntelliJ will compile and run it, and display any output.  |
     |                                                                        |
     | If IntelliJ adds the relevant imports for you, it's worth checking     |
     | whether the model solution code or the exercise code you're working on |
     | has been imported (as the classes have the same name in each package,  |
     | e.g. StringArray in dsa.lab01.exercises and dsa.lab01.solutions).      |
     |                                                                        |
     | For pretty-printing the data structures in these labs, we provide a    |
     | toString() overrides that mean you can write                           |
     |   System.out.println(myDataStructure);                                 |
     | and it should generally print out a fairly nice string representation. |
     | (Note that it should also work well with nested data structures,       |
     | e.g. Array<StringArray>.)                                              |
     |                                                                        |
     | Note however that if you're doing with the exercise classes and there  |
     | are errors in your implementation, the output might be nonsense.       |
     |                                                                        |
     | For debugging such errors in your implementations, we also provide a   |
     | toDebugString() method that means you can write                        |
     |   System.out.println(myDataStructure.toDebugString());                 |
     | to get a representation of the state of the data structure's fields.   |
     *------------------------------------------------------------------------*/


    ArrayMap<Integer, String> myArrayMap = new ArrayMap<>();
    myArrayMap.insert(1, "A");
    myArrayMap.insert(2, "B");
    myArrayMap.insert(3, "C");
    System.out.println(myArrayMap);





  }

}
