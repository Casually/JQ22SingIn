package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 签到
 * @author Casually5
 */
public class SingInAll {

    public static void singinAll() throws IOException {
        FileReader file = new FileReader("D:/jq22.txt");
        BufferedReader bf = new BufferedReader(file);
        String line = "";
        String[] arrs = new String[2];
        while ((line = bf.readLine()) != null) {
            arrs = line.split("=");
            Response response = new Response();
            String cookie = new Login().getCookie(arrs[0], arrs[1]);
            if (!"登录失败".equals(cookie)) {
                HashMap<String, String> param = new GainSignInParam().gainParam(cookie);
                response = new SingIn().sing(cookie, param);
                new GainSignInParam().gainParam(cookie);
            } else {
                System.out.println("登录失败");
            }
        }
    }
}
