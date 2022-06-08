package databricks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

class Record {
    public String type;
    public Integer value;
    public Record(String type, Integer value) {
        this.type = type;
        this.value = value;
    }
    public Record(String type) {
        this.type = type;
    }
}
public class BatchIterator<T extends Record> {

    public void call(Iterator<T> it) {
        PeekingIterator<T> peekingIterator = new PeekingIterator<T>(it);
        while(peekingIterator.hasNext()) {
            if (peekingIterator.peek().type.equals("start batch")) {
                peekingIterator.next();
                boolean alternative = peekingIterator.peek().type.equals("use alternate process");
                if (alternative) {
                    peekingIterator.next();
                }
                processBatch(new StopIterator<>(peekingIterator, r -> r.type.equals("item")), alternative);
            } else {
                // TODO illegal operation
                System.out.println("it is here");
            }
        }
    }

    private void processBatch(Iterator<Record> it, boolean alternative) {
        while(it.hasNext()) {
            Record r = it.next();
            System.out.printf("process %s %d with %s %n", r.type, r.value, alternative);
        }
    }
    public static void main(String[] args) {
        List<Record> input = Arrays.asList(
          new Record("start batch"),
          new Record("item", 11),
          new Record("item", 55),
          new Record("item", 99),
          new Record("start batch"),
          new Record("use alternate process"),
          new Record("item", 100),
          new Record("item", 50)
        );
        new BatchIterator<Record>().call(input.iterator());
    }
}

class StopIterator<T> implements Iterator<T> {
    private final PeekingIterator<T> iterator;
    private final Function<T, Boolean> condition;
    public StopIterator(PeekingIterator iterator, Function<T, Boolean> condition) {
        this.iterator = iterator;
        this.condition = condition;
    }
    @Override
    public boolean hasNext() {
        return iterator.hasNext() && condition.apply(iterator.peek());
    }

    @Override
    public T next() {
        return iterator.next();
    }
}
class PeekingIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;
    private T cache = null;
    public PeekingIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }
    @Override
    public boolean hasNext() {
        return cache != null || iterator.hasNext();
    }

    @Override
    public T next() {
        if (cache != null) {
            T val = cache;
            cache = null;
            return val;
        } else {
            return iterator.next();
        }
    }
    public T peek() {
        if (cache == null) {
           cache = iterator.next();
        }
        return cache;
    }
}
