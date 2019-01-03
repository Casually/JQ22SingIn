package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

/**
 * 退出登录
 * @author 13545
 */
public class SingOut {


    /**
     * 退出登录的账号
     * @param cookie
     * @return
     */
    public Response singOut(String cookie){
        Request request = new Request();
        request.setUri("http://www.jq22.com/myout.aspx");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Upgrade-Insecure-Requests", "1");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader("Referer", "http://www.jq22.com/signIn.aspx");
        request.addHeader("Cookie", cookie);
        Response response = HttpClient.httpGet(request);
        return response;
    }
}
