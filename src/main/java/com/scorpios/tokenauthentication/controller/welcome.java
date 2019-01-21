package com.scorpios.tokenauthentication.controller;

import com.alibaba.fastjson.JSONObject;
import com.scorpios.tokenauthentication.annotation.AuthToken;
import com.scorpios.tokenauthentication.entity.User;
import com.scorpios.tokenauthentication.mapper.UserMapper;
import com.scorpios.tokenauthentication.model.ResponseTemplate;
import com.scorpios.tokenauthentication.utils.ConstantKit;
import com.scorpios.tokenauthentication.utils.Md5TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author Think
 * @Title: welocome
 * @ProjectName token-authentication
 * @Description: TODO
 * @date 2019/1/1815:41
 */
@RestController
public class welcome {

    Logger logger = LoggerFactory.getLogger(welcome.class);

    @Autowired
    Md5TokenGenerator tokenGenerator;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/welcome")
    public String welcome(){

        return "welcome token authentication";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseTemplate login(String username, String password) {

        logger.info("username:"+username+"      password:"+password);

        User user = userMapper.getUser(username,password);

        logger.info("user:"+user);

        JSONObject result = new JSONObject();
        if (user != null) {

            Jedis jedis = new Jedis("192.168.1.106", 6379);
            String token = tokenGenerator.generate(username, password);
            jedis.set(username, token);
            //设置key生存时间，当key过期时，它会被自动删除，时间是秒
            jedis.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
            jedis.set(token, username);
            jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
            Long currentTime = System.currentTimeMillis();
            jedis.set(token + username, currentTime.toString());

            //用完关闭
            jedis.close();

            result.put("status", "登录成功");
            result.put("token", token);
        } else {
            result.put("status", "登录失败");
        }

        return ResponseTemplate.builder()
                .code(200)
                .message("登录成功")
                .data(result)
                .build();

    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @AuthToken
    public ResponseTemplate test() {

        logger.info("已进入test路径");

        return ResponseTemplate.builder()
                .code(200)
                .message("Success")
                .data("test url")
                .build();
    }

}
