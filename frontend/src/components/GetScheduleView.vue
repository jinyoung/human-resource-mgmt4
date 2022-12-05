<template>

    <v-data-table
        :headers="headers"
        :items="getSchedule"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'GetScheduleView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            getSchedule : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/getSchedules'))

            temp.data._embedded.getSchedules.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.getSchedule = temp.data._embedded.getSchedules;
        },
        methods: {
        }
    }
</script>

