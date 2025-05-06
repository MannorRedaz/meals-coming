function getSalesVolume (data) {
    // console.error(data)
    const newParams = {...data};
    // 序列化日期对象
    if (newParams.createTimeStart) {
        newParams.createTimeStart = newParams.createTimeStart.toISOString();
    }
    if (newParams.createTimeEnd) {
        newParams.createTimeEnd = newParams.createTimeEnd.toISOString();
    }
    return $axios({
        url: '/statistic/all',
        method: 'get',
        params: newParams
    })
}




/*
function getVisitorNum () {
    return $axios({
        url: '/statistic/visit',
        method: 'get',
    })
}*/
