package dark.silent.b;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import com.ape.filemanager.TextInputDialog;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class f {
    static Map<String, String> a = new HashMap();
    static Boolean b = null;

    public static String a() {
        return (((((("android" + ";MANUFACTURER/" + Build.MANUFACTURER) + ";MODEL/" + Build.MODEL) + ";BOARD/" + Build.BOARD) + ";BRAND/" + Build.BRAND) + ";DEVICE/" + Build.DEVICE) + ";HARDWARE/" + Build.HARDWARE) + ";PRODUCT/" + Build.PRODUCT;
    }

    public static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "0";
        }
    }

    public static String a(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
        messageDigest.update(str.getBytes());
        byte[] digest = messageDigest.digest();
        String str2 = new String();
        for (byte b : digest) {
            int i = b & TextInputDialog.FILENAME_MAX_LENGTH;
            if (i <= 15) {
                str2 = str2 + "0";
            }
            str2 = str2 + Integer.toHexString(i);
        }
        return str2.toUpperCase();
    }

    public static String b() {
        return new SimpleDateFormat("Z").format(Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault()).getTime());
    }
}
