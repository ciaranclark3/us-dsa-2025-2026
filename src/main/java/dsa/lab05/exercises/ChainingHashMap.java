package dsa.lab05.exercises;

import dsa.lab04.base.Map;
import dsa.lab04.base.MapItem;
import dsa.lab04.solutions.ArrayMap;
import dsa.lab05.solutions.HashFunction;
import dsa.lib.DSAObject;
import dsa.lib.Iterators;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A chaining hash map.
 *
 * @param <Key>   the key type
 * @param <Value> the value type
 */
public class ChainingHashMap<Key, Value>
  extends DSAObject
  implements Map<Key, Value>
{

  /** The array of chains/buckets. */
  private ArrayMap<Key, Value>[] chains;
  // NOTE: Each ArrayMap in the array contains items with the same hash (mod N).


  /** The hash function used for assigning items to chains. */
  private HashFunction hashFunction;
  // NOTE: We will maintain the invariant that
  //         hashFunction.size() == chains.length
  //       This means that
  //         hashFunction.hash(item)
  //       will always return a valid chain index.


  /** The number of contained items. */
  private int size = 0;


  /** If the load factor exceeds this, resize() to add more chains. */
  private float maxLoadFactor = 2;
  // NOTE: The load factor is the average number of items per chain.
  // NOTE: This maximum is higher for chaining rather than probing hash maps.
  // NOTE: (In a probing hash map, a load factor >= 2 > 1 isn't even possible!)


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty chaining hash map.
   */
  @SuppressWarnings("unchecked")
  public ChainingHashMap()
  {
    this.chains =
      (ArrayMap<Key, Value>[]) new ArrayMap[1];
    this.chains[0] = new ArrayMap<>();
    this.hashFunction = new HashFunction(1);
  }


  /**
   * Construct a chaining hash map containing the given items.
   *
   * @param items the items
   */
  public ChainingHashMap(Iterable<MapItem<Key, Value>> items)
  {
    this();
    for (MapItem<Key, Value> item : items)
    {
      this.insert(item);
    }
  }


  /**
   * Construct a chaining hash map containing the given items
   * more efficiently than {@link #ChainingHashMap(Iterable)}.
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  @SuppressWarnings("unchecked")
  public ChainingHashMap(Iterable<MapItem<Key, Value>> items, int size)
    throws IllegalArgumentException
  {
    int chainCount =
      Math.max(1, (int) Math.ceil((float) size / this.maxLoadFactor));
    this.chains =
      (ArrayMap<Key, Value>[]) new ArrayMap[chainCount];
    for (int chainIndex = 0; chainIndex < chainCount; chainIndex++)
    {
      this.chains[chainIndex] = new ArrayMap<>();
    }
    this.hashFunction = new HashFunction(chainCount);
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
   * Construct a chaining hash map containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public ChainingHashMap(MapItem<Key, Value>... items)
  {
    this(Arrays.asList(items), items.length);
  }


  //</editor-fold>


  @Override
  public int size()
  {
    return this.size;
  }


  @Override
  public MapItem<Key, Value> find(Key key)
    throws NoSuchElementException
  {
    if(this.chains[this.hashFunction.hash(key)].isEmpty()){
      throw new NoSuchElementException();
    }

    return this.chains[this.hashFunction.hash(key)].find(key);
  }


  /**
   * Change the number of chains/buckets in this hash map.
   * <p>
   * This should be called when the load factor is above the maximum or below
   * the minimum (to, respectively, increase or decrease the number of chains).
   * 
   * @param chainCount the new length of the chains array
   */
  @SuppressWarnings("unchecked")
  private void resize(int chainCount)
  {
    ArrayMap<Key, Value>[] oldChains = this.chains;
    this.chains = (ArrayMap<Key, Value>[]) new ArrayMap[chainCount];
    for (int i = 0; i < chainCount; i++)
    {
      this.chains[i] = new ArrayMap<>();
    }
    this.hashFunction = new HashFunction(chainCount);
    this.size = 0;
    for (ArrayMap<Key, Value> chain : oldChains)
    {
      for (MapItem<Key, Value> item : chain)
      {
        this.insert(item);
      }
    }
  }


  @Override
  public void insert(MapItem<Key, Value> item)
  {
    // TODO: Implement ChainingHashMap.insert(Item item)
  }


  @Override
  public MapItem<Key, Value> remove(Key key)
    throws NoSuchElementException
  {
    // TODO: Implement ChainingHashMap.remove(Key key)
    return null;
  }


  //<editor-fold defaultstate="collapsed" desc="Iteration">


  @Override
  public Iterable<MapItem<Key, Value>> items()
  {
    return Iterators.flatten(Arrays.asList(this.chains));
  }


  //</editor-fold>

}
