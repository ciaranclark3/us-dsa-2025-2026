package dsa.lab03.exercises;

import dsa.lab03.base.Stack;
import dsa.lib.DSAObject;
import dsa.lib.Iterators;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * An array stack.
 * <p>
 * Implements the stack interface by using a dynamic array.
 *
 * @param <Item> the item type
 */
public class ArrayStack<Item>
  extends DSAObject
  implements Stack<Item>
{

  private DynamicArray<Item> items;


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty array stack.
   */
  public ArrayStack()
  {
    this.items = new DynamicArray<>();
  }


  /**
   * Construct an array stack containing the given items.
   *
   * @param items the items
   */
  public ArrayStack(Iterable<Item> items)
  {
    this.items = new DynamicArray<>(Iterators.reversed(items));
  }


  /**
   * Construct an array stack containing the given items
   * more efficiently than {@link #ArrayStack(Iterable)}.
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  public ArrayStack(Iterable<Item> items, int size)
    throws IllegalArgumentException
  {
    this.items = new DynamicArray<>(Iterators.reversed(items, size), size);
  }


  /**
   * Construct an array stack containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public ArrayStack(Item... items)
  {
    this(Arrays.asList(items), items.length);
  }


  //</editor-fold>


  @Override
  public void push(Item item)
  {
    this.items.insertLast(item);
  }


  @Override
  public Item pop()
    throws NoSuchElementException
  {
    return this.items.removeLast();
  }


  @Override
  public Item top()
    throws NoSuchElementException
  {
    return this.items.get(this.size()-1);
  }


  @Override
  public int size()
  {
    // NOTE: We just forward methods to corresponding methods on the wrapped
    //       DynamicArray. (Re the term "forward", you can think of an analogy
    //       with emails: Someone has emailed us asking what the size is, and we
    //       forward it on to DynamicArray, which will reply with the size, and
    //       we then forward that reply back to whoever asked.)
    return this.items.size();
  }


  @Override
  public Iterable<Item> items()
  {
    return this.items.reversed();
  }

}
