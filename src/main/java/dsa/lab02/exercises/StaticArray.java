package dsa.lab02.exercises;

import dsa.lab02.base.DynamicSequence;
import dsa.lib.DSAObject;

import java.util.Arrays;

/**
 * A static array.
 * <p>
 * A dynamic sequence implemented using a full array
 * (i.e. with as many items as slots).
 * <p>
 * Dynamic operations always reallocate a new array and are all O({@code n})
 * (where {@code n} is the size).
 *
 * @param <Item> the item type
 */
public class StaticArray<Item>
  extends DSAObject  // NOTE: Don't worry about the `extends DSAObject`.
  implements DynamicSequence<Item>
{

  /** The backing array - the Java array this wraps and is implemented using. */
  private Item[] items;


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty static array.
   */
  @SuppressWarnings("unchecked")
  public StaticArray()
  {
    this.items = (Item[]) new Object[0];
  }


  /**
   * Construct a static array containing the given items.
   *
   * @param items the items
   */
  public StaticArray(Iterable<Item> items)
  {
    this();
    for (Item item : items)
    {
      this.insertLast(item);
    }
  }


  /**
   * Construct a static array containing the given items
   * more efficiently than {@link #StaticArray(Iterable)}.
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  @SuppressWarnings("unchecked")
  public StaticArray(Iterable<Item> items, int size)
    throws IllegalArgumentException
  {
    if (size < 0)
    {
      throw new IllegalArgumentException();
    }
    this.items = (Item[]) new Object[size];
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
   * Construct a static array containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public StaticArray(Item... items)
  {
    this(Arrays.asList(items), items.length);
  }


  //</editor-fold>


  @Override
  public int size()
  {
    // NOTE: Java arrays already store their size, so we don't have to.
    return this.items.length;
  }


  @Override
  public Item get(int index)
    throws IndexOutOfBoundsException
  {
    // NOTE: Java arrays have O(1) random access, so this will be O(1).
    return this.items[index];
  }


  @Override
  public void set(int index, Item item)
    throws IndexOutOfBoundsException
  {
    // NOTE: Similar comments to get().
    this.items[index] = item;
  }


  @Override
  @SuppressWarnings("unchecked")
  public void insert(int index, Item item)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index > this.size())
    {
      throw new IndexOutOfBoundsException();
    }

    Item[] itemsOld = this.items;
    Item[] items2 = (Item[]) new Object[this.size() + 1];

    // they are they same array up to index
    for (int i = 0; i < index; i++)
    {
      items2[i] = itemsOld[i];
    }


    items2[index] = item;

    //they are out of sync after index
    for (int i = index; i < items2.length - 1; i++)
    {
      items2[i+1] = itemsOld[i];
    }

    this.items = items2;

  }


  @Override
  @SuppressWarnings("unchecked")
  public Item remove(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size())
    {
      throw new IndexOutOfBoundsException();
    }

    Item itemToReturn = this.get(index);

    Item[] items2 = (Item[]) new Object[this.size() - 1];

    for (int i = 0; i < index ; i++)
    {
      items2[i] = this.get(i);
    }

    for (int i = index + 1; i < this.size() ; i++)
    {
      items2[i-1] = this.get(i);
    }

    this.items = items2;


    return itemToReturn;
  }

}
