config.module.rules.push({
    test: /\.(css)$/,
    use: ['style-loader', 'css-loader']
}, {
    test: /\.(png|svg|jpg|gif)$/,
    loader: 'file-loader',
    options: {
        name: 'images/[name].[ext]',
        // publicPath: '../'
    }
}, {
    test: /\.(woff|woff2|eot|ttf|otf)$/,
    loader: 'file-loader',
    options: {
        name: 'fonts/[name].[ext]',
        // publicPath: '../'
    }
});
