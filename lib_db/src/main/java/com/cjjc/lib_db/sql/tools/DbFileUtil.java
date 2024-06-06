package com.cjjc.lib_db.sql.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.cjjc.lib_tools.util.log.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作
 */
public class DbFileUtil {

    /**
     * 复制单个文件(可更名复制)
     *
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名(注：目录路径需带文件名)
     * @return
     */
    public static boolean CopySingleFile(String oldPathFile, String newPathFile) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            File newFile = new File(newPathFile);
            File parentFile = newFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                return newFile.exists();
            }
        } catch (Exception e) {
            LogUtil.xLoge("文件拷贝 异常-->"+e.getMessage());
            return false;
        }
        return false;
    }

    //读取本地文件  获取 数据库升级版本信息
    public static String[] getLocalVersionInfo(File file) {
        boolean ret = false;
        String[] data = null;
        if (file.exists()) {
            int byteread = 0;
            byte[] tempbytes = new byte[100];
            StringBuilder stringBuilder = new StringBuilder();
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                while ((byteread = in.read(tempbytes)) != -1) {
                    stringBuilder.append(new String(tempbytes, 0, byteread));
                }
                data = stringBuilder.toString().split("/");
            } catch (Exception e) {

            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    in = null;
                }
            }
        }
        if (data != null && data.length == 2) {
            return data;
        }
        return null;
    }

    /**
     * 获取APK版本号
     *
     * @param context 上下文
     * @return 版本号
     * @throws throws [违例类型] [违例说明]
     * @see
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionName;
    }


    /**
     * 保存下载APK版本信息
     *
     * @param context apk留不下信息
     * @return 保存成功返回true，否则返回false
     * @throws throws [违例类型] [违例说明]
     * @see
     */
    public static boolean saveVersionInfo(Context context, String newVersion) {
        boolean ret = false;
        File parentFile = new File(DbConstantConfig.DB_PATH,
                DbConstantConfig.SUM_DB_DIR_NAME);

        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(parentFile, DbConstantConfig.RECORD_UPDATE_FILE_NAME), false);
            writer.write(getVersionName(context) + "/" + newVersion);//   "V002/V003"
            writer.flush();
            ret = true;
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

}
