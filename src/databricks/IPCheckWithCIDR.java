package databricks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IPCheckWithCIDR {
    private class CidrBlock {
        public String cidr;
        public int ipInt;
        public int mask;
        public int maskLen;
        public int ipWithMask;
        public CidrBlock(String ip) {
            String[] ipSplit = ip.split("/");
            this.cidr = ip;
            this.ipInt = ipToInt(ipSplit[0]);
            maskLen = ipSplit.length == 2 ? Integer.parseInt(ipSplit[1]) : 32;
            // handle 2^32 will exceed int maximum value
            this.mask = maskLen == 32 ? 0xffffffff : ((1 << maskLen) - 1) << (32 - maskLen);
            this.ipWithMask = this.ipInt & this.mask;
        }
    }
    private class TrieNode {
        public boolean isLeaf;
        public TrieNode[] child = new TrieNode[2];
    }
    private List<CidrBlock> cidrChecklist;
    private TrieNode trieNode;

    public void buildTrieTree(List<String> list) {
        for (String cidr : list) {
            CidrBlock cidrBlock = new CidrBlock(cidr);
            TrieNode cur = trieNode;
            for (int i = 0; i < cidrBlock.maskLen; i ++) {
                int curBit = (cidrBlock.ipInt & (1 << (32 - i - 1))) > 0 ? 1 : 0;
                if (cur.child[curBit] == null) {
                    cur.child[curBit] = new TrieNode();
                }
                cur = cur.child[curBit];
            }
            cur.isLeaf = true;
        }
    }
    public IPCheckWithCIDR(List<String> list) {
        trieNode = new TrieNode();
        buildTrieTree(list);
        cidrChecklist = new ArrayList<>();
        for (String cidr : list) {
            CidrBlock cidrBlock = new CidrBlock(cidr);
            cidrChecklist.add(cidrBlock);
        }
    }

    public boolean isAllowed2(String ip) {
        int ipInt = ipToInt(ip);
        TrieNode cur = trieNode;
        for (int i = 0; i < 32; i ++) {
            int curBit = (ipInt & (1 << (32 - i - 1))) > 0 ? 1 : 0;
            if (cur.child[curBit] == null) {
                return true;
            } else {
                cur = cur.child[curBit];
                if (cur.isLeaf) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isAllowed(String ip) {
        int ipInt = ipToInt(ip);
        for (CidrBlock cidrBlock : cidrChecklist) {
            if ((cidrBlock.mask & ipInt) == cidrBlock.ipWithMask) {
                return false;
            }
        }
        return true;
    }

    private int ipToInt(String ip) {
        int res = 0;
        String[] ipSplit = ip.split("\\.");
        if (ipSplit.length != 4) {
            return -1;
        } else {
            for (String s : ipSplit) {
                res = (res << 8) + Integer.parseInt(s);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList(
                "255.255.255.255",
                "255.255.255.210/24",
                "123.123.123.123",
                "123.123.123.124"
        );
        IPCheckWithCIDR ipCheckWithCIDR = new IPCheckWithCIDR(list);
        System.out.println(ipCheckWithCIDR.isAllowed2("255.255.240.1"));
        System.out.println(ipCheckWithCIDR.isAllowed2("255.255.255.1"));
        System.out.println(ipCheckWithCIDR.isAllowed2("123.123.123.123"));
        System.out.println(ipCheckWithCIDR.isAllowed2("123.123.123.125"));
    }

}
