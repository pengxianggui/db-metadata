import Dashboard from "./dashboard/Dashboard";
import Login from "./login/Login";
import Page401 from "./error/Page401";
import Page404 from "./error/Page404";
import ApiResourceList from "./api-resource/ApiResourceList";
import AuthList from "./auth/AuthList";
import RoleList from "./role/RoleList";
import UserList from "./user/UserList";
import DictList from "./dict/DictList";
import ExceptionList from "./ex/ExceptionList";
import SysSetting from "./sys-setting/SysSetting";
import Profile from "./user/Profile";

export default [
    Dashboard,
    Profile,
    Login,
    ApiResourceList,
    AuthList,
    RoleList,
    UserList,
    DictList,
    ExceptionList,
    SysSetting,
    Page404,
    Page401
]
