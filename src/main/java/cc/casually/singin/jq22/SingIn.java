package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

import java.util.HashMap;

/**
 * 签到
 *
 * @author Casually5
 */
public class SingIn {
    private static final String GAINSINGINPARAMURL = "http://www.jq22.com/signIn.aspx";

    public static void main(String[] args) {

    }

    public Response sing(String cookie, HashMap<String,String> param){
        Request request = new Request();
        request.setUri(GAINSINGINPARAMURL);
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Origin", "http://www.jq22.com");
        request.addHeader("Upgrade-Insecure-Requests", "1");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader("Referer", "http://www.jq22.com/signIn.aspx");
        request.addHeader("Cookie", cookie);
        request.setParams(param);
        Response response = HttpClient.httpPost(request);
        return response;
    }
}
