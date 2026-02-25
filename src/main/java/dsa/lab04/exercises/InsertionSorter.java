package dsa.lab04.exercises;

import dsa.lab02.base.StaticSequence;
import dsa.lab04.base.Sorter;

import java.util.Comparator;

/**
 * Insertion sort.
 */
public class InsertionSorter
  implements Sorter
{

  @Override
  public <Item> void sort(
    StaticSequence<Item> items,
    Comparator<Item> comparator)
  {
    for (int i = 1; i < items.size(); i++)
    {
      Item itemToInsert = items.get(i);
      boolean foundThePlace = false;
      int j = 0;
      while(!foundThePlace){
        if(0 > comparator.compare(itemToInsert, items.get(i-1-j))){
          items.swap((i-1-j),(i-j));
        }
        else { //greater than 0 therefore
          foundThePlace = true;
        }

        if((i-1-j) == 0){
          foundThePlace = true;
        }

        j++;
      }
    }
  }

}
