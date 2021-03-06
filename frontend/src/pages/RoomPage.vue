<template>
    <div class="text-center">
        <div v-if="connecting">
          <div class="text-center">
            <b-spinner variant="primary" label="Text Centered"></b-spinner>
          </div>
        </div>
        <div v-else-if="username">
            <b-row class="mb-3">
                <b-col class="col-md-6 offset-md-4">
                    <b-button-toolbar key-nav aria-label="Toolbar with button groups">
                        <b-button-group class="mx-1 btn-group-md">
                            <b-button v-on:click="submitEstimate('XS')" variant="outline-success">XS</b-button>
                            <b-button v-on:click="submitEstimate('S')" variant="outline-success">S</b-button>
                            <b-button v-on:click="submitEstimate('M')" variant="outline-success">M</b-button>
                            <b-button v-on:click="submitEstimate('L')" variant="outline-success">L</b-button>
                            <b-button v-on:click="submitEstimate('XL')" variant="outline-success">XL</b-button>
                        </b-button-group>
                        <b-button-group class="mx-1 btn-group-md">
                            <b-button v-on:click="hideEstimates" variant="outline-success" v-if="room.estimatesOpened">Hide</b-button>
                            <b-button v-on:click="showEstimates" variant="outline-success" v-else>Show</b-button>
                        </b-button-group>
                        <b-button-group class="mx-1 btn-group-md">
                            <b-button v-on:click="clearEstimates" variant="danger">Clear</b-button>
                        </b-button-group>
                    </b-button-toolbar>
                </b-col>
            </b-row>

            <b-row class="row-cols-1 row-cols-md-3">
                <b-col class="mb-4" v-for="(estimate, i) in room.estimates" :key="i">
                    <div class="card h-100">
                        <div class="card-body">
                            <h1 class="card-title display-1"
                                v-bind:class="{diff: room.dominatingEstimate && estimate.size !== room.dominatingEstimate}"
                            >{{ estimate.size }}</h1>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">{{ estimate.username }}</small>
                        </div>
                    </div>
                </b-col>
            </b-row>
            <div v-if="room.estimatesOpened">
                <div>
                    <p v-if="room.dominatingEstimate">
                        Estimated as {{room.dominatingEstimate}}
                        <b-icon-check-all style="color: #28a745;" v-if="room.hasConsensus"></b-icon-check-all>
                        <b-icon-exclamation-triangle style="color: #ffc107" v-else
                                                     v-b-popover.hover.top="'There are nonmatching estimates'"></b-icon-exclamation-triangle>
                    </p>
                    <p v-else>There is no dominating estimate.</p>
                </div>
            </div>
        </div>
        <div v-else class="text-left">
            <b-row>
                <b-col>
                    <b-form v-on:submit.prevent="enterUsername">
                        <b-form-group label="Name">
                            <b-form-input
                                v-model="usernameInput"
                                required
                            ></b-form-input>
                        </b-form-group>

                        <b-button type="submit" variant="outline-success">Enter</b-button>
                    </b-form>
                </b-col>
            </b-row>
        </div>
        <p>Room id is <b>{{room.roomId}}</b></p>
    </div>

</template>

<script>
  import { mapState } from "vuex";

  export default {
    name: "RoomPage",
    data() {
      return {
        usernameInput: null,
        connecting: false,
      }
    },
    computed: {
      ...mapState('room', ['room', 'username', 'error']),
    },
    watch: {
      username() {
        this.$connect()
      }
    },
    methods: {
      submitEstimate(size) {
        this.sendMessage({
          type: "ESTIMATE",
          payload: size
        })
      },
      showEstimates() {
        this.sendMessage({
          type: "SHOW"
        })
      },
      hideEstimates() {
        this.sendMessage({
          type: "HIDE"
        })
      },
      clearEstimates() {
        this.sendMessage({
          type: "CLEAR"
        })
      },
      enterUsername() {
        this.$cookie.set("tshirt-planning-username", this.usernameInput)
        this.$store.dispatch("room/submitUsername", {username: this.usernameInput})
        this.connecting = true
      },
      sendMessage(message) {
        this.$socket.send(JSON.stringify(message))
      },
      configureSocket() {
        this.$options.sockets.onopen = function() {
        }
        this.$options.sockets.onmessage = function(data) {
          console.log("on message")
          this.connecting = false;
          if(data.data === 'init') {
            let connectMessage = {
              roomId: this.$route.params.id,
              username: this.username
            };
            this.$socket.send(JSON.stringify(connectMessage))
          } else if (data.data !== 'OK') {
            this.$store.dispatch('room/setRoomFrom', {room: JSON.parse(data.data)})
          }
        }
      }
    },
    created() {
      this.configureSocket()
      this.$store.dispatch('room/getRoom', {id: this.$route.params.id})
      let storedUserName = this.$cookie.get("tshirt-planning-username")
      if (storedUserName) {
        this.$store.dispatch('room/submitUsername', {username: storedUserName})
        this.$connect()
        this.connecting = true
      }
    },
    destroyed() {
      console.log("destroy")
      delete this.$options.sockets.onmessage
      this.$disconnect()
    },
  }
</script>

<style scoped>
    .diff {
        color: #dc3545;
    }
</style>