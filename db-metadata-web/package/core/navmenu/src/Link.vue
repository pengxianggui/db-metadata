<template>
    <component v-bind="linkProps(to, query)">
        <slot/>
    </component>
</template>

<script>
    import {isExternal} from '../../../utils/common'

    export default {
        props: {
            to: {
                type: String,
                required: true
            },
            query: {
                type: Object
            }
        },
        methods: {
            linkProps(url, query) {
                if (isExternal(url)) {
                    return {
                        is: 'a',
                        href: url,
                        target: '_blank',
                        rel: 'noopener'
                    }
                }
                return {
                    is: 'router-link',
                    to: {
                        path: url,
                        query: query
                    }
                }
            }
        }
    }
</script>
