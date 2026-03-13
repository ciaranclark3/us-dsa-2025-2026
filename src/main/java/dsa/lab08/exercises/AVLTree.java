package dsa.lab08.exercises;

import dsa.lab04.base.MapItem;
import dsa.lab05.solutions.ChainingHashMap;
import dsa.lab07.solutions.BinarySearchTree;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * An AVL tree.
 *
 * @param <Key>   the key type
 * @param <Value> the value type
 */
public class AVLTree<Key extends Comparable<Key>, Value>
  extends BinarySearchTree<Key, Value>
{

  private ChainingHashMap<Node<Key, Value>, Integer> heights =
    new ChainingHashMap<>();


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty AVL tree.
   */
  public AVLTree()
  {
  }


  /**
   * Construct an AVL tree containing the given items.
   *
   * @param items the items
   */
  public AVLTree(Iterable<MapItem<Key, Value>> items)
  {
    for (MapItem<Key, Value> item : items)
    {
      this.insert(item);
    }
  }


  /**
   * Construct an AVL tree containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public AVLTree(MapItem<Key, Value>... items)
  {
    this(Arrays.asList(items));
  }


  //</editor-fold>


  @Override
  public void insert(MapItem<Key, Value> item)
  {
    // NOTE: This is basically very similar to insertion in normal/naive BSTs
    //       (e.g. our BinarySearchTree class), just with some extra
    //       AVL-specific stuff to keep the height minimal (and thus the
    //       operations' asymptotic efficiencies O(log(n)), where n is the size.
    if (this.isEmpty())
    {
      this.root = new Node<>(null, this, null, item, null);
      this.size = 1;
      this.heights.insert(this.root, 0);
    }
    else
    {
      int oldSize = this.size();

      // NOTE: Insert the item.
      Node<Key, Value> inserted = this.root.insert(item);

      // NOTE: If size has changed, there must not have been an item already
      //       with the same key, so a new node will have been inserted.
      if (this.size() != oldSize)
      {
        // NOTE: The node, being new, won't yet have an entry in our map of
        //       cached heights, so we add it.
        // NOTE: Nodes are always inserted as leaves, so their height is always
        //       initially 0.
        this.heights.insert(inserted, 0);

        // NOTE: The structure of the tree will have changed, and so some of the
        //       subtrees' heights may also have changed. We therefore need to
        //       update our cached values for the heights.
        // NOTE: If heights have changed, then balance factors may have changed.
        //       The AVL condition is that all balance factors bf are in the range
        //       -1 <= bf <= +1, but some may now be -2 (and/or others may be +2).
        //       We will need to check for this and, if we discover such
        //       unbalanced nodes, we will need to do some rotations to bring
        //       their balance factors back into the appropriate range.
        // NOTE: Balance factors can only have changed for ancestors of those
        //       whose heights have changed, and heights can only have changed
        //       for ancestors of the newly-inserted leaf.
        this.updateAncestors(inserted.parent, true);
      }
    }
  }


  @Override
  public MapItem<Key, Value> remove(Key key)
    throws NoSuchElementException
  {
    // NOTE: Similarly to insert(), the part of this that actually does the
    //       removing is very similar to BinarySearchTree.remove. Basically all
    //       we add is some rebalancing.
    Node<Key, Value> node = this.findNode(key);
    MapItem<Key, Value> item = node.item;
    Node<Key, Value> removed = node.remove();

    // NOTE: We no longer need an entry for the node in our map of cached
    //       heights, so we remove it.
    // NOTE: This also saves a potential memory leak: If we kept a reference to
    //       it in our heights map, Java's garbage collector would never be able
    //       to free up its memory, so if the program were to run long enough,
    //       with enough insert()s and remove()s, we'd potentially run out of
    //       memory. Even in less extreme scenarios, we'd still be using more
    //       memory than really required.
    this.heights.remove(removed);

    // NOTE: Similarly to insert(), the structure of the tree will have changed,
    //       so ancestors heights - and thus balance factors - may have changed,
    //       so we go up the chain from removed node to root updating cached
    //       heights and rebalancing as necessary.
    this.updateAncestors(removed.parent, false);

    // NOTE: Return the removed item.
    return item;
  }


  /**
   * Rotate the given node clockwise.
   *
   * @param node the node to rotate
   */
  @SuppressWarnings({"SuspiciousNameCombination", "UnnecessaryLocalVariable"})
  private void rotateC(Node<Key, Value> node)
  {
    Node<Key, Value> z = node;
    if (z == null)
    {
      throw new IllegalStateException();
    }
    Node<Key, Value> y = z.left;
    if (y == null)
    {
      throw new IllegalStateException();
    }
    Node<Key, Value> b = y.right;

    //                           ╭╌╌╌╮
    //                           ┆ p ┆
    //                           ╰╌╌╌╯
    //                            ↓ ↑
    //                           ╔═══╗
    //                           ║ z ║
    //                           ╚═══╝
    //            ╭──────────────╯ ↑ ╰──────╮
    //            ↓ ╭──────────────┴──────╮ ↓
    //           ╔═══╗                   ╭╌╌╌╮
    //           ║ y ║                   ┆ c ┆
    //           ╚═══╝                   ╰╌╌╌╯
    //    ╭──────╯ ↑ ╰──────╮
    //    ↓ ╭──────┴──────╮ ↓
    //   ╭╌╌╌╮           ╭╌╌╌╮
    //   ┆ a ┆           ┆ b ┆
    //   ╰╌╌╌╯           ╰╌╌╌╯

    // TODO: Implement AVLTree.rotateC(Node node)
    // NOTE: You will need to do a LOT of link juggling!
    // NOTE: Expect to write approx 10+ lines.

    //                           ╭╌╌╌╮
    //                           ┆ p ┆
    //                           ╰╌╌╌╯
    //            ╭───────────────╯ ↑
    //            ↓ ╭───────────────╯
    //           ╔═══╗
    //           ║ y ║
    //           ╚═══╝
    //    ╭──────╯ ↑ ╰──────────────╮
    //    ↓ ╭──────┴──────────────╮ ↓
    //   ╭╌╌╌╮                   ╔═══╗
    //   ┆ a ┆                   ║ z ║
    //   ╰╌╌╌╯                   ╚═══╝
    //                    ╭──────╯ ↑ ╰──────╮
    //                    ↓ ╭──────┴──────╮ ↓
    //                   ╭╌╌╌╮           ╭╌╌╌╮
    //                   ┆ b ┆           ┆ c ┆
    //                   ╰╌╌╌╯           ╰╌╌╌╯

    this.recalculateHeight(z);
    this.recalculateHeight(y);
  }


  /**
   * Rotate the given node anticlockwise.
   *
   * @param node the node to rotate
   */
  @SuppressWarnings({"SuspiciousNameCombination", "UnnecessaryLocalVariable"})
  private void rotateA(Node<Key, Value> node)
  {
    Node<Key, Value> y = node;
    if (y == null)
    {
      throw new IllegalStateException();
    }
    Node<Key, Value> z = y.right;
    if (z == null)
    {
      throw new IllegalStateException();
    }
    Node<Key, Value> b = z.left;

    //           ╭╌╌╌╮
    //           ┆ p ┆
    //           ╰╌╌╌╯
    //            ↑ ↓
    //           ╔═══╗
    //           ║ y ║
    //           ╚═══╝
    //    ╭──────╯ ↑ ╰──────────────╮
    //    ↓ ╭──────┴──────────────╮ ↓
    //   ╭╌╌╌╮                   ╔═══╗
    //   ┆ a ┆                   ║ z ║
    //   ╰╌╌╌╯                   ╚═══╝
    //                    ╭──────╯ ↑ ╰──────╮
    //                    ↓ ╭──────┴──────╮ ↓
    //                   ╭╌╌╌╮           ╭╌╌╌╮
    //                   ┆ b ┆           ┆ c ┆
    //                   ╰╌╌╌╯           ╰╌╌╌╯

    // TODO: Implement AVLTree.rotateA(Node node)
    // NOTE: This is very similar to rotateC.
    //       (Just swap left for right, and right for left.)

    //           ╭╌╌╌╮
    //           ┆ p ┆
    //           ╰╌╌╌╯
    //            ↑ ╰───────────────╮
    //            ╰───────────────╮ ↓
    //                           ╔═══╗
    //                           ║ z ║
    //                           ╚═══╝
    //            ╭──────────────╯ ↑ ╰──────╮
    //            ↓ ╭──────────────┴──────╮ ↓
    //           ╔═══╗                   ╭╌╌╌╮
    //           ║ y ║                   ┆ c ┆
    //           ╚═══╝                   ╰╌╌╌╯
    //    ╭──────╯ ↑ ╰──────╮
    //    ↓ ╭──────┴──────╮ ↓
    //   ╭╌╌╌╮           ╭╌╌╌╮
    //   ┆ a ┆           ┆ b ┆
    //   ╰╌╌╌╯           ╰╌╌╌╯

    this.recalculateHeight(y);
    this.recalculateHeight(z);
  }


  /**
   * Rebalance the given node (if necessary) after
   * an insertion or removal within its descendants.
   * <p>
   * If the node is still balanced, do nothing.
   * <p>
   * At most two rotations should be performed.
   *
   * @param node the node to rebalance
   * @return whether the node needed rebalancing
   */
  private boolean rebalance(Node<Key, Value> node)
  {
    int balanceFactor = this.balanceFactor(node);
    if (balanceFactor == -2)
    {
      // TODO: Implement AVLTree.rebalance(Node node)
      // NOTE: There are two cases to consider here!

      return true;
    }
    if (balanceFactor == 2)
    {
      // TODO: Implement AVLTree.rebalance(Node node)
      // NOTE: There are also two more cases to consider here!
      // NOTE: Remember, there are four total cases.

      return true;
    }
    return false;
  }


  private void updateAncestors(
    Node<Key, Value> node,
    boolean canStopAfterRebalance)
  {
    if (node != null)
    {
      this.recalculateHeight(node);
      boolean rebalanced = this.rebalance(node);
      if (!canStopAfterRebalance || !rebalanced)
      {
        this.updateAncestors(node.parent, canStopAfterRebalance);
      }
    }
  }


  private int calculateHeight(Node<Key, Value> node)
  {
    int leftHeight = this.cachedHeight(node.left);
    int rightHeight = this.cachedHeight(node.right);
    return 1 + Math.max(leftHeight, rightHeight);
  }


  private void recalculateHeight(Node<Key, Value> node)
  {
    this.heights.insert(node, this.calculateHeight(node));
  }


  private int balanceFactor(Node<Key, Value> node)
  {
    int leftHeight = this.cachedHeight(node.left);
    int rightHeight = this.cachedHeight(node.right);
    return rightHeight - leftHeight;
  }


  private int cachedHeight(Node<Key, Value> node)
  {
    return node == null ? -1 : this.heights.get(node);
  }


  //<editor-fold defaultstate="collapsed" desc="Methods for testing">


  public boolean _isHeightCacheCorrect()
  {
    for (MapItem<Node<Key, Value>, Integer> cacheItem : heights)
    {
      Node<Key, Value> node = cacheItem.key();
      int height = cacheItem.value();
      if (this.calculateHeight(node) != height)
      {
        return false;
      }
    }
    return true;
  }


  public boolean _isAVLConditionSatisfied()
  {
    for (Node<Key, Value> node : heights.keys())
    {
      int balanceFactor = this.balanceFactor(node);
      if (Math.abs(balanceFactor) >= 2)
      {
        return false;
      }
    }
    return true;
  }


  //</editor-fold>

}
