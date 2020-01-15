import Vue from 'vue'

Vue.directive('hasAuthority', {
    bind: function (el, binding, vnode) {
        let permissions = [];
        if (binding.value) {
            permissions = Array.of(binding.value);
        }
        if (!Vue.prototype.$hasAuth(permissions)) {
            el.parentNode.removeChild(el);
        }
    }
});

Vue.directive('notAdmin', {
    bind: function (el, binding, vnode) {
        if (el.parentNode)
            el.parentNode.removeChild(el);
        // if (Vue.prototype.$hasAuth("ADMIN")) {
        //     el.parentNode.removeChild(el);
        // }
    }
});