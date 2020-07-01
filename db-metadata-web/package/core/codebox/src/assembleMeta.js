import utils from '../../../utils'
import Modes from './modes'
import Themes from './themes'

export default function assembleMeta(mergedMeta) {
    const {modes, themes, conf: {mode, theme}} = mergedMeta;

    if (utils.isEmpty(modes)) {
        this.$set(mergedMeta, 'modes', Modes)
    }
    if (utils.isEmpty(themes)) {
        this.$set(mergedMeta, 'themes', Themes);
    }

    if (utils.isEmpty(mode)) {
        mergedMeta['conf']['mode'] = mergedMeta['modes'][0];
    }

    if (utils.isEmpty(theme)) {
        mergedMeta['conf']['theme'] = mergedMeta['themes'][0];
    }

    return mergedMeta;
}
