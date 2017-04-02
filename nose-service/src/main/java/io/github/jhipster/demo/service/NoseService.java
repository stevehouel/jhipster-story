package io.github.jhipster.demo.service;

import io.github.jhipster.demo.service.utils.QueryCounter;
import io.github.jhipster.demo.web.rest.ItemResource;
import io.github.jhipster.demo.web.rest.LieClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stevehouel on 28/03/2017.
 */
@Service
public class NoseService {

    private final Logger log = LoggerFactory.getLogger(NoseService.class);

    private QueryCounter queryCounter;
    private LieClient lieClient;
    private ThreadPoolExecutor threadPoolExecutor;

    public NoseService(QueryCounter queryCounter, LieClient lieClient) {
        this.queryCounter = queryCounter;
        this.lieClient = lieClient;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }

    @Scheduled(fixedDelay = 100)
    public void fillQueue(){
        while (threadPoolExecutor.getActiveCount() < 10){
            threadPoolExecutor.execute(new LieWorker(queryCounter, lieClient));
        }
    }

    @PreDestroy
    public void killExecutor() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }
    }

}
