let express = require('express'),
  router = express.Router();

let config = require('../config/config.json');
let ApiExt = require('./api-ext');

let mailer = require('../services/mailer');
let slack = require('../services/slack');

const Filter = require('../models').Filter;

router.post('/v1/sms/forward', function (req, res) {
  req.checkBody('incoming_number').optional().len({
    min: 0,
    max: 100
  });
  req.checkBody('message_body').notEmpty().len({
    min: 1,
    max: 1000
  });
  req.checkBody('email').optional().len({
    min: 0,
    max: 1000
  }).isEmail();
  req.checkBody('slack_webhook').optional().len({
    min: 0,
    max: 1000
  }).isURL();

  req.sanitizeBody('email').toLowerCase();

  ApiExt.validateRequest(req, res, function () {
    let incomingNumber = req.body.incoming_number;
    let messageBody = req.body.message_body;

    let binder = {
      email: req.body.email,
      subject: 'New SMS message - ' + config.app_name,
      incoming_number: incomingNumber,
      message_body: messageBody
    };
    mailer.send('forgot-password', binder, function (error, response, html, text) {
      if (error) {
        console.log(error);
      }
      forwardToSlack(res, error, req.body.slack_webhook, incomingNumber, messageBody);
    });
  });
});

router.get('/v1/filter/list', function (req, res) {
  // TODO get user from access token after implemting Oauth2
  let userId = 2;
  req.checkQuery('limit').isInt({
    min: 1,
    max: 100
  });
  req.checkQuery('offset').isInt({
    min: 0
  });

  ApiExt.validateRequest(req, res, function () {
    Filter
      .findByUserId(userId, req.query.limit, req.query.offset)
      .then(function (filters) {
        if (filters) {
          ApiExt.buildSuccessResponse(res, filters);
        } else {
          ApiExt.buildFailedResponse(res, "No filters yet.");
        }
      });
  });
});


function forwardToSlack(res, emailError, slackWebhookUrl, incomingNumber, messageBody) {
  slack.sendMessage(slackWebhookUrl, incomingNumber, messageBody, function (error) {
    if (error) {
      console.log(error);
    }
    buildResponse(res, emailError && error)
  })
}

function buildResponse(res, emailError, slackApiErr) {
  var error;
  if (emailError) error = emailError
  else error = slackApiErr;

  if (error) {
    console.log(error);
    ApiExt.buildFailedResponse(res, error.message);
  } else {
    ApiExt.buildSuccessResponse(res, 'The SMS message has been forwarded');
  }
}

module.exports = router;
