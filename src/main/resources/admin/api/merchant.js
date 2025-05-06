// 查询商家分页列表
const getMerchantPage = (params) => {
    const newParams = {...params};
    // 序列化日期对象
    if (newParams.createTimeStart) {
        newParams.createTimeStart = newParams.createTimeStart.toISOString();
    }
    if (newParams.createTimeEnd) {
        newParams.createTimeEnd = newParams.createTimeEnd.toISOString();
    }
    if (newParams.updateTimeStart) {
        newParams.updateTimeStart = newParams.updateTimeStart.toISOString();
    }
    if (newParams.updateTimeEnd) {
        newParams.updateTimeEnd = newParams.updateTimeEnd.toISOString();
    }
    return $axios({
        url: '/merchant/page',
        method: 'get',
        params: newParams
    });
};


// 查询商家详情
const getMerchantInfo = (id) => {
    return (axios({
        url: `/merchant/${id}`,
        method: 'get'
    }));
};
const getMerchantDetail = (id) => {
    return (axios({
        url: `/merchant/detail/${id}`,
        method: 'get'
    }));
};


// 新增商家
const createMerchant = (params) => {
    return $axios({
        url: '/merchant',
        method: 'post',
        data: {...params}
    });
};


// 更新商家
const updateMerchant = (params) => {
    return $axios({
        url: '/merchant',
        method: 'put',
        data: {...params}
    });
};


// 删除商家
const removeMerchant = (ids) => {
    return $axios({
        url: '/merchant',
        method: 'delete',
        params: {ids}
    });
};


// 审核商家
const auditMerchant = (id, auditComment, statusValue) => {
    return (axios({
        url: `/merchantAudit`,
        method: 'put',
        data: {
            merchantId: id,
            auditComment: auditComment,
            auditStatus: statusValue
        }
    }))
};


// 查询商家审核详情
const getMerchantAudit = (merchantId) => {
    return $axios({
        url: `/merchantAudit/page`,
        method: 'get',
        params: {merchantId: merchantId}
    });
};

// 查询商家列表
const merchantListApi = (() => {
    return $axios({
        url: '/merchant/list',
        method: 'get',
    });
});
