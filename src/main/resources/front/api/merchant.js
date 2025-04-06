// 查询商家详情
const getMerchantInfo = (id) => {
    return (axios({
        url: `/merchant/${id}`,
        method: 'get'
    }));
};

// 查询商家列表
const merchantListApi = (() => {
    return $axios({
        url: '/merchant/list',
        method: 'get',
    });
})

