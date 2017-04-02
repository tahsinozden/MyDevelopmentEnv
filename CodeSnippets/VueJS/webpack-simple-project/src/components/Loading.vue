<template> 
    <div>
        <h2>Loading...</h2>
        <h2>{{ sign }}</h2>
    </div>
</template>


<script>
    export default {
        data() {
            return {
                sign: '',
                SIGN_SPEED_IN_MILLISECONDS: 150,
                SIGN_CHAR_LENGTH: 15
            }
        },
        methods: {
            generateSign() {
                // we need to assign this to another variable,
                // since 'this' will be overridden by the document as element itself
                var self = this;
                setInterval(function() {
                    if (self.sign.length > self.SIGN_CHAR_LENGTH) {
                        self.sign = '*';
                    }
                    else {
                        self.sign += "*";
                    }
                }, self.SIGN_SPEED_IN_MILLISECONDS);
            }
        },
        mounted() {
            this.generateSign();
        }
    }
</script>
