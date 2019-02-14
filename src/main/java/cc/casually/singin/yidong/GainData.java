package cc.casually.singin.yidong;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;
import cc.casually.htmlParse.nodeutil.Node;
import cc.casually.htmlParse.nodeutil.Nodes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取数据
 * @author 13545
 */
public class GainData extends Thread {


    private static Pattern pattern = Pattern.compile("\\d+");
    private List<String> tags = new ArrayList<>(16);
    private String url = "https://b2b.10086.cn/b2b/main/viewNoticeContent.html?noticeBean.id=";

    @Override
    public void run() {
        String dataPath = System.getProperty("user.dir") + "/data/yidong.txt";
        try {
            FileReader file = new FileReader(dataPath);
            BufferedReader bf = new BufferedReader(file);
            String line = "";
            while ((line = bf.readLine()) != null) {
                tags.add(line);
            }
            bf.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String str:tags){
            List<Map<String, String>> re = getData(str);
            if(re.size() == 0){
                continue;
            }
            StringBuffer table = new StringBuffer();
            table.append("<table>");
            for (Map<String, String> map:re){
                table.append(String.format("<tr><td><a href='%s%s'>%s</a></td></tr>",url,map.get("code"),map.get("title")));
            }
            table.append("</table>");
            sendEmail(table.toString());
        }
    }

    public void sendEmail(String content){
        Request request = new Request();
        request.setUri("http://xa.makalu.cc/qdwy/app/send/email.html");
        request.addBody("subject","移动招标信息");
        request.addBody("content",content);
        request.addBody("to","gaojun@makalu.cc");
        Response response = HttpClient.post(request);
        System.out.println(response.getBodyStr());
    }

    public List<Map<String,String>> getData(String tag){
        List<Map<String,String>> result = new ArrayList<>();
        Request request = new Request();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        request.setUri("https://b2b.10086.cn/b2b/main/listVendorNoticeResult.html?noticeBean.noticeType=2");
        request.addBody("page.currentPage","1");
        request.addBody("page.perPageSize","100");
        request.addBody("noticeBean.sourceCH","");
        request.addBody("noticeBean.source","");
        request.addBody("noticeBean.title",tag);
        request.addBody("noticeBean.startDate",date);
        request.addBody("noticeBean.endDate",date);
        request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        request.addHeader("X-Requested-With","XMLHttpRequest");
        request.addHeader("Referer","https://b2b.10086.cn/b2b/main/listVendorNotice.html?noticeType=2");
        request.addHeader("Origin","https://b2b.10086.cn");
        Response response = HttpClient.post(request);
        Nodes nodes = new Nodes(response.getBodyStr());
        List<Node> nodeList = nodes.getListNodeForTag("tr");
        for(Node node: nodeList){
            String str = node.getAttributeValue("onclick");
            if(str != null && str.indexOf("selectResult") != -1){
                Map<String,String> re = new HashMap<>();
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()){
                    re.put("code",matcher.group());
                }
                Node node1 = new Nodes(node.getContext()).getListNodeForTag("a").get(0);
                re.put("title",node1.getAttributeValue("title") != null?node1.getAttributeValue("title"):node1.getText());
                result.add(re);
            }
        }
        return result;
    }
}
