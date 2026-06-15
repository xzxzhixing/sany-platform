module.exports = {
  publicPath: '/monitor/',
  outputDir: 'dist',
  devServer: {
    port: 3001,
    proxy: {
      '/monitor/api': {
        target: 'http://localhost:8083',
        pathRewrite: { '^/monitor/api': '/api' },
        changeOrigin: true
      }
    }
  }
}
