package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;
import cc.casually.htmlParse.nodeutil.Node;
import cc.casually.htmlParse.nodeutil.Nodes;

import java.util.HashMap;
import java.util.List;

/**
 * 获取签到的参数
 * __VIEWSTATE
 * __VIEWSTATEGENERATOR
 * __EVENTVALIDATION
 *
 * @author Casually5
 */
public class GainSignInParam {

    private static final String GAINSINGINPARAMURL = "http://www.jq22.com/signIn.aspx";

    public static void main(String[] args) {
    }

    public HashMap<String, String> gainParam(String cookie) {
        HashMap<String, String> param = new HashMap<>(3);
        Response response = reqUrl(cookie);
        Nodes nodes = new Nodes(response.getBodyStr());
        List<Node> nodeList = nodes.getListNodeForTag("input");
        for (Node node : nodeList) {
            param.put(node.getAttributeValue("name"), node.getAttributeValue("value"));
        }
        return param;
    }

    public Response reqUrl(String cookie) {

        Request request = new Request();
        request.setUri(GAINSINGINPARAMURL);
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Upgrade-Insecure-Requests", "1");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        request.addHeader("X-Requested-With","XMLHttpRequest");
        request.addHeader("Cookie", cookie);
        Response response = HttpClient.httpGet(request);
        return response;
    }

}
