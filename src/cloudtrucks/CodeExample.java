package cloudtrucks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class CodeExample<T> {
    private List<T> list;
    private List<Function<T, T>> opList;

    public CodeExample(List<T> list) {
        this.list = list;
        opList = new ArrayList<>();
    }
    CodeExample map(Function<T, T> mapOp) {
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
        CodeExample<Integer> codeExample = new CodeExample<>(originalList);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        Function<Integer, Integer> mapOp1 = a -> {
            atomicInteger.set(atomicInteger.get() + 1);
            return a * 2;
        };
        Function<Integer, Integer> mapOp2 = a -> a + 2;


        System.out.println(new CodeExample<>(originalList).map(mapOp1).map(mapOp2).indexOf(4));

        System.out.println(codeExample.indexOf(4));
        System.out.println(atomicInteger.get());
    }
}
