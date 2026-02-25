package dsa.lab04.exercises;

import dsa.lab02.base.StaticSequence;
import dsa.lab02.exercises.StaticArray;
import dsa.lab03.exercises.DynamicArray;
import dsa.lab04.base.Sorter;

import java.util.Comparator;

/**
 * Merge sort.
 */
public class MergeSorter
  implements Sorter
{

  @Override
  public <Item> void sort(
    StaticSequence<Item> items,
    Comparator<Item> comparator)
  {
    if (items.size() <= 1){
      return;
    }
    int nSize = items.size();
    int midpoint = nSize/2;

    DynamicArray<Item> itemsL = new DynamicArray<>();
    DynamicArray<Item> itemsR = new DynamicArray<>();

    for (int i = 0; i < midpoint ; i++)
    {
      // copy first half in itemsL with n/2 calls to itemsL.insertLast
      itemsL.insertLast(items.get(i));
    }
    for (int i = midpoint; i < nSize; i++)
    {
      // copy second half in itemsR with n/2 calls to itemsR.insertLast
      itemsR.insertLast(items.get(i));
    }

    this.sort(itemsL, comparator);
    this.sort(itemsR, comparator);

    int indexL = 0;
    int indexR = 0;

    for (int i = 0; i < nSize; i++)
    {
      if((indexR >= nSize - midpoint) || ((indexL < midpoint) && comparator.compare(itemsL.get(indexL), itemsR.get(indexR)) <= 0)){
        items.set(i, itemsL.get(indexL));
        indexL++;
      }
      else {
        items.set(i, itemsR.get(indexR));
        indexR++;
      }
    }
  }
}
