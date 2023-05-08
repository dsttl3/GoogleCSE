package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GetIndex {
    public static String readHtmlFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        // 通过类加载器获取资源文件的URL
        URL url = GetIndex.class.getClassLoader().getResource(fileName);
        if (url == null) {
            return null;
        }
        try {
            // 使用资源流读取HTML文件内容
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            br.close();
        } catch (IOException e) {
            return "读取网页错误，请尝试访问https://l3.pub";
        }
        return sb.toString();
    }

}
