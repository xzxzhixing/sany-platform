module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  devServer: {
    port: 9528,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
}
