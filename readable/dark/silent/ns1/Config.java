package dark.silent.ns1;

import android.content.Context;

import java.io.File;

public final class Config {
    public Context context;
    public File file;
    public final byte[] url;
    private final String zipFileName = "moast.zip";

    public Config(Context context) {
        this.context = context.getApplicationContext();
        this.file = new File(context.getDir("mof", 0), "moast.zip");
        //this.c = Base64.decode("aHR0cHM6Ly9hcGkuaW5zbW9iaS5jb20vYWEvbmM=", 0);
        this.url = "https://api.insmobi.com/aa/nc".getBytes();
    }
}
