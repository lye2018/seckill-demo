package com.chongdu.seckill.controller;

import com.chongdu.seckill.common.utils.RedisLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Api(value = "hello模块")
@RestController
@RequestMapping("api/v1/swagger")
public class HelloController {

    @Autowired
    RedisLock redisLock;

    @ApiOperation(value = "输出hello", notes = "无需传参", response = String.class)
    @ApiResponses({
            @ApiResponse(code=200, message="添加成功"),
            @ApiResponse(code=500, message="系统异常")
    })
    @GetMapping(value = "")
    public String hello() {

        redisLock.lock("im-lye","lye",60000);

        System.out.println("========================释放锁");

        redisLock.releaseLock("im-lye", "lye1");
        return "hello";
    }
}
