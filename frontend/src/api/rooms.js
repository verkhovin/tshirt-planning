import { post, get} from "@/api/http-commons";

export default {
  createRoom(callback, errorCallback) {
    post("/api/rooms", null, callback, errorCallback)
  },

  getRoom(roomId, callback, errorCallback) {
    get("/api/rooms/" + roomId, callback, errorCallback)
  }
}