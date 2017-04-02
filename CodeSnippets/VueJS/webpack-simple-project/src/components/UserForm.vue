<template> 
    <div>
        <h1>{{title}}</h1>
        <input type="text" v-on:input=" name = $event.target.value"><br>
        <label>Hello {{yourName}}</label>

        <hr />
        <h3>User List</h3>
        <ul>
            <li v-for="(user, index) in users" 
                v-on:click="removeUser(index);">
                {{ user.name }}
            </li>
            <li><input type="text" v-model="newUserName" v-on:keyup.enter="addUser(newUserName);"></li>
            <button v-on:click="addUser(newUserName);">
                    Add</button>
        </ul>
    </div>
        
</template>

<script>
    export default {
        data() {
            return {
                title: "welcome here!",
                name: '',
                users: [
                    {name: 'Tahsin'},
                    {name: 'Ania'},
                    {name: 'Jack'},
                    {name: 'Mack'}
                ],
                newUserName: ''
            }            
        },
        methods: {
            onClickUser: function(idx){
                alert("You clicked on the user " + this.users[idx].name);
            },
            addUser: function(name){
                if (name == ''){
                    return;
                }
                this.users.push({'name': name});
                this.newUserName = '';
            },
            removeUser: function(idx){
                this.users.splice(idx,1);
            }
        },
        computed: {
            yourName: function(){
                return this.name.toUpperCase();
            }
        }
    }

</script>