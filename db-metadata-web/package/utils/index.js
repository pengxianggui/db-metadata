import * as merges from './merge.js'
import * as kvFormats from './kvFormat'
import * as common from './common'
import * as dialog from './dialog'
import * as url from './url'
import * as auth from './user'

export default {
    ...common,
    ...merges,
    ...url,
    ...kvFormats,
    ...dialog,
    ...auth
}
