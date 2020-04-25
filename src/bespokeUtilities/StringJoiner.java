package bespokeUtilities;

import java.util.List;

public class StringJoiner {
    public StringJoiner() {

    }

    public String join(List<String> strsToJoin) {
        StringBuilder out = new StringBuilder();
        for (String str: strsToJoin) {
            out.append(str).append("\n");
        }
        return out.toString();
    }
}
