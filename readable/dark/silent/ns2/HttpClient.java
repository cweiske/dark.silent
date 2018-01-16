package dark.silent.ns2;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.swiftp.ProxyConnector;

public final class HttpClient {
    static boolean a = false;

    public static class HttpResponse {
        public int statusCode;
        public String content;
    }

    public static int downloadUrlToFile(String url, File file) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            String property = System.getProperty("http.agent");
            String str2 = "User-Agent";
            if (property == null) {
                property = "Android";
            }
            httpURLConnection.setRequestProperty(str2, property);
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(300000);
            int contentLength = httpURLConnection.getContentLength();
            FileTools.writeStreamToFile(httpURLConnection.getInputStream(), file);
            return contentLength;
        } catch (Exception e) {
            return 0;
        }
    }

    private static HttpResponse sendPOST(String url, Object data) {
        boolean z = data instanceof JSONObject;
        String data2 = data == null ? null : data.toString();
        HttpResponse response = new HttpResponse();
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
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
                if (data2 != null) {
                    if (z) {
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    }
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    outputStreamWriter.write(data2);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                response.content = FileTools.extractFileFromZip(inputStream, ProxyConnector.ENCODING);
                response.statusCode = httpURLConnection.getResponseCode();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (Exception e) {
                if (httpURLConnection != null) {
                    try {
                        response.content = FileTools.getStreamContents(httpURLConnection.getErrorStream(), ProxyConnector.ENCODING);
                    } catch (Exception e2) {
                    }
                }
                return response;
            }
        } catch (Exception e3) {
            httpURLConnection = null;
            if (httpURLConnection != null) {
                if (response.content == null && httpURLConnection.getErrorStream() != null) {
                    response.content = FileTools.getStreamContents(httpURLConnection.getErrorStream(), ProxyConnector.ENCODING);
                }
            }
            return response;
        }
        return response;
    }

    public static HttpResponse sendPOST(String url, JSONObject jSONObject) {
        return sendPOST(url, (Object) jSONObject);
    }
}
