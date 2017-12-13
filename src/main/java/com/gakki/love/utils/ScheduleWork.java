package com.gakki.love.utils;

import com.gakki.love.service.TopicService;
import javafx.scene.input.DataFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.text.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/11
 * \* Time: 22:17
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * schedule job:
 *  execute  every ten seconds;
 *  query tags from table topic and remove duplicates ,and compare to local file "/xml/tags.xml";
 *  new tags will be append to file.
 *
 * 定时任务：
 *  定时获取数据库中的tag，并比对xml/tags.xml文件中的tag如果有新的tag就写入到文件中
 * @author ramer
 *
 */

@Slf4j
public class ScheduleWork implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * get millis for 'time'
     * @param time "HH:mm:ss
     * @return
     */
    private static long getTimeMillis(String time){
        try {

            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

        @Autowired
    TopicService topicService;


    @Value("${flight.tags.xml.position")
    private String files;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (contextRefreshedEvent.getApplicationContext().getParent() == null){

            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

            long oneDay = 300 * 1000;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            //start now
            long initDelay = getTimeMillis(simpleDateFormat.format(new Date())) - System.currentTimeMillis();

            initDelay = initDelay > 0 ? initDelay : oneDay +initDelay;
            //start execute job
            executorService.scheduleAtFixedRate(() ->{
                log.debug("--------------start update tags--------------------");

                String file = System.getProperty("firstflight")+files;
                log.debug(file);

                //tag in databases;
                List<String> tags = topicService.getAllTags();
                //去除重复标签
                StringBuilder stringBuilder = new StringBuilder();
                for (String string:tags){
                    stringBuilder.append(string + ";");
                }
                tags = CollectionsUtils.removeSame(Arrays.asList(stringBuilder.toString().split(";")));

                log.debug("tags in databases: ");
                for (String string : tags){
                    log.debug("\t" + string);
                }

                Set<String> tagsInFile = new HashSet<>();
                try {
                    tagsInFile = FileUtils.readTag(file);
                } catch (Exception e) {
                    log.debug("Exception ScheduleWork(Line 111)");
                    e.printStackTrace();
                }
                log.debug("tags in file： ");
                for (String string : tagsInFile) {
                    log.debug("\t" + string);
                }
                //将要添加的tag
                List<String> updateTags = new ArrayList<>();
                // less than tags in database ,so each the tags in local file
                for (int i = 0; i < tags.size(); i++) {
                    if (!tagsInFile.contains(tags.get(i))) {
                        updateTags.add(tags.get(i));
                    }
                }
                log.debug("update tags： ");
                for (String string : updateTags) {
                    log.debug("\t" + string);
                }
                if (updateTags.size() == 0) {
                    log.debug("没有要添加的标签");
                    return;
                }
                try {
                    log.debug("更新文件");
                    // update tags in local file
                    FileUtils.writeTag(updateTags, file);
                } catch (Exception e) {
                    log.debug("Exception ScheduleWork(Line 129)");
                    e.printStackTrace();
                }

            }, initDelay, oneDay, TimeUnit.MILLISECONDS);
        }
    }

}