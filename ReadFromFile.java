package com.edianjucai.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadFromFile {

    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */

    private static String path = ReadFromFile.class.getResource("/").getPath();

    public static String readFile(String fileName) {
        path = path.substring(0, path.indexOf("WEB-INF") + ("WEB-INF".length() + 1)) + "Article/";
        fileName = path + fileName;
        File file = new File(fileName);
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public static void writeFile(String content, String fileName, String folderName) {
        path = path.substring(0, path.indexOf("WEB-INF") + ("WEB-INF".length() + 1)) + "Article/";
        String foderPath = path + folderName;
        File folder = new File(foderPath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdir();
        }
        
        fileName = folder + "/" + fileName;
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, false);

            fw.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.flush();
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
