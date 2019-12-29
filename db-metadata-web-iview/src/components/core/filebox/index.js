import FileBox from './src/FileBox'

FileBox.install = function (Vue) {
    Vue.component(FileBox.name, FileBox);
};

export default FileBox;