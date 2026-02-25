package dsa.lab02.exercises;

import dsa.lab02.base.LinkedList;
import dsa.lab02.base.LinkedNode;
import dsa.lib.DSAObject;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A singly-linked list.
 * <p>
 * Nodes are only directly linked to their successors.
 * Holds references to both the first and last nodes (if non-empty).
 *
 * @param <Item> the item type
 */
public class SinglyLinkedList<Item>
  extends DSAObject  // NOTE: Don't worry about the `extends DSAObject`.
  implements LinkedList<Item>
{

  /** The number of contained items/nodes. */
  private int size = 0;
  // NOTE: Not all singly-linked list implementations include this.
  // NOTE: PRO: It makes size() O(1) rather than O(n) without worsening the
  //       asymptotic efficiency of any other operations. (This is very good.)
  // NOTE: CON: It does have a very slight performance penalty for insert/remove
  //       operations, as we need to keep it updated/correct. However, it's only
  //       an extra increment/decrement, so that really doesn't matter.
  // NOTE: CON: It's an extra thing that the lists store, so they require more
  //       storage space. However, it's only one int per list, and often the
  //       lists will have many items each, so one extra int (again, not one
  //       extra int per item, but per list) doesn't really matter. A case when
  //       it might matter more is if you had many very small or even empty
  //       lists, as the proportional increase would then be more.


  /** The first node in the list (null if empty). */
  private Node<Item> first = null;


  /** The last node in the list (null if empty). */
  private Node<Item> last = null;
  // NOTE: Not all singly-linked list implementations include this, but it
  //       improves the asymptotic efficiency of some operations without
  //       worsening the (asymptotic) efficiency of any other operations.
  //       Similarly to size, it does have a slight performance penalty, and
  //       leads to lists taking up slightly more space in memory, but in both
  //       cases not enough to usually matter enough to outweigh the benefits.
  // NOTE: Assuming a 64-bit system, an empty singly-linked list that only
  //       stores the first node takes up 8 bytes, but one like this that also
  //       stores the size and last node takes up at least 4 + 8 + 8 = 20 bytes.
  //       This might seem like a 150%+ increase, and for empty lists it is, but
  //       again, most lists are not empty, and for those lists the vast
  //       majority of the storage requirement is due to all the items/nodes.
  // NOTE: Usually you needn't worry too much about how large your structures
  //       are, partly because there's so much storage available these days, but
  //       also because as a rule fast is more important than small (though as
  //       with any rule there are exceptions, e.g. when working in embedded
  //       contexts on devices with very little memory, or when storing so much
  //       data that any proportional reduction in its size makes a big
  //       absolute difference).


  //<editor-fold defaultstate="collapsed" desc="Constructors">


  /**
   * Construct an empty singly-linked list.
   */
  public SinglyLinkedList()
  {
  }


  /**
   * Construct a singly-linked list containing the given items.
   *
   * @param items the items
   */
  public SinglyLinkedList(Iterable<Item> items)
  {
    // NOTE: Uses insertLast() <=> insert(size), so if insert is not implemented
    //       this won't successfully construct a list containing these items.
    for (Item item : items)
    {
      this.insertLast(item);
    }
  }


  /**
   * Construct a singly-linked list containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public SinglyLinkedList(Item... items)
  {
    // NOTE: The ... means that we can call this constructor with a variable
    //       number of arguments, each Items, which will be collected up into
    //       an array called items. That is, we can write e.g.
    //         new SinglyLinkedList<String>("foo", "bar", "dolor")
    //       and it will be as if we'd written
    //         new SinglyLinkedList<String>(new String[]{"foo", "bar", "dolor"})
    //       (which, in fact, we can also write, and will also work with this).
    // NOTE: Within here (i.e. the constructor body), items is just an Item[].
    // NOTE: The this() is calling another constructor of this class
    //       (specifically the one that takes an Iterable).
    // NOTE: Java array don't implement Iterable, so Arrays.asList wraps items
    //       in something that does.
    this(Arrays.asList(items));
  }


  //</editor-fold>

  @Override
  public int size()
  {
    // NOTE: We cache the size in a field, so we can just return the value of
    //       that field for a nice O(1) size() implementation.
    return this.size;
  }


  @Override
  public Node<Item> node(int index)
    throws IndexOutOfBoundsException
  {
    // NOTE: Checks if the index is valid, and if not, throws an exception.
    // NOTE: Without this, this implementation could return the first item for
    //       too small (negative) indices and the last item for too large
    //       (>=size) indices. You might be attracted by the idea of this, and
    //       in some cases that might be preferable (as might some other ways of
    //       handling OOB indices, such as using a modulus operation to bring it
    //       in range), but an invalid index nearly always indicates some sort
    //       of logic error, so it's better to inform the user, who likely needs
    //       to fix their code.
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }

    Node<Item> nodeToReturn;
    Node<Item> currentNode = this.first;
    for (int i = 0; i < index; i++)
    {
      currentNode = currentNode.next;
    }
    nodeToReturn = currentNode;

    return nodeToReturn;
  }


  @Override
  public void insert(int index, Item item)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index > this.size)
    {
      throw new IndexOutOfBoundsException();
    }

    boolean isLastLocal;
    Node<Item> nodeToAdd = new Node<Item>(this, item);

    if(index == this.size){
      this.last = nodeToAdd;
      isLastLocal = true;
    }
    else{
      isLastLocal = false;
    }

    if(!isLastLocal){
      nodeToAdd.next = this.node(index);
    }
    if(index != 0){
      this.node(index - 1).next = nodeToAdd;
    }

    if(index == 0){
      this.first = nodeToAdd;
    }

    this.size++;
  }

  @Override
  public Item remove(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }

    Item itemToReturn;
    if(index == 0){ //first
      itemToReturn = this.first.item;
      this.first = this.first.next;
    }
    else if (index == this.size - 1){ //last
      itemToReturn = this.last.item;

      Node<Item> newLastNode = this.node(index - 1);
      newLastNode.next = null;
      this.last = newLastNode;
    }
    else { //not last or first
      itemToReturn = this.node(index).item;

      Node<Item> nodeBefore = this.node(index - 1);
      nodeBefore.next = nodeBefore.next.next;
    }

    this.size--;
    return itemToReturn;
  }


  /**
   * A node in a singly-linked list.
   * <p>
   * Only holds a direct link to the next node.
   *
   * @param <Item> the item type
   */
  public static class Node<Item>
    extends DSAObject
    implements LinkedNode<Item>
  {
    // NOTE: This is a "static nested class". It's just like a normal class,
    //       except that it (Node) and the outer class (SinglyLinkedList) can
    //       access all of eachothers' private members. This will be useful in
    //       dynamic Node methods that insert or remove, since we'll need to
    //       update the node's containing list's size, as well as potentially
    //       its first and/or last node pointers.
    // NOTE: Within SinglyLinkedList and Node, we can just refer to it as Node,
    //       but outside of this file we refer to it as SinglyLinkedList.Node.
    // NOTE: Even outside of this file, it's possible to just call it Node if we
    //       import dsa.lab02.solutions.SinglyLinkedList.Node;, but it's better
    //       to be clear about which Node class is meant, as other classes will
    //       also have nested Node classes (e.g. DoublyLinkedList & BinaryTree),
    //       so we'll instead import dsa.lab02.solutions.SinglyLinkedList; and
    //       refer to it as SinglyLinkedList.Node.


    /** The list that contains this node. */
    private SinglyLinkedList<Item> list;
    // NOTE: Same comment as LinkedNode.item(); omitted by some implementations.


    /** The item that this node contains. */
    private Item item;


    /** The next node immediately after this one (null if last). */
    private Node<Item> next;


    /**
     * Construct a node with the given item.
     *
     * @param list the containing linked list
     * @param item the contained item
     */
    public Node(SinglyLinkedList<Item> list, Item item)
    {
      this(list, item, null);
    }


    /**
     * Construct a node with the given item and successor.
     *
     * @param list the containing linked list
     * @param item the contained item
     * @param next the next node
     */
    public Node(SinglyLinkedList<Item> list, Item item, Node<Item> next)
    {
      // NOTE: Simply assign the fields. This pattern of assigning fields from
      //       constructor parameters is very common in Java.
      // NOTE: We also use the convention of naming the fields and parameters
      //       the same, and disambiguating them in the assignments by using
      //       this.x to refer to the field, and x to refer to the parameter.
      this.list = list;
      this.item = item;
      this.next = next;
    }


    @Override
    public SinglyLinkedList<Item> list()
    {
      return this.list;
    }


    @Override
    public Item item()
    {
      return this.item;
    }


    @Override
    public void setItem(Item item)
    {
      this.item = item;
    }


    @Override
    public Node<Item> previous()
    {
      // NOTE: If this is the first node, there is no previous node, so we just
      //       return null.
      // NOTE: If we were getting the previous _item_ (rather than node), we
      //       wouldn't want to return null as that could be confused with the
      //       case of there being a previous node that contains the item null,
      //       so in that instance we'd be better off throwing a
      //       NoSuchElementException to help users distinguish the two cases.
      //       Here, though, returning null uniques means there is no previous
      //       node, as if there is a previous node containing null as an item,
      //       the node itself (which is what we return) won't be null.
      if (this.isFirst())
      {
        return null;
      }

      // NOTE: Start at the first node (which we know can't be this node, and so
      //       must come before this in the sequence), and check if it's this
      //       node's predecessor. If not, move to the next one, and check
      //       again. Repeat until the predecessor is found, then return it.
      Node<Item> node = this.list.first;
      while (node.next != this)
      {
        node = node.next;
      }
      return node;

      // NOTE: This, and all Node methods, assume that the node is actually in
      //       the list, i.e. it hasn't been removed from it. We take the
      //       convention that, once a node is removed from a list, any further
      //       operations on it have undefined behaviour, i.e. essentially once
      //       you remove a node from a list, if you still have any references
      //       to that node, you shouldn't use it to perform any operations on
      //       it. The alternative would be to set list to null when removing
      //       and in all operations first check whether list is null, and if so
      //       throw an exception. This would add some code, complexity and
      //       inefficiency, though it would still be good practice (we just
      //       choose not to do it here to make the methods slightly simpler).
    }


    @Override
    public Node<Item> next()
    {
      // NOTE: We store a pointer to the next node, so just return it for an
      //       O(1) implementation of next(), unlike our O(n) previous().
      return this.next;
    }


    @Override
    public void insertPrevious(Item item)
    {

      Node<Item> nodeBefore = this.previous();
      Node<Item> nodeToAdd = new Node<Item>(this.list, item, this);

      if (this.list.first == this) {
        this.list.first = nodeToAdd;
      }

      if(nodeBefore != null){
        //make previous node point to the new node
        nodeBefore.next = nodeToAdd;
      }

      this.list.size = this.list.size() + 1;
    }


    @Override
    public void insertNext(Item item)
    {
      this.list.size = this.list.size() + 1;

      Node<Item> nodeAfter = this.next();
      //"this" is the current node
      Node<Item> nodeToAdd = new Node<Item>(this.list, item, nodeAfter);

      if (this.list.last == this) {
        this.list.last = nodeToAdd;
      }
      this.next = nodeToAdd;


    }


    @Override
    public Item remove()
    {
      // NOTE: If this was the first node, there won't be any previous node's
      //       next links to update.
      if (this.isFirst())
      {
        this.list.first = this.next;
        if (this.list.first == null)
        {
          this.list.last = null;
        }
        this.list.size--;
        return this.item;
      }

      // NOTE: Otherwise, we will need to update the previous node's successor
      //       to be ours (as well as updating the list's last node if this was,
      //       and updating size). We do all this in removeNext(), though, so
      //       rather than implement it twice, we just call that.
      // NOTE: previous() (and thus remove()) is O(n)) but removeNext() is O(1).
      return this.previous().removeNext();
    }


    @Override
    public Item removePrevious()
      throws NoSuchElementException
    {
      // NOTE: Instead of all this we could do essentially previous().remove(),
      //       but that would end up traversing the list twice (first to get the
      //       previous node, then in remove() to get its previous node), which
      //       feels needlessly inefficient. We therefore provide a one-pass
      //       implementation here. Both approaches are O(n) however.

      if (this.isFirst())
      {
        throw new NoSuchElementException();
      }

      Node<Item> node = this.list.first;
      if (node.next == this)
      {
        this.list.first = this;
        this.list.size--;
        return node.item;
      }

      while (node.next.next != this)
      {
        node = node.next;
      }
      return node.removeNext();
    }


    @Override
    public Item removeNext()
      throws NoSuchElementException
    {
      if (this.isLast())
      {
        throw new NoSuchElementException();
      }

      Item itemToReturn = this.next.item;

      //if the removed item is last, make this node last
      if(this.next.isLast()){
        this.list.last = this;

        this.next = null;
      }
      else{ //else the last item remains the same and make this point to the
        // next item in the list after the removed item.
        this.next = this.next.next;

      }

      this.list.size--;
      return itemToReturn;
    }

  }


  //<editor-fold defaultstate="collapsed" desc="Iteration">


  // NOTE: SinglyLinkedList.reversed() will use the LinkedList.reversed()
  //       implementation which starts from the last node and repeatedly calls
  //       previous(), which for singly-linked lists is O(n), making it O(n^2)
  //       overall. We don't implement it here, but FYI it's possible to
  //       implement O(n) reverse-iteration over singly-linked lists if you
  //       first create a reversed copy of the list by doing essentially:
  //         SinglyLinkedList<Item> copy = new SinglyLinkedList();
  //         for (Item item : list)
  //         {
  //           copy.insertFirst(item);
  //         }
  //       which is O(n), and then simply iterating over that reversed copy,
  //       which is also O(n). This has two disadvantages, the first being that
  //       it requires O(n) additional storage for the copy, and the second
  //       being that you're iterating over a copy rather than the list itself,
  //       though that doesn't matter if you're not making any changes to the
  //       list or copy during the iteration. It also only really works for
  //       reversed(), not reversedNodes(), as the nodes yielded would be
  //       different nodes in the temporary copy rather than in the actual list.


  //</editor-fold>

}
