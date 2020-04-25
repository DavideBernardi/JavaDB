package bespokeUtilities;

import java.util.HashMap;

public class CaseInsensitiveMap<V> extends HashMap<String, V> {

    public V put(String key, V value) {
        return super.put(key.toLowerCase(), value);
    }

    public V get(String key) {
        return super.get(key.toLowerCase());
    }

    public V remove(String key) {
        return super.remove(key.toLowerCase());
    }

    public boolean containsKey(String key) {
        return super.containsKey(key.toLowerCase());
    }

}
