package roblox;

import java.util.HashMap;
import java.util.Map;

public class ChatPlayers {
    public static int chatPlayer(String chat) {
        int[] cnt = new int[4];
        Map<Character, Integer> mp = new HashMap();
        mp.put('c', 0);
        mp.put('h', 1);
        mp.put('a', 2);
        mp.put('t', 3);
        int res = 0;
        for (int i = 0; i < chat.length(); i ++) {
            char op = chat.charAt(i);
            if (!mp.containsKey(op)) {
                return -1;
            } else {
                int idx = mp.get(op);
                cnt[idx] ++;
                if (idx == 0) {
                    res = Math.max(cnt[idx], res);
                } else if (cnt[idx] > cnt[idx - 1]) {
                    return -1;
                } else if (idx == 3) {
                    for (int j = 0; j < 4; j ++) {
                        cnt[j] --;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String args[]) {
        System.out.println(chatPlayer("chatchat"));
        System.out.println(chatPlayer("chcathat"));
        System.out.println(chatPlayer("chatchht"));
    }
}
