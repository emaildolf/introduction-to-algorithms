import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static int INITIAL_SIZE = 4;

    private Item[] queue;
    private int last;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[INITIAL_SIZE];
        this.last = -1;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the queue
    public int size() {
        return this.last + 1;
    }

    protected int queueSize() {
        return this.queue.length;
    }

    // add the item
    public void enqueue(Item item) {

        ensuresNotNull(item);

        if(shouldIncreaseQueueSize()){
            increaseQueueSize();
        }

        this.last++;
        queue[last] = item;
    }

    private boolean shouldIncreaseQueueSize() {
        return queue.length-1 == last;
    }

    private void increaseQueueSize() {
        Item[] newQueue = (Item[]) new Object[queueSize()*2];
        for(int i=0; i<queue.length; i++){
            newQueue[i] = queue[i];
        }

        queue = newQueue;
    }

    private void ensuresNotNull(Item item) {
        if(item == null){
            throw new NullPointerException();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        ensureNotEmpty();

        int index = StdRandom.uniform(size());
        Item item = queue[index];

        this.last--;

        if(!isEmpty()){
            queue[index] = queue[last+1];
            queue[last+1] = null;
        }

        if(shouldDecreaseQueueSize()){
            decreaseQueueSize();
        }

        return item;
    }

    private boolean shouldDecreaseQueueSize() {
        return (queue.length)/4 == size();
    }

    private void decreaseQueueSize() {
        Item[] newQueue = (Item[]) new Object[queueSize()/2];
        for(int i=0; i<newQueue.length; i++){
            newQueue[i] = queue[i];
        }

        queue = newQueue;
    }

    // return (but do not remove) a random item
    public Item sample() {
        ensureNotEmpty();
        return queue[StdRandom.uniform(size())];
    }

    private void ensureNotEmpty(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private int[] indexes;
        private int current;

        public RandomizedQueueIterator() {
            indexes = new int[size()];
            for(int i=0; i<size(); i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < indexes.length;
        }

        @Override
        public Item next() {

            if(!hasNext()){
                throw new NoSuchElementException();
            }

            int index = indexes[current];
            current++;

            return (Item)queue[index];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        assert queue.isEmpty() == true;
        assert queue.size() == 0;

        queue.enqueue(1);

        assert queue.isEmpty() == false;
        assert queue.size() == 1;
        assert queue.sample() == 1;
        assert queue.dequeue() == 1;
        assert queue.isEmpty() == true;

        queue.enqueue(0);
        queue.enqueue(1);

        boolean[] itens = new boolean[2];
        itens[queue.dequeue()] = true;
        itens[queue.dequeue()] = true;

        assert queue.isEmpty() == true;
        assert itens[0] == true;
        assert itens[1] == true;

        itens = new boolean[2];
        queue.enqueue(0);
        queue.enqueue(1);
        Iterator<Integer> it = queue.iterator();

        assert it.hasNext() == true;
        itens[it.next()] = true;
        assert it.hasNext() == true;
        itens[it.next()] = true;
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
            queue.enqueue(null);
        }catch(NullPointerException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        queue = new RandomizedQueue<>();

        failed = false;
        try{
            queue.dequeue();
        }catch(NoSuchElementException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        failed = false;
        try{
            queue.sample();
        }catch(NoSuchElementException e){
            failed = true;
        }finally {
            assert failed == true;
        }

        //resize

        assert queue.queueSize() == 4;
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        assert queue.queueSize() == 4;
        queue.enqueue(5);
        assert queue.queueSize() == 8;
        queue.dequeue();
        queue.dequeue();
        assert queue.queueSize() == 8;
        queue.dequeue();
        assert queue.queueSize() == 4;

        System.out.println("the end");
    }
}