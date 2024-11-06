//获取当前用户信息
function userInfoApi() {
    return $axios({
        'url': '/user/userInfo',
        'method': 'get',
    })
}


//提交用户信息
function submitApi(data) {
    return $axios({
        'url': '/user/userInfo',
        'method': 'put',
        data,
    })
}
