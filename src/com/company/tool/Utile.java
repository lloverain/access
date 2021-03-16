package com.company.tool;

import com.company.Main;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * 工具类
 * 获得数据库连接
 * 判断数据库连接状态
 *
 * @author 杨佳颖
 */
public class Utile {
    private static Logger logger = Logger.getLogger(Utile.class);
    /**
     * 获取目标库连接
     * @return
     */
    public static Connection getMysqlConn(){
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./db2.properties"));
        } catch (FileNotFoundException e) {
            logger.error("找不到db2.properties文件:"+e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("db2.properties配置文件加载失败:"+e.toString());
        }
        try {
            Class.forName(properties.getProperty("driverClass"));
            return DriverManager.getConnection(properties.getProperty("jdbcUrl"),properties.getProperty("user"),properties.getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
            Main.UpdateStatistics(properties.getProperty("driverClass")+"驱动加载启动失败");
            Main.UpdateStatistics(e.toString());
            logger.error(properties.getProperty("driverClass")+"驱动加载启动失败:"+e.toString());
        }
        return null;
    }

    /**
     * 判断目标数据库是否配置正确
     * @return
     */
    public static boolean whetherToConnect(){
        boolean statu = false;
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./db2.properties"));
        } catch (FileNotFoundException e) {
            logger.error("找不到db2.properties文件:"+e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("db2.properties配置文件加载失败:"+e.toString());
        }
        try {
            Class.forName(properties.getProperty("driverClass"));
            Connection connection = DriverManager.getConnection(properties.getProperty("jdbcUrl"),properties.getProperty("user"),properties.getProperty("password"));
            if(!connection.isClosed()){
                statu = true;
                close(connection,null,null);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Main.UpdateStatistics(properties.getProperty("driverClass")+"驱动加载启动失败");
            Main.UpdateStatistics(e.toString());
            logger.error(properties.getProperty("driverClass")+"驱动加载启动失败:"+e.toString());
        }
        return statu;
    }

    /**
     * 检测Access数据是否可用
     * @return
     */
    public static boolean getAccessStatu(){
        boolean statu = false;
        InputStream config = null;
        try {
            config = new BufferedInputStream(new FileInputStream("./db.properties"));
        } catch (FileNotFoundException e) {
            logger.error("找不到db.properties文件:"+e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            logger.error("db.properties配置文件加载失败:"+e.toString());
        }
        Database db = null;
        try {
            db = DatabaseBuilder.open(new File(AlertConfig.getUrl()));
            Table t_xxfp = db.getTable("t_xxfp");
            Table t_xxfp_mx = db.getTable("t_xxfp_mx");
            if(!t_xxfp.iterator().next().isEmpty() && !t_xxfp_mx.iterator().next().isEmpty()){
                statu = true;
                t_xxfp = null;
                t_xxfp_mx = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return statu;
    }

    /**
     * 关闭连接
     *
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("关闭ResultSet失败:"+e.toString());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error("关闭Statement失败:"+e.toString());
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("关闭Connection失败:"+e.toString());
            }
        }
    }

}
