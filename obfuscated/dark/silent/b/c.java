package dark.silent.b;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.swiftp.ProxyConnector;

public final class c {
    static boolean a = false;

    public static class a {
        public int a;
        public String b;
    }

    public static int a(String str, File file) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            String property = System.getProperty("http.agent");
            String str2 = "User-Agent";
            if (property == null) {
                property = "Android";
            }
            httpURLConnection.setRequestProperty(str2, property);
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(300000);
            int contentLength = httpURLConnection.getContentLength();
            b.a(httpURLConnection.getInputStream(), file);
            return contentLength;
        } catch (Exception e) {
            return 0;
        }
    }

    private static a a(String str, Object obj) {
        HttpURLConnection httpURLConnection;
        boolean z = obj instanceof JSONObject;
        String obj2 = obj == null ? null : obj.toString();
        a aVar = new a();
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                String property = System.getProperty("http.agent");
                String str2 = "User-Agent";
                if (property == null) {
                    property = "Android";
                }
                httpURLConnection.setRequestProperty(str2, property);
                httpURLConnection.setConnectTimeout(60000);
                httpURLConnection.setReadTimeout(300000);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                if (obj2 != null) {
                    if (z) {
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    }
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    outputStreamWriter.write(obj2);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                aVar.b = b.a(inputStream, ProxyConnector.ENCODING);
                aVar.a = httpURLConnection.getResponseCode();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (Exception e) {
                if (httpURLConnection != null) {
                    try {
                        aVar.b = b.a(httpURLConnection.getErrorStream(), ProxyConnector.ENCODING);
                    } catch (Exception e2) {
                    }
                }
                return aVar;
            }
        } catch (Exception e3) {
            httpURLConnection = null;
            if (httpURLConnection != null) {
                if (aVar.b == null && httpURLConnection.getErrorStream() != null) {
                    aVar.b = b.a(httpURLConnection.getErrorStream(), ProxyConnector.ENCODING);
                }
            }
            return aVar;
        }
        return aVar;
    }

    public static a a(String str, JSONObject jSONObject) {
        return a(str, (Object) jSONObject);
    }
}
