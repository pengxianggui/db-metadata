<template>
    <div class="ui-fas">
        <el-tabs v-model="chose">
            <el-tab-pane label="Font Icon" name="font">
                <el-input class="inputIcon" v-model="name"
                          suffix-icon="el-icon-search" placeholder="请输入图标名称" clearable>
                </el-input>
                <ul class="fas-icon-list">
                    <li v-for="(item, index) in filteredIconList" :key="index" @dblclick="handleRowDbClick(item)">
                        <i class="fas" :class="[item]"/>
                    </li>
                </ul>
            </el-tab-pane>
            <el-tab-pane label="SVG" name="svg">
                <el-input class="inputIcon" v-model="name"
                          suffix-icon="el-icon-search" placeholder="请输入图标名称" clearable>
                </el-input>
                <ul class="fas-icon-list">
                    <li v-for="svg in filteredSvgList" :key="svg" @dblclick="handleRowDbClick(svg)">
                        <svg-icon :value="svg"></svg-icon>
                    </li>
                </ul>
            </el-tab-pane>
        </el-tabs>

    </div>
</template>
<script>
    import utils from '../../../utils'
    import IconList from './iconList'

    export default {
        name: 'IconPanel',
        data() {
            return {
                name: '',
                iconList: IconList,
                chose: 'font',
                svgs: []
            }
        },
        methods: {
            handleRowDbClick(icon) {
                this.$emit('selected', icon);
            }
        },
        mounted() {
            const symbols = document.querySelectorAll('svg>symbol')
            const svgs = Array.prototype.slice.call(symbols).map(s => s.id.match(/icon-(\S*)/)[1])
            this.svgs = svgs
        },
        computed: {
            filteredIconList() {
                const {iconList, name} = this;
                if (!utils.isEmpty(name)) {
                    return iconList.filter(item => item.includes(this.name));
                } else {
                    return iconList;
                }
            },
            filteredSvgList() {
                const {svgs, name} = this;
                return utils.isEmpty(name) ? svgs : svgs.filter(i => i.includes(name))
            }
        }
    }
</script>
<style>
    .inputIcon {
        width: 100%;
        height: 30px;
        margin-bottom: 10px;
    }

    .ui-fas {
        height: 300px;
        overflow: hidden;
    }

    .fas-icon-list {
        height: 100%;
        overflow: scroll;
        list-style: none;
    }

    .fas-icon-list li {
        float: left;
        margin: 10px;
        padding: 5px;
    }

    .fas-icon-list li:hover {
        background-color: #dddddd;
    }

    .fas {
        font-size: 20px;
        color: #1989fa;
        cursor: pointer;
    }
</style>
