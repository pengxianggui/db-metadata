<template>
  <div style="padding: 50px">
    <json-box v-model="images"></json-box>
    <img-box :meta="imgMeta" v-model="images"></img-box>
  </div>
</template>

<script>
export default {
  name: "WorkSpace",
  data() {
    return {
      imgMeta: {"explain":"","component_name":"ImgBox","name":"id_card_picture","conf":{"list-type":"picture-card","auto-upload":true,"limit":1,"multiple":false,"action":"/file/upload?objectCode=t_employee&fieldCode=id_card_picture","drag":false,"show-file-list":true},"label":"证件照","sort":4,"seats":["身份证正面","身份证反面"]},
      images: [{"seat":"身份证正面","name":"avatar.png","uploadedName":"preview?path=t_employee%5Cid_card_picture%5Cavatar_20230626_14_17_34_568.png","url":"/file/preview?path=t_employee%5Cid_card_picture%5Cavatar_20230626_14_17_34_568.png"},{"seat":"身份证反面","name":"234047eqfSq.jpg","uploadedName":"preview?path=t_employee%5Cid_card_picture%5C234047eqfSq_20230703_08_19_42_111.jpg","url":"/file/preview?path=t_employee%5Cid_card_picture%5C234047eqfSq_20230703_08_19_42_111.jpg"}]
    }
  },
  computed: {},
  methods: {}
}
</script>

<style scoped>
</style>
