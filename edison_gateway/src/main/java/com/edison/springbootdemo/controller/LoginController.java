package com.edison.springbootdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edison.springbootdemo.Util.ServletUtil;
import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.domain.RspException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@RestController
public class LoginController {
    /**-@Autowired 这个是按照类型来注入，当有多个同类型的bean存在spring容器中时，需要配合@Qualifier(name="userDao1")
     来指定注入的bean
     -@Resource默认byName匹配，没指定的话，它认为beanName是字段名，
     当找不到的会按照byType进行匹配，如果指定了name或type属性，它就会根据你指定的去找，没找到，
     抛出异常，@Resource不能放在方法的形参上，属于java jdk自带的注解*/
//    @Autowired
    @Resource(name = "normalRedisTemplate") //考虑到定制化后又多个RedisTemplate实现类对象，每个都制定名称
    private RedisTemplate<String,Object> redisTemplate;

    /**登录controller，为了测试cookie，看是否能保存登录信息，假定该请求体包含用户和密码信息*/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //将登录信息json解析成bean对象
        StringBuilder body= ServletUtil.getRequestBody(request);

        //将登陆信息json解析出JSONObject对象
        JSONObject jsonObject=ServletUtil.parseJSONObject(body.toString());
        if(jsonObject==null){
            throw new RspException(ResponseConstant.LOGIN_WRONG_PARAMETERS);
        }
        Set<String> keys= jsonObject.keySet();
        System.out.println("JSONObject:");
        for(String key:keys) {
            System.out.println(key+":"+jsonObject.get(key));
        }

        //对Session进行处理
        HttpSession httpsession=request.getSession(false);
        if(httpsession!=null){//之前登陆过，现在重新登录或者登录其他账号
            httpsession.invalidate();//需要将旧的session清除
        }
        httpsession=request.getSession();//每次登录生成新的session 注意，如果不调用该方法，则session不会产生
//        httpsession.setAttribute("em_id",user.getEm_id());//session保存用户部分信息

        //登陆之后设置cookie信息,cookie是http域头，可以存放session id
        //Set-Cookie: name=value[; expires=date][; domain=domain][; path=path][; secure]
        //Set-Cookie:name=Nicholas; domain=nczonline.net; path=/blog
        Cookie cookie=new Cookie("sid",httpsession.getId());//将session_id设置到cookie中
//        cookie.setDomain("");
        cookie.setMaxAge(120);
//        cookie.setPath("/*");
        response.addCookie(cookie);

        //登记redis登录信息 注意redisTemplate需要进行配置，否则String类型的key前面会有乱码，value也是一样的。
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String key=request.getRemoteHost()+"_"+sdf.format(date);
        long res=redisTemplate.opsForValue().increment(key);//访问次数加一
        if(res>5){
            System.out.println("登录过于频繁！！");
        }
        return "LOGINED";
    }
}
