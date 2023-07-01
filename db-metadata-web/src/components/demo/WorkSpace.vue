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
      imgMeta: {
        "conf": {
          "drag": false,
          "limit": 5,
          "action": "/file/upload?objectCode=t_employee&fieldCode=photo",
          "multiple": false,
          "list-type": "picture-card",
          "auto-upload": true,
          "show-file-list": true
        },
        "name": "photo",
        "sort": 2,
        "label": "生活照",
        "seats": [],
        "explain": "",
        "component_name": "ImgBox"
      },
      images: [{
        "url": "/file/preview?path=t_employee%5Cid_card_picture%5Cavatar_20230626_14_17_34_568.png",
        "name": "avatar.png",
        "seat": "身份证正面",
        "uploadedName": "preview?path=t_employee%5Cid_card_picture%5Cavatar_20230626_14_17_34_568.png"
      }, {
        "url": "/file/preview?path=t_employee%5Cid_card_picture%5C234047eqfSq_20230626_14_17_49_772.jpg",
        "name": "234047eqfSq.jpg",
        "seat": "身份证反面",
        "uploadedName": "preview?path=t_employee%5Cid_card_picture%5C234047eqfSq_20230626_14_17_49_772.jpg"
      }]
    }
  },
  computed: {},
  methods: {}
}
</script>

<style scoped>
</style>
