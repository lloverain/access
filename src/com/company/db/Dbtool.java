package com.company.db;

import com.company.Main;
import com.company.entity.AccessDate;
import com.company.entity.AccessXxfp;
import com.company.tool.AlertConfig;
import com.company.tool.Utile;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 该方法是数据库相关的操作
 * 读取Access
 * 比对数据
 * 备份sqlite
 * 保存mysql/oracle/sqlserver
 *
 * @author 杨佳颖
 */
public class Dbtool {

    private static Log logger = LogFactory.getLog("user");
    //Access和sqlite的表名
    private static String t_xxfp = "t_xxfp";
    private static String t_xxfp_mx = "t_xxfp_mx";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);

    /**
     * 对比数据，并生成结果
     */
    public boolean contrastAccessDate() {
        //查询t_xxfp开始id
        int num = AlertConfig.getStartNum(t_xxfp);
        //查询t_xxfp新增内容
        List<AccessXxfp> t_xxfpList = checkAddTxxfp(t_xxfp, num);
        //t_xxfp新增数量
        int t_xxfpsize = t_xxfpList.size();
        //查询t_xxfp_mx开始id
        int startNum = AlertConfig.getStartNum(t_xxfp_mx);
        //查询出t_xxfp_mx新增内容
        List<AccessDate> t_xxfp_mxList = checkAdd(t_xxfp_mx, startNum);
        //t_xxfp_mx新增数量
        boolean t = false;
        boolean tmx = false;
        int t_xxfpmxsize = t_xxfp_mxList.size();
        if (t_xxfpsize > 0) {
            //保存到数据库
            t = savet_xxfp(t_xxfpList);
            if (t) {
                //保存t_xxfp成功
                int t_xxfpID = t_xxfpList.get(t_xxfpsize - 1).getId();
                AlertConfig.AlertConfig(t_xxfp, t_xxfpID);
                Main.UpdateStatistics("t_xxfp新增数据:" + t_xxfpsize + "条");
                logger.info("t_xxfp新增数据:" + t_xxfpsize + "条");
            }
        }
        if (t_xxfpmxsize > 0) {
            //保存到数据库
            tmx = savet_xxfp_mx(t_xxfp_mxList);
            if (tmx) {
                //保存t_xxfp_mx成功
                int t_xxfp_mxID = t_xxfp_mxList.get(t_xxfpmxsize - 1).getId();
                AlertConfig.AlertConfig(t_xxfp_mx, t_xxfp_mxID);
                Main.UpdateStatistics("t_xxfp_mx新增数据:" + t_xxfpmxsize + "条");
                logger.info("t_xxfp_mx新增数据:" + t_xxfpmxsize + "条");
            }
        }
        //保存成功
        t_xxfpList.clear();
        t_xxfp_mxList.clear();
        if (t == true || tmx == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读取t_xxfp表新增内容
     *
     * @param t_xxfp
     * @param num    初始id位置
     * @return
     */
    private List<AccessXxfp> checkAddTxxfp(String t_xxfp, int num) {
        Database db = null;
        List<AccessXxfp> accessXxfps = new ArrayList<>();
        try {
            db = DatabaseBuilder.open(new File(AlertConfig.getUrl()));
            Table table = db.getTable(t_xxfp);
//            int num = AlertConfig.getStartNum(t_xxfp);System.out
            for (Row row : table) {
                int id = row.getInt("id");
                if (id > num) {
                    AccessXxfp accessXxfp = new AccessXxfp();
                    accessXxfp.setId(row.getInt("id"));
                    accessXxfp.setFpzl(row.getString("fpzl"));
                    accessXxfp.setFpdm(row.getString("fpdm"));
                    accessXxfp.setFphm(row.getInt("fphm"));
                    accessXxfp.setGfmc(row.getString("gfmc"));
                    accessXxfp.setGfbh(row.getString("gfbh"));
                    accessXxfp.setGfsh(row.getString("gfsh"));
                    accessXxfp.setGfdzdh(row.getString("gfdzdh"));
                    accessXxfp.setGfyhzh(row.getString("gfyhzh"));
                    accessXxfp.setXfmc(row.getString("xfmc"));
                    accessXxfp.setXfsh(row.getString("xfsh"));
                    accessXxfp.setXfdzdh(row.getString("xfdzdh"));
                    accessXxfp.setXfyhzh(row.getString("xfyhzh"));

                    String kprq = String.valueOf(row.get("kprq"));
                    System.out.println();
                    if (kprq == null || kprq.equals("") || kprq.equals("null")) {
                        accessXxfp.setKprq(null);
                    } else {
                        Date date1 = simpleDateFormat.parse(kprq);
                        accessXxfp.setKprq(new Timestamp(date1.getTime()));
                    }
                    accessXxfp.setHjje(row.getDouble("hjje"));
                    accessXxfp.setSlv(row.getDouble("slv"));
                    accessXxfp.setHjse(row.getDouble("hjse"));
                    accessXxfp.setKpr(row.getString("kpr"));
                    accessXxfp.setSkr(row.getString("skr"));
                    accessXxfp.setFhr(row.getString("fhr"));
                    accessXxfp.setZfbz(row.getInt("zfbz"));

                    String zfrq = String.valueOf(row.get("zfrq"));
                    if (zfrq == null || zfrq.equals("") || zfrq.equals("null")) {
                        accessXxfp.setZfrq(null);
                    } else {
                        Date date1 = simpleDateFormat.parse(zfrq);
                        accessXxfp.setZfrq(new Timestamp(date1.getTime()));
                    }
                    accessXxfp.setBz(row.getString("bz"));
                    accessXxfp.setXsdjbh(row.getString("xsdjbh"));
                    accessXxfp.setReturntag(row.getInt("returntag"));
                    accessXxfp.setSqr(row.getString("sqr"));
                    accessXxfp.setHyk(row.getString("hyk"));
                    accessXxfp.setFpmw(row.getString("fpmw"));
                    accessXxfp.setJym(row.getString("jym"));
                    accessXxfp.setEwm(row.getString("ewm"));
                    accessXxfp.setPdffile(row.getString("pdffile"));
                    accessXxfp.setQzcode(row.getString("qzcode"));
                    accessXxfp.setQzmsg(row.getString("qzmsg"));
                    accessXxfp.setEmailtag(row.getString("emailtag"));
                    accessXxfp.setPre1(row.getString("pre1"));
                    accessXxfp.setPre2(row.getString("pre2"));
                    accessXxfp.setPre3(row.getString("pre3"));
                    accessXxfp.setPre4(row.getString("pre4"));
                    accessXxfps.add(accessXxfp);

                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return accessXxfps;
    }

    /**
     * 读取t_xxfp_mx表新增内容
     *
     * @param t_xxfp_mx
     * @param num       初始id位置
     * @return
     */
    private List<AccessDate> checkAdd(String t_xxfp_mx, int num) {
        Database db = null;
        List<AccessDate> accessDateList = new ArrayList<>();
        try {
            db = DatabaseBuilder.open(new File(AlertConfig.getUrl()));
            Table table = db.getTable(t_xxfp_mx);
            for (Row row : table) {
                int id = row.getInt("id");
                if (id > num) {
                    AccessDate accessDate = new AccessDate();
                    accessDate.setId(row.getInt("id"));
                    accessDate.setFpzl(row.getString("fpzl"));
                    accessDate.setFpdm(row.getString("fpdm"));
                    accessDate.setFphm(row.getInt("fphm"));
                    accessDate.setXh(row.getInt("xh"));
                    accessDate.setMxtype(row.getInt("mxtype"));
                    accessDate.setJe(row.getDouble("je"));
                    accessDate.setSlv(row.getDouble("slv"));
                    accessDate.setSe(row.getDouble("se"));
                    accessDate.setGgxh(row.getString("ggxh"));
                    accessDate.setZiduan(row.getString("ziduan"));
                    accessDate.setSpbh(row.getString("spbh"));
                    accessDate.setSpmc(row.getString("spmc"));
                    accessDate.setSpsm(row.getString("spsm"));
                    accessDate.setJldw(row.getString("jldw"));
                    accessDate.setDj(row.getDouble("dj"));
                    accessDate.setSl(row.getDouble("sl"));
                    accessDate.setHsjbz(row.getInt("hsjbz"));
                    accessDate.setXsdjmxid(row.getString("xsdjmxid"));
                    accessDate.setPre1(row.getString("pre1"));
                    accessDate.setPre2(row.getString("pre2"));
                    accessDate.setPre3(row.getString("pre3"));
                    accessDate.setPre4(row.getString("pre4"));
                    accessDateList.add(accessDate);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return accessDateList;
    }


    /**
     * 保存到mysql
     */
    public boolean saveMysql(List<AccessXxfp> accessXxfps, List<AccessDate> accessDates) {
        boolean a = savet_xxfp(accessXxfps);
        boolean b = savet_xxfp_mx(accessDates);
        if (a && b) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存到t_xxfp
     *
     * @param accessXxfps
     * @return
     */
    public boolean savet_xxfp(List<AccessXxfp> accessXxfps) {
        Connection mysqlConn = Utile.getMysqlConn();
        PreparedStatement psts = null;
        String sql = "insert into t_xxfp values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            mysqlConn.setAutoCommit(false);
            psts = mysqlConn.prepareStatement(sql);
            for (int i = 0; i < accessXxfps.size(); i++) {
                AccessXxfp accessXxfp = accessXxfps.get(i);
                psts.setInt(1, accessXxfp.getId());
                psts.setString(2, accessXxfp.getFpzl());
                psts.setString(3, accessXxfp.getFpdm());
                psts.setInt(4, accessXxfp.getFphm());
                psts.setString(5, accessXxfp.getGfmc());
                psts.setString(6, accessXxfp.getGfbh());
                psts.setString(7, accessXxfp.getGfsh());
                psts.setString(8, accessXxfp.getGfdzdh());
                psts.setString(9, accessXxfp.getGfyhzh());
                psts.setString(10, accessXxfp.getXfmc());
                psts.setString(11, accessXxfp.getXfsh());
                psts.setString(12, accessXxfp.getXfdzdh());
                psts.setString(13, accessXxfp.getXfyhzh());
                psts.setTimestamp(14, accessXxfp.getKprq());
                psts.setDouble(15, accessXxfp.getHjje());
                psts.setDouble(16, accessXxfp.getSlv());
                psts.setDouble(17, accessXxfp.getHjse());
                psts.setString(18, accessXxfp.getKpr());
                psts.setString(19, accessXxfp.getSkr());
                psts.setString(20, accessXxfp.getFhr());
                psts.setInt(21, accessXxfp.getZfbz());
                psts.setTimestamp(22, accessXxfp.getZfrq());
                psts.setString(23, accessXxfp.getBz());
                psts.setString(24, accessXxfp.getXsdjbh());
                psts.setInt(25, accessXxfp.getReturntag());
                psts.setString(26, accessXxfp.getSqr());
                psts.setString(27, accessXxfp.getHyk());
                psts.setString(28, accessXxfp.getFpmw());
                psts.setString(29, accessXxfp.getJym());
                psts.setString(30, accessXxfp.getEwm());
                psts.setString(31, accessXxfp.getPdffile());
                psts.setString(32, accessXxfp.getQzcode());
                psts.setString(33, accessXxfp.getQzmsg());
                psts.setString(34, accessXxfp.getEmailtag());
                psts.setString(35, accessXxfp.getPre1());
                psts.setString(36, accessXxfp.getPre2());
                psts.setString(37, accessXxfp.getPre3());
                psts.setString(38, accessXxfp.getPre4());
                psts.addBatch();
                psts.executeBatch();
            }
            mysqlConn.commit();
            return true;
        } catch (SQLException throwables) {
            logger.error("保存到t_xxfp数据记录失败！" + throwables.toString());
            try {
                mysqlConn.rollback();
            } catch (SQLException e) {
                logger.error("savet_xxfp回滚失败:" + e.toString());
            }
            return false;
        } finally {
            Utile.close(mysqlConn, psts, null);
        }
    }

    /**
     * 保存到t_xxfp_mx
     *
     * @param accessDates
     * @return
     */
    public boolean savet_xxfp_mx(List<AccessDate> accessDates) {
        Connection mysqlConn = Utile.getMysqlConn();
        PreparedStatement psts = null;
        String sql = "insert into t_xxfp_mx values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            mysqlConn.setAutoCommit(false);
            psts = mysqlConn.prepareStatement(sql);
            for (int i = 0; i < accessDates.size(); i++) {
                AccessDate accessDate = accessDates.get(i);
                //这里写插入sql语句
                psts.setInt(1, accessDate.getId());
                psts.setString(2, accessDate.getFpzl());
                psts.setString(3, accessDate.getFpdm());
                psts.setInt(4, accessDate.getFphm());
                psts.setInt(5, accessDate.getXh());
                psts.setInt(6, accessDate.getMxtype());
                psts.setDouble(7, accessDate.getJe());
                psts.setDouble(8, accessDate.getSlv());
                psts.setDouble(9, accessDate.getSe());
                psts.setString(10, accessDate.getGgxh());
                psts.setString(11, accessDate.getSpbh());
                psts.setString(12, accessDate.getSpmc());
                psts.setString(13, accessDate.getSpsm());
                psts.setString(14, accessDate.getJldw());
                psts.setDouble(15, accessDate.getDj());
                psts.setDouble(16, accessDate.getSl());
                psts.setInt(17, accessDate.getHsjbz());
                psts.setString(18, accessDate.getXsdjmxid());
                psts.setString(19, accessDate.getPre1());
                psts.setString(20, accessDate.getPre2());
                psts.setString(21, accessDate.getPre3());
                psts.setString(22, accessDate.getPre4());
                psts.addBatch();
                psts.executeBatch();
            }
            mysqlConn.commit();
            Utile.close(mysqlConn, psts, null);
            return true;
        } catch (SQLException throwables) {
            logger.error("savet_xxfp_mx数据记录失败！" + throwables.toString());
            try {
                mysqlConn.rollback();
            } catch (SQLException e) {
                logger.error("savet_xxfp_mx回滚失败:" + e.toString());
            }
            return false;
        } finally {
            Utile.close(mysqlConn, psts, null);
        }
    }
}
