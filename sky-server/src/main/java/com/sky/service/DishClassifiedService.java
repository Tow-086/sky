package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface DishClassifiedService {
    //新增套餐
    void saveDishClassified(SetmealDTO dishDTO);
    //分页查询
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    //批量删除套餐
    void deleteBatch(List<Long> ids);
    //根据id查询菜品
    SetmealVO getByIdWithDish(Long id);

    //修改套餐
    void update(SetmealDTO setmealDTO);
    //启用停售套餐
    void startOrStop(Integer status, Long id);

    //新增套餐
//    void saveWithDish(DishDTO dishDTO);
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
