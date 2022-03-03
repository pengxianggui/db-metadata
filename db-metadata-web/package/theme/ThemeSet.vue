<template>
  <div style="display: flex;">
    <svg-icon value="el-icon-setting" @click.native="visible = true" style="cursor: pointer"></svg-icon>

    <el-drawer title="主题设置" :visible.sync="visible" direction="rtl" size="30%" :show-close="false">
      <template #title>
        <h3 style="margin: 0">主题设置</h3>
        <el-button-group>
          <el-button type="success" @click="save">保存</el-button>
          <el-button type="danger" @click="visible=false">关闭</el-button>
        </el-button-group>
      </template>
      <div class="padding-30">
        <el-form :model="easyData">
          <h4>整体风格</h4>
          <el-form-item label="布局" prop="layout">
            <radio-box v-model="easyData.layout" :options="layouts" class="hidden-radio">
              <template #vertical="{option}"><svg-icon :value="option.icon[easyData.color]" :class="{'active': easyData.layout == 'vertical'}"></svg-icon></template>
              <template #horizontal="{option}"><svg-icon :value="option.icon[easyData.color]" :class="{'active': easyData.layout == 'horizontal'}"></svg-icon></template>
            </radio-box>
          </el-form-item>
          <el-form-item label="主题" prop="color">
            <radio-box v-model="easyData.color" :options="colors"></radio-box>
          </el-form-item>
        </el-form>
        <el-form :model="themeData">
          <h4>菜单</h4>
          <el-form-item label="单开">
            <bool-box v-model="themeData.menu.uniqueOpened"></bool-box>
          </el-form-item>

          <h4>TagView</h4>
          <el-form-item label="显示">
            <bool-box v-model="themeData.tag.show"></bool-box>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import Theme from './'
import cacheKey from "../constant/cacheKey";
import {buildInLayouts, buildColors} from "./";
import {isEmpty} from "../utils/common";

export default {
  name: "ThemeSet",
  data() {
    let easyData = localStorage.getItem(cacheKey.keyInLocal.THEME_OVERVIEW)
    if (isEmpty((easyData))) {
      easyData = {
        layout: 'vertical', // vertical/horizontal
        color: 'light' // light/dark
      }
    } else {
      easyData = JSON.parse(easyData)
    }
    return {
      visible: false,
      easyData: easyData,

      layouts: [
        {
          key: '垂直',
          value: 'vertical',
          icon: {
            'light': 'light-vertical',
            'dark': 'dark-vertical'
          }
        },
        {
          key: '水平',
          value: 'horizontal',
          icon: {
            'light': 'light-horizontal',
            'dark': 'dark-horizontal'
          }
        }
      ],
      colors: [
        {
          key: '默认',
          value: 'light'
        },
        {
          key: '深色',
          value: 'dark'
        }
      ],

      themeData: Theme.getTheme(),
    }
  },
  methods: {
    save() {
      const {themeData, easyData} = this
      const {layout, color} = easyData
      this.$reverseMerge(themeData, buildInLayouts[layout])
      this.$reverseMerge(themeData, buildColors[color])
      Theme.setTheme(themeData)
      localStorage.setItem(cacheKey.keyInLocal.THEME_OVERVIEW, JSON.stringify(easyData))

      this.visible = false
      location.reload()
    }
  }
}
</script>

<style scoped lang="scss">
/deep/ .el-drawer__body {
  background-color: #e0e0e0;
}
.padding-30 {
  padding: 0 30px;
}
.hidden-radio {
  /deep/ .el-radio__input {
    display: none;
  }

  svg {
    font-size: 50px;
  }

  svg.active {
    border: 1px solid #c1bcbc;
  }
}
</style>
