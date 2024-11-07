package com.mannor.mealscoming.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mannor.mealscoming.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * 自定义元数据处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private Employee employee;
    /**
     * 插入操作,自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {//metaObject里面包含insert提交的对象数据
        log.info("插入操作公共填充······");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * 更新操作,自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {//metaObject里面包含update提交的对象数据
        log.info("更新操作公共填充······{}",employee);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",  BaseContext.getCurrentId());
    }

}