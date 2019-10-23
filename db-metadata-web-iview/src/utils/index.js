import {merge} from './merge.js'
import {complieVarString} from './format'
import * as kvFormat from './kvFormat'

console.log(kvFormat.converKv1(['aaa']));

export default {merge, complieVarString, kvFormat}
