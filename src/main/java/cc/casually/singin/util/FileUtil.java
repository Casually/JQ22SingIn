package cc.casually.singin.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author 13545
 */
public class FileUtil {

    /**
     * 写入文件，追加文件下一行
     *
     * @param filename
     * @param str
     */
    public static void writeFile(String filename, String str) {
        File file = new File(filename);
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            bf.write(str + "\n");
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<String> readLineFile(String filePath) throws IOException {
        List<String> fileList = new ArrayList<>(16);
        FileReader file = new FileReader(filePath);
        BufferedReader bf = new BufferedReader(file);
        String line = "";
        while ((line = bf.readLine()) != null) {
            fileList.add(line);
        }
        bf.close();
        file.close();
        return fileList;
    }
}
