function orderEvaluateApi(data) {
    return $axios({
        'url': '/evaluation',
        'method': 'post',
        data
    })
}

const getMerchantEvaluationApi = (id) => {
    return $axios({
        url: `/evaluation/list/${id}`,
        method: 'get',
    });
}