package util;

import bean.search.CseEntity;
import cn.hutool.setting.Setting;

/**
 * @author dsttl3
 */
public class GetGoogle {

    /**
     * 获取CES js
     *
     * @param cx cx
     * @return  -
     */
    private String getCseJs(String cx) {
        String url = "https://cse.google.com/cse.js?cx=" + cx;
        String js = new OkUtil().get(url);
        if (js.indexOf("cse_token") > 0 && js.indexOf("\"cselibVersion\": \"") > 0) {
            return js;
        } else {
            System.out.println("cse初始化出错");
            return null;
        }
    }

    /**
     * 获取CES对象
     *
     * @param cx cx id
     * @return -
     */
    private CseEntity getCse(String cx) {
        String js = getCseJs(cx);
        if (TextUtil.isBlank(js)) {
            System.out.println("getCse:error");
            return null;
        } else {
            int tokenIntex = js.indexOf("cse_token");
            String token = js.substring(tokenIntex + 13, tokenIntex + 55);
            int cselibVersionIndex = js.indexOf("\"cselibVersion\": \"");
            String cselibVersion = js.substring(cselibVersionIndex + 18, cselibVersionIndex + 34);
            CseEntity cse = new CseEntity();
            cse.setCse_tok(token);
            cse.setCselibv(cselibVersion);
            System.out.println("getCse:ok");
            return cse;
        }
    }

    /**
     * 获取搜索链接
     *
     * @param q  搜索内容
     * @param cx 搜索网页ID
     * @return URL
     */
    private String getUrl(String q, String cx, String start) {
        CseEntity cse = getCse(cx);
        if (cse != null) {
            return "https://cse.google.com/cse/element/v1?hl=zh-CN"
                    + "&gl=cn"
                    + "&callback=google.search.cse.api5190"
                    + "&cx=" + cx
                    + "&cselibv=" + cse.getCselibv()
                    + "&cse_tok=" + cse.getCse_tok()
                    + "&start=" + start
                    + "&q=" + q;
        } else {
            return "https://fc.dsttl3.cn";
        }
    }

    /**
     * 获取查询结果（原始结果）
     * @param q     搜索值
     * @param start  查询结果索引
     * @return url
     */
    public String getJson(String q, String start, String cx) {
//        String cx = new Setting("config.setting").getStr("cx","cse","NOLL");
        //cx为搜索引擎ID
        String sUrl = getUrl(q, cx, start);
        return new OkUtil().get(sUrl);
    }

}
