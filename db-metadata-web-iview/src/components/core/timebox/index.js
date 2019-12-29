import TimeBox from './src/TimeBox'

TimeBox.install = function (Vue) {
    Vue.component(TimeBox.name, TimeBox);
};

export default TimeBox;