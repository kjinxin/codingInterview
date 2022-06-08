package databricks;

import java.util.*;

public class MyBlockList<T> {
    private int defaultSize;
    private class Block<T> {
        private List<T> data;
        private Block<T> next;

        public Block() {
            data = new ArrayList<>();
        }
        public Block(List<T> data) {
            this.data = data;
        }
        public int size() {
            return data.size();
        }
        public T get(int pos) {
            return data.get(pos);
        }
        public void add(int pos, T val) {
            data.add(pos, val);
        }
        public T remove(int pos) {
            return data.remove(pos);
        }
        public void split(int newSize) {
            List<T> nextData = new ArrayList<>(data.subList(newSize, data.size()));
            List<T> tmp = new ArrayList<>(data.subList(0, newSize));
            data.clear();
            data = tmp;
            Block<T> newBlock = new Block(nextData);
            newBlock.next = this.next;
            this.next = newBlock;
        }
    }
    private int size;
    private Block<T> headBlock;
    public MyBlockList(int defaultSize) {
        this.defaultSize = defaultSize;
        this.size = 0;
        this.headBlock = new Block();
    }

    public void add(int pos, T val) {
        if (this.size < pos) {
            // throw exception
        }
        int[] posArr = new int[] {pos};
        Block<T> cur = getBlock(posArr);
        cur.add(posArr[0], val);
        this.size ++;
        maintainBlock(cur);
    }

    public T remove(int pos) {
        if (this.size < pos + 1) {
            // throw exception
        }
        int[] posArr = new int[]{pos};
        Block<T> cur = getBlock(posArr);
        T res = cur.remove(posArr[0]);
        this.size --;
        return res;
    }

    public T get(int pos) {
        if (this.size < pos + 1) {
            // throw exception
        }
        int[] posArr = new int[]{pos};
        Block<T> cur = getBlock(posArr);

        return cur.get(posArr[0]);
    }

    private Block<T> getBlock(int[] posArr) {
        int pos = posArr[0];
        int cnt = 0;
        Block<T> cur = headBlock;
        while(cur != null && cnt + cur.size() < pos + 1) {
            cnt += cur.size();
            if (cur.next == null) {
                // means pos == this.size
                posArr[0] = cur.size();
                return cur;
            }
            cur = cur.next;
        }
        posArr[0] = pos - cnt;
        return cur;
    }
    private int getBlockSize() {
        int blockSize = (int) Math.sqrt(size);
        return blockSize < defaultSize ? defaultSize : blockSize;
    }
    private void maintainBlock(Block<T> block) {
        int blockSize = getBlockSize();
        if (block.size() > 2 * blockSize) {
            block.split(blockSize);
        }
    }
    public void print() {
        Block block = headBlock;
        while(block != null) {
            System.out.println("start of Block");

            for (int i = 0; i < block.size(); i ++) {
                System.out.println(block.get(i));
            }
            block = block.next;
            System.out.println("end of Block");
        }
    }
    public static void main(String[] args) {
        MyBlockList<Integer> myBlockList = new MyBlockList<>(2);
        for (int i = 0; i < 8; i ++) {
            myBlockList.add(i, i);
        }
        for (int i = 0; i < 8; i ++) {
            System.out.println(myBlockList.get(i));
        }
        myBlockList.print();

        for (int i = 7; i > 3; i --) {
            System.out.println(myBlockList.remove(i));
        }

        for (int i = 0; i < 4; i ++) {
            System.out.println(myBlockList.get(i));
        }
        myBlockList.print();
    }
}
