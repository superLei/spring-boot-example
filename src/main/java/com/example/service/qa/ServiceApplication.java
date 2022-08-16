package com.example.service.qa;

import java.util.concurrent.ThreadPoolExecutor;

import com.example.service.qa.thread.MdcTaskDecorator;
import com.example.service.qa.thread.pool.RequestProcessorThreadPool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author maxsu
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class ServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        return new TomcatServletWebServerFactory(2080);
    }

    @Override
    public void run(String... args) throws Exception {
        RequestProcessorThreadPool.init();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(10000);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setTaskDecorator(new MdcTaskDecorator());
        return executor;
    }
}
