import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item) {
            ensuresNotNullItem(item);
            this.item = item;
        }

        private void ensuresNotNullItem(Item item) {
            if(item == null) {
                throw new NullPointerException();
            }
        }
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        Node node = new Node(item);

        if(isEmpty()){
            first = node;
            last = node;
        }else {
            node.next = first;
            first.prev = node;

            first = node;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        Node node = new Node(item);

        if(isEmpty()){
            first = node;
            last = node;
        }else {

            node.prev = last;
            last.next = node;

            last = node;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        ensuresNotEmpty();

        Item item = first.item;

        size--;
        if(isEmpty()){
            first = null;
            last = null;
        }else {

            Node newFirst = first.next;
            newFirst.prev = null;

            first.next = null;
            first = newFirst;
        }


        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        ensuresNotEmpty();;

        Item item = last.item;

        size--;
        if(isEmpty()){
            first = null;
            last = null;
        }else {

            Node newLast = last.prev;
            newLast.next = null;
        }

        return item;
    }

    private void ensuresNotEmpty() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {

        private Node node;

        public DequeIterator(Node node){
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Item next() {
            Item item = (Item)node.item;
            node = node.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();

        assert deque.isEmpty() == true;
        assert deque.size() == 0;

        deque.addFirst(1);
        assert  deque.isEmpty() == false;
        assert  deque.size() == 1;

        deque.addFirst(2);
        assert deque.removeFirst() == 2;
        assert deque.isEmpty() == false;
        assert deque.size() == 1;

        assert deque.removeFirst() == 1;
        assert deque.isEmpty() == true;
        assert deque.size() == 0;

        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);

        assert deque.isEmpty() == false;
        assert deque.size() == 3;

        assert deque.removeLast() == 3;
        assert deque.removeLast() == 2;
        assert deque.removeLast() == 1;

        assert deque.isEmpty() == true;
        assert deque.size() == 0;

        //Iterator

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        Iterator<Integer> it = deque.iterator();

        assert it.hasNext() == true;
        assert it.next() == 1;
        assert it.hasNext() == true;
        assert it.next() == 2;
        assert it.hasNext() == true;
        assert it.next() == 3;
        assert it.hasNext() == false;

        //boundaries
        boolean failed = false;
        try {
            it.remove();
        }catch(UnsupportedOperationException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        failed = false;
        try{
            it.next();
        }catch(NoSuchElementException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        failed = false;
        try{
            deque.addFirst(null);
        }catch(NullPointerException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        failed = false;
        try{
            deque.addLast(null);
        }catch(NullPointerException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        failed = false;
        try{
            deque.removeFirst();
        }catch(NoSuchElementException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        failed = false;
        try{
            deque.removeLast();
        }catch(NoSuchElementException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        System.out.println("the end");
    }
}
