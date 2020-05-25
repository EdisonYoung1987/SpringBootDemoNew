package com.edison.springbootdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edison.springbootdemo.Util.ServletUtil;
import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.constant.SystemConstant;
import com.edison.springbootdemo.domain.Response;
import com.edison.springbootdemo.domain.RspException;
import com.edison.springbootdemo.domain.UserCache;
import com.edison.springbootdemo.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.edison.springbootdemo.constant.SystemConstant.USERCACHE_KEY;

/**登录流程：
 * 1. 客户端请求/pk获取公钥pk
 * 2. 客户端使用公钥pk加密AES密钥请求/login,服务端使用私钥解密后缓存AES密钥
 * 3. 客户端其他请求都采用AES密钥加解密*/
@RestController
@Slf4j
public class LoginController {
    /**-@Autowired 这个是按照类型来注入，当有多个同类型的bean存在spring容器中时，需要配合@Qualifier(name="userDao1")
     来指定注入的bean
     -@Resource默认byName匹配，没指定的话，它认为beanName是字段名，
     当找不到的会按照byType进行匹配，如果指定了name或type属性，它就会根据你指定的去找，没找到，
     抛出异常，@Resource不能放在方法的形参上，属于java jdk自带的注解*/
//    @Autowired
    @Resource(name = "normalRedisTemplate") //考虑到定制化后又多个RedisTemplate实现类对象，每个都制定名称
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping(value = "/pk",method = RequestMethod.POST)
    public Response getPk(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> map=new HashMap<>(2);
        map.put("pk","adfdas234asddfasdfasdasfasf");//假装是公钥

        return Response.success(map);
    }

    /**登录controller，为了测试cookie，看是否能保存登录信息，假定该请求体包含用户和密码信息*/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //将登录信息json解析成bean对象
        StringBuilder body= ServletUtil.getRequestBody(request);

        //将登陆信息json解析出JSONObject对象
        JSONObject jsonObject=ServletUtil.parseJSONObject(body.toString());
        if(jsonObject==null){
            throw new RspException(ResponseConstant.LOGIN_WRONG_PARAMETERS);
        }

        //对Session进行处理
        HttpSession session=request.getSession(false);
        if(session!=null){//之前登陆过，现在重新登录或者登录其他账号
            session.invalidate();//需要将旧的session清除
        }
        //注意 调用session生成后，会自动set-Cookie，下次请求就会带上该session
        session=request.getSession();//每次登录生成新的session 注意，如果不调用该方法，则session不会产生
        log.info("当前session={}-{}",session,session.getId());

        //保存用户信息以及AES密钥到session中。
        String userId=jsonObject.getString("userId");//用户id
        String aesKey=jsonObject.getString("_secret");//秘钥--登录之后采用对称加解密
        if(userId==null||aesKey==null){
            throw new RspException(ResponseConstant.LOGIN_WRONG_PARAMETERS);
        }
        UserCache userCache=new UserCache();
        userCache.setUserId(userId);
        //其他信息需要调用用户微服务进行查询获取，可以无则插入
        session.setAttribute(SystemConstant.USERCACHE_KEY,userCache);
        session.setAttribute(SystemConstant.AES_KEY,aesKey);

        //生成token返给用户使用
        String token=TokenUtil.genToken16();
        session.setAttribute(SystemConstant.TOKEN_KEY,token);

        Map<String,String> res=new HashMap<>(2);
        res.put("token",token);

        return Response.success(res);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Response logout(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        if(session!=null){
            session.invalidate();
            log.info("用户已登出");
        }else{
            log.info("session为空，用户未登录");
        }
        return Response.success(null);
    }
}
