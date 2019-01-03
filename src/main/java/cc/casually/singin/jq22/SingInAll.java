package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.Response;
import cc.casually.htmlParse.nodeutil.Nodes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 签到
 *
 * @author Casually5
 */
public class SingInAll {

    public static void singinAll() throws IOException {
        FileReader file = new FileReader("D:/jq22.txt");
        BufferedReader bf = new BufferedReader(file);
        String line = "";
        String[] arrs = new String[2];
        while ((line = bf.readLine()) != null) {
            line = line.replaceAll(" ", "");
            if (line.startsWith("#")) {
                continue;
            }
            arrs = line.split("=");
            Response response = new Response();
            String cookie = new Login().getCookie(arrs[0], arrs[1]);
            if (!"登录失败".equals(cookie)) {
                GainSignInParam gainSignInParam = new GainSignInParam();
                /** 获取当前签到几天 */
                String startStr = new Nodes(gainSignInParam.reqUrl(cookie).getBodyStr()).getListNodeForTag("h4").get(0).getText();
                /** 获取签到时用到的参数 */
                HashMap<String, String> param = gainSignInParam.gainParam(cookie);
                /** 进行签到操作 */
                response = new SingIn().sing(cookie, param);
                /** 获取最终签到多少天 */
                String endStr = new Nodes(gainSignInParam.reqUrl(cookie).getBodyStr()).getListNodeForTag("h4").get(0).getText();
                System.out.println(startStr + "===" + endStr);
                if (startStr.equals(endStr)){
                    System.out.println(String.format("账号：%s签到失败",arrs[0]));
                }else{
                    System.out.println(String.format("账号：%s签到成功",arrs[0]));
                }
                /** 签到完成后退出登录的账号，避免后续账号无法正确登录 */
                new SingOut().singOut(cookie);
            } else {
                System.out.println("登录失败");
            }
        }
    }
}
