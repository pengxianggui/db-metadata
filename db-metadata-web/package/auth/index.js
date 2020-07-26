import {isArray} from "../utils/common";
import {access} from "../constant/variable";

export function setRoles(roles = []) {
    if (!isArray(roles)) {
        throw new Error('[MetaElement] the roles must be a Array, composed by String value!');
    }

    access.roles = roles;
    return access.roles;
}