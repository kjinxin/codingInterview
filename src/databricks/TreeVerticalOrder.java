package databricks;

import java.util.*;
/*
leetcode
 */
/*
public class TreeVerticalOrder {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        Map<Integer, List<Integer>> myMap = new HashMap();
        Queue<Pair<TreeNode, Integer>> myQueue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList();

        int minOrder = 0, maxOrder = 0;
        if (root == null) {
            return res;
        }
        myQueue.add(new Pair(root, 0));
        while(!myQueue.isEmpty()) {
            Pair<TreeNode, Integer> p = myQueue.remove();
            TreeNode node = p.getKey();
            Integer column = p.getValue();
            if (!myMap.containsKey(column)) {
                myMap.put(column, new ArrayList<Integer>());
            }
            myMap.get(column).add(node.val);
            minOrder = Math.min(minOrder, column);
            maxOrder = Math.max(maxOrder, column);
            if (node.left != null) {
                myQueue.add(new Pair(node.left, column - 1));
            }
            if (node.right != null) {
                myQueue.add(new Pair(node.right, column + 1));
            }
        }
        for (int i = minOrder; i <= maxOrder; i ++) {
            res.add(myMap.get(i));
        }
        return res;
    }
}
 */
