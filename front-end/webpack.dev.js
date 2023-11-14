const { merge } = require("webpack-merge");
const common = require("./webpack.common.js");

module.exports = merge(common, {
  mode: "development",
  devtool: "eval",
  devServer: {
    historyApiFallback: true,
    port: 3000,
    hot: true,
    proxy: {
      "/user-service/": {
        target: "http://localhost:8000",
        changeOrigin: true,
      },
      "/auth-service/": {
        target: "http://localhost:8000",
        changeOrigin: true,
      },
    },
  },
});
