import UserList from "./UserList";
import RoleList from "./RoleList";

UserList.install = function (Vue, opts = {}) {
    Vue.component(UserList.name, UserList);
}
RoleList.install = function (Vue, opts = {}) {
    Vue.component(RoleList.name, RoleList);
}

export {
    UserList, RoleList
}
