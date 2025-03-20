// 查询商家分页列表
const getMerchantPage = (params) => {
    console.log(params)
    console.log(1111111111111111)
    return $axios({
        url: '/merchant/page',
        method: 'get',
        params: params
    });
};


// 查询商家详情
const getMerchantInfo = (id) => {
    return (axios({
        url: `/merchant/${id}`,
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
const auditMerchant = (id, auditComment) => {
    return (axios({
        url: `/merchantAudit`,
        method: 'put',
        data: {
            merchantId: id,
            auditComment: auditComment,
            auditStatus: ' 已通过 '
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