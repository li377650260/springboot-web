package com.tonpower.springbootweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/24 15:03
 */

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean
    public Executor asyncServiceExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程输
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 配置队列的大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("博客系统");
        // 设置等待所有任务都关闭后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 执行初始化配置
        executor.initialize();

        return executor;
    }
}
