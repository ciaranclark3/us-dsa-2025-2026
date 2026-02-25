package dsa.lab01.exercises;

import dsa.lab01.base.Container;
import dsa.lib.utils.ArrayUtils;
import dsa.lib.DSAObject;
import dsa.lib.utils.ToStringUtils;

import java.util.Objects;

/**
 * An array-based container.
 * <p>
 * Contains some number of items using an array.
 *
 * @param <Item> the item type
 */
public class Array<Item>
  extends DSAObject  // NOTE: Don't worry about the `extends DSAObject`.
  implements Container<Item>
{

  private Item[] items;


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty array.
   */
  @SuppressWarnings("unchecked")
  public Array()
  {
    this.items = (Item[]) new Object[0];
  }


  /**
   * Construct an array containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  @SuppressWarnings("unchecked")
  public Array(Item... items)
  {
    this.items = (Item[]) new Object[items.length];
    for (int i = 0; i < items.length; i++)
    {
      this.items[i] = items[i];
    }
  }


  //</editor-fold>


  @Override
  public int size()
  {
    // TODO: Implement Array.size()

    return this.items.length;
  }


  @Override
  public boolean contains(Item item)
  {
    for (Item containedItem : this.items)
    {
      if (Objects.equals(item, containedItem))
      {
        return true;
      }
    }
    return false;
  }


  //<editor-fold defaultstate="collapsed" desc="toString()">


  @Override
  public String toString(String indent)
  {
    return ToStringUtils.toTypedString(
      this,
      ArrayUtils.toUntypedString(this.items, indent));
  }


  //</editor-fold>

}
