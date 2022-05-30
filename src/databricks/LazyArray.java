package databricks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LazyArray<T> {
    @FunctionalInterface
    interface MapOp<T> {
        T apply(T t);
    }
    private List<T> list;
    private List<MapOp> opList;

    public LazyArray(List<T> list) {
        this.list = list;
        opList = new ArrayList<>();
    }
    LazyArray map(MapOp mapOp) {
        opList.add(mapOp);
        return this;
    }

    int indexOf(T t) {
        for (int i = 0; i < list.size(); i ++) {
            T value = list.get(i);
            for (MapOp mapOp : opList) {
                value = (T) mapOp.apply(value);
            }
            if (value.equals(t)) {
                return i;
            }
        }
        return -1;
    }
    public void add(T t) {
        list.add(t);
    }


    public static void main(String[] args) {
        List<Integer> originalList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        LazyArray<Integer> lazyArray = new LazyArray<>(originalList);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        MapOp<Integer> mapOp1 = a -> {
            atomicInteger.set(atomicInteger.get() + 1);
            return a * 2;
        };
        MapOp<Integer> mapOp2 = a -> a + 2;


        System.out.println(new LazyArray<>(originalList).map(mapOp1).map(mapOp2).indexOf(4));

        System.out.println(lazyArray.indexOf(4));
        System.out.println(atomicInteger.get());
    }
}
