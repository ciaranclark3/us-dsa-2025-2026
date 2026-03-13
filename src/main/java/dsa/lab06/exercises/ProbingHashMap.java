package dsa.lab06.exercises;

import dsa.lab04.base.Map;
import dsa.lab04.base.MapItem;
import dsa.lab05.solutions.HashFunction;
import dsa.lib.DSAObject;
import dsa.lib.Iterators;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A (linearly-)probing hash map.
 *
 * @param <Key>   the key type
 * @param <Value> the value type
 */
public class ProbingHashMap<Key, Value>
  extends DSAObject
  implements Map<Key, Value>
{

  /** A special marker item used to indicate a slot is now free, but wasn't. */
  private final MapItem<Key, Value> REMOVED = new MapItem<>(null, null);


  /** The array of items. */
  private MapItem<Key, Value>[] items;
  // NOTE: Indexing into this array will give: null, if there isn't and hasn't
  //       been an item there; REMOVED, if one was there but now isn't; or an
  //       actual item, if there currently is one there.


  /** The hash function we will use to determine items' indices in our array. */
  private HashFunction hashFunction;


  /** The number of actual items in our array. */
  private int size = 0;


  /** The max our load factor can be before we should grow the array. */
  private float maxLoadFactor = 0.8f;
  // NOTE: The load factor is the proportion of array slots that are filled.
  // NOTE: This maximum is lower for probing than for chaining hash maps.
  // NOTE: Chaining hash maps often have load factors > 1, which is impossible
  //       in probing hash maps, as there can't be more items than slots.


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty probing hash map.
   */
  @SuppressWarnings("unchecked")
  public ProbingHashMap()
  {
    this.items = (MapItem<Key, Value>[]) new MapItem[1];
    this.hashFunction = new HashFunction(1);
  }


  /**
   * Construct a probing hash map containing the given items.
   *
   * @param items the items
   */
  public ProbingHashMap(Iterable<MapItem<Key, Value>> items)
  {
    this();
    for (MapItem<Key, Value> item : items)
    {
      this.insert(item);
    }
  }


  /**
   * Construct a probing hash map containing the given items
   * more efficiently than {@link #ProbingHashMap(Iterable)}.
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  @SuppressWarnings("unchecked")
  public ProbingHashMap(Iterable<MapItem<Key, Value>> items, int size)
    throws IllegalArgumentException
  {
    int capacity =
      Math.max(1, (int) Math.ceil((float) size / this.maxLoadFactor));
    this.items = (MapItem<Key, Value>[]) new MapItem[capacity];
    this.hashFunction = new HashFunction(capacity);
    int itemsSize = 0;
    for (MapItem<Key, Value> item : items)
    {
      this.insert(item);
      itemsSize++;
    }
    if (itemsSize != size)
    {
      throw new IllegalArgumentException();
    }
  }


  /**
   * Construct a probing hash map containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public ProbingHashMap(MapItem<Key, Value>... items)
  {
    this(Arrays.asList(items), items.length);
  }


  //</editor-fold>


  @Override
  public int size()
  {
    return this.size;
  }


  /**
   * Resize the backing array, and clear any {@code REMOVED} markers.
   *
   * @param slotCount the new value of @code{this.items.length}
   */
  @SuppressWarnings("unchecked")
  private void resize(int slotCount)
  {
    // NOTE: Save a reference to the old array.
    MapItem<Key, Value>[] oldItems = this.items;

    // NOTE: Create the new array.
    this.items = (MapItem<Key, Value>[]) new MapItem[slotCount];

    // NOTE: Create a new hash function for the new slot count.
    this.hashFunction = new HashFunction(slotCount);

    // NOTE: Re-insert all the items.
    this.size = 0;
    for (MapItem<Key, Value> item : oldItems)
    {
      if (item != null && item != this.REMOVED)
      {
        this.insert(item);
      }
    }
  }


  @Override
  public void insert(MapItem<Key, Value> newItem)
  {
    // TODO: Implement ProbingHashMap.insert(MapItem newItem)
  }


  @Override
  public MapItem<Key, Value> find(Key key)
    throws NoSuchElementException
  {
    // TODO: Implement ProbingHashMap.find(Key key)
    return null;
    // NOTE: If you find that no item has the given key, then write:
    //       throw new NoSuchElementException();
  }


  @Override
  public MapItem<Key, Value> remove(Key key)
    throws NoSuchElementException
  {
    // TODO: Implement ProbingHashMap.remove(Key key)
    return null;
    // NOTE: If you find that no item has the given key, then write:
    //       throw new NoSuchElementException();
  }


  //<editor-fold defaultstate="collapsed" desc="Iteration">


  @Override
  public Iterable<MapItem<Key, Value>> items()
  {
    return Iterators.filter(
      (item) -> item != null && item != this.REMOVED,
      Arrays.asList(this.items));
  }


  //</editor-fold>

}
