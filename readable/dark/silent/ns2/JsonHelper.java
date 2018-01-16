package dark.silent.ns2;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.swiftp.Defaults;

public final class JsonHelper {
    private static Object pathFromJson(JSONObject jSONObject, String str, Object obj) {
        if (jSONObject == null || str == null) {
            return obj;
        }
        int indexOf = str.indexOf(Defaults.chrootDir);
        String substring = indexOf == -1 ? str : str.substring(0, indexOf);
        String substring2 = indexOf == -1 ? null : str.substring(indexOf + 1);
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            if (substring.equals(str2)) {
                try {
                    Object obj2 = jSONObject.get(str2);
                    return substring2 == null ? obj2 : obj2 instanceof JSONObject ? pathFromJson((JSONObject) obj2, substring2, obj) : obj;
                } catch (JSONException e) {
                }
            }
        }
        return obj;
    }

    public static String pathFromJson(JSONObject jSONObject, String str) {
        Object a = pathFromJson(jSONObject, str, null);
        if (a != null) {
            if (a instanceof String) {
                return (String) a;
            }
            if (a != null) {
                return String.valueOf(a);
            }
        }
        return null;
    }

    public static JSONObject strToJson(String str) {
        if (str == null) {
            return null;
        }
        try {
            return (JSONObject) new JSONTokener(str).nextValue();
        } catch (Exception e) {
            return null;
        }
    }
}
