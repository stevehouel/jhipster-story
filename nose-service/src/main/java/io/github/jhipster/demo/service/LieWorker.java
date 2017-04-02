package io.github.jhipster.demo.service;

import io.github.jhipster.demo.service.utils.QueryCounter;
import io.github.jhipster.demo.web.rest.LieClient;

/**
 * Created by stevehouel on 31/03/2017.
 */
public class LieWorker implements Runnable {
    private QueryCounter queryCounter;
    private LieClient lieClient;

    public LieWorker(QueryCounter queryCounter, LieClient lieClient) {
        this.queryCounter = queryCounter;
        this.lieClient = lieClient;
    }

    @Override
    public void run() {
        String s = lieClient.getRequest();
        if (s != null) {
            queryCounter.addQuery();
        }
    }
}
