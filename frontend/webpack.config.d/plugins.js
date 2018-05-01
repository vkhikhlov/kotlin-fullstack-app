const HtmlWebpackPlugin = require('html-webpack-plugin');

Object.assign(config.plugins, [
    new HtmlWebpackPlugin({
        filename: 'index.html',
        template: '../../resources/main/index.html',
        inject: true
    })
]);