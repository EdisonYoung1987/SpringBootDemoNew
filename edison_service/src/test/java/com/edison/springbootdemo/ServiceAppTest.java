package com.edison.springbootdemo;

import com.edison.springbootdemo.service.MicroSvcsImpl.EmployeeMicrosvcs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApp.class)
public class ServiceAppTest {
    @Autowired
    EmployeeMicrosvcs employeeMicrosvcs;

    @Test
    public void test(){
        employeeMicrosvcs.findAll();
    }
}
