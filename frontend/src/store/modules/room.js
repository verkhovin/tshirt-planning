import rooms from "@/api/rooms";

const state = () => ({
  room: null,
  username: null,
  error: null
})

const getters = {}

const actions = {
  getRoom({commit}, {id}) {
    rooms.getRoom(
      id, room => commit("setRoom", room),
      error => commit("setError", error)
    )
  },
  setRoomFrom({commit}, {room}) {
    commit("setRoom", room)
  },
  submitUsername({commit}, {username}) {
    commit("setUsername", username)
  }
}

const mutations = {
  setRoom(state, room) {
    state.error = null
    state.room = room
  },
  setError(state, error) {
    state.room = null
    state.error = error
  },
  setUsername(state, username) {
    state.username = username
  }
}


export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}