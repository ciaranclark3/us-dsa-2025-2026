package dsa.lab04.exercises;

import dsa.lab03.solutions.DynamicArray;
import dsa.lab04.base.Map;
import dsa.lab04.base.MapItem;
import dsa.lib.DSAObject;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * An (unsorted) array map.
 * <p>
 * A map implemented using a dynamic array of key-value items.
 * <p>
 * {@link #find}, {@link #insert} and {@link #remove} are all O({@code n}),
 * where {@code n} is the size.
 *
 * @param <Key>   the key type
 * @param <Value> the value type
 */
public class ArrayMap<Key, Value>
  extends DSAObject
  implements Map<Key, Value>
{

  /** The DynamicArray we're implementing the map in terms of. */
  private DynamicArray<MapItem<Key, Value>> items;


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty array map.
   */
  public ArrayMap()
  {
    this.items = new DynamicArray<>();
  }


  /**
   * Construct an array map containing the given items.
   *
   * @param items the items
   */
  public ArrayMap(Iterable<MapItem<Key, Value>> items)
  {
    this.items = new DynamicArray<>(items);
  }


  /**
   * Construct an array map containing the given items
   * more efficiently than {@link #ArrayMap(Iterable)}.
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  public ArrayMap(Iterable<MapItem<Key, Value>> items, int size)
    throws IllegalArgumentException
  {
    this.items = new DynamicArray<>(items, size);
  }


  /**
   * Construct an array map containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public ArrayMap(MapItem<Key, Value>... items)
  {
    this(Arrays.asList(items));
  }


  //</editor-fold>


  @Override
  public int size()
  {
    return this.items.size();
  }


  /**
   * Get the index of the item with the given key.
   * <p>
   * If no item has the given key, returns -1.
   *
   * @param key the key to find the index of
   * @return the index of {@code key} (or {@code -1} if there isn't one)
   */
  private int indexOf(Key key)
  {

    int i = 0;
    for(MapItem<Key, Value> item : this.items){

      if(item.key() == key){ return i; }
      i++;
    }

    return -1;
  }


  @Override
  public MapItem<Key, Value> find(Key key)
    throws NoSuchElementException
  {
    // NOTE: Find the index of the item with the given key.
    int index = this.indexOf(key);

    // NOTE: If there is no such item, throw an exception to say so.
    if (index == -1)
    {
      throw new NoSuchElementException();
    }

    // NOTE: Return the item at that index (which has the given key).
    return this.items.get(index);
  }


  @Override
  public void insert(MapItem<Key, Value> item)
  {
    // NOTE: Find the index of the item with the given key.
    int index = this.indexOf(item.key());

    // NOTE: If there is no such item, add the new item as a new item.
    // NOTE: The order of the items in the array doesn't matter.
    // NOTE: We add to the end only because we're using a DynamicArray, for
    //       which that's the most efficient place to insert a new item.
    // NOTE: If we were using a CircularDynamicArray, we could just as well add
    //       it to the start with insertFirst().
    if (index == -1)
    {
      this.items.insertLast(item);
    }

    // NOTE: If there is already an item with the given key, replace/overwrite
    //       it with the new one.
    else
    {
      this.items.set(index, item);
    }
  }


  @Override
  public MapItem<Key, Value> remove(Key key)
    throws NoSuchElementException
  {
    int index = this.indexOf(key);

    if (index == -1)
    {
      throw new NoSuchElementException();
    }

    return this.items.remove(index);
  }


  //<editor-fold defaultstate="collapsed" desc="Iteration">


  @Override
  public Iterable<MapItem<Key, Value>> items()
  {
    return this.items.items();
  }


  //</editor-fold>

}
