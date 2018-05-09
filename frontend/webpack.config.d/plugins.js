const HtmlWebpackPlugin = require('html-webpack-plugin');

Object.assign(config.plugins, [
    new webpack.ProvidePlugin({
        jQuery: 'jquery'
    }),
    new HtmlWebpackPlugin({
        filename: 'index.html',
        template: '../../resources/main/index.html',
        inject: true
    })
]);