package util;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class BaiDuSugrec implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Context context) throws IOException, ServletException {
        String wd = httpServletRequest.getParameter("wd");

        try {
            OkHttpClient client = new OkHttpClient();
            Request okRequest = new Request.Builder()
                    .url("https://www.baidu.com/sugrec?ie=utf-8&json=1&prod=pc&from=pc_web&wd="+wd)
                    .build();
            Response okResponse = client.newCall(okRequest).execute();
            String json = Objects.requireNonNull(okResponse.body()).string();
            httpServletResponse.setStatus(200);
            httpServletResponse.setHeader("content-type", "text/html; charset=utf-8");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            OutputStream out = httpServletResponse.getOutputStream();
            out.write(json.getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("下载错误");
            throw new RuntimeException(e);
        }
    }
}
