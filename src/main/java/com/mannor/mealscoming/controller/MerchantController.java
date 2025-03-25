package com.mannor.mealscoming.controller;

import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.MerchantAudit;
import com.mannor.mealscoming.entity.MerchantDetails;
import com.mannor.mealscoming.service.MerchantAuditService;
import com.mannor.mealscoming.service.MerchantDetailsService;
import com.mannor.mealscoming.service.MerchantService;
import com.mannor.mealscoming.vo.MerchantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * 分页查询商家信息
     *
     * @param page         页码
     * @param pageSize     每页数量
     * @param merchantName 商家名称（可选）
     * @return 分页后的商家信息
     */
    @GetMapping("/page")
    public R<List<MerchantVo>> findPage(@RequestParam Integer page,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false) String merchantName,
                                        @RequestParam(required = false) String auditStatus,
                                        @RequestParam(required = false) String auditComment,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createTimeEnd,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createTimeStart,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateTimeEnd,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateTimeStart
    ) {
        return R.success(merchantService.page(page, pageSize, merchantName, auditStatus, auditComment, createTimeEnd, createTimeStart, updateTimeEnd, updateTimeStart));
//        return R.success(null);
    }


    /**
     * 注册商家
     *
     * @param merchantVo 商家信息
     * @return 注册结果
     */
    @PostMapping("register")
    public R<Boolean> register(@RequestBody MerchantVo merchantVo) {

        return R.success(merchantService.register(merchantVo));
    }
}