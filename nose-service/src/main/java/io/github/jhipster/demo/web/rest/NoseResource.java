package io.github.jhipster.demo.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.demo.service.NoseService;
import io.github.jhipster.demo.service.utils.QueryCounter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by stevehouel on 28/03/2017.
 */
@RestController
@RequestMapping("/api")
public class NoseResource {

    private final QueryCounter queryCounter;

    public NoseResource(QueryCounter queryCounter) {
        this.queryCounter = queryCounter;
    }

    @GetMapping(value = "/qps")
    public Map<String, Integer> getMeanQps() {
        return queryCounter.getQps();
    }

    @GetMapping(value = "/qpsSinceStarted")
    public Float getMeanQpsSinceStarted() {
        return queryCounter.getQpsSinceStarted();
    }

    @GetMapping(value = "/lastSecQps")
    public Integer getLastSecQps() {
        return queryCounter.getLastSecCount();
    }
}
