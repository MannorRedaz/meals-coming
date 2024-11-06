function getSalesVolume () {
    return $axios({
        url: '/statistic',
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
