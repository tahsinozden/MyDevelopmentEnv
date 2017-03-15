<template>
  <div id="app">
    <ul class="nav nav-pills">
      <li><a href="/#/">Home</a></li>
      <li><a href="/#/about">About</a></li>
    </ul>
      <router-view></router-view>
      <!--<app-message></app-message>  
      <hr>
      <user-form></user-form>-->
  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {

    }
  }
}
</script>

<style>

</style>
