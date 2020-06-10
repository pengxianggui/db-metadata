<template>
    <div class="el-card container" style="height: 100%; overflow: auto; padding: 0 10px;">
        <h4 align="center">组件列表({{comps.length}})</h4>
        <el-row>
            <draggable
                    :clone="addFormItem"
                    :group="{ name: 'form', pull: 'clone', put: false }"
                    :list="comps"
                    @end="handleMoveEnd"
                    @start="handleMoveStart"
                    :sort="false">
                <el-col v-for="comp of comps" :key="comp.name" class="el-card">
                    <el-tooltip class="item" effect="dark" :content="comp.name" :open-delay=1000 placement="top-start">
                        <span><i class="el-icon-receiving"></i><span>{{comp.label}}</span></span>
                    </el-tooltip>
                </el-col>
            </draggable>
        </el-row>
    </div>
</template>

<script>
    import draggable from 'vuedraggable'
    import {defaultMeta} from '../../core/index'
    import BoolBox from '../../core/boolbox'
    import CheckBox from '../../core/checkbox'
    import DateBox from '../../core/datebox'
    import DateTimeBox from '../../core/datetimebox'
    import DropDownBox from '../../core/dropdownbox'
    import FileBox from '../../core/filebox'
    import ImgBox from '../../core/imgbox'
    import JsonBox from '../../core/jsonbox'
    import FindBox from '../../core/findbox'
    import NumBox from '../../core/numbox'
    import PassBox from '../../core/passbox'
    import RadioBox from '../../core/radiobox'
    import SqlBox from '../../core/sqlbox'
    import TextAreaBox from '../../core/textareabox'
    import TextBox from '../../core/textbox'
    import TimeBox from '../../core/timebox'


    export default {
        name: "ComponentList",
        components: {
            draggable
        },
        data() {
            return {
                globalId: 0,
                comps: [
                    TextBox, TextAreaBox, PassBox, NumBox, BoolBox, CheckBox, RadioBox, DropDownBox,
                    DateBox, DateTimeBox, TimeBox, FindBox, FileBox, ImgBox, JsonBox, SqlBox
                ]
            }
        },
        methods: {
            addFormItem(data) {
                let {name, label} = data;
                let meta = {
                    name: name + this.globalId++,
                    label: label
                };
                this.$merge(meta, defaultMeta[name]);
                return meta;
            },
            handleMoveEnd() {
            },
            handleMoveStart() {
            }
        }
    }
</script>

<style scoped>
    .el-col {
        display: inline-block;
        float: left;
        width: 130px;
        margin: 5px 0px 5px 5px;
        cursor: pointer;
        text-align: center;
        border-radius: 2px
    }

    .el-col > span > i {
        margin: 2px 5px 2px 0px;
        float: left;
    }

    .el-col > span > span {
        float: left;
        font-size: medium;
    }
</style>
