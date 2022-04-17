package util;

import bean.search.CseEntity;

public class GetGoogle {

    /**
     * 获取CES js
     * @param cx
     * @return
     */
    private String getCseJs(String cx){
        String url = "https://cse.google.com/cse.js?cx="+cx;
        String js = new OK3().get(url);
        if (js.indexOf("cse_token") > 0 && js.indexOf("\"cselibVersion\": \"") > 0) {
            return js;
        }else {
            System.out.println("cse初始化出错");
            return null;
        }
    }

    /**
     * 获取CES对象
     * @param cx
     * @return
     */
    private CseEntity getCse(String cx) {
        String js = getCseJs(cx);
        if (TextUtil.isBlank(js)) {
            System.out.println("getCse:error");
            return null;
        }else {
            int tokenIntex = js.indexOf("cse_token");
            String token = js.substring(tokenIntex+13, tokenIntex+55);
            int cselibVersionIndex = js.indexOf("\"cselibVersion\": \"");
            String cselibVersion = js.substring(cselibVersionIndex+18, cselibVersionIndex+34);
            CseEntity cse = new CseEntity();
            cse.setCse_tok(token);
            cse.setCselibv(cselibVersion);
            System.out.println("getCse:ok");
            return cse;
        }
    }
    /**
     * 获取搜索链接
     * @param q 搜索内容
     * @param cx 搜索网页ID
     * @return URL
     */
    private String getURL(String q,String cx,String start) {
        CseEntity cse = getCse(cx);
        if (cse != null) {
            return "https://cse.google.com/cse/element/v1?hl=zh-CN"
                    +"&gl=cn"
                    +"&callback=google.search.cse.api5190"
                    +"&cx="+cx
                    +"&cselibv="+cse.getCselibv()
                    +"&cse_tok="+cse.getCse_tok()
                    +"&start="+start
                    +"&q="+q;
        }else {
            return "https://fc.dsttl3.cn";
        }
    }

    /**
     * 获取搜索结果
     * @param q 搜索值
     * @param start 索引
     * @return
     */
    public String getJson(String q,String start) {
        String surl = getURL(q,"007765593562555407508:qxj7yrxd00a",start);
        return new OK3().get(surl);
    }

}
