package j.spring.framework.core.testdata.ioc.scanerror.beancycle;

import j.spring.framework.core.web.annotation.Bean;
import j.spring.framework.core.web.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public BeanCycle1 beanCycle1(BeanCycle2 beanCycle2) {
        return new BeanCycle1();
    }

    @Bean
    public BeanCycle2 beanCycle2(BeanCycle1 beanCycle1) {
        return new BeanCycle2();
    }

}
