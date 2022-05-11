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
public class GetHtml implements HttpRequestHandler {
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
        String cssUrl = setting.getStr("cssurl","cse","https://dsttl3.cn/css/st.css");
        String logoUrl = setting.getStr("logourl","cse","https://dsttl3.cn/img/dstt.png");
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
        String htmlTitle = "<!DOCTYPE html>\n<html><head>\n<title>" +
                sou +
                "</title>\n<link type=\"text/css\" href=\"" +
                cssUrl +
                "\" rel=\"stylesheet\">" +
                "<script>\n" +
                "var _hmt = _hmt || [];\n" +
                "(function() {\n" +
                "  var hm = document.createElement(\"script\");\n" +
                "  hm.src = \"https://hm.baidu.com/hm.js?a177f39aa76e5026c3a549f48d7b8a0e\";\n" +
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
        out.write(html.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
