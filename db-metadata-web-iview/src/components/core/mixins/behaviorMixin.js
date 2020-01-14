/**
 * 无渲染行为插槽
 * Render-less component providing the collapse on behaviors.
 * Can be used as a behavior slot of component.
 */
export default {
    props: {
        /**
         *  Parent event listener.
         */
        on: {
            required: true,
            type: Function
        },
        /**
         *  actions. Typically provided by behavior slot.
         */
        actions: {
            required: true,
            type: Object
        }
    },
    render: () => null
}
