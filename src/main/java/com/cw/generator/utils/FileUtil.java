package com.cw.generator.utils;

import java.io.File;
import java.io.IOException;

/**
 * @Author C.W
 * @Date 2020/6/16 4:07 下午
 * @Description 文件工具
 */
public class FileUtil {


    /**
     * 创建文件
     *
     * @param path
     * @return
     */
    public static boolean createFile(String path) throws IOException {

        File file = new File(path);

        // 认为是文件
        if (path.lastIndexOf(".") > path.lastIndexOf("/")) {
            File fileDir = new File(path.substring(0, path.lastIndexOf("/") + 1));
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            file.createNewFile();
        } else {
            // 认为是文件夹
            if (!file.exists()) {
                file.mkdirs();
            }
        }

        return true;
    }


    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);

        if (file.exists()) {
            if (file.isFile()) {
                // 文件
                file.delete();
            } else {
                // 文件夹
                File[] children = file.listFiles();
                for (File child : children) {
                    deleteFile(child.getAbsolutePath());
                }
            }
        }

        file.delete();

        return true;
    }

}
