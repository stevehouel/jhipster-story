package io.github.jhipster.demo.config.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        WeightedResponseTimeRule rule = new WeightedResponseTimeRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }

    @Bean
    public IPing ribbonPing() {
        return new NIWSDiscoveryPing();
    }
}
