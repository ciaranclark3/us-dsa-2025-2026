package dsa.lab03.exercises;

import dsa.lab02.base.DynamicSequence;
import dsa.lib.DSAObject;

import java.util.Arrays;

/**
 * A circular dynamic array.
 * <p>
 * A dynamic sequence implemented using an array
 * with spare capacity and variable start index.
 * <p>
 * Dynamic operations only rarely reallocate a new array.
 * <p>
 * Improves on non-circular dynamic arrays' efficiencies with
 * {@link #insertFirst(Object)} and {@link #removeFirst()}
 * having (amortised) asymptotic complexity O(1).
 *
 * @param <Item> the item type
 */
public class CircularDynamicArray<Item>
  extends DSAObject
  implements DynamicSequence<Item>
{

  /** The backing array. */
  private Item[] items;


  /** The index of the first item (or where it will be if currently empty). */
  private int start = 0;


  /** The number of contained items. */
  private int size;


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty circular dynamic array.
   */
  @SuppressWarnings("unchecked")
  public CircularDynamicArray()
  {
    this.items = (Item[]) new Object[0];
    this.size = 0;
  }


  /**
   * Construct a circular dynamic array containing the given items.
   *
   * @param items the items
   */
  public CircularDynamicArray(Iterable<Item> items)
  {
    this();
    for (Item item : items)
    {
      this.insertLast(item);
    }
  }


  /**
   * Construct a circular dynamic array containing the given items
   * more efficiently than {@link #CircularDynamicArray(Iterable)}.
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  @SuppressWarnings("unchecked")
  public CircularDynamicArray(Iterable<Item> items, int size)
    throws IllegalArgumentException
  {
    if (size < 0)
    {
      throw new IllegalArgumentException();
    }
    this.items = (Item[]) new Object[size];
    this.size = size;
    int index = 0;
    for (Item item : items)
    {
      this.items[index++] = item;
    }
    if (index != size)
    {
      throw new IllegalArgumentException();
    }
  }


  /**
   * Construct a circular dynamic array containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public CircularDynamicArray(Item... items)
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
   * Get the maximum number of items that can be contained without reallocation.
   *
   * @return the capacity
   */
  public int capacity()
  {
    return this.items.length;
  }


  /*there isn't one*
   * Return the backing array index of the given logical index.
   * <p>
   * {@code index} is the external index users might call {@link #get} with.
   * The returned index is used internally in this class's implementation,
   * and would be used to look up the corresponding item in {@code this.items}.
   *
   * @param index the logical index
   * @return the backing array index
   */
  private int index(int index)
  {
    // NOTE: You don't _have_ to implement this method, but you'll likely find
    //       it very useful for implementing the other methods in this class.
    if(this.capacity() == 0){
      return -1;
    }
    if(this.size == 1){
      return 0;
    }

    return (this.capacity() + this.start + index) % this.capacity();
  }


  @Override
  public Item get(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }

    Item itemToReturn = this.items[this.index(index)];
    return itemToReturn;
  }


  @Override
  public void set(int index, Item item)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }

    this.items[this.index(index)] = item;

  }


  /**
   * Resize the backing array.
   * <p>
   * Allocates a new array with the given capacity, copies the items over to it,
   * and sets that as the backing array.
   * <p>
   * Assumes that {@code capacity} is at least {@code size()}.
   *
   * @param capacity the new capacity
   */
  @SuppressWarnings("unchecked")
  private void resize(int capacity)
  {
    // NOTE: Save a reference to the old backing array and start index.
    Item[] oldItems = this.items;
    int oldStart = this.start;

    // NOTE: Allocate a new array with the desired capacity and reset start.
    this.items = (Item[]) new Object[capacity];
    this.start = 0;

    // NOTE: Copy the items across.
    int oldCapacity = oldItems.length;
    for (int i = 0; i < this.size; i++)
    {
      this.items[i] = oldItems[(oldCapacity + oldStart + i) % oldCapacity];
    }
  }


  @Override
  public void insert(int index, Item item)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index > this.size)
    {
      throw new IndexOutOfBoundsException();
    }

    //resize if needed
    if(this.capacity() == 0){
      this.resize(1);
    }
    else if(this.size == this.capacity()) {
      this.resize(this.capacity() * 2);
    }

    this.size++;
    for (int i = this.size - 2; i >= this.index(index); i--)
    {
      this.set(this.index(i+1), this.get(this.index(i)));
    }
    this.set(this.index(index), item);

  }


  @Override
  public Item remove(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }

    Item itemToReturn = this.get(this.index(index));

    for (int i = index; i < this.index(this.size - 1); i++)
    {
      this.set(this.index(i), this.get(this.index(i + 1)));
    }

    this.size--;

    //resize if needed
    if(this.size * 4 <= this.capacity()) {
      this.resize(this.capacity() / 2);
    }

    return itemToReturn;
  }

}
