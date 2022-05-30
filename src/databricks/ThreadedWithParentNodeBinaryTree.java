package databricks;

import java.util.ArrayList;
import java.util.List;

/*
1. Initialize current node as root
2. Initialize a flag: leftdone = false;
3. Do following while root is not NULL
     a) If leftdone is false, set current node as leftmost
        child of node.
     b) Mark leftdone as true and print current node.
     c) If right child of current nodes exists, set current
        as right child and set leftdone as false.
     d) Else If parent exists, If current node is left child
        of its parent, set current node as parent.
        If current node is right child, keep moving to ancestors
        using parent pointer while current node is right child
        of its parent.
     e) Else break (We have reached back to root)
 */
class TreeNodeParent {
    int val;
    TreeNodeParent left;
    TreeNodeParent right;
    TreeNodeParent parent;
    TreeNodeParent() {}
    TreeNodeParent(int val) { this.val = val; }
    TreeNodeParent(int val, TreeNodeParent left, TreeNodeParent right, TreeNodeParent parent) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
}


public class ThreadedWithParentNodeBinaryTree {
    public List<Integer> inorderTraversal(TreeNodeParent root) {
        List<Integer> res = new ArrayList();

        TreeNodeParent cur = root;
        boolean leftVisited = false;
        while(cur != null) {
            if (!leftVisited) {
                while (cur.left != null) {
                    cur = cur.left;
                }
            }
            leftVisited = true;
            res.add(cur.val);
            if (cur.right != null) {
                cur = cur.right;
                leftVisited = false;
            } else {
                // find the curNode's predecessor
                while(cur.parent != null && cur == cur.parent.right) {
                    cur = cur.parent;
                }
                cur = cur.parent;
            }
        }
        return res;
    }

    public static void main(String[] args)
    {
        ThreadedWithParentNodeBinaryTree tree = new ThreadedWithParentNodeBinaryTree();
        TreeNodeParent root = new TreeNodeParent(4);
        TreeNodeParent left = new TreeNodeParent(2);
        TreeNodeParent right = new TreeNodeParent(6);
        TreeNodeParent leftLeft = new TreeNodeParent(1);
        TreeNodeParent leftRight = new TreeNodeParent(3);
        TreeNodeParent rightLeft = new TreeNodeParent(5);
        TreeNodeParent rightRight = new TreeNodeParent(7);
        root.left = left;
        root.right = right;
        left.parent = root;
        right.parent = root;
        left.left = leftLeft;
        left.right = leftRight;
        leftLeft.parent = left;
        leftRight.parent = left;
        right.left = rightLeft;
        right.right = rightRight;
        rightLeft.parent = right;
        rightRight.parent = right;
        List<Integer> res = tree.inorderTraversal(root);
        for (Integer i : res) {
            System.out.println(i);
        }
    }
}
