package com.edison.springbootdemo.controller;

//import com.gupaoedu.starter.HelloFormatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormatController {
//    @Autowired
//    HelloFormatTemplate helloFormatTemplate;//这里就可以直接拿来使用了。

    @GetMapping("/format")
    public  String format(){
        User user=new User();
        user.setName("yc");
        user.setAge(18);
//        return   helloFormatTemplate.doFormat(user);
        return "";
    }
}
