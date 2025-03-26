function loginApi(data) {
  return $axios({
    // 'url': '/employee/login',
    'url': '/merchant/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    // 'url': '/employee/logout',
    'url': '/merchant/logout',
    'method': 'post',
  })
}
