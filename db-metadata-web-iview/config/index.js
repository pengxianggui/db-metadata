module.exports = {
    publicPath: process.env.NODE_ENV === 'production' ? '/' : './',
    apiBaseUrl: process.env.NODE_ENV === 'production' ? '/' : './'
};