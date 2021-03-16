package com.company.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Properties;

/**
 * 修改配置文件工具
 *
 * @author 杨佳颖
 */
public class AlertConfig {

    private static Log logger = LogFactory.getLog("user");

    /**
     * 获取config.properties配置文件里的id
     *
     * @return
     */
    public static int getStartNum(String tableName) {
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./config.properties"));
        } catch (FileNotFoundException e) {
            logger.error("找不到config.properties文件:" + e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("config.properties配置文件加载失败:" + e.toString());
        }
        int num = Integer.parseInt(properties.getProperty(tableName));
        return num;
    }

    /**
     * 获取db.proerties配置文件jdbcUrl
     *
     * @return
     */
    public static String getUrl() {
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./db.properties"));
        } catch (FileNotFoundException e) {
            logger.error("找不到db.properties文件:" + e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("db.properties配置文件加载失败:" + e.toString());
        }
        return properties.getProperty("jdbcUrl");
    }

    /**
     * 检测数据库驱动和连接是否为空
     * true 为空
     * false 不为空
     */
    public static boolean checkDB() {
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./db2.properties"));
        } catch (FileNotFoundException e) {
            logger.error("读取db2.properties失败:" + e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("加载db2.properties失败:" + e.toString());
        }
        String driverClass = properties.getProperty("driverClass");
        String jdbcUrl = properties.getProperty("jdbcUrl");
        try {
            if (driverClass.length() <= 0 || jdbcUrl.length() <= 0) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    /**
     * 修改config.properties
     *
     * @param
     */
    public static void AlertConfig(String tableName,int num) {
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./config.properties"));
        } catch (FileNotFoundException e) {
            logger.error("读取config.properties失败:" + e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("加载config.properties失败:" + e.toString());
        }
        properties.setProperty(tableName, String.valueOf(num));
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream("./config.properties");
            properties.store(oFile, "");
        } catch (FileNotFoundException e) {
            logger.error("找不到config.properties文件:" + e.toString());
        } catch (IOException e) {
            logger.error("写入config.properties失败:" + e.toString());
        } finally {
            try {
                oFile.close();
            } catch (IOException e) {
                logger.error("关闭输入流失败:" + e.toString());
            }
        }
    }

    /**
     * 修改db.properties jdbcurl
     *
     * @param filepath
     */
    public static void AlertDBJdbcUrl(String filepath) {
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./db.properties"));
        } catch (FileNotFoundException e) {
            logger.error("读取db.properties失败:" + e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("加载db.properties失败:" + e.toString());
        }
        properties.setProperty("jdbcUrl", filepath);
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream("./db.properties");
            properties.store(oFile, "");
        } catch (FileNotFoundException e) {
            logger.error("找不到db.properties文件:" + e.toString());
        } catch (IOException e) {
            logger.error("写入db.properties失败:" + e.toString());
        } finally {
            try {
                oFile.close();
            } catch (IOException e) {
                logger.error("关闭输入流失败:" + e.toString());
            }
        }
    }

    /**
     * 修改db2配置文件
     *
     * @param deriverClass
     * @param jdbcUrl
     * @param user
     * @param password
     */
    public static void AlertDB2Config(String deriverClass, String jdbcUrl, String user, String password) {
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./db2.properties"));
        } catch (FileNotFoundException e) {
            logger.error("读取db2.properties失败:" + e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("加载db2.properties失败:" + e.toString());
        }
        properties.setProperty("driverClass", deriverClass);
        properties.setProperty("jdbcUrl", jdbcUrl);
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream("./db2.properties");
            properties.store(oFile, "");
        } catch (FileNotFoundException e) {
            logger.error("找不到db2.properties文件:" + e.toString());
        } catch (IOException e) {
            logger.error("写入db2.properties失败:" + e.toString());
        } finally {
            try {
                oFile.close();
            } catch (IOException e) {
                logger.error("关闭输入流失败:" + e.toString());
            }
        }
    }


    /**
     * 读取定时任务时间
     *
     * @return
     */
    public static Properties readWaitTime() {
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./time.properties"));
        } catch (FileNotFoundException e) {
            logger.error("找不到db.properties文件:" + e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("db.properties配置文件加载失败:" + e.toString());
        }

        return properties;
    }
}
