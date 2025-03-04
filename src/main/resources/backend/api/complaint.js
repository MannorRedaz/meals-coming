// 查询投诉建议列表接口（分页查询）
const getComplaintList = (pageNum, pageSize) => {
    return axios.get('/complaintSuggestionManagement/page', {
        params: {
            pageNum,
            pageSize
        }
    })
};

// 删除投诉建议接口
const deleteComplaint = (id) => {
    return $axios({
        url: `/complaintSuggestionManagement/${id}`,
        method: 'delete'
    });
};

// 修改投诉建议接口
const editComplaint = (params) => {
    return $axios({
        url: '/complaintSuggestionManagement',
        method: 'put',
        data: params
    });
};

// 新增投诉建议接口
const addComplaint = (params) => {
    return $axios({
        url: '/complaintSuggestionManagement',
        method: 'post',
        data: params
    });
};

// 查询投诉建议详情
const queryComplaintById = (id) => {
    return $axios({
        url: `/complaintSuggestionManagement/${id}`,
        method: 'get'
    });
};

// 查询所有投诉建议管理信息
const findAllComplaints = () => {
    return $axios({
        url: '/complaintSuggestionManagement',
        method: 'get'
    });
};
