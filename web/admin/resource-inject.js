module.exports = {
  injectStaticPath: function () {
    let fs = require('fs'),
      path = require('path');

    let fpath = path.join(__dirname, './config/custom.json');
    let data = fs.readFileSync(fpath, 'utf8');
    fs.writeFileSync(fpath,
      data.replace(/APP_DIR\/admin/g, __dirname)
    );
  }
};
