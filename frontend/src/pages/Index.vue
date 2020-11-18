<template>
    <div>
        <b-row class="text-center justify-content-center align-self-center">
            <b-col class="col-md-6">
                <b-card>
                    <b-container>
                        <b-col>
                            <div class="d-flex col-md">
                                <b-button v-if="loading" variant="success" class="m-auto" disabled>
                                  <b-spinner small type="grow"></b-spinner>
                                  Loading...
                                </b-button>
                                <b-button v-else v-on:click="createRoom" variant="outline-success" class="m-auto">
                                  Create a new room
                                </b-button>
                            </div>
                            <div>
                              <hr class="m-4">
                            </div>
                            <div class="text-muted text-center justify-content-center align-self-center">
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
                        </b-col>
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
        loading: false,
      }
    },
    methods: {
      createRoom() {
        this.loading = true
        rooms.createRoom(
          response => {
            this.enterRoom(Number(response.headers["location"].split("/")[3]))
          },
          error => console.log(error),
            () => this.loading = false
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