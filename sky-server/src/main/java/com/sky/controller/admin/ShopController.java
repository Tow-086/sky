package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@Slf4j
@Api(tags ="店铺相关接口")
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private  RedisTemplate redisTemplate;


    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status) {

        log.info("设置店铺状态为：{}", status == 1 ? "开启" : "关闭");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);

        return Result.success();
    }

    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        if (status == null) {
            // 设置默认值或处理逻辑
            status = 1; // 或者根据业务需求设置其他默认值
            log.warn("SHOP_STATUS is null, using default value: {}", status);
        }
        log.info("获取店铺状态为：{}", status == 1 ? "开启" : "关闭");
        return Result.success(status);

    }

}
