<template>
    <div>
        <b-row>
            <b-col>
                <b-card>
                    <b-container>
                        <b-row>
                            <b-col>
                                <b-button v-on:click="createRoom" variant="outline-success">Create a new room</b-button>
                            </b-col>
                        </b-row>
                        <b-row class="mt-3">
                            <b-col>
                                <p class="text-muted text-center justify-content-center align-self-center">or</p>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <b-form v-on:submit.prevent="enterRoom(enterRoomId)">
                                    <div class="form-row">
                                        <b-col class="mb-2">
                                            <b-form-input
                                                v-model="enterRoomId"
                                                required
                                                placeholder="Room id"
                                            ></b-form-input>
                                        </b-col>
                                        <b-col class="col-md-2">
                                            <b-button type="submit" variant="outline-success">Enter the room</b-button>
                                        </b-col>
                                    </div>
                                </b-form>
                            </b-col>
                        </b-row>
                    </b-container>
                </b-card>
            </b-col>
        </b-row>
    </div>
</template>

<script>
  import rooms from "../api/rooms";

  export default {
    name: "Index",
    data() {
      return {
        enterRoomId: null,
      }
    },
    methods: {
      createRoom() {
        rooms.createRoom(
          response => { console.log(Number(response.headers["location"].split("/")[3])); this.enterRoom(Number(response.headers["location"].split("/")[3]))},
          error => console.log(error)
        )
      },
      enterRoom(roomId) {
        this.$router.push("/rooms/" + roomId)
      }
    }
  }
</script>

<style scoped>

</style>