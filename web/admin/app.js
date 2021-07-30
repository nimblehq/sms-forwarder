const express = require('express'),
  expressAdmin = require('express-admin'),
  resourceInject = require('./resource-inject');

const app = express();

// merge config for express-admin from base module
const env = process.env.NODE_ENV || 'development';
const config = require('./../config/config.json')[env];
const expressAppConfig = require('./config/config.json');

resourceInject.injectStaticPath();
// resourceInject.injectCustomJS();

expressAppConfig.pg = {
  user: config.username,
  password: config.password,
  database: config.database,
  host: config.host,
  ssl: config.dialectOptions.ssl
};

const expressConfig = {
  dpath: './admin/config',
  config: expressAppConfig,
  settings: require('./config/settings.json'),
  custom: require('./config/custom.json'),
  users: require('./config/users.json')
  // additionally you can pass your own session middleware to use
  // session: session({...})
};

// init express-admin middleware
expressAdmin.init(expressConfig, function (err, admin) {
  if (err) return console.log(err);

  // mount express-admin
  app.use('/admin', admin);
});

module.exports = app;
