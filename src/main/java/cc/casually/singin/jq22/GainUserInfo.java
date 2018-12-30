package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.Request;

/**
 * 获取个人信息
 * @author Casually5
 */
public class GainUserInfo {

    public static void main(String[] args) {
        Request request = new Request();
        request.setUri("http://www.jq22.com/myhome");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Accept", "text/plain, */*; q=0.01");
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    }
}
