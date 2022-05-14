import bean.cse.Cse;
import bean.cse.GCes;
import bean.cse.Result;
import cn.hutool.setting.Setting;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.google.gson.Gson;
import util.GetGoogle;
import util.TextUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 阿里云函数计算接口
 * 返回 Json
 * @author dsttl3
 */
public class GetJson implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Context context)
            throws IOException, ServletException {
        String cx = httpServletRequest.getParameter("cx");
        String sou = httpServletRequest.getParameter("sou");
        String sindex = httpServletRequest.getParameter("index");
        int index = 0;
        if (!TextUtil.isBlank(sindex)) {
            index = Integer.parseInt(httpServletRequest.getParameter("index"));
            if (index + 10 > 100) {
                index = -10;
            }
        }
        if (TextUtil.isBlank(cx)){
            cx = new Setting("config.setting").getStr("cx","cse","a177f39aa76e5026c3a549f48d7b8a0e");
        }
        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("content-type", "application/json; charset=utf-8");
        String json = new GetGoogle().getJson(sou, String.valueOf(index),cx);
        json = json.substring(34, json.length() - 2);
        GCes gcse = new Gson().fromJson(json, GCes.class);
        Setting setting = new Setting("config.setting");
        String url = setting.getStr("url","cse","https://google.dsttl3.cn/");
        if (gcse.getResults().size() < 10) {
            index = -10;
        }
        Cse cse = new Cse();
        if (TextUtil.isBlank(sou)) {
            cse.setOk(false);
            cse.setMsg("sou参数为空");
        } else {
            cse.setOk(true);
            cse.setMsg("ok");
            cse.setNext(url + "?sou=" + sou + "&index=" + (index + 10) + "&cx=" + cx);
            cse.setResultCount(gcse.getCursor().getResultCount());
            cse.setSearchResultTime(gcse.getCursor().getSearchResultTime());
            cse.setResults(gcse.getResults());
        }
        String myJson = new Gson().toJson(cse);
        OutputStream out = httpServletResponse.getOutputStream();
        out.write(myJson.getBytes());
        out.flush();
        out.close();
    }
}
