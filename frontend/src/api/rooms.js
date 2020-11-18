import { post, get} from "@/api/http-commons";

export default {
  createRoom(callback, errorCallback, finallyCallback) {
    post("/api/rooms", null, callback, errorCallback, finallyCallback)
  },

  getRoom(roomId, callback, errorCallback) {
    get("/api/rooms/" + roomId, callback, errorCallback)
  }
}