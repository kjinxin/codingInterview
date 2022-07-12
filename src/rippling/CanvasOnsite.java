package rippling;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

// create a MxN blank canvas
// display the canvas to the user
// Example:
// A canvas of 10x25 would look like this. String “0” means a white space.

// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0

// a a a a a a 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// a a a a a a 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// a a a a a c c c c c 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// a a a a a c c c c c 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
// 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 b b b b
// 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 b b b b
// 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 b b b b
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b

/*
input: size of the canvas, m * n
    draw rectangle with order  (x1, y1), (h, w)
output:
    printTheCanvas
*/

public class CanvasOnsite {
    private class Rect {
        public Character name;
        public int x1, x2, y1, y2;
        public Rect(Character name, int x1, int y1, int h, int w) {
            this.name = name;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x1 + h - 1;
            this.y2 = y1 + w - 1;
        }
        public boolean contains(int x, int y) {
            return x1 <= x && x2 >= x && y1 <= y && y2 >= y;
        }
    }
    private int m, n;
    private int x1, x2, y1, y2;
    private List<Rect> rects;
    private Map<Character, Rect> rectMap;

    public CanvasOnsite(int m, int n) {
        this.m = m;
        this.n = n;
        this.rects = new ArrayList<Rect>();
        this.rectMap = new HashMap<Character, Rect>();
    }

    public void addRect(Character name, int x1, int y1, int h, int w) {
        this.rects.add(new Rect(name, x1, y1, h, w));
        this.rectMap.put(name, this.rects.get(this.rects.size() - 1));
    }

    public void moveRect(Character name, int x1, int y1) {

        // add check if name exists, otherwise throw exception
        Rect rect = this.rectMap.get(name);
        int nX2 = rect.x2 + (x1 - rect.x1), nY2 = rect.y2 + (y1 - rect.y1);
        rect.x1 = x1;
        rect.y1 = y1;
        rect.x2 = nX2;
        rect.y2 = nY2;
        // switch the order
        int idx = -1;
        for (int i = 0; i < rects.size(); i ++) {
            if (rects.get(i).name.equals(name)) {
                idx = i;
                break;
            }
        }
        rects.remove(idx);
        rects.add(rect);
        rectMap.put(name, rect);
    }

    private void calculateBoundary() {
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
    public void printCavas() {
        calculateBoundary();
        for (int i = x1; i < x2; i ++) {
            for (int j = y1; j < y2; j ++) {
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
                    System.out.print("0");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

// 'a' is 4x6 at (0,0)

// 'b' is 6x4 at (4,21)

    // 'c' is 5x5 at (2,5)
    public static void main(String[] args) {
        CanvasOnsite so = new CanvasOnsite(10, 25);
        so.addRect('a', 0, 0, 4, 6);
        so.addRect('b', 4, 21, 6, 4);
        so.addRect('c', 2, 5, 5, 5);
        //so.printCavas();

        so.moveRect('c', 2, 7);
        //so.printCavas();
        so.addRect('d', 9, 24, 5, 5);
        so.moveRect('a', -1, -1);
        so.printCavas();
    }
}
