package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    // 根据菜品id查询套餐id
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    // 插入套餐
    void insertBatch(List<SetmealDish> setmealDishes);
    // 根据套餐id删除套餐和菜品的关联数据
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}"
    )
    void deleteBySetmealId(Long setmealId);
    // 根据套餐id查询套餐
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);
}
