import bean.cse.Cse;
import bean.cse.GCes;
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
 * GetSURL 测试
 */
public class Get implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Context context)
            throws IOException, ServletException {
        String sou = httpServletRequest.getParameter("sou");
        String sindex = httpServletRequest.getParameter("index");
        int index = 0;
        if (!TextUtil.isBlank(sindex)){
            index = Integer.parseInt(httpServletRequest.getParameter("index"));
            if (index+10 > 100){
                index = -10;
            }
        }
        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("content-type","application/json; charset=utf-8");
        String json = new GetGoogle().getJson(sou,index+"");
        json = json.substring(34,json.length()-2);
        GCes gcse = new Gson().fromJson(json,GCes.class);
        if (gcse.getResults().size() < 10){
            index = -10;
        }
        Cse cse = new Cse();
        if (TextUtil.isBlank(sou)){
            cse.setOk(false);
            cse.setMsg("sou is null");
        }else {
            cse.setOk(true);
            cse.setMsg("ok");
            cse.setNext("https://google.dsttl3.cn/api?sou="+sou+"&index="+(index+10));
            cse.setResultCount(gcse.getCursor().getResultCount());
            cse.setSearchResultTime(gcse.getCursor().getSearchResultTime());
            cse.setResults(gcse.getResults());
        }
        String cseJson = new Gson().toJson(cse);
        OutputStream out = httpServletResponse.getOutputStream();
        out.write(cseJson.getBytes());
        out.flush();
        out.close();
    }
}
