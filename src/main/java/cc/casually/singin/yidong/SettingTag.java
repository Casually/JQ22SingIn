package cc.casually.singin.yidong;

import cc.casually.singin.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 13545
 */
@RequestMapping("/tag")
@Controller
public class SettingTag {

    private String dataPath = System.getProperty("user.dir") + "/data/yidong.txt";
    private String dataPathe = System.getProperty("user.dir") + "/data/yidongemail.txt";

    @RequestMapping("/gettags")
    @ResponseBody
    public Object getTags(){
        List<String> tags = null;
        try {
            tags = FileUtil.readLineFile(dataPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> emails = null;
        try {
            emails = FileUtil.readLineFile(dataPathe);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Object> result = new HashMap<>(2);
        result.put("tag",tags);
        result.put("email",emails);
        return result;
    }

    @RequestMapping("/settag")
    public String settag(){
        return "addTags";
    }

    @RequestMapping("/addtags")
    @ResponseBody
    public Object addtags(@RequestParam String tags,@RequestParam String email){
        writeData(tags,dataPath);
        writeData(email,dataPathe);
        return "success";
    }

    public void writeData(String obj,String fileP) {
        String[] strs = obj.split(",");
        File file = new File(fileP);
        if(file.isFile()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String str:strs){
            FileUtil.writeFile(fileP,str);
        }
    }
}
