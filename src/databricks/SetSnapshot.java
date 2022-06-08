package databricks;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

interface SnapshotSet<T> {
    void add(T e);
    void remove(T e);
    boolean contains(T e);
    Iterator<T> iterator();
}
public class SetSnapshot<T> implements SnapshotSet<T>{
    private Map<T, Boolean> cur;
    private Map<T, Boolean> snapshot;

    public SetSnapshot() {
        cur = new HashMap<>();
        snapshot = new HashMap();
    }
    @Override
    public void add(T e) {
        cur.put(e, true);
    }

    @Override
    public void remove(T e) {
        if (cur.containsKey(e)) {
            cur.remove(e);
        }
        if (snapshot.containsKey(e)) {
            snapshot.put(e, false);
        }
    }

    @Override
    public boolean contains(T e) {
        return cur.containsKey(e) || snapshot.containsKey(e) && snapshot.get(e);
    }

    @Override
    public Iterator<T> iterator() {
        if (snapshot.size() > 0) {
            // throw exception as there is already an iterator
            // or create a new one and remove the initial one.
        } else {
            snapshot = cur;
            cur = new HashMap();
            Iterator<T> iterator = new SnapshotIterator(snapshot, cur);
            return iterator;
        }
        return null;
    }

    private class SnapshotIterator implements Iterator<T> {
        private Map<T, Boolean> snapshot;
        private Map<T, Boolean> cur;
        private Iterator<T> it;
        public SnapshotIterator(Map<T, Boolean> snapshot, Map<T, Boolean> cur) {
            this.snapshot = snapshot;
            this.cur = cur;
            this.it = snapshot.keySet().iterator();
        }
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public T next() {
            T res = it.next();
            if (snapshot.get(res)) {
                cur.put(res, true);
                System.out.printf("cur size %d %n", cur.size());
            }
            it.remove();
            return res;
        }
    }

    public static void main(String[] args) {
        SetSnapshot<Integer> setSnapshot = new SetSnapshot<>();
        setSnapshot.add(1);
        setSnapshot.add(2);
        setSnapshot.add(3);
        Iterator<Integer> it = setSnapshot.iterator();
        setSnapshot.remove(2);
        setSnapshot.add(2);
        setSnapshot.remove(2);
        while(it.hasNext()) {
            System.out.println(it.next());
        }
        setSnapshot.add(4);
        System.out.println("hh");
        it = setSnapshot.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
