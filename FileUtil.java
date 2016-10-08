package com.edianjucai.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.edianjucai.common.Constant;

public class FileUtil {

    public static void Copy(String oldPath, String folderName, String imgName) throws IOException {
        String path = FileUtil.class.getResource("/").getPath();
        path = path.substring(0, path.indexOf("WEB-INF") + ("WEB-INF".length() + 1)) + "images/";
        String foderPath = path + folderName;
        File oldfile = new File(oldPath);
        if (oldfile.exists()) {
            InputStream inStream = new FileInputStream(oldPath);
            saveImageByIO(inStream, foderPath, folderName, imgName);
        }
    }

    public static void Copy( MultipartFile mulFile, String folderName, String imgName) throws IllegalStateException, IOException {
        String path = FileUtil.class.getResource("/").getPath();
        path = path.substring(0, path.indexOf("WEB-INF")) + "/images/";
        InputStream inStream = mulFile.getInputStream();
        saveImage(mulFile, path, imgName, folderName);
        copyToLocal(inStream, folderName, imgName);
    }
    
    private static void copyToLocal( InputStream inStream, String folderName, String imgName) throws IllegalStateException, IOException {
        String path = Constant.SAVE_IMAGE_TO_LOCAL_PATH;
        saveImageByIO(inStream, path, folderName, imgName);
    }
    
    private static void saveImageByIO(InputStream inStream, String path, String folderName, String imgName) throws IOException {
        FileOutputStream fs = null;
        try {
            int byteread = 0;
            String foderPath = path + folderName;
            File folder = new File(foderPath);
            if (!folder.exists() && !folder.isDirectory()) {
                folder.mkdir();
            }
            foderPath = folder + "/" + imgName;
            fs = new FileOutputStream(foderPath);
            byte[] buffer = new byte[1444];
            // int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    private static void saveImage(MultipartFile mulFile, String path, String imgName, String folderName) throws IllegalStateException, IOException {
        String foderPath = path + folderName;
        File folder = new File(foderPath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdir();
        }
        foderPath = folder + "/" + imgName;
        mulFile.transferTo(new File(foderPath));
    }

}
