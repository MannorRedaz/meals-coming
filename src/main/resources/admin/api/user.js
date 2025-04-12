//修改用户状态
const editUserStatus= (params) => {
    return $axios({
        url: '/user/userInfo',
        method: 'put',
        data: { ...params }
    })
}

//获取用户数据
const getUserList = (params) => {
    return $axios({
        url: '/user/page',
        method: 'get',
        params
    })
}
