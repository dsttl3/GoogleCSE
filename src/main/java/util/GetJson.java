package util;

import bean.cse.Cse;
import bean.cse.GCes;
import com.google.gson.Gson;

/**
 * 将Google返回的json转成自用json
 */
public class GetJson {
    public String load(String json,String sou,int index){
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
        return new Gson().toJson(cse);
    }
}
