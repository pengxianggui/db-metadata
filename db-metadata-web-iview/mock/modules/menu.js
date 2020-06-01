import metaRoutes from '@/router/metaRoutes'

export default [
    {
        url: '/route/list',
        type: 'get',
        response: config => {
            console.log('mock: /route/list');
            return {
                code: 200,
                state: 'ok',
                data: metaRoutes
            }
        }
    }
]