A.登录流程(加解密目前只是有个框架，没有具体实现)
    1./pk 获取公钥 //后续的login需要使用该公钥加密
    2./login
       请求{
           "id":"122",
           "userId":"123456",
           "_secret":"123sadfasfasf"   //AES加解密的密钥
       }
       响应：{"token": "ujktT9ORnENxJroA"}

B.登陆后流程：
    请求和响应内容使用上面的_secret进行AES加解密;
    签名：MD5(请求体json内容按key排序后+登录的token),然后追加请求体内容_signature
    请求{
         "其他":"xxx",
         "_ts":"当前unix时间-到毫秒",
         "_signature":"签名"
    }

这样，token只在登录时传输，后续通信只是作为签名的一部分，避免请求被拦截，同时后端会根据_ts
校验请求是否超时失效，也会根据用户登录时的id+签名作为key检查请求是否重复