package dark.silent.ns2;

import java.lang.reflect.Method;
import java.util.HashMap;

public final class SomeHashThingy {
    static HashMap<Class<?>, HashMap<String, Method>> a = new HashMap();
    static HashMap<Class<?>, HashMap<String, Method>> b = new HashMap();

    public static Object a(Object obj, String methodName, Class<?>[] clsArr, Object[] objArr) {
        try {
            return obj instanceof Class ? ((Class) obj).getMethod(methodName, clsArr).invoke(null, objArr) : obj.getClass().getMethod(methodName, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            throw e;
        }
    }
}
