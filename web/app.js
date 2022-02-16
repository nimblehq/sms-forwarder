var createError = require('http-errors');
var path = require('path');
var express = require('express'),
  logger = require('morgan'),
  cookieParser = require('cookie-parser'),
  expressValidator = require('express-validator'),
  app = express();

const User = require('./models').User;

// basic authentication https://www.npmjs.com/package/express-basic-auth
const basicAuth = require('express-basic-auth')
app.use(basicAuth({
  authorizer: asyncAuthorizer,
  unauthorizedResponse: getUnauthorizedResponse,
  authorizeAsync: true,
  challenge: true
}))

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({
  extended: false
}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(expressValidator({
  customSanitizers: {
    toLowerCase: function (value) {
      return value.toLowerCase();
    }
  }
}));

app.use('/', require('./routes/index'));
app.use('/api', require('./api/app'));
app.use(require('./admin/app'));

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

function asyncAuthorizer(username, password, callback) {
  User.findOne({
    where: {
      username: username,
      password: password
    }
  }).then(function (user) {
    if (user) {
      return callback(null, true)
    } else {
      return callback(null, false)
    }
  });
}

function getUnauthorizedResponse(req) {
  return req.auth ?
    buildFailedResponse('Credentials ' + req.auth.user + ':' + req.auth.password + ' rejected') :
    buildFailedResponse('No credentials provided')
}

function buildFailedResponse(message) {
  return {
    message: message,
    success: 0,
    data: null
  };
}

module.exports = app;
