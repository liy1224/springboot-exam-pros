package com.exam.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author liyang
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(6);
        //最大线程数
        taskExecutor.setMaxPoolSize(12);
        //队列大小
        taskExecutor.setQueueCapacity(249);

        //线程名称前缀
        taskExecutor.setThreadNamePrefix("resourceStatisticsHandler-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
