package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

/**
 * 登录
 *
 * @author Casually5
 */
public class Login {

    private static final String LOGINURL = "http://www.jq22.com/";

    Request request = new Request();
    {
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Accept", "text/plain, */*; q=0.01");
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    }
    public static void main(String[] args) {
        System.out.println(new Login().getCookie("用户名","密码"));
    }

    /**
     * 登录获得必要的cookie信息
     *
     * @param user
     * @param pw
     * @return
     */
    public Response login(String user, String pw) {
        request.setUri(LOGINURL + "/emdl.aspx");
        request.addBody("em", user);
        request.addBody("pw", pw);
        Response response = HttpClient.post(request);
        return response;
    }

    /**
     * 直接获取Cookie
     *
     * @param user
     * @param passwd
     * @return
     */
    public String getCookie(String user, String passwd) {
        String cookie = "";
        Response response = login(user,passwd);
        if("y".equals(response.getBodyStr())){
            cookie = extractCookie(login(user, passwd));
            response = index(cookie);
            cookie = "CityCookie=y; " + cookie + extractCookie(response);
        }else{
            cookie = "登录失败";
        }
        return cookie;
    }

    /**
     * 从返回的数据中提取cookie
     *
     * @param response
     * @return
     */
    public String extractCookie(Response response) {
        String cookie = "";
        String[] cookies = response.getHeader().get("Set-Cookie").toString()
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll("HttpOnly,", "")
                .split(";");
        for (String o : cookies) {
            if (o.indexOf("MydlCookie") != -1
                    || o.indexOf("ASP.NET_SessionId") != -1
                    || o.indexOf("__cfduid") != -1
                    || o.indexOf("Myinfo") != -1) {
                cookie += o + "; ";
            }
        }
        return cookie;
    }

    /**
     * 返回主页得到必要的cookie信息
     *
     * @param cookie
     * @return
     */
    public Response index(String cookie) {
        request.setUri(LOGINURL);
        request.addHeader("cookie", cookie);
        Response response = HttpClient.httpGet(request);
        return response;
    }

}
