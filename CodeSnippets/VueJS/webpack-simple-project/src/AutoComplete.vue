<template>
    <div class="container">
        <h2>Autocomplete</h2>
        <div class="col-md-6 .col-md-offset-3">
            <div class="form-group">
                <div class="back">
                    <input id="searchBox" type="text" class="form-control" v-model="query">
                    <ul class="list-group" v-if="query.length > 3">
                        <li class="list-group-item" v-for="(item, index) in filteredLst" @click="itemClicked(index)">{{ item }}</li>
                    </ul>
                </div>
                <br><br>
                <div class="head">
                    <label for="searchBox">This text will not move</label>
                </div>
            </div>

        </div>

    </div>
</template>


<script>
    export default {
        data() {
            return {
                query: '',
                dataLst: ['Tahsin', 'Fony', 'Tony', 'Jack', 'Master', 'Tahsin2', 'Tahsin3', 'Tahsin4'],
                filteredLst: []
            }
        },
        methods: {
            filteredList: function () {
                if (this.dataLst == null) {
                    console.log("list is null");
                    return;
                }
                // console.log("inside");
                // console.log(this.dataLst);
                // it works this way "var self = this;", it doesn't work if we try to access query element inside the function like this.query
                var self = this;
                return self.dataLst.filter(function (val) {
                    return val.toLowerCase().indexOf(self.query.toLowerCase()) !== -1;
                })
            },
            itemClicked(idx) {
                // show the clicked item
                console.log(this.filteredLst[idx]);
                this.query = '';
            }
        },
        props: ['datalist'],
        filters: {
            filterByValue: function (value) {
                // this.dataLst.splice(0,1);
                return value;
            }
        },
        mounted: function () {
            console.log("mounted");
            console.log(this.datalist);
            // update the data list with the values from property
            if (this.datalist != null) {
                this.dataLst = eval(this.datalist);
            }
        },
        updated: function () {
            // console.log("updated");
            // update the filtered list when the query text is updated
            this.filteredLst = this.filteredList();
        }
    }
</script>

<style>

/*  the following css is used for keeping the elements in absolute position while new elements are generated */
.header {
    position:relative
}
.back {
    position:absolute;
}

</style>