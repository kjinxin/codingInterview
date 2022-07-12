package databricks.design;
import java.util.*;
public class KVStore {
    private Map<String, String> kv = new HashMap<>();
    /*
    public KVStore(Config config) {
        this.config = config;
        this.wal = WriteAheadLog.openWAL(config);
        this.applyLog();
    }

    public void applyLog() {
        List<WALEntry> walEntries = wal.readAll();
        applyEntries(walEntries);
    }

    private void applyEntries(List<WALEntry> walEntries) {
        for (WALEntry walEntry : walEntries) {
            Command command = deserialize(walEntry);
            if (command instanceof SetValueCommand) {
                SetValueCommand setValueCommand = (SetValueCommand)command;
                kv.put(setValueCommand.key, setValueCommand.value);
            }
        }
    }

    public void initialiseFromSnapshot(SnapShot snapShot) {
        kv.putAll(snapShot.deserializeState());
    }
     */
    public String get(String key) {
        return kv.get(key);
    }

    public void put(String key, String value) {
        appendLog(key, value);
        kv.put(key, value);
    }

    private Long appendLog(String key, String value) {
        //return wal.writeEntry(new SetValueCommand(key, value).serialize());
        return 0L;
    }
}
