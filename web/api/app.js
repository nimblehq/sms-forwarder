let express = require('express'),
  router = express.Router();

let config = require('../config/config.json');
let ApiExt = require('./api-ext');

let mailer = require('../services/mailer');

router.post('/v1/sms/forward', function (req, res) {
  req.checkBody('incoming_number').notEmpty().len({min: 1, max: 100});
  req.checkBody('message_body').notEmpty().len({min: 1, max: 1000});
  req.checkBody('email').optional().len({min: 1, max: 1000}).isEmail();
  req.checkBody('slack_webhook').optional().len({min: 1, max: 1000}).isURL();

  req.sanitizeBody('email').toLowerCase();

  ApiExt.validateRequest(req, res, function () {
    let binder = {
      email: req.body.email,
      subject: 'New SMS message - ' + config.app_name,
      incoming_number: req.body.incoming_number,
      message_body: req.body.message_body
    };
    mailer.send('forgot-password', binder, function (err, response, html, text) {
      if (err) {
        console.log(err);
        ApiExt.buildFailedResponse(res, err.message);
      } else {
        ApiExt.buildSuccessResponse(res, 'The SMS message has been forwarded to ' + req.body.email);
      }
    });
  });
});

module.exports = router;
