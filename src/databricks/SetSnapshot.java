package databricks;

import java.util.*;
import java.util.function.Function;
/*
1) the version the value was stored.
2) the version the value was deleted.
https://leetcode.com/problems/snapshot-array/
 */

interface SnapshotSet<T> {
    void add(T e);
    void remove(T e);
    boolean contains(T e);
    Iterator<T> iterator();
}


public class SetSnapshot<T> implements SnapshotSet<T>{
    private Map<T, Boolean> cur;
    private Map<T, Boolean> snapshot;

    private LinkedHashMap<T, Boolean> snapshotSet;
    T prevKey = null;
    public SetSnapshot() {
        cur = new HashMap<>();
        snapshot = new HashMap();
        snapshotSet = new LinkedHashMap<>();
    }
    @Override
    public void add(T e) {
        cur.put(e, true);
        snapshotSet.put(e, true);
        prevKey = e;
    }

    @Override
    public void remove(T e) {
        cur.put(e, false);
        snapshotSet.put(e, false);
    }

    @Override
    public boolean contains(T e) {
        boolean res = cur.containsKey(e) || snapshot.containsKey(e) && snapshot.get(e);
        return snapshotSet.getOrDefault(e, false);
    }

    @Override
    public Iterator<T> iterator() {
        snapshot.clear(); // remove if it is the first solution
        if (snapshot.size() > 0) {
            // throw exception as there is already an iterator
            // or create a new one and remove the initial one.
        } else {
            System.out.println("good");
            snapshot = cur;
            cur = new HashMap();
            Iterator<T> iterator = new SnapshotIterator(snapshot, cur);

            Iterator<T> iterator1 = new StoppingIterator<>(a -> prevKey.equals(a), snapshotSet);
            return iterator1;
        }
        return null;
    }

    private class StoppingIterator<T> implements Iterator<T> {
        private Function<T, Boolean> stopCheck;
        private LinkedHashMap<T, Boolean> snapshotSet;
        private Iterator<T> iterator;

        private T prev = null;
        public StoppingIterator(Function<T, Boolean> stopCheck, LinkedHashMap<T, Boolean> snapshotSet) {
            this.stopCheck = stopCheck;
            this.iterator = snapshotSet.keySet().iterator();
            this.snapshotSet = snapshotSet;
        }
        @Override
        public boolean hasNext() {
            return iterator.hasNext() && !stopCheck.apply(prev);
        }

        @Override
        public T next() {
            T prev = iterator.next();
            if (!snapshotSet.get(prev)) {
                iterator.remove();
            }
            return prev;
        }
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
                if (!cur.containsKey(res)) {
                    cur.put(res, true);
                }
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
