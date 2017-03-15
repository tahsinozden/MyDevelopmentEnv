<template>
    <div class="container">
        <h1>Home Page</h1>
        <app-message></app-message>  
        <hr>
        <user-form></user-form>
        <hr>
        <auto-complete v-bind:datalist="userList"></auto-complete>
        <button @click="addUser()">Add User</button>
    </div>
</template>

<script>
    import AutoComplete from './AutoComplete.vue'

    export default {
        data() {
            return {
                userList: ['tahsin', 'ozden', 'tahsin2', 'jack']
            }
        },
        components: {
            'autoComplete' : AutoComplete
        },
        methods: {
            addUser() {
                this.userList.push("ozden");
            }
        }   
    }
</script>