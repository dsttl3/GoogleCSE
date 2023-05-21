import bean.cse.Cse;
import bean.cse.GCes;
import bean.cse.Result;
import cn.hutool.setting.Setting;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.google.gson.Gson;
import util.GetGoogle;
import util.GetIndex;
import util.TextUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 阿里云函数计算接口
 * 直接返回 html 页面
 * @author dsttl3
 */
public class GetHtml implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Context context)
            throws IOException, ServletException {
        String sou = httpServletRequest.getParameter("sou");
        String sindex = httpServletRequest.getParameter("index");
        String cx = httpServletRequest.getParameter("cx");
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
        httpServletResponse.setHeader("content-type", "text/html; charset=utf-8");
        String json = new GetGoogle().getJson(sou, String.valueOf(index),cx);
        json = json.substring(34, json.length() - 2);
        GCes gcse = new Gson().fromJson(json, GCes.class);
        Setting setting = new Setting("config.setting").getSetting("cse");
        String url = setting.getStr("url","https://google.dsttl3.cn/");
        String cssUrl = setting.getStr("cssurl","https://dsttl3.cn/css/st.css");
        String logoUrl = setting.getStr("logourl","https://dsttl3.cn/img/dstt.png");
        String bdId = setting.getStr("bdid","");
        if (gcse.getResults().size() < 10) {
            index = -10;
        }
        Cse cse = new Cse();
        if (TextUtil.isBlank(sou)) {
            cse.setOk(false);
//            cse.setMsg("sou参数为空");
            OutputStream out = httpServletResponse.getOutputStream();
            out.write(GetIndex.readHtmlFile("index.html").getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            return;
        } else {
            cse.setOk(true);
            cse.setMsg("ok");
            cse.setNext(url + "?sou=" + sou + "&index=" + (index + 10) + "&cx=" + cx);
            cse.setResultCount(gcse.getCursor().getResultCount());
            cse.setSearchResultTime(gcse.getCursor().getSearchResultTime());
            cse.setResults(gcse.getResults());
        }
        String htmlTitle = "<!DOCTYPE html>\n<html><head>\n<title>" +
                sou +
                "</title>\n<link type=\"text/css\" href=\"" +
                cssUrl +
                "\" rel=\"stylesheet\">" +
                "<link rel=\"shortcut icon\" href=\"https://wb.dsttl3.cn/img/google_logo.ico\" type=\"image/x-icon\">" +
                "<script>\n" +
                "var _hmt = _hmt || [];\n" +
                "(function() {\n" +
                "  var hm = document.createElement(\"script\");\n" +
                "  hm.src = \"https://hm.baidu.com/hm.js?" +
                bdId +
                "\";\n" +
                "  var s = document.getElementsByTagName(\"script\")[0]; \n" +
                "  s.parentNode.insertBefore(hm, s);\n" +
                "})();\n" +
                "</script>\n" +
                "</head>";
        StringBuilder htmlList = new StringBuilder();
        for (Result result : cse.getResults()) {
            htmlList.append("<div class=\"google-list\">\n<a href=\"");
            htmlList.append(result.getUnescapedUrl());
            htmlList.append("\" target=\"_blank\">\n<div class=\"title\">");
            htmlList.append(result.getTitle());
            htmlList.append("</div>\n</a>\n<div class=\"formattedUrl\">");
            htmlList.append(result.getFormattedUrl());
            htmlList.append("</div>\n<div class=\"content\">");
            htmlList.append(result.getContent());
            htmlList.append("</div>\n<div class=\"visibleUrl\">");
            htmlList.append(result.getVisibleUrl());
            htmlList.append("</div>\n</div>");
        }
        String souHtml = "<div class=\"m_top\">\n<div class=\"top_icon\"><img style=\"height: 50px; margin-left: 30px;\" src=\"" +
                logoUrl +
                "\" /></div>\n" +
                "<div class=\"top_sou\">\n" +
                "<form class=\"search_form\" action=\"" +
                url +
                "\"> \n<input type=\"text\" class=\"input_text\" name=\"sou\" value=\"" +
                sou +
                "\">\n<input type=\"submit\" value=\"&#x641C;&#x7D22;\" class=\"input_sub\">\n</form>\n</div>\n</div>";
        String htmlBody = "<body>\n" +
                souHtml +
                "<div class=\"main\">\n<div class=\"top\">&#x641C;&#x7D22;&#x5230;<b>" +
                cse.getResultCount() +
                "</b>&#x4E2A;&#x7ED3;&#x679C;&#xFF0C;&#x8017;&#x65F6;&#xFF1A;<b>" +
                cse.getSearchResultTime() +
                "</b>&#x79D2;&#x3002;</div>" +
                htmlList +
                "<a href=\"" +
                cse.getNext() +
                "\">\n<div class=\"next\">&#x4E0B;&#x4E00;&#x9875;</div>\n</a>\n</div>\n</body></html>";
        String html = htmlTitle + htmlBody;
        OutputStream out = httpServletResponse.getOutputStream();
        out.write(html.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
