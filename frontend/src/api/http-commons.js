import axios from 'axios'

const options = {};
options.baseURL = process.env.NODE_ENV === 'production' ? 'https://estimates.ithurts.dev' : 'http://localhost:8080'
export const AXIOS = axios.create(options)

export function get(url, callback, errorCallback) {
  AXIOS.get(url)
    .then(response => callback(response.data))
    .catch(error => errorCallback(error))
}

export function post(url, body, callback, errorCallback, finallyCallback) {
  AXIOS.post(url, body)
    .then(response => callback(response))
    .catch(error => errorCallback(error))
    .finally(() => finallyCallback())
}