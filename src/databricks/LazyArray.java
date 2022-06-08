package databricks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class LazyArray<T> {
    private List<T> list;
    private List<Function<T, T>> opList;

    public LazyArray(List<T> list) {
        this.list = list;
        opList = new ArrayList<>();
    }
    LazyArray map(Function<T, T> mapOp) {
        opList.add(mapOp);
        return this;
    }

    int indexOf(T t) {
        for (int i = 0; i < list.size(); i ++) {
            T value = list.get(i);
            for (Function<T, T> mapOp : opList) {
                value = mapOp.apply(value);
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
        Function<Integer, Integer> mapOp1 = a -> {
            atomicInteger.set(atomicInteger.get() + 1);
            return a * 2;
        };
        Function<Integer, Integer> mapOp2 = a -> a + 2;


        System.out.println(new LazyArray<>(originalList).map(mapOp1).map(mapOp2).indexOf(4));

        System.out.println(lazyArray.indexOf(4));
        System.out.println(atomicInteger.get());
    }
}
