package com.mannor.mealscoming.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mannor.mealscoming.common.BaseContext;
import com.mannor.mealscoming.common.R;
import com.mannor.mealscoming.dto.EvaluationUserDto;
import com.mannor.mealscoming.entity.*;
import com.mannor.mealscoming.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private UserService userService;


    @Autowired
    private MerchantService merchantService;

    /**
     * 新增评价管理信息
     *
     * @param evaluation 评价管理实体
     * @return 新增结果
     */
    @PostMapping

    public R<Boolean> save(@RequestBody Evaluation evaluation) {
        evaluation.setUserId(BaseContext.getCurrentId());
        evaluation.setEvaluationTime(LocalDateTime.now());
        return R.success(evaluationService.save(evaluation));
    }

    /**
     * 删除评价管理信息
     *
     * @param id 评价管理信息的主键
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.success(evaluationService.removeById(id));
    }

    /**
     * 修改评价管理信息
     *
     * @param evaluation 评价管理实体
     * @return 修改结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody Evaluation evaluation) {
//        evaluation.setEvaluationTime(LocalDateTime.now());
        return R.success(evaluationService.updateById(evaluation));
    }

    /**
     * 根据主键查询单个评价管理信息
     *
     * @param id 评价管理信息的主键
     * @return 评价管理信息实体
     */
    @GetMapping("/{id}")
    public R<Evaluation> getById(@PathVariable Long id) {
        return R.success(evaluationService.getOne(new LambdaQueryWrapper<Evaluation>().eq(Evaluation::getEvaluatedObjectId, id)));
    }

    /**
     * 查询所有评价管理信息
     *
     * @return 评价管理信息列表
     */
    @GetMapping
    public R<Page<EvaluationUserDto>> list(@RequestParam Integer pageNum,
                                           @RequestParam Integer pageSize,
                                           @RequestParam(required = false) String evaluationContent,
                                           @RequestParam(required = false) String evaluatedObjectType,
                                           HttpServletRequest request) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        if (evaluationContent != null && !evaluationContent.isEmpty()) {
            queryWrapper.like("evaluation_content", evaluationContent);
        }
        if (evaluatedObjectType != null && !evaluatedObjectType.isEmpty()) {
            queryWrapper.eq("evaluated_object_type", evaluatedObjectType);
        }
        queryWrapper.orderByDesc("id");

        Page<Evaluation> evaluationPage = evaluationService.page(new Page<>(pageNum, pageSize), queryWrapper);

        Page<EvaluationUserDto> evaluationUserDtoPage = new Page<>();
        BeanUtils.copyProperties(evaluationPage, evaluationUserDtoPage, "records");

        List<EvaluationUserDto> evaluationUserDtoList = new ArrayList<>();
        for (Evaluation evaluation : evaluationPage.getRecords()) {
            EvaluationUserDto evaluationUserDto = new EvaluationUserDto();
            BeanUtils.copyProperties(evaluation, evaluationUserDto);

            Long userId = evaluation.getUserId();
            if (userId != null) {
                User user = userService.getById(userId);
                evaluationUserDto.setUser(user);
            }

            Long merchantId = evaluation.getMerchantId();
            if (merchantId != null) {
                Merchant merchant = merchantService.getById(merchantId);
                evaluationUserDto.setMerchant(merchant);
            }

            evaluationUserDtoList.add(evaluationUserDto);
        }
        evaluationUserDtoPage.setRecords(evaluationUserDtoList);

        return R.success(evaluationUserDtoPage);
    }

    /**
     * 根据商家id查询评价管理信息
     *
     * @param id
     * @return
     */
    @GetMapping("list/{id}")
    public R<List<Evaluation>> findMerchantAll(@PathVariable Long id) {

        List<Orders> list = ordersService.list(new LambdaQueryWrapper<Orders>().eq(Orders::getMerchantId, id));
        ArrayList<Evaluation> evaluations = new ArrayList<>();
        list.forEach(orders -> {
            System.out.println(orders);
            System.out.println(orders.getId());
            Evaluation evaluation = evaluationService.getOne(new LambdaQueryWrapper<Evaluation>().eq(Evaluation::getEvaluatedObjectId, orders.getId()));
            evaluations.add(evaluation);
        });
        return R.success(evaluations);
    }

    /**
     * 分页查询评价管理信息
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 分页后的评价管理信息
     */
    @GetMapping("/page")
    public R<Page<Evaluation>> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false) String evaluationContent,
                                        @RequestParam(required = false) String evaluatedObjectType, HttpServletRequest request) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        System.out.println(evaluationContent);
        System.out.println(evaluatedObjectType);
        // 构造商家查询条件
        Object merchantId = request.getSession().getAttribute("MerchantId");
        if (merchantId == null) {
            merchantId = request.getSession().getAttribute("EmployeeId");
            merchantId = employeeService.getOne(new LambdaQueryWrapper<Employee>().eq(Employee::getId, merchantId)).getMerchantId();
        }
        queryWrapper.eq("merchant_id", merchantId);
        if (evaluationContent != null && evaluationContent != "") {
            queryWrapper.like("evaluation_content", evaluationContent);
        }
        if (evaluatedObjectType != null && evaluatedObjectType != "") {
            queryWrapper.eq("evaluated_object_type", evaluatedObjectType);
        }
        queryWrapper.orderByDesc("id");
        return R.success(evaluationService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}