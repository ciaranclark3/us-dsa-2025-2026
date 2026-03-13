package dsa.lab06.solutions;

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
    // NOTE: Find where we'd like to put the item.
    int idealIndex = this.hashFunction.hash(newItem.key());

    // NOTE: Find where we can actually put the item.
    int slotCount = this.items.length;
    int availableIndex = -1;
    for (int offset = 0; offset < slotCount; offset++)
    {
      int index = (idealIndex + offset) % slotCount;
      MapItem<Key, Value> item = this.items[index];

      // NOTE: If we find a never-used slot, we need look no further.
      // NOTE: If there was an item with the same key, we would have found it.
      // NOTE: If we didn't find a previously-used slot, we'll insert here.
      if (item == null)
      {
        if (availableIndex == -1)
        {
          availableIndex = index;
        }
        break;
      }

      // NOTE: The first time we find a previously-used slot, note it down as
      //       where we'll insert if we don't find an item with the same key.
      if (item == this.REMOVED)
      {
        if (availableIndex == -1)
        {
          availableIndex = index;
        }
      }
      // NOTE: If we do find an item with the same key, simply overwrite it.
      else if (newItem.key().equals(item.key()))
      {
        this.items[index] = newItem;
        return;
      }
    }

    // NOTE: If we get to this point, we didn't find an item with the same key
    //       so are going to have to do a "proper" insertion, i.e. fill an
    //       additional slot.

    // NOTE: If inserting would make us overfull, then double the capacity and
    //       try again.
    if ((float) (this.size + 1) / slotCount > this.maxLoadFactor)
    {
      this.resize(slotCount * 2);
      this.insert(newItem);
    }
    // NOTE: If there's sufficient space, put the item in the slot we found.
    else
    {
      this.items[availableIndex] = newItem;
      this.size++;
    }
  }


  @Override
  public MapItem<Key, Value> find(Key key)
    throws NoSuchElementException
  {
    // NOTE: Find where an item with the given key would ideally be.
    int idealIndex = this.hashFunction.hash(key);

    // NOTE: Start looking from there.
    int itemsSize = this.items.length;
    for (int offset = 0; offset < itemsSize; offset++)
    {
      int index = (idealIndex + offset) % itemsSize;
      MapItem<Key, Value> item = this.items[index];

      // NOTE: If we find a never-used slot before finding an item with the
      //       given key, then there cannot be such an item in the map.
      if (item == null)
      {
        break;
      }

      // NOTE: If we find an item with the given key, return the item.
      if (key.equals(item.key()))
      {
        return item;
      }
    }

    // NOTE: If we get to here, then we didn't find an item with the given key,
    //       as if we had, we would have returned it.
    throw new NoSuchElementException();
  }


  @Override
  public MapItem<Key, Value> remove(Key key)
    throws NoSuchElementException
  {
    int idealIndex = this.hashFunction.hash(key);

    int slotCount = this.items.length;
    for (int offset = 0; offset < slotCount; offset++)
    {
      int index = (idealIndex + offset) % slotCount;
      MapItem<Key, Value> item = this.items[index];

      if (item == null)
      {
        break;
      }

      // NOTE: If we find the item, mark the slot as having had an item removed.
      // NOTE: If the removal means we're underfull, reduce the capacity.
      // NOTE: (We make sure, however, that the capacity is always >= 1.)
      if (key.equals(item.key()))
      {
        this.items[index] = this.REMOVED;
        this.size--;
        if ((float) this.size / slotCount < this.maxLoadFactor / 4)
        {
          this.resize(Math.max(1, slotCount / 2));
        }
        return item;
      }
    }

    throw new NoSuchElementException();
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
