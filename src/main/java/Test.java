import cn.hutool.setting.Setting;

public class Test {
    public static void main(String[] args) {
        Setting setting = new Setting("config.setting");
        String cx = setting.getStr("cx","cse","NOLL");
        System.out.println(cx);
    }
}
