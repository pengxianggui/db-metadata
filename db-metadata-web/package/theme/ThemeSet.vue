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

      <!-- 自定义主题 -->
      <div class="body" v-if="themeData.freeMode && allowCustomTheme">
        <el-form :model="themeData">
          <h5>布局</h5>
          <el-form-item label="布局" prop="layout">
            <radio-box v-model="themeData.layout" :options="layouts" class="hidden-radio">
              <template #column="{option}">
                <svg-icon :value="option.icon.light"
                          :class="{'active': themeData.layout == 'column'}"></svg-icon>
              </template>
              <template #row="{option}">
                <svg-icon :value="option.icon.light" :class="{'active': themeData.layout == 'row'}"></svg-icon>
              </template>
            </radio-box>
          </el-form-item>

          <h5>Header</h5>
          <row-grid>
            <template #0>
              <el-form-item label="文字色" prop="textColor">
                <el-color-picker v-model="themeData.header.textColor"></el-color-picker>
              </el-form-item>
            </template>
            <template #1>
              <el-form-item label="背景色" prop="textColor">
                <el-color-picker v-model="themeData.header.backgroundColor"></el-color-picker>
              </el-form-item>
            </template>
          </row-grid>

          <h5>菜单</h5>
          <row-grid>
            <template #0>
              <el-form-item label="文字色" prop="textColor">
                <el-color-picker v-model="themeData.menu.textColor"></el-color-picker>
              </el-form-item>
            </template>
            <template #1>
              <el-form-item label="激活文字色" prop="activeTextColor">
                <el-color-picker v-model="themeData.menu.activeTextColor"></el-color-picker>
              </el-form-item>
            </template>
          </row-grid>
          <row-grid>
            <template #0>
              <el-form-item label="背景色" prop="backgroundColor">
                <el-color-picker v-model="themeData.menu.backgroundColor"></el-color-picker>
              </el-form-item>
            </template>
            <template #1>
              <el-form-item label="是否单开" prop="uniqueOpened">
                <bool-box v-model="themeData.menu.uniqueOpened"></bool-box>
              </el-form-item>
            </template>
          </row-grid>

          <h5>Tag</h5>
          <el-form-item label="显示">
            <bool-box v-model="themeData.tag.show"></bool-box>
          </el-form-item>
          <row-grid>
            <template #0>
              <el-form-item label="文字色" prop="textColor">
                <el-color-picker v-model="themeData.tag.textColor"></el-color-picker>
              </el-form-item>
            </template>
            <template #1>
              <el-form-item label="背景色" prop="backgroundColor">
                <el-color-picker v-model="themeData.tag.backgroundColor"></el-color-picker>
              </el-form-item>
            </template>
          </row-grid>
          <row-grid>
            <template #0>
              <el-form-item label="选中项文字色" prop="activeTextColor">
                <el-color-picker v-model="themeData.tag.activeTextColor"></el-color-picker>
              </el-form-item>
            </template>
            <template #1>
              <el-form-item label="选中项背景色" prop="activeBackgroundColor">
                <el-color-picker v-model="themeData.tag.activeBackgroundColor"></el-color-picker>
              </el-form-item>
            </template>
          </row-grid>
        </el-form>
      </div>

      <!-- 简单设置主题 -->
      <div class="body" v-else>
        <el-form :model="themeData">
          <h4>整体风格</h4>
          <el-form-item label="布局" prop="layout">
            <radio-box v-model="themeData.layout" :options="layouts" class="hidden-radio">
              <template #column="{option}">
                <svg-icon :value="option.icon[themeData.themeColor]"
                          :class="{'active': themeData.layout == 'column'}"></svg-icon>
              </template>
              <template #row="{option}">
                <svg-icon :value="option.icon[themeData.themeColor]" :class="{'active': themeData.layout == 'row'}"></svg-icon>
              </template>
            </radio-box>
          </el-form-item>
          <el-form-item label="主题" prop="color">
            <radio-box v-model="themeData.themeColor" :options="colors"></radio-box>
          </el-form-item>
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

      <div class="footer">
        <el-link type="info" @click="reset">重置</el-link>
        <el-link type="info" @click="themeData.freeMode = !themeData.freeMode" v-if="allowCustomTheme">
          {{ themeData.freeMode ? '简单配置' : '高级配置' }}
        </el-link>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import Theme from './'
import {layoutOptions} from './'
import {appConfig} from "../config";

export default {
  name: "ThemeSet",
  data() {
    return {
      visible: false,

      layouts: layoutOptions,
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
      Theme.setTheme(this.themeData)
      location.reload()
    },
    reset() { // 重置用户设置的主题
      this.$alert('重置主题会移除您自定义的主题配置, 使用系统默认主题。您的自定义主题配置会丢失。',
          '确定重置主题?', {
            type: 'warning'
          }).then(() => {
        Theme.resetTheme()
        location.reload()
      })
    }
  },
  computed: {
    allowCustomTheme() {
      return appConfig.allowCustomTheme
    }
  }
}
</script>

<style scoped lang="scss">
$headerHeight: 100px;
$footerHeight: 40px;

/deep/ .el-drawer__header {
  margin-bottom: 0;
  padding: 20px;
  box-sizing: border-box;
  height: $headerHeight;
}
/deep/ .el-drawer__body {
  background-color: #e0e0e0;
  height: calc(100% - 140px);
  overflow: hidden auto;
}

.body {
  padding: 0 30px;
  margin-bottom: $footerHeight;
}

.footer {
  position: absolute;
  bottom: 0;
  height: $footerHeight;
  width: 100%;
  display: flex;
  padding: 0 15px;
  justify-content: flex-end;
  background-color: #ffffff;

  & > * {
    margin: 0 10px;
  }
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
