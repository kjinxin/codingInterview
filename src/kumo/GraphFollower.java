package kumo;
/*
Implement a graph of follow users like twitter.

We want to support the following functionality:
[1] Add a new user.
[2] Follow another user.
[3] Remove a user.
[4] Find if a user is in the subnet of another user within n steps. That is, if you start a walk from user A, can you reach user B in n steps disregarding the direction of follow edges.
 */
/*
A -> B
directed graph,
addUser(String uid, String userName, ...) {

}

user --> a list of follower
class User {

}
setOfUsers:
adjListDirect: uID --> a list of followers
adjListReverseDirect: uID --> a list of follower

userADirect: B, C, D
userAReverse: F

userB: A
userF: A
 */
import java.util.*;
public class GraphFollower {
    class User {
        public String uId;
        public String firstName;
        public String lastName;
        public User(String uId, String firstName, String lastName) {
            this.uId = uId;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    private Map<String, User> users;
    private Map<String, Set<String>> adjListDirect;
    private Map<String, Set<String>> adjListReverse;

    public GraphFollower() {
        users = new HashMap<>();
    }
    public User addUser(String uId, String firstName, String lastName) {
        // check if uId exists, throw exception if exists
        User user = new User(uId, firstName, lastName);
        users.put(uId, user);
        return user;
    }

    public void followUser(String fromUser, String toUser) {
        Set<String> fromUserAdj = adjListDirect.get(fromUser);
        fromUserAdj.add(toUser);
        Set<String> toUserAdjReverse = adjListReverse.get(toUser);
        toUserAdjReverse.add(fromUser);
    }

    public void removeUser(String uId) {
        if (users.containsKey(uId)) {
            users.remove(uId);
            // remove adjListDirect
            for (String toUser : adjListDirect.get(uId)) {
                adjListReverse.get(toUser).remove(uId);
            }
            // remove adjListReverse
            for (String fromUser : adjListReverse.get(uId)) {
                adjListDirect.get(fromUser).remove(uId);
            }
        }
    }

    public boolean findDistance(String fromUser, String toUser, int dis) {
        if (fromUser.equals(toUser)) {
            return dis >= 0;
        }
        ArrayDeque<String> q = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        q.add(fromUser);
        visited.add(fromUser);
        int curDis = 0;
        while(!q.isEmpty() || curDis < dis) {
            // level scan
            for (int i = q.size(); i > 0; i --) {
                String cur = q.poll();
                // direct
                for (String nextUser : adjListDirect.get(cur)) {
                    if (nextUser.equals(toUser)) {
                        return true;
                    }
                    if (!visited.contains(nextUser)) {
                        q.add(nextUser);
                        visited.add(nextUser);
                    }
                }

                // reverse direct
                for (String nextUser : adjListReverse.get(cur)) {
                    if (nextUser.equals(toUser)) {
                        return true;
                    }
                    if (!visited.contains(nextUser)) {
                        q.add(nextUser);
                        visited.add(nextUser);
                    }
                }
            }
            curDis ++;
        }

        return false;
    }
}
