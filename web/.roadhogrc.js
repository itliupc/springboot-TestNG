/**
 * Created by Administrator on 2017/8/15 0015.
 */
module.exports = {
    "entry": "src/index.js",
    "disableCSSModules": false,
    "publicPath": "/interfacetestdemo/",
    "autoprefixer": null,
    "proxy":{
      "/api/v1":{
          "target": "http://localhost:8005/interfacetestdemo/api/v1/",
          "changeOrigin":true,
          "pathRewrite": {"^/api/v1": ""}
      }
    },
    "extraBabelPlugins": [
        "transform-runtime",
        ["import", {"libraryName": "antd", "style": true}]
    ],
    "env": {
        "development": {
            "extraBabelPlugins": [
                "dva-hmr",
            ]
        },
    },

};