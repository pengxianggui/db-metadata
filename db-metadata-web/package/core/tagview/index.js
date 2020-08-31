import TagView from './src/TagView'
import {isEmpty} from "../../utils/common";
import {reverseMerge} from "../../utils/merge";
import Conf from './conf'

TagView.install = function (Vue, opt = {}) {
    const {tagView: tagViewConf} = opt
    if (!isEmpty(tagViewConf)) {
        reverseMerge(Conf, tagViewConf)
    }
    Vue.component(TagView.name, TagView)
}

export default TagView
