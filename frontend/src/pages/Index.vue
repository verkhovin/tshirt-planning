<template>
    <div>
        <b-row>
            <b-col>
                <b-card>
                    <b-container>
                        <b-row>
                            <div class="d-flex col-md">
                                <b-button v-on:click="createRoom" variant="outline-success" class="m-auto">Create a new room</b-button>
                            </div>
                            <div class="col-md-2 d-flex text-muted text-center justify-content-center align-self-center m-2">
                                <p class="m-auto">or</p>
                            </div>
                            <div class="col-md d-flex text-muted text-center justify-content-center align-self-center">
                                <b-form v-on:submit.prevent="enterRoom(enterRoomId)">
                                    <b-form-group>
                                        <b-form-input
                                            v-model="enterRoomId"
                                            required
                                            placeholder="Room id"
                                        ></b-form-input>
                                    </b-form-group>
                                    <b-button type="submit" variant="outline-success">Enter the room</b-button>
                                </b-form>
                            </div>
                        </b-row>
                    </b-container>
                </b-card>
            </b-col>
        </b-row>
    </div>
</template>

<script>
  import rooms from "@/api/rooms";

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
          response => {
            this.enterRoom(Number(response.headers["location"].split("/")[3]))
          },
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