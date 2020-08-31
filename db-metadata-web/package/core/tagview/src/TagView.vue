<template>
  <div id="tags-view-container" class="tags-view-container">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper">
      <router-link
          v-for="tag in visitedViews"
          ref="tag"
          :key="tag.path"
          :style="isActive(tag) ? {'background-color': bgColor, 'color': color, 'border-color': bgColor} : {}"
          :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"
          tag="span"
          class="tags-view-item"
          @click.middle.native="!isAffix(tag)?closeSelectedTag(tag):''"
      >
        <pop-menu trigger="right-click" @show="openMenu(tag)">
          <template #label>
            <span>{{ tag.meta.title }}</span>
            <span v-if="!isAffix(tag)" class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)"/>
          </template>
          <list style="width: 80px;">
            <list-item style="height: 22px; line-height: 22px;" @click="refreshSelectedTag(selectedTag)"
                       v-if="isActive(tag)">刷新
            </list-item>
            <list-item style="height: 22px; line-height: 22px;" v-if="!isAffix(selectedTag)"
                       @click="closeSelectedTag(selectedTag)">关闭
            </list-item>
            <list-item style="height: 22px; line-height: 22px;" @click="closeOthersTags">关闭其他</list-item>
            <list-item style="height: 22px; line-height: 22px;" @click="closeAllTags(selectedTag)">关闭所有</list-item>
          </list>
        </pop-menu>
      </router-link>
    </scroll-pane>
  </div>
</template>

<script>
import ScrollPane from './ScrollPane'
import path from 'path'
import VisitedViewMaintain from "../visitedViewMaintain"
import Conf from '../conf'

/**
 * TODO TagView的切换缓存基于vue 动态组件的 keep-alive, 因此keep-alive中的include只能根据组件名来, 而TagView的数据基于路由,
 * 如果多个路由共用一个组件, 则都会被keep-alive命中
 */
export default {
  name: "TagView",
  // props: {
  //   bgColor: {
  //     type: String,
  //     default: () => '#409EFF'
  //   },
  //   color: {
  //     type: String,
  //     default: () => '#ffffff'
  //   }
  // },
  components: {ScrollPane},
  mixins: [VisitedViewMaintain],
  data() {
    return {
      top: 0,
      left: 0,
      selectedTag: {},
      affixTags: [],
      visitedViews: [],
      cachedViews: []
    }
  },
  computed: {
    routes() {
      return this.$router.options.routes // TODO 此值并不能获取到addRoutes动态添加进来的路由, 导致无法初始化并固定affix路由
    },
    color() {
      return Conf.color
    },
    bgColor() {
      return Conf.bgColor
    }
  },
  watch: {
    $route() {
      this.addTags()
      this.moveToCurrentTag()
    },
    cachedViews: {
      handler: function (newV) {
        this.$emit('cacheViewChange', newV)
      },
      deep: true
    }
  },
  mounted() {
    this.initTags()
    this.addTags()
  },
  methods: {
    isActive(route) {
      return route.path === this.$route.path
    },
    isAffix(tag) {
      return tag.meta && tag.meta.affix
    },
    filterAffixTags(routes, basePath = '/') {
      let tags = []
      routes.forEach(route => {
        if (route.meta && route.meta.affix) {
          const tagPath = path.resolve(basePath, route.path)
          tags.push({
            fullPath: tagPath,
            path: tagPath,
            name: route.name,
            meta: {...route.meta}
          })
        }
        if (route.children) {
          const tempTags = this.filterAffixTags(route.children, route.path)
          if (tempTags.length >= 1) {
            tags = [...tags, ...tempTags]
          }
        }
      })
      return tags
    },
    initTags() {
      const affixTags = this.affixTags = this.filterAffixTags(this.routes)
      for (const tag of affixTags) {
        if (tag.name) { // 必须有路由名name
          this.addVisitedView(tag)
        }
      }
    },
    addTags() {
      const {name} = this.$route
      if (name) {
        this.addView(this.$route)
      }
      return false
    },
    moveToCurrentTag() {
      const tags = this.$refs.tag
      this.$nextTick(() => {
        for (const tag of tags) {
          if (tag.to.path === this.$route.path) {
            this.$refs.scrollPane.moveToTarget(tag)
            // when query is different then update
            if (tag.to.fullPath !== this.$route.fullPath) {
              this.updateVisitedView(this.$route)
            }
            break
          }
        }
      })
    },
    refreshSelectedTag(view) {
      this.deleteCachedView(view).then(() => {
        const {fullPath} = view
        this.$nextTick(() => {
          this.$router.replace({
            path: '/meta-element-redirect' + fullPath
          })
        })
      })
    },
    closeSelectedTag(view) {
      this.deleteView(view).then(({visitedViews}) => {
        if (this.isActive(view)) {
          this.toLastView(visitedViews, view)
        }
      })
    },
    closeOthersTags() {
      this.$router.push(this.selectedTag)
      this.deleteOtherView(this.selectedTag).then(() => {
        this.moveToCurrentTag()
      })
    },
    closeAllTags(view) {
      this.deleteAllViews(view).then(({visitedViews}) => {
        if (this.affixTags.some(tag => tag.path === view.path)) {
          return
        }
        this.toLastView(visitedViews, view)
      })
    },
    toLastView(visitedViews, view) {
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        this.$router.push(latestView.fullPath)
      } else {
        this.$router.push(Conf.outPath)
      }
    },
    openMenu(tag) {
      this.selectedTag = tag
    }
  }
}
</script>

<style lang="scss" scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  //margin-bottom: 1px;
  //border-bottom: 1px solid #d8dce5;
  //box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);

  .tags-view-wrapper {
    .tags-view-item {
      display: inline-block;
      position: relative;
      cursor: pointer;
      height: 26px;
      line-height: 26px;
      border: 1px solid #d8dce5;
      padding: 0 8px;
      font-size: 12px;
      margin: 4px 2px;

      &:first-of-type {
        margin-left: 5px;
      }

      &:last-of-type {
        margin-right: 5px;
      }
    }
  }
}
</style>