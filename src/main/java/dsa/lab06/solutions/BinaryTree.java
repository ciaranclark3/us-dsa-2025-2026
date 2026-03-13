package dsa.lab06.solutions;

import dsa.lab02.base.Container;
import dsa.lab03.solutions.DynamicArray;
import dsa.lib.DSAObject;
import dsa.lib.Iterators;
import dsa.lib.utils.BinaryNodeUtils;
import dsa.lib.utils.BinaryTreeUtils;
import dsa.lib.utils.ToStringUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A binary tree.
 * <p>
 * This implementation is designed for binary trees that are built bottom-up.
 * It will work in other cases, just less efficiently.
 *
 * @param <Item> the item type
 */
public class BinaryTree<Item>
  extends DSAObject
  implements Container<Item>
{

  /** The root node - null if empty. */
  private Node<Item> root;


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty binary tree.
   */
  public BinaryTree()
  {
    this.root = null;
  }


  /**
   * Construct a binary tree containing the given items.
   * <p>
   * The binary tree will be complete, and a pre-order traversal of it will
   * yield the same items in the same order as supplied.
   * <p>
   * For example, given the items [a, b, c, d, e, f], the tree will be
   * [[[d] b [e]] a [[f] c []]].
   *
   * @param items the items
   */
  public BinaryTree(Iterable<Item> items)
  {
    DynamicArray<Item> itemsArray = new DynamicArray<>(items);
    this.root = BinaryTree.buildNode(this, itemsArray, 0);
  }


  /**
   * Construct a binary tree containing the given items
   * more efficiently than {@link #BinaryTree(Iterable)}.
   * <p>
   * The binary tree will be complete, and a pre-order traversal of it will
   * yield the same items in the same order as supplied.
   * <p>
   * For example, given the items [a, b, c, d, e, f], the tree will be
   * [[[d] b [e]] a [[f] c []]].
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  public BinaryTree(Iterable<Item> items, int size)
    throws IllegalArgumentException
  {
    DynamicArray<Item> itemsArray = new DynamicArray<>(items, size);
    this.root = BinaryTree.buildNode(this, itemsArray, 0);
  }


  /**
   * Construct a binary tree containing the given items.
   * <p>
   * The binary tree will be complete, and a pre-order traversal of it will
   * yield the same items in the same order as supplied.
   * <p>
   * For example, given the items [a, b, c, d, e, f], the tree will be
   * [[[d] b [e]] a [[f] c []]].
   *
   * @param items the items
   */
  @SafeVarargs
  public BinaryTree(Item... items)
  {
    this(Arrays.asList(items), items.length);
  }


  /**
   * Helper function for the (pre-order) constructors.
   * <p>
   * Recursively constructs a subtree starting from the item at the given index.
   * <p>
   * For example, assume the items [a, b, c, d, e, f].
   * <p>
   * Given index 0, the subtree will be [[[d] b [e]] a [[f] c []]].
   * <p>
   * Given index 1, the subtree will be [[d] b [e]].
   * <p>
   * Given index 2, the subtree will be [[f] c []].
   * <p>
   * Given index 3, the subtree will be [d].
   * <p>
   * Given index 4, the subtree will be [e].
   * <p>
   * Given index 5, the subtree will be [f].
   * <p>
   * Given index 6, the subtree will be [].
   *
   * @param tree  the containing tree
   * @param items the items the tree will contain
   * @param index the index of the item that will be the root of the subtree
   */
  private static <Item> Node<Item> buildNode(
    BinaryTree<Item> tree,
    DynamicArray<Item> items,
    int index)
  {
    // NOTE: We assume index >= 0, since the initial calls all supply 0, and
    //       the recursive calls only increase it.
    // NOTE: We can only do this because the method is private. If it weren't,
    //       even though we always start index as 0, we couldn't be sure others
    //       might not try calling e.g.
    //         BinaryTree.buildNode(tree, items, -1)

    // NOTE: Check whether we've gone off the end of the array of items.
    // NOTE: If we have, then this subtree won't have a root, nor contain any
    //       other items, and so we return null.
    if (index >= items.size())
    {
      return null;
    }

    // NOTE: Recursively construct the left and right subtrees.
    Node<Item> left = BinaryTree.buildNode(tree, items, index * 2 + 1);
    Node<Item> right = BinaryTree.buildNode(tree, items, index * 2 + 2);

    // NOTE: Combine them into one subtree, with the given item as its root.
    return new Node<>(tree, left, items.get(index), right);
  }


  /**
   * Construct a binary tree containing the given items.
   * <p>
   * The binary tree will be complete, and an in-order traversal of it will
   * yield the same items in the same order as supplied.
   * <p>
   * For example, given the items [a, b, c, d, e, f], the tree will be
   * [[[a] b [c]] d [[e] f []]].
   *
   * @param items the items
   */
  public static <Item> BinaryTree<Item> buildInOrder(Iterable<Item> items)
  {
    return BinaryTree.buildInOrder(new DynamicArray<>(items));
  }


  /**
   * Construct a binary tree containing the given items
   * more efficiently than {@link #buildInOrder(Iterable)}.
   * <p>
   * The binary tree will be complete, and an in-order traversal of it will
   * yield the same items in the same order as supplied.
   * <p>
   * For example, given the items [a, b, c, d, e, f], the tree will be
   * [[[a] b [c]] d [[e] f []]].
   *
   * @param items the items
   * @param size  the number of items
   * @throws IllegalArgumentException if {@code size} != {@code n}
   *                                  (where {@code n} is {@code items}'s size)
   */
  public static <Item> BinaryTree<Item> buildInOrder(
    Iterable<Item> items,
    int size)
    throws IllegalArgumentException
  {
    return BinaryTree.buildInOrder(new DynamicArray<>(items, size));
  }


  /**
   * Construct a binary tree containing the given items.
   * <p>
   * The binary tree will be complete, and an in-order traversal of it will
   * yield the same items in the same order as supplied.
   * <p>
   * For example, given the items [a, b, c, d, e, f], the tree will be
   * [[[a] b [c]] d [[e] f []]].
   *
   * @param items the items
   */
  @SafeVarargs
  public static <Item> BinaryTree<Item> buildInOrder(Item... items)
  {
    return BinaryTree.buildInOrder(Arrays.asList(items), items.length);
  }


  /**
   * Construct a binary tree containing the given items.
   * <p>
   * The binary tree will be complete, and an in-order traversal of it will
   * yield the same items in the same order as supplied.
   * <p>
   * For example, given the items [a, b, c, d, e, f], the tree will be
   * [[[a] b [c]] d [[e] f []]].
   *
   * @param items the items
   */
  private static <Item> BinaryTree<Item> buildInOrder(DynamicArray<Item> items)
  {
    BinaryTree<Item> tree = new BinaryTree<>();
    tree.root = BinaryTree.buildNodeInOrder(tree, items, 0, items.size());
    return tree;
  }


  /**
   * Helper function for the BinaryTree.buildInOrder(...) functions.
   * <p>
   * Recursively constructs a subtree starting from the item at the given index.
   * <p>
   * For example, assume the items [a, b, c, d, e, f].
   * <p>
   * Given start 0 and stop 6, the subtree will be [[[a] b [c]] d [[e] f []]].
   * <p>
   * Given start 0 and stop 5, the subtree will be [[[a] b [c]] d [e]].
   * <p>
   * Given start 1 and stop 6, the subtree will be [[[b] c [d]] e [f]].
   * <p>
   * Given start 2 and stop 4, the subtree will be [[c] d []].
   * <p>
   * Given start 2 and stop 3, the subtree will be [c].
   * <p>
   * Given start 2 and stop 2, the subtree will be [].
   *
   * @param tree  the containing tree
   * @param items the items the tree will contain
   * @param start the (inclusive) start index of the subarray to make a subtree
   * @param stop  the (exclusive) stop index of the subarray to make a subtree
   */
  private static <Item> Node<Item> buildNodeInOrder(
    BinaryTree<Item> tree,
    DynamicArray<Item> items,
    int start,
    int stop)
  {
    // NOTE: We assume that 0 <= {start, stop} <= items.size().
    // NOTE: Similarly to BinaryTree.buildNode(), this is because we know all
    //       calls to this function, and they all maintain this invariant.

    // NOTE: If the subarray is empty, make an empty (i.e. null) subtree.
    if (start >= stop)
    {
      return null;
    }

    // NOTE: Find the middle of the subarray.
    int index = (start + stop) / 2;

    // NOTE: Recursively build the left and right subtrees.
    // NOTE: These contain all items to the left/right (respectively) of the
    //       middle item in the subarray.
    Node<Item> left = BinaryTree.buildNodeInOrder(tree, items, start, index);
    Node<Item> right =
      BinaryTree.buildNodeInOrder(tree, items, index + 1, stop);

    // NOTE: Combine them into one subtree, with the middle item as root.
    return new Node<>(tree, left, items.get(index), right);
  }


  //</editor-fold>


  /**
   * Get the root node.
   * <p>
   * If there is none (i.e. it is empty), returns {@code null}.
   *
   * @return the root node
   */
  public Node<Item> root()
  {
    return this.root;
  }


  /**
   * Insert the given node as the root.
   *
   * @param root the new root
   * @throws IllegalStateException    if there's already a root, or
   * @throws IllegalArgumentException if the one given already has a parent
   *                                  or is in a different tree
   */
  public void insertRoot(Node<Item> root)
    throws IllegalStateException, IllegalArgumentException
  {
    if (!this.isEmpty())
    {
      throw new IllegalStateException();
    }
    if (root.hasParent() || root.tree != this)
    {
      throw new IllegalArgumentException();
    }
    this.root = root;
  }


  /**
   * Remove and return the root.
   *
   * @return the old root
   * @throws NoSuchElementException if there is no root
   */
  public Node<Item> removeRoot()
    throws NoSuchElementException
  {
    if (this.isEmpty())
    {
      throw new NoSuchElementException();
    }
    Node<Item> root = this.root;
    this.root = null;
    return root;
  }


  /**
   * Get the number of levels below the root of the tree.
   * <p>
   * If the tree is empty, returns {@code -1},
   * otherwise returns a non-negative integer.
   *
   * @return the height
   */
  public int height()
  {
    return this.isEmpty() ? -1 : this.root.height();
  }


  @Override
  public int size()
  {
    return this.isEmpty() ? 0 : this.root.size();
  }


  @Override
  public boolean isEmpty()
  {
    return this.root == null;
  }


  public void printPreOrder()
  {
    if (!this.isEmpty())
    {
      this.root.printPreOrder();
    }
  }


  public void printInOrder()
  {
    if (!this.isEmpty())
    {
      this.root.printInOrder();
    }
  }


  public void printPostOrder()
  {
    if (!this.isEmpty())
    {
      this.root.printPostOrder();
    }
  }


  /**
   * A node in a binary tree.
   *
   * @param <Item> the item type
   */
  public static class Node<Item>
    extends DSAObject
  {

    /** The parent node. Possibly null (if root). */
    private Node<Item> parent = null;


    /** The containing tree. */
    private BinaryTree<Item> tree;


    /** The left child. Possibly null. */
    private Node<Item> left;


    /** The contained item. */
    private Item item;


    /** The right child. Possibly null. */
    private Node<Item> right;


    /** The height of the subtree rooted at this node. */
    private int height;


    /** The size of the subtree rooted at this node. */
    private int size;


    //<editor-fold defaultstate="collapsed" desc="Constructors">


    /**
     * Construct a binary node with no parent nor children.
     *
     * @param tree the containing tree
     * @param item the contained item
     */
    public Node(BinaryTree<Item> tree, Item item)
    {
      this(tree, null, item, null);
    }


    /**
     * Construct a binary node with no parent and only a left child.
     *
     * @param tree the containing tree
     * @param left the left node
     * @param item the contained item
     */
    public Node(BinaryTree<Item> tree, Node<Item> left, Item item)
    {
      this(tree, left, item, null);
    }


    /**
     * Construct a binary node with no parent and only a right child.
     *
     * @param tree  the containing tree
     * @param item  the contained item
     * @param right the right node
     */
    public Node(BinaryTree<Item> tree, Item item, Node<Item> right)
    {
      this(tree, null, item, right);
    }


    /**
     * Construct a binary node with no parent and both children.
     *
     * @param tree  the containing tree
     * @param left  the left node
     * @param item  the contained item
     * @param right the right node
     * @throws IllegalArgumentException if a child already has a parent or is
     *                                  in a different tree
     */
    public Node(
      BinaryTree<Item> tree,
      Node<Item> left,
      Item item,
      Node<Item> right)
      throws IllegalArgumentException
    {
      // NOTE: Check that the children are valid, and mark this as their parent.
      if (left != null)
      {
        if (left.hasParent() || left.tree != tree)
        {
          throw new IllegalArgumentException();
        }
        left.parent = this;
      }
      if (right != null)
      {
        if (right.hasParent() || right.tree != tree)
        {
          throw new IllegalArgumentException();
        }
        right.parent = this;
      }

      // NOTE: Directly initialise the structural fields.
      this.tree = tree;
      this.left = left;
      this.item = item;
      this.right = right;

      // NOTE: Calculate and cache the size and height (from the children).
      this.recalculateSizeAndHeight();
    }


    //</editor-fold>


    /**
     * Get the parent node.
     *
     * @return the parent
     */
    public Node<Item> parent()
    {
      return this.parent;
    }


    /**
     * Get the containing binary tree.
     *
     * @return the tree
     */
    public BinaryTree<Item> tree()
    {
      return this.tree;
    }


    /**
     * Get the left child node.
     *
     * @return the left node
     */
    public Node<Item> left()
    {
      return this.left;
    }


    /**
     * Get the contained item.
     *
     * @return the item
     */
    public Item item()
    {
      return this.item;
    }


    /**
     * Get the right child node.
     *
     * @return the right node
     */
    public Node<Item> right()
    {
      return this.right;
    }


    /**
     * Set the contained item.
     *
     * @param item the new item
     */
    public void setItem(Item item)
    {
      this.item = item;
    }


    /**
     * Check if it has a parent (i.e. isn't the root).
     *
     * @return whether it has a parent
     */
    public boolean hasParent()
    {
      return this.parent != null;
    }


    /**
     * Check if it has a left child.
     *
     * @return whether it has a left child
     */
    public boolean hasLeft()
    {
      return this.left != null;
    }


    /**
     * Check if it has a right child.
     *
     * @return whether it has a right child
     */
    public boolean hasRight()
    {
      return this.right != null;
    }


    /**
     * Check if it is the root (i.e. doesn't have a parent).
     *
     * @return whether it's the root
     */
    public boolean isRoot()
    {
      return !this.hasParent();
    }


    /**
     * Check if it is a leaf (i.e. doesn't have either child).
     *
     * @return whether it's a leaf
     */
    public boolean isLeaf()
    {
      return !this.hasLeft() && !this.hasRight();
    }


    /**
     * Check if it is a left child (i.e. has a parent and is its left child).
     *
     * @return whether it's a left child
     */
    public boolean isLeft()
    {
      return this.hasParent() && this.parent.left == this;
    }


    /**
     * Check if it is a right child (i.e. has a parent and is its right child).
     *
     * @return whether it's a right child
     */
    public boolean isRight()
    {
      return this.hasParent() && this.parent.right == this;
    }


    /**
     * Get the number of contained items.
     *
     * @return the size
     */
    public int size()
    {
      return this.size;
    }


    /**
     * Get the number of levels below.
     *
     * @return the height
     */
    public int height()
    {
      return this.height;
    }


    /**
     * Get the number of levels above.
     *
     * @return the level
     */
    public int level()
    {
      // NOTE: If we are the root, we are level 0.
      // NOTE: If not, we're 1 level deeper than our parent.
      return this.isRoot() ? 0 : this.parent.level() + 1;
    }


    /**
     * Calculate the number of contained items.
     * <p>
     * Don't use {@code this.size} or {@link #size()}, but if the node has
     * children you can use their {@code .size} field(s).
     *
     * @return the size
     */
    private int calculateSize()
    {
      // NOTE: We contain all the items in the left subtree, all those in the
      //       right, and the item at the root of this subtree.
      int leftSize = this.hasLeft() ? this.left.size : 0;
      int rightSize = this.hasRight() ? this.right.size : 0;
      return leftSize + rightSize + 1;
    }


    /**
     * Calculate the number of levels below.
     * <p>
     * Don't use {@code this.height}, but if the node has children
     * you can use their {@code .height} field(s).
     *
     * @return the height
     */
    private int calculateHeight()
    {
      // NOTE: Our height is one more than the taller of our child subtrees.
      int leftHeight = this.hasLeft() ? this.left.height : -1;
      int rightHeight = this.hasRight() ? this.right.height : -1;
      return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * Recalculate (and cache) this node's size and height from its childrens',
     * and then recursively do the same for its parent if it has one.
     * <p>
     * This method updates the cached sizes and heights of all this node's
     * ancestors, and only its ancestors (i.e. no other nodes).
     * <p>
     * This should be done whenever left or right subtrees are inserted or
     * removed, as this is when our size and height may change.
     */
    private void recalculateSizeAndHeight()
    {
      this.size = this.calculateSize();
      this.height = this.calculateHeight();
      if (this.hasParent())
      {
        this.parent.recalculateSizeAndHeight();
      }
    }


    /**
     * Print out the items in this subtree, "pre-order".
     * <p>
     * Prints items using {@code System.out.println(item)},
     * in the order "node-left-right".
     */
    public void printPreOrder()
    {
      // NOTE: "Node".
      System.out.println(this.item);

      // NOTE: "Left".
      if (this.hasLeft())
      {
        this.left.printPreOrder();
      }

      // NOTE: "Right".
      if (this.hasRight())
      {
        this.right.printPreOrder();
      }
    }


    /**
     * Print out the items in this subtree, "in-order".
     * <p>
     * Prints items using {@code System.out.println(item)},
     * in the order "left-node-right".
     */
    public void printInOrder()
    {
      // NOTE: "Left".
      if (this.hasLeft())
      {
        this.left.printInOrder();
      }

      // NOTE: "Node".
      System.out.println(this.item);

      // NOTE: "Right".
      if (this.hasRight())
      {
        this.right.printInOrder();
      }
    }


    /**
     * Print out the items in this subtree, "post-order".
     * <p>
     * Prints items using {@code System.out.println(item)},
     * in the order "left-right-node".
     */
    public void printPostOrder()
    {
      // NOTE: "Left".
      if (this.hasLeft())
      {
        this.left.printPostOrder();
      }

      // NOTE: "Right".
      if (this.hasRight())
      {
        this.right.printPostOrder();
      }

      // NOTE: "Node".
      System.out.println(this.item);
    }


    /**
     * Insert the given node as the left child.
     *
     * @param left the new left child
     * @throws IllegalStateException    if there's already a left child
     * @throws IllegalArgumentException if the one given already has a parent
     *                                  or is in a different tree
     */
    public void insertLeft(Node<Item> left)
      throws IllegalStateException, IllegalArgumentException
    {
      if (this.hasLeft())
      {
        throw new IllegalStateException();
      }
      if (left.hasParent() || left.tree != this.tree)
      {
        throw new IllegalArgumentException();
      }
      this.left = left;
      left.parent = this;
      this.recalculateSizeAndHeight();
    }


    /**
     * Insert the given node as the right child.
     *
     * @param right the new right child
     * @throws IllegalStateException    if there's already a right child
     * @throws IllegalArgumentException if the one given already has a parent
     *                                  or is in a different tree
     */
    public void insertRight(Node<Item> right)
      throws IllegalStateException, IllegalArgumentException
    {
      if (this.hasRight())
      {
        throw new IllegalStateException();
      }
      if (right.hasParent() || right.tree != this.tree)
      {
        throw new IllegalArgumentException();
      }
      this.right = right;
      right.parent = this;
      this.recalculateSizeAndHeight();
    }


    /**
     * Remove and return the left child.
     *
     * @return the old left child
     * @throws NoSuchElementException if there is no left child
     */
    public Node<Item> removeLeft()
      throws NoSuchElementException
    {
      if (!this.hasLeft())
      {
        throw new NoSuchElementException();
      }
      Node<Item> left = this.left;
      this.left = left.parent = null;
      this.recalculateSizeAndHeight();
      return left;
    }


    /**
     * Remove and return the right child.
     *
     * @return the old right child
     * @throws NoSuchElementException if there is no right child
     */
    public Node<Item> removeRight()
      throws NoSuchElementException
    {
      if (!this.hasRight())
      {
        throw new NoSuchElementException();
      }
      Node<Item> right = this.right;
      this.right = right.parent = null;
      this.recalculateSizeAndHeight();
      return right;
    }


    //<editor-fold defaultstate="collapsed" desc="toCompactString()">


    public static <Item> String toCompactString(Node<Item> node)
    {
      if (node == null)
      {
        return "[]";
      }
      return node.toCompactString();
    }


    public String toCompactString()
    {
      String n = ToStringUtils.toString(this.item);
      if (this.isLeaf())
      {
        return "[" + n + "]";
      }
      String l = Node.toCompactString(this.left);
      String r = Node.toCompactString(this.right);
      return "[" + l + ' ' + n + ' ' + r + "]";
    }


    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="toString()">


    @Override
    public String toString(String indent)
    {
      return BinaryNodeUtils.toString(
        this,
        Node::left,
        Node::right,
        Node::item,
        indent);
    }


    //</editor-fold>

  }


  //<editor-fold defaultstate="collapsed" desc="Iteration">


  public Iterable<Node<Item>> preOrderNodes()
  {
    return () -> new NodeIterator<>(this, IterationOrder.PRE);
  }


  public Iterable<Node<Item>> inOrderNodes()
  {
    return () -> new NodeIterator<>(this, IterationOrder.IN);
  }


  public Iterable<Node<Item>> postOrderNodes()
  {
    return () -> new NodeIterator<>(this, IterationOrder.POST);
  }


  public Iterable<Node<Item>> reversePreOrderNodes()
  {
    return () -> new NodeIterator<>(this, IterationOrder.REVERSE_PRE);
  }


  public Iterable<Node<Item>> reverseInOrderNodes()
  {
    return () -> new NodeIterator<>(this, IterationOrder.REVERSE_IN);
  }


  public Iterable<Node<Item>> reversePostOrderNodes()
  {
    return () -> new NodeIterator<>(this, IterationOrder.REVERSE_POST);
  }


  public Iterable<Item> preOrder()
  {
    return Iterators.applyEach(this.preOrderNodes(), Node::item);
  }


  public Iterable<Item> inOrder()
  {
    return Iterators.applyEach(this.inOrderNodes(), Node::item);
  }


  public Iterable<Item> postOrder()
  {
    return Iterators.applyEach(this.postOrderNodes(), Node::item);
  }


  public Iterable<Item> reversePreOrder()
  {
    return Iterators.applyEach(this.reversePreOrderNodes(), Node::item);
  }


  public Iterable<Item> reverseInOrder()
  {
    return Iterators.applyEach(this.reverseInOrderNodes(), Node::item);
  }


  public Iterable<Item> reversePostOrder()
  {
    return Iterators.applyEach(this.reversePostOrderNodes(), Node::item);
  }


  @Override
  public Iterable<Item> items()
  {
    return this.preOrder();
  }


  public enum IterationOrder
  {
    PRE,
    IN,
    POST,
    REVERSE_PRE,
    REVERSE_IN,
    REVERSE_POST,
  }


  public static class NodeIterator<Item>
    implements Iterator<Node<Item>>
  {

    private Node<Item> node;


    private NodeIterator<Item> left;


    private NodeIterator<Item> right;


    private IterationOrder order;


    /**
     * Construct an iterator over the nodes in a binary tree.
     *
     * @param tree  the binary tree
     * @param order the order nodes should be iterated in
     */
    public NodeIterator(BinaryTree<Item> tree, IterationOrder order)
    {
      this(tree.root(), order);
    }


    private NodeIterator(Node<Item> node, IterationOrder order)
    {
      this.order = order;
      this.node = node;
      if (node.hasLeft())
      {
        this.left = new NodeIterator<>(node.left(), order);
      }
      if (node.hasRight())
      {
        this.right = new NodeIterator<>(node.right(), order);
      }
    }


    private boolean hasNode()
    {
      return this.node != null;
    }


    private boolean hasLeft()
    {
      return this.left != null && this.left.hasNext();
    }


    private boolean hasRight()
    {
      return this.right != null && this.right.hasNext();
    }


    private Node<Item> node()
    {
      Node<Item> node = this.node;
      this.node = null;
      return node;
    }


    private Node<Item> left()
    {
      return this.left.next();
    }


    private Node<Item> right()
    {
      return this.right.next();
    }


    @Override
    public boolean hasNext()
    {
      return this.hasNode() || this.hasLeft() || this.hasRight();
    }


    @Override
    public Node<Item> next()
      throws NoSuchElementException
    {
      switch (this.order)
      {
        case PRE:
          if (this.hasNode())
          {
            return this.node();
          }
          if (this.hasLeft())
          {
            return this.left();
          }
          if (this.hasRight())
          {
            return this.right();
          }
          break;
        case IN:
          if (this.hasLeft())
          {
            return this.left();
          }
          if (this.hasNode())
          {
            return this.node();
          }
          if (this.hasRight())
          {
            return this.right();
          }
          break;
        case POST:
          if (this.hasLeft())
          {
            return this.left();
          }
          if (this.hasRight())
          {
            return this.right();
          }
          if (this.hasNode())
          {
            return this.node();
          }
          break;
        case REVERSE_PRE:
          if (this.hasNode())
          {
            return this.node();
          }
          if (this.hasRight())
          {
            return this.right();
          }
          if (this.hasLeft())
          {
            return this.left();
          }
          break;
        case REVERSE_IN:
          if (this.hasRight())
          {
            return this.right();
          }
          if (this.hasNode())
          {
            return this.node();
          }
          if (this.hasLeft())
          {
            return this.left();
          }
          break;
        case REVERSE_POST:
          if (this.hasRight())
          {
            return this.right();
          }
          if (this.hasLeft())
          {
            return this.left();
          }
          if (this.hasNode())
          {
            return this.node();
          }
          break;
      }
      throw new NoSuchElementException();
    }

  }


  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="toCompactString()">


  public String toCompactString()
  {
    return Node.toCompactString(this.root);
  }


  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="toString()">


  @Override
  public String toString(String indent)
  {
    return BinaryTreeUtils.toString(this, BinaryTree::root, indent);
  }


  //</editor-fold>

}
