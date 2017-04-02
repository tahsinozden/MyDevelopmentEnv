<template>
    <div class="container">
        <h1>Home Page</h1>
        <app-message></app-message>
        <loading v-if="!loaded"></loading>          
        <hr>
        <user-form></user-form>
        <hr>
        <auto-complete v-bind:datalist="userList"></auto-complete>
        <button @click="addUser()">Add User</button>
    </div>
</template>

<script>
    import AutoComplete from './components/AutoComplete.vue'
    import Loading from './components/Loading.vue'

    export default {
        data() {
            return {
                userList: ['tahsin', 'ozden', 'tahsin2', 'jack'],
                loaded: false
            }
        },
        components: {
            'autoComplete' : AutoComplete,
            'loading' : Loading
        },
        methods: {
            addUser() {
                this.userList.push("ozden");
            },
            simulateContentLoading() {
                var self = this;
                setTimeout(function() {
                    self.loaded = true;
                }, 3000);
            }
        },
        mounted() {
            this.simulateContentLoading();
        }  
    }
</script>