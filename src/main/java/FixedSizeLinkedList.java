import java.util.LinkedList;

public class FixedSizeLinkedList<E> extends LinkedList<E> {
    private int capasity = 10;

    public FixedSizeLinkedList(int capasity) {
        super();
        this.capasity = capasity;
    }

    @Override
    public boolean add(E e) {
        if (size() >= capasity) {
            removeFirst();
        }
        return  super.add(e);
    }
}
