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
 * 返回 html
 * @author dsttl3
 */
public class GetJson implements HttpRequestHandler {



    @Override
    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Context context)
            throws IOException, ServletException {
        String sou = httpServletRequest.getParameter("sou");
        String sindex = httpServletRequest.getParameter("index");
        int index = 0;
        if (!TextUtil.isBlank(sindex)) {
            index = Integer.parseInt(httpServletRequest.getParameter("index"));
            if (index + 10 > 100) {
                index = -10;
            }
        }
        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("content-type", "text/html; charset=utf-8");
        String json = new GetGoogle().getJson(sou, String.valueOf(index));
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
            cse.setNext(url + "?sou=" + sou + "&index=" + (index + 10));
            cse.setResultCount(gcse.getCursor().getResultCount());
            cse.setSearchResultTime(gcse.getCursor().getSearchResultTime());
            cse.setResults(gcse.getResults());
        }
        String myJson = new Gson().toJson(cse);
        OutputStream out = httpServletResponse.getOutputStream();
        out.write(myJson.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
