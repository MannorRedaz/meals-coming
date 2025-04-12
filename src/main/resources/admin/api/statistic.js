function getSalesVolume () {
    return $axios({
        url: '/statistic/all',
        method: 'get',
    })
}
/*
function getVisitorNum () {
    return $axios({
        url: '/statistic/visit',
        method: 'get',
    })
}*/
