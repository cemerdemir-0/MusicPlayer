import java.util.NoSuchElementException;

public class HW02 {
    public static void main(String[] args) {

        MusicPlayer mp = new MusicPlayer("./Musics");
                
    }
}

interface INode <T> { // storage unit
    // Constructor (T data, Node<T> prev, Node<T> next)
    T getData(); // returns the data
    Node<T> getNext(); // returns the next of this storage unit
    Node<T> getPrev(); // returns the previous storage unit of this unit
    void setNext(Node<T> next); // sets next pointer of this node
    void setPrev(Node<T> prev); // sets the prev pointer of this node
    String toString(); // string representation
}

interface IDoublyCircularLinkedList <T> {
    // must have the data field current
    // Constructor ()
    void addFirst(T data); // adds an element to the head of the list. If first element in list, must also be last element
    // if only element in the list its next and prev should point to itself
    void addLast(T data); // adds an element to the tail of the list. If first element in list, must also be last element
    // if only element in the list its next and prev should point to itself
    T removeFirst() throws NoSuchElementException; // removes the first element in the list,
    // throw exception if list is empty, if only element remaining it should be first and last and its next and prev,
    // should be itself
    T removeLast() throws NoSuchElementException; // removes the last element in the list,
    // throw exception if list is empty, if only element remaining it should be first and last and its next and prev,
    // should be itself
    T get(int index) throws IndexOutOfBoundsException; // gets the ith element in the list,
    // should throw exception if out of bounds
    T first() throws NoSuchElementException; // should set current, returns the first data
    T last() throws NoSuchElementException; // should set current, returns the last data
    boolean remove(T data); // should return false if data doesnt exists, returns true and removes if exists
    boolean isEmpty();
    int size();
    T next() throws NoSuchElementException; // if empty, throws exception, should change current correctly
    // if current is null should return head and set it to head
    T previous() throws NoSuchElementException; // if empty, throws exception, should change current correctly
    // if current is null should return tail data and set it
    T getCurrent() throws NoSuchElementException; // Retruns the current pointer, if no element exits throws exception
    // if current is null returns heads data
    Node<T> getHead(); // returns the head of the list, if is empty returns null
    // any other method needed

}

class Node<T> implements INode<T> {
    private T data;
    private Node<T> next;
    private Node<T> prev;
    public Node(T data, Node<T> prev, Node<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
    @Override
    public T getData() {
        return data;
    }

    @Override
    public Node<T> getNext() {
        return next;
    }

    @Override
    public Node<T> getPrev() {
        return prev;
    }

    @Override
    public void setNext(Node<T> next) {
        this.next = next;
    }

    @Override
    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Data şudur: " + data;
    }
}

class DoublyCircularLinkedList<T> implements IDoublyCircularLinkedList<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;
    public DoublyCircularLinkedList(){
        size = 0;
        head = null;
        tail = null;
        current = null;
    }
    @Override
    public void addFirst(T data) {
        Node<T> element = new Node<>(data,null,null);
        if(size == 0){
            head = element;
            tail = element;
            head.setNext(head);
            head.setPrev(head);
        }
        else{
            tail.setNext(element);
            element.setPrev(tail);
            element.setNext(head);
            head.setPrev(element);
            head = element;
        }
        size++;
    }

    @Override
    public void addLast(T data) {
        Node<T> element = new Node<>(data, null, null);
        if(size == 0){
            head = element;
            tail = element;
            head.setPrev(head);
            head.setNext(head);
        }

        else{
            tail.setNext(element);
            element.setPrev(tail);
            element.setNext(head);
            head.setPrev(element);
            tail = element;
        }
        size++;
    }

    @Override
    public T removeFirst() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException("Liste boş, başta eleman yok.");
        }
        else if(size() == 1){
            Node<T> temp = head;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        }
        else{
            Node<T> temp = head;
            T tempData = temp.getData();
            head = head.getNext();
            head.setPrev(tail);
            tail.setNext(head);
            size--;
            return tempData;
        }
    }

    @Override
    public T removeLast() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException("Liste boş, sonda eleman yok.");
        }
        else if(size() == 1){
            Node<T> temp = head;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        }
        else{
            Node<T> temp = tail;
            T tempData = tail.getData();
            tail = tail.getPrev();
            tail.setNext(head);
            head.setPrev(tail);
            size--;
            return tempData;
        }
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException("Geçersiz index girdiniz.");
        }
        else{
            Node<T> temp = head;
            for(int i = 0; i < index; i++){
                temp = temp.getNext();
            }
            return temp.getData();
        }
    }

    @Override
    public T first() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException("Liste boş, ilk eleman yok.");
        }
        current = head;
        return head.getData();
    }

    @Override
    public T last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Liste boş, ilk eleman yok.");
        }
        current = tail;
        return tail.getData();
    }
    @Override
    public boolean remove(T data) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (temp.getData().equals(data)) {

                if (temp == head) {
                    head = head.getNext();
                    tail.setNext(head);
                    head.setPrev(tail);
                } else if (temp == tail) {
                    tail = tail.getPrev();
                    tail.setNext(head);
                    head.setPrev(tail);
                } else {
                    temp.getPrev().setNext(temp.getNext());
                    temp.getNext().setPrev(temp.getPrev());
                }

                size--;
                return true;
            }

            temp = temp.getNext();
        }
        return false;
    }
    @Override
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Liste boş.");
        }
        if (current == null) {
            current = head;
            return head.getData();
        }
        current = current.getNext();
        return current.getData();
    }

    @Override
    public T previous() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Liste boş.");
        }
        if (current == null) {
            current = tail;
            return tail.getData();
        }
        current = current.getPrev();
        return current.getData();
    }

    @Override
    public T getCurrent() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Liste boş.");
        }
        if (current == null) {
            current = head;
            return head.getData();
        }
        return current.getData();
    }

    @Override
    public Node<T> getHead() {
        if(size == 0){
            return null;
        }
        else{
            return head;
        }
    }
}
