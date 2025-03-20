package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.service.MerchantAuditService;
import com.mannor.mealscoming.service.MerchantDetailsService;
import com.mannor.mealscoming.service.MerchantService;
import com.mannor.mealscoming.vo.MerchantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantDetailsService merchantDetailsService;

    @Autowired
    private MerchantAuditService merchantAuditService;

    /**
     * 新增商家信息
     *
     * @param merchant 商家实体
     * @return 新增结果
     */
    @PostMapping
    public R<Boolean> save(@RequestBody Merchant merchant) {
        return R.success(merchantService.save(merchant));
    }

    /**
     * 删除商家信息
     *
     * @param id 商家信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.success(merchantService.removeById(id));
    }

    /**
     * 修改商家信息
     *
     * @param merchant 商家实体
     * @return 修改结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody Merchant merchant) {
        return R.success(merchantService.updateById(merchant));
    }

    /**
     * 根据主键查询单个商家信息
     *
     * @param id 商家信息的主键
     * @return 商家信息实体
     */
    @GetMapping("/{id}")
    public R<Merchant> getById(@PathVariable Long id) {
        return R.success(merchantService.getById(id));
    }

    /**
     * 查询所有商家信息
     *
     * @return 商家信息列表
     */
    @GetMapping
    public R<List<Merchant>> findAll() {
        return R.success(merchantService.list());
    }

    /**
     * 分页查询商家信息
     *
     * @param page         页码
     * @param pageSize     每页数量
     * @param merchantName 商家名称（可选）
     * @return 分页后的商家信息
     */
    @GetMapping("/page")
    public R<ArrayList<Object>> findPage(@RequestParam Integer page,
                                         @RequestParam Integer pageSize,
                                         @RequestParam(required = false) String merchantName,
                                         @RequestParam(required = false) String auditStatus,
                                         @RequestParam(required = false) String merchantType,
                                         @RequestParam(required = false) LocalDateTime createTimeEnd,
                                         @RequestParam(required = false) LocalDateTime createTimeStart,
                                         @RequestParam(required = false) LocalDateTime updateTimeEnd,
                                         @RequestParam(required = false) LocalDateTime updateTimeStart
    ) {
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        if (merchantName != null && !merchantName.isEmpty()) {
            queryWrapper.like("merchant_name", merchantName);
        }
        // 1.是审核表中的信息筛选
//        if (auditStatus != null && !auditStatus.isEmpty()) {
//            queryWrapper.eq("merchant_status", auditStatus);
//        }
//        if (merchantType != null && !merchantType.isEmpty()) {
//            queryWrapper.eq("merchant_type", merchantType);
//        }
//        if (createTimeEnd != null) {
//            queryWrapper.le("create_time", createTimeEnd);
//        }
//        if (createTimeStart != null) {
//            queryWrapper.ge("create_time", createTimeStart);
//        }
//        if (updateTimeEnd != null) {
//            queryWrapper.le("update_time", updateTimeEnd);
//        }
//        if (updateTimeStart != null) {
//            queryWrapper.ge("update_time", updateTimeStart);
//        }
        queryWrapper.orderByDesc("update_time");
        Page<Merchant> page1 = merchantService.page(new Page<>(page, pageSize), queryWrapper);
        List<MerchantVo> merchantVos = new ArrayList<>();
        page1.getRecords().forEach(merchant -> {
            MerchantVo merchantVo = new MerchantVo();
            merchantVo.setId(merchant.getId());
            merchantVo.setDetailId(merchant.getDetailId());
            merchantVo.setMerchantName(merchant.getMerchantName());
            merchantVo.setCreateTime(merchant.getCreateTime());
            merchantVo.setUpdateTime(merchant.getUpdateTime());
            merchantVo.setMerchantDetails(merchantDetailsService.getById(merchant.getDetailId()));
            merchantVo.setMerchantAudit(merchantAuditService.getOne(new QueryWrapper<MerchantAudit>().eq("merchant_id", merchant.getId())));
            merchantVos.add(merchantVo);
        });
        ArrayList<Object> result = new ArrayList<>();
        result.add(merchantVos);

        return R.success(result);
    }
}    