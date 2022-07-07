package rippling;

import java.util.*;

/*
part 1.
给个height和width，画出一副都是空的矩阵用‘0’ 表示每个空白的坐标
part 2.
给出颜色比如 ‘C’ 起始位置(0,0) 和 大小 (3x5) ， 重新渲染canvas
可以有多个覆盖
part3.
如果第二部分的颜色矩阵可以移动，需要重新渲染canvas

1. 给定一个m，n，打印一个m*n的矩阵。
m=4，n=7, 画出：
.......
.......
.......
.......
解决方法：弄个class Canvas，存矩阵大小，加print 函数
2. 给定一些方块（每个方块有名字，起始位置和大小），要求在矩阵里画出来, 后面加的方块可能覆盖前面的。
起始矩阵大小m=4, n=7
add_rect('a', 0, 0, 2, 2)
add_rect('b', 1, 1, 1, 2)
画出
aa . . . . .
abb. . . .
. . . . . . .
. . . . . . .
解决方法：加Rect class，在 Canvas 加add_rect 方法，用一个数组存所有rect （我们需要保留他们被加入的顺序）
3. 移动方块到新的位置，如果之前覆盖了方块，挪开以后还得显示。
move_rect('b', 2，0）
aa . . . . .
aa . . . . .
bb. . . . .
. . . . . . .
解决方法：加一个哈希表存name_2_index，加一个move_rect方法，根据哈希表找到数组里的rect，改位置
4. 挪动方块后如果方块的位置超过了一开始矩阵的外延，打印以后整个矩阵要相应变大。
 */
public class Canvas {
    private class Rect {
        public String name;
        public int x1, x2, y1, y2;
        public Rect(String name, int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.name = name;
        }

        public boolean contains(int x, int y) {
            return x1 <= x && x2 >= x && y1 <= y && y2 >= y;
        }
    }
    private int m, n;
    private int x1, y1, x2, y2;
    private List<Rect> rects;
    private Map<String, Integer> rectMap;
    public Canvas(int m, int n) {
        this.m = m;
        this.n = n;
        this.rects = new ArrayList();
        this.rectMap = new HashMap<>();
    }

    private void calculateCanvasSize() {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = m;
        this.y2 = n;
        for (Rect rect : rects) {
            this.x1 = Math.min(this.x1, rect.x1);
            this.y1 = Math.min(this.y1, rect.y1);
            this.x2 = Math.max(this.x2, rect.x2 + 1);
            this.y2 = Math.max(this.y2, rect.y2 + 1);
        }
    }
    public void printCanvas() {
        calculateCanvasSize();
        for (int i = this.x1; i < this.x2; i ++) {
            for (int j = this.y1; j < this.y2; j ++) {
                boolean flag = false;
                for (int k = rects.size() - 1; k >= 0; k --) {
                    Rect rect = rects.get(k);
                    if (rect.contains(i, j)) {
                        flag = true;
                        System.out.print(rect.name);
                        break;
                    }
                }
                if (!flag) {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void addRect(String name, int x1, int y1, int x2, int y2) {
        this.rects.add(new Rect(name, x1, y1, x2, y2));
        this.rectMap.put(name, this.rects.size() - 1);
    }

    public void moveRect(String name, int nX1, int nY1) {
        Rect rect = this.rects.get(this.rectMap.get(name));
        int nX2 = rect.x2 + (nX1 - rect.x1), nY2 = rect.y2 + (nY1 - rect.y1);
        rect.x1 = nX1;
        rect.y1 = nY1;
        rect.x2 = nX2;
        rect.y2 = nY2;
    }
    public static void main(String[] args) {
        Canvas canvas = new Canvas(4, 7);
        canvas.printCanvas();
        canvas.addRect("a", 0, 0, 1, 1);
        canvas.addRect("b", 1, 1, 1, 2);
        canvas.printCanvas();
        canvas.moveRect("b", 2, 0);
        canvas.printCanvas();
        canvas.addRect("c", 3, 6, 8, 9);
        canvas.printCanvas();
    }
}
