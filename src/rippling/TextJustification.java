package rippling;
import java.util.*;
/*
https://www.1point3acres.com/bbs/thread-892187-1-1.html
就是Text Justification。 不过最后一行没有向左靠齐，最后一行的处理跟之前的行没有不同
https://leetcode.com/problems/text-justification/
 */
public class TextJustification {
    public List<String> fullJustify(String[] words, int maxWidth) {
        int n = words.length, idx = 0;
        List<String> res = new ArrayList();
        while(idx < n) {
            List<String> tmp = new ArrayList();
            int curLen = 0;
            while(idx < n && curLen + words[idx].length() + tmp.size() <= maxWidth) {
                curLen += words[idx].length();
                tmp.add(words[idx ++]);
            }
            StringBuilder sb = new StringBuilder();
            int wordNum = tmp.size();
            sb.append(tmp.get(0));
            // last line or a line with only 1 word
            if (idx == n || wordNum == 1) {
                for (int i = 1; i < wordNum; i ++) {
                    sb.append(" " + tmp.get(i));
                }
                sb.append(" ".repeat(maxWidth - (curLen + wordNum - 1)));
            } else {
                int spaceForRight = (maxWidth - curLen) / (wordNum - 1);
                int leftOver = (maxWidth - curLen) % (wordNum - 1);
                for (int i = 1; i < wordNum; i ++) {
                    sb.append(" ".repeat(leftOver-- > 0 ? spaceForRight + 1 : spaceForRight));
                    sb.append(tmp.get(i));
                }
            }
            res.add(sb.toString());
        }
        return res;
    }
}
