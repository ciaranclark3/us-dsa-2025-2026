package dsa.lab04.exercises;

import dsa.lab02.base.StaticSequence;
import dsa.lab04.base.Sorter;

import java.util.Comparator;

/**
 * Selection sort.
 */
public class SelectionSorter
  implements Sorter
{

  @Override
  public <Item> void sort(
    StaticSequence<Item> items,
    Comparator<Item> comparator)
  {

    if(items.size() <= 1){
      return;
    }

    for (int i = 0; i < items.size(); i++) {

      Item smallestSoFar = items.get(i);
      int indexOfSmallestSoFar = i;

      for (int j = i; j < items.size(); j++) {
        if(0 < comparator.compare(smallestSoFar, items.get(j))){
          smallestSoFar = items.get(j);
          indexOfSmallestSoFar = j;
        }
      }

      items.swap(i, indexOfSmallestSoFar);
    }
  }

}
