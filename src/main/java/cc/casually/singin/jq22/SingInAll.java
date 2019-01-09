package cc.casually.singin.jq22;

import cc.casually.htmlParse.http.Response;
import cc.casually.htmlParse.nodeutil.Nodes;
import cc.casually.singin.jq22.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 签到
 *
 * @author Casually5
 */
public class SingInAll extends Thread {
    @Override
    public void run() {
        try {
            singinAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void singinAll() throws IOException {
        List<String> datas = readData();
        String[] arrs = new String[2];
        FileUtil.writeFile(System.getProperty("user.dir") + "/data/result.txt",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        for (String line : datas) {
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
                FileUtil.writeFile(System.getProperty("user.dir") + "/data/result.txt",
                        arrs[0] + "：" + startStr + "===" + endStr);
                if (startStr.equals(endStr)) {
                    System.out.println(String.format("账号：%s签到失败", arrs[0]));
                } else {
                    System.out.println(String.format("账号：%s签到成功", arrs[0]));
                }
                /** 签到完成后退出登录的账号，避免后续账号无法正确登录 */
                new SingOut().singOut(cookie);
            } else {
                System.out.println("登录失败");
            }

            try {
                /** 每签到完成一个都休眠一分钟，避免过多的请求，导致无法正确签到 */
                sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("签到完成");
    }

    public List<String> readData() throws IOException {
        List<String> datas = new ArrayList<>(16);
        String dataPath = System.getProperty("user.dir") + "/data/jq22.txt";
        FileReader file = new FileReader(dataPath);
        BufferedReader bf = new BufferedReader(file);
        String line = "";
        while ((line = bf.readLine()) != null) {
            datas.add(line);
        }
        return datas;
    }
}
