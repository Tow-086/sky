package com.sky.controller.admin;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishClassifiedService;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//菜品分类管理
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "菜品套餐相关接口")
@Slf4j
public class DishClassifiedController {
    @Autowired
    private DishClassifiedService dishClassifiedService;

    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames="setmealCache",key="#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐:{}", setmealDTO);
        dishClassifiedService.saveDishClassified(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询:{}", setmealPageQueryDTO);
        PageResult pageResult = dishClassifiedService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    //删除套餐
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames="setmealCache",allEntries=true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐:{}", ids);
        dishClassifiedService.deleteBatch(ids);
        return Result.success();
    }
    //根据id查询套餐
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据id查询套餐:{}", id);
        SetmealVO setmealVO = dishClassifiedService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    //修改套餐
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames="setmealCache",allEntries=true)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐:{}", setmealDTO);
        dishClassifiedService.update(setmealDTO);
        return Result.success();
    }

    //套餐起售停售
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    @CacheEvict(cacheNames="setmealCache",allEntries=true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("套餐起售停售:{},{}", status, id);
        dishClassifiedService.startOrStop(status, id);
        return Result.success();
    }

}
