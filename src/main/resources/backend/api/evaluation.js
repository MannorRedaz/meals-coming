// 查询评价列表接口（分页查询）
const getEvaluationList = (pageNum, pageSize, userId, evaluatedObjectId) => {
    return axios.get('/evaluation/page', {
        params: {
            pageNum,
            pageSize,
            userId,
            evaluatedObjectId
        }
    });
};

// 删除评价接口
const deleteEvaluation = (id) => {
    return $axios({
        url: `/evaluation/${id}`,
        method: 'delete'
    });
};

// 修改评价接口
const editEvaluation = (params) => {
    return $axios({
        url: '/evaluation',
        method: 'put',
        data: params
    });
};

// 新增评价接口
const addEvaluation = (params) => {
    return $axios({
        url: '/evaluation',
        method: 'post',
        data: params
    });
};

// 查询评价详情
const queryEvaluationById = (id) => {
    return $axios({
        url: `/evaluation/${id}`,
        method: 'get'
    });
};

// 查询所有评价管理信息
const findAllEvaluations = () => {
    return $axios({
        url: '/evaluation',
        method: 'get'
    });
};

