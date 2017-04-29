package io.github.jhipster.demo.web.rest;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by stevehouel on 28/03/2017.
 */
@FeignClient(value = "lieService")
public interface LieClient {

    @RequestMapping(value = "/api/lies/request", method = RequestMethod.GET)
    String getRequest();
}
