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
import java.util.List;

/**
 * @author 13545
 */
@RequestMapping("/tag")
@Controller
public class SettingTag {

    private String dataPath = System.getProperty("user.dir") + "/data/yidong.txt";

    @RequestMapping("/gettags")
    @ResponseBody
    public Object getTags(){
        List<String> tags = new ArrayList<>(16);

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

        return tags;
    }

    @RequestMapping("/settag")
    public String settag(){
        return "addTags";
    }

    @RequestMapping("/addtags")
    @ResponseBody
    public Object addtags(@RequestParam String tags){
        String[] tag = tags.split(",");
        File file = new File(dataPath);
        if(file.isFile()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String str:tag){
            FileUtil.writeFile(dataPath,str);
        }

        return "success";
    }
}
