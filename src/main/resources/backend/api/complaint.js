// 查询投诉建议列表接口（分页查询）
const getComplaintList = (pageNum, pageSize, userId, complaintType, handlingStatus) => {
    return axios.get('/complaint/page', {
        params: {
            pageNum,
            pageSize,
            userId,
            complaintType,
            handlingStatus
        }
    })
};

// 删除投诉建议接口
const deleteComplaint = (id) => {
    return $axios({
        url: `/complaint/${id}`,
        method: 'delete'
    });
};

// 修改投诉建议接口
const editComplaint = (params) => {
    return $axios({
        url: '/complaint',
        method: 'put',
        data: params
    });
};

// 新增投诉建议接口
const addComplaint = (params) => {
    return $axios({
        url: '/complaint',
        method: 'post',
        data: params
    });
};

// 查询投诉建议详情
const queryComplaintById = (id) => {
    return $axios({
        url: `/complaint/${id}`,
        method: 'get'
    });
};

// 查询所有投诉建议管理信息
const findAllComplaints = () => {
    return $axios({
        url: '/complaint',
        method: 'get'
    });
};

// 新增 updateComplaint 接口方法
const updateComplaint = (params) => {
    return $axios({
        url: '/complaint',
        method: 'put',
        data: params
    });
};