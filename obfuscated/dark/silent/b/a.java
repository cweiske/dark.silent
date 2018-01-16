package dark.silent.b;

import java.lang.reflect.Method;
import java.util.HashMap;

public final class a {
    static HashMap<Class<?>, HashMap<String, Method>> a = new HashMap();
    static HashMap<Class<?>, HashMap<String, Method>> b = new HashMap();

    public static Object a(Object obj, String str, Class<?>[] clsArr, Object[] objArr) {
        try {
            return obj instanceof Class ? ((Class) obj).getMethod(str, clsArr).invoke(null, objArr) : obj.getClass().getMethod(str, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            throw e;
        }
    }
}
