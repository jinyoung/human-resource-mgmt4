<template>

    <v-card outlined>
        <v-card-title>
            AddCalendar
        </v-card-title>

        <v-card-text>
            <String label="Title" v-model="value.title" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="addCalendar"
            >
                AddCalendar
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'AddCalendarCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.title = '';
        },
        watch: {
        },
        methods: {
            addCalendar() {
                this.$emit('addCalendar', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

