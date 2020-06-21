import axios from 'axios'

export const AXIOS = axios.create()

export function get(url, callback, errorCallback) {
  AXIOS.get(url)
    .then(response => callback(response.data))
    .catch(error => errorCallback(error))
}

export function post(url, body, callback, errorCallback) {
  AXIOS.post(url, body)
    .then(response => callback(response))
    .catch(error => errorCallback(error))
}