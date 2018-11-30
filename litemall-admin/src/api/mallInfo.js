import request from '@/utils/request'

export function listMallInfo(query) {
  return request({
    url: '/mallinfo/list',
    method: 'get',
    params: query
  })
}

export function createMallInfo(data) {
  return request({
    url: '/mallinfo/create',
    method: 'post',
    data
  })
}

export function readMallInfo(data) {
  return request({
    url: '/mallinfo/read',
    method: 'get',
    data
  })
}

export function updateMallInfo(data) {
  return request({
    url: '/mallinfo/update',
    method: 'post',
    data
  })
}

export function deleteMallInfo(data) {
  return request({
    url: '/mallinfo/delete',
    method: 'post',
    data
  })
}
