package com.mannor.mealscoming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.vo.MerchantVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {
    /**
     * 分页查询商家信息
     *
     * @param page           分页对象
     * @param merchantName   商家名称
     * @param auditStatus    审核状态
     * @param auditComment   审核评论
     * @param createTimeEnd  创建时间结束
     * @param createTimeStart 创建时间开始
     * @param updateTimeEnd  更新时间结束
     * @param updateTimeStart 更新时间开始
     * @return 分页后的商家信息
     */
     IPage<Merchant> selectMerchantPage(Page<Merchant> page,
                                               @Param("merchantName") String merchantName,
                                               @Param("auditStatus") String auditStatus,
                                               @Param("auditComment") String auditComment,
                                               @Param("createTimeEnd") LocalDateTime createTimeEnd,
                                               @Param("createTimeStart") LocalDateTime createTimeStart,
                                               @Param("updateTimeEnd") LocalDateTime updateTimeEnd,
                                               @Param("updateTimeStart") LocalDateTime updateTimeStart);



}