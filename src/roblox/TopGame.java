package roblox;
/*
We want to know what the Top Game is, defined by: The Top Game is the game users spent the most time in.
Each line of the file has the following information (comma separated):
- timestamp in seconds (long)
- user id (string)
- game id (int)
- action (string, either "join" or "quit")
e.g.
[
"1000000000,user1,1001,join", // user 1 joined game 1001
"1000000005,user2,1002,join", // user 2 joined game 1002
"1000000010,user1,1001,quit", // user 1 quit game 1001 after 10 seconds
"1000000020,user2,1002,quit", // user 2 quit game 1002 after 15 seconds
];
In this log,
The total time spent in game 1001 is 10 seconds.
The total time spent in game 1002 is 15 seconds.
Hence game 1002 is the Top Game. -> 1002
This file could be missing some lines of data (e.g. the user joined, but then the app crashed).
If data for a session (join to quit) is incomplete, please discard the session.
To recover some data, we attempt to estimate session length with this rule:
If a user joined a game but did not leave, assume they spent the minimum of
- time spent before they joined a different game; and
- average time spent across the same user's gaming sessions (from join to leave)
e.g.
"1500000000,user1,1001,join"
"1500000010,user1,1002,join"
"1500000015,user1,1002,quit"
The user spent 5 seconds in game 2, so we assume they spent 5 seconds in game 1.
Write a function that returns the top game ID, given an array of strings representing
each line in the log file.
 */

import java.util.*;

class UserLog {
    public String game;
    public long time;
    public UserLog(String game, long time) {
        this.game = game;
        this.time = time;
    }
}
public class TopGame {
    Map<String, UserLog> userPrevLog = new HashMap();
    Map<String, List<UserLog>> userGameTime = new HashMap();
    Map<String, List<UserLog>> userRecoveredTime = new HashMap<>();
    Map<String, Long> gameTime = new HashMap();
    long maxGameTime = 0L;
    String res;
    public String processLogs(String[] logs) {
        for (String log : logs) {
            String[] logData = log.split(",");
            System.out.println(logData[0]);
            long time = Long.parseLong(logData[0]);
            String user = logData[1];
            String game = logData[2];
            String state = logData[3];
            if (state.equals("join")) {
                if (userPrevLog.containsKey(user)) {
                    UserLog prevLog = userPrevLog.get(user);
                    if (!prevLog.game.equals(game)) {
                        if (!userRecoveredTime.containsKey(user)) {
                            userRecoveredTime.put(user, new ArrayList());
                        }
                        userRecoveredTime.get(user).add(new UserLog(prevLog.game, time - prevLog.time));
                        System.out.println(time - prevLog.time);
                    }
                }
                userPrevLog.put(user, new UserLog(game, time));
            } else {
                if (userPrevLog.containsKey(user)) {
                    UserLog prevLog = userPrevLog.get(user);
                    if (prevLog.game.equals(game)) {
                        if (!userGameTime.containsKey(user)) {
                            userGameTime.put(user, new ArrayList());
                        }
                        System.out.println(time - prevLog.time);
                        userGameTime.get(user).add(new UserLog(game, time - prevLog.time));
                    }
                    userPrevLog.remove(user);
                }
            }
        }
        for (String user : userGameTime.keySet()) {
            long aveTime = calculateAveTime(userGameTime.get(user));
            for (UserLog userlog : userRecoveredTime.getOrDefault(user, new ArrayList<>())) {
                System.out.println(user);
                System.out.println(userlog.game);
                userlog.time = Math.min(userlog.time, aveTime);
                System.out.println(userlog.time);
            }
        }

        for (List<UserLog> userLogs : userGameTime.values()) {
            calculateGameTime(userLogs);
        }
        for (List<UserLog> userLogs : userRecoveredTime.values()) {
            calculateGameTime(userLogs);
        }
        for (String game : gameTime.keySet()) {
            System.out.println(gameTime.get(game));
        }
        return this.res;
    }

    public void calculateGameTime(List<UserLog> userLogs) {
        System.out.println(userLogs.size());
        for (UserLog userLog : userLogs) {
            System.out.println(userLog.game);
            gameTime.put(userLog.game, gameTime.getOrDefault(userLog.game, 0L) + userLog.time);
            if (gameTime.get(userLog.game) > this.maxGameTime) {
                this.maxGameTime = gameTime.get(userLog.game);
                this.res = userLog.game;
            }
        }
    }
    private long calculateAveTime(List<UserLog> games) {
        long res = 0;
        for (UserLog userLog : games) {
            res += userLog.time;
        }
        return res / games.size();
    }

    public static void main(String[] args) {
        String[] logs = new String[]{"1000000000,user1,1001,join",
                "1000000005,user2,1002,join",
                "1000000010,user1,1001,quit", "1000000020,user2,1002,quit",
                "1000000030,user1,1001,join",
                "1000000050,user1,1002,join",
                };
        TopGame topGame = new TopGame();
        System.out.println(topGame.processLogs(logs));
    }
}
