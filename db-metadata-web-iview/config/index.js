module.exports = {
    publicPath: process.env.NODE_ENV === 'production' ? '/db-metadata-view' : './',
    apiBaseUrl: process.env.NODE_ENV === 'production' ? '/metadata-web-jfinal' : './'
};