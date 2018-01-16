package dark.silent.b;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.ape.filemanager.TextInputDialog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.swiftp.ProxyConnector;

public final class b {
    static File a = null;

    public static String a(File file, String str) {
        String str2 = null;
        if (file != null && file.exists()) {
            byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
            try {
                InputStream fileInputStream = new FileInputStream(file);
                ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                while (nextEntry != null) {
                    String name = nextEntry.getName();
                    if (nextEntry.isDirectory() || !name.equals(str)) {
                        nextEntry = zipInputStream.getNextEntry();
                    } else {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        while (true) {
                            int read = zipInputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        str2 = byteArrayOutputStream.toString(ProxyConnector.ENCODING);
                        zipInputStream.closeEntry();
                        zipInputStream.close();
                        fileInputStream.close();
                    }
                }
                zipInputStream.closeEntry();
                zipInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
            }
        }
        return str2;
    }

    public static String a(InputStream inputStream, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, str);
        char[] cArr = new char[10240];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read > 0) {
                stringBuilder.append(cArr, 0, read);
            } else {
                inputStreamReader.close();
                return stringBuilder.toString();
            }
        }
    }

    public static void a(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
                return;
            }
            for (File a : listFiles) {
                a(a);
            }
            file.delete();
        }
    }

    public static void a(File file, File file2) {
        InputStream fileInputStream = new FileInputStream(file);
        OutputStream fileOutputStream = new FileOutputStream(file2);
        a(fileInputStream, fileOutputStream);
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static void a(InputStream inputStream, File file) {
        try {
            OutputStream fileOutputStream = new FileOutputStream(file);
            a(inputStream, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }

    private static void a(InputStream inputStream, OutputStream outputStream) {
        if (inputStream != null) {
            try {
                byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read > 0) {
                        outputStream.write(bArr, 0, read);
                    } else {
                        return;
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static String b(File file) {
        int read;
        Throwable th;
        byte[] digest;
        FileInputStream fileInputStream = null;
        if (file == null || !file.exists()) {
            return null;
        }
        MessageDigest instance;
        String str;
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            instance = null;
        }
        byte[] bArr = new byte[10240];
        FileInputStream fileInputStream2;
        try {
            fileInputStream2 = new FileInputStream(file);
            while (true) {
                try {
                    read = fileInputStream2.read(bArr);
                    if (read > 0) {
                        instance.update(bArr, 0, read);
                    } else {
                        try {
                            break;
                        } catch (Exception e2) {
                        }
                    }
                } catch (IOException e3) {
                    fileInputStream = fileInputStream2;
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            fileInputStream2.close();
        } catch (IOException e4) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e5) {
                }
            }
            digest = instance.digest();
            str = new String();
            read = 0;
            while (read < digest.length) {
                int i;
                i = digest[read] & TextInputDialog.FILENAME_MAX_LENGTH;
                if (i <= 15) {
                    str = str + "0";
                }
                read++;
                str = str + Integer.toHexString(i);
            }
            return str.toUpperCase();
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = null;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e6) {
                }
            }
            throw th;
        }
        digest = instance.digest();
        str = new String();
        read = 0;
        while (read < digest.length) {
            i = digest[read] & TextInputDialog.FILENAME_MAX_LENGTH;
            if (i <= 15) {
                str = str + "0";
            }
            read++;
            str = str + Integer.toHexString(i);
        }
        return str.toUpperCase();
    }
}
