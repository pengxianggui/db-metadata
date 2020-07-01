const featureConfs = {
    'list-table-tmpl': {
        "list": {
            "objectCode": "meta_object",
            "primaryKey": "code"
        },
        "table": {
            "objectCode": "meta_field",
            "foreignFieldCode": "object_code"
        }
    },
    'tree-table-tmpl': {
        "tree": {
            "objectCode": "meta_object",
            "primaryKey": "code"
        },
        "table": {
            "objectCode": "meta_field",
            "foreignFieldCode": "object_code"
        }
    },
    'tree-form-tmpl-demo': {
        "tree": {
            "objectCode": "meta_object",
            "primaryKey": "id",
        },
        "form": {
            "objectCode": "meta_object"
        }
    }
};

export default [
    {
        url: '/feature/load',
        type: 'get',
        disable: true,
        response: config => {
            const {fc} = config.query;
            console.log('mock: /feature/load, featureCode: %s', fc);
            return {
                code: 200,
                state: 'ok',
                data: featureConfs[fc]
            }
        }
    }
]