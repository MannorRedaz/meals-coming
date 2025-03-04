package com.mannor.mealscoming;

import com.mannor.mealscoming.entity.*;
import com.mannor.mealscoming.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
public class EntityCrudTest {

    @Autowired
    private MerchantAuditMapper merchantAuditMapper;
    @Autowired
    private ComplaintSuggestionManagementMapper complaintSuggestionManagementMapper;
    @Autowired
    private EvaluationManagementMapper evaluationManagementMapper;
    @Autowired
    private CertificationManagementMapper certificationManagementMapper;
    @Autowired
    private DishEvaluationMapper dishEvaluationMapper;
    @Autowired
    private CustomerComplaintSuggestionMapper customerComplaintSuggestionMapper;

    @Test
    public void testMerchantAuditCrud() {
        // 新增
        MerchantAudit merchantAudit = new MerchantAudit();
        merchantAudit.setMerchant_id(1L);
        merchantAudit.setAudit_status("待审核");
        merchantAudit.setAudit_comment("无");
        merchantAudit.setAudit_time(LocalDateTime.now());
        merchantAuditMapper.insert(merchantAudit);

        // 查询
        MerchantAudit selectMerchantAudit = merchantAuditMapper.selectById(merchantAudit.getId());
        System.out.println("查询到的商家审核信息: " + selectMerchantAudit);

        // 修改
        selectMerchantAudit.setAudit_status("已通过");
        merchantAuditMapper.updateById(selectMerchantAudit);

        // 删除
        merchantAuditMapper.deleteById(selectMerchantAudit.getId());
    }

    @Test
    public void testComplaintSuggestionManagementCrud() {
        // 新增
        ComplaintSuggestionManagement complaint = new ComplaintSuggestionManagement();
        complaint.setComplaint_type("菜品问题");
        complaint.setComplaint_content("菜品不新鲜");
        complaint.setUser_id(1L);
        complaint.setHandling_status("待处理");
        complaint.setHandling_result("无");
        complaint.setHandling_time(LocalDateTime.now());
        complaintSuggestionManagementMapper.insert(complaint);

        // 查询
        ComplaintSuggestionManagement selectComplaint = complaintSuggestionManagementMapper.selectById(complaint.getId());
        System.out.println("查询到的投诉建议信息: " + selectComplaint);

        // 修改
        selectComplaint.setHandling_status("已处理");
        complaintSuggestionManagementMapper.updateById(selectComplaint);

        // 删除
        complaintSuggestionManagementMapper.deleteById(selectComplaint.getId());
    }

    @Test
    public void testEvaluationManagementCrud() {
        // 新增
        EvaluationManagement evaluation = new EvaluationManagement();
        evaluation.setEvaluation_content("服务很好");
        evaluation.setUser_id(1L);
        evaluation.setEvaluated_object_id(1L);
        evaluation.setEvaluated_object_type("商家");
        evaluation.setEvaluation_time(LocalDateTime.now());
        evaluation.setScore((short) 1);
        evaluationManagementMapper.insert(evaluation);

        // 查询
        EvaluationManagement selectEvaluation = evaluationManagementMapper.selectById(evaluation.getId());
        System.out.println("查询到的评价信息: " + selectEvaluation);

        // 修改
        selectEvaluation.setEvaluation_content("服务非常好");
        evaluationManagementMapper.updateById(selectEvaluation);

        // 删除
        evaluationManagementMapper.deleteById(selectEvaluation.getId());
    }

    @Test
    public void testCertificationManagementCrud() {
        // 新增
        CertificationManagement certification = new CertificationManagement();
        certification.setMerchant_id(1L);
        certification.setCertification_type("营业执照认证");
        certification.setCertification_file_path("/path/to/file");
        certification.setCertification_status("待审核");
        certification.setCertification_time(LocalDateTime.now());
        certificationManagementMapper.insert(certification);

        // 查询
        CertificationManagement selectCertification = certificationManagementMapper.selectById(certification.getId());
        System.out.println("查询到的认证信息: " + selectCertification);

        // 修改
        selectCertification.setCertification_status("已通过");
        certificationManagementMapper.updateById(selectCertification);

        // 删除
        certificationManagementMapper.deleteById(selectCertification.getId());
    }

    @Test
    public void testDishEvaluationCrud() {
        // 新增
        DishEvaluation dishEvaluation = new DishEvaluation();
        dishEvaluation.setDish_id(1L);
        dishEvaluation.setEvaluator_id(1L);
        dishEvaluation.setEvaluation_content("味道不错");
        dishEvaluation.setEvaluation_time(LocalDateTime.now());
        dishEvaluation.setScore(new BigDecimal("4.5"));
        dishEvaluationMapper.insert(dishEvaluation);

        // 查询
        DishEvaluation selectDishEvaluation = dishEvaluationMapper.selectById(dishEvaluation.getId());
        System.out.println("查询到的菜品评价信息: " + selectDishEvaluation);

        // 修改
        selectDishEvaluation.setScore(new BigDecimal("4.8"));
        dishEvaluationMapper.updateById(selectDishEvaluation);

        // 删除
        dishEvaluationMapper.deleteById(selectDishEvaluation.getId());
    }

    @Test
    public void testCustomerComplaintSuggestionCrud() {
        // 新增
        CustomerComplaintSuggestion customerComplaint = new CustomerComplaintSuggestion();
        customerComplaint.setComplaint_type("服务问题");
        customerComplaint.setComplaint_content("服务员态度不好");
        customerComplaint.setOrder_id(1L);
        customerComplaint.setUser_id(1L);
        customerComplaint.setSubmit_time(LocalDateTime.now());
        customerComplaintSuggestionMapper.insert(customerComplaint);

        // 查询
        CustomerComplaintSuggestion selectCustomerComplaint = customerComplaintSuggestionMapper.selectById(customerComplaint.getId());
        System.out.println("查询到的客户投诉建议信息: " + selectCustomerComplaint);

        // 修改
        selectCustomerComplaint.setComplaint_content("服务员态度极差");
        customerComplaintSuggestionMapper.updateById(selectCustomerComplaint);

        // 删除
        customerComplaintSuggestionMapper.deleteById(selectCustomerComplaint.getId());
    }
}