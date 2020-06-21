<template>
    <div>
        <form v-on:submit.prevent="onsubmit">
            <input type="text" v-model="inputText">
            <input type="submit">
        </form>
        {{ outputText }}
    </div>
</template>

<script>
  export default {
    name: "SocketExample",
    data() {
      return {
        inputText: "",
        outputText: ""
      }
    },
    methods: {
      onsubmit() {
        this.$socket.send("{}")
      }
    },
    created() {
      this.$options.sockets.onmessage = (data) => this.outputText = data.data
    },
    destroyed() {
      delete this.$options.sockets.onmessage
    }
  }
</script>

<style scoped>

</style>