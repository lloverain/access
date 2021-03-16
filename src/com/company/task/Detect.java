package com.company.task;

import com.company.Main;
import com.company.db.Dbtool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务的详细任务
 *
 * @author 杨佳颖
 */
public class Detect implements Job {

    private static Log logger = LogFactory.getLog("user");
    private Dbtool dbtool = new Dbtool();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long startTime = System.currentTimeMillis();    //获取开始时间
        logger.info(simpleDateFormat.format(new Date())+"开始扫描数据库");
        Main.UpdateStatistics(simpleDateFormat.format(new Date())+"开始扫描数据库");
        //查询出新增和修改的
        boolean statu = dbtool.contrastAccessDate();
        if (statu) {
            Main.UpdateStatistics("数据有变动");
            logger.info("数据有变动");
        } else {
            Main.UpdateStatistics("未检测到数据变化");
            logger.info("未检测到数据变化");
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        Main.UpdateStatistics(simpleDateFormat.format(new Date())+"扫描结束：" + (endTime - startTime) + "ms");
        logger.info(simpleDateFormat.format(new Date())+"扫描结束：" + (endTime - startTime) + "ms");
    }


}
