//获取所有地址
function complaintListApi() {
    return $axios({
      'url': '/complaint/list',
      'method': 'get',
    })
  }

//新增地址
function  addComplaintApi(data){
    return $axios({
        'url': '/complaint',
        'method': 'post',
        data
      })
}

//修改地址
function  updateComplaintApi(data){
    return $axios({
        'url': '/complaint',
        'method': 'put',
        data
      })
}

//删除地址
function deleteComplaintApi(params) {
    return $axios({
        'url': '/addressBook',
        'method': 'delete',
        params
    })
}

//查询单个地址
function complaintFindOneApi(id) {
  return $axios({
    'url': `/complaint/${id}`,
    'method': 'get',
  })
}
