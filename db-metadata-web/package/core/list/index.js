import List from './src/List'

List.install = function (Vue) {
    Vue.component(List.name, List)
}

export default List