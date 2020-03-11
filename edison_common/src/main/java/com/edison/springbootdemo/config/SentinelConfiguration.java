package com.edison.springbootdemo.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**这个是为了引入sentinel的注解SentinelResource*/
public class SentinelConfiguration  implements CommandLineRunner {
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Override
    public void run(String... args) throws Exception {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("findAll");
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);// 设置降级规则RT, 平均响应时间
        rule.setCount(200); // set threshold rt, 200 ms
        rule.setTimeWindow(20);// 设置时间窗口 秒
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
}
