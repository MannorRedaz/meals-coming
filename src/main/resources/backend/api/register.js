const certificationApi = (data) => {
    return $axios({
        url: '/merchant/register',
        method: 'post',
        data: {...data}
    })
}
