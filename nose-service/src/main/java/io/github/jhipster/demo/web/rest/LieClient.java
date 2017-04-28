package io.github.jhipster.demo.web.rest;

import io.github.jhipster.demo.config.ribbon.RibbonConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by stevehouel on 28/03/2017.
 */
@FeignClient(value = "lieService")
@RibbonClient(name = "lieService", configuration = RibbonConfiguration.class)
public interface LieClient {

    @LoadBalanced
    @RequestMapping(value = "/api/lies/request", method = RequestMethod.GET)
    String getRequest();
}
