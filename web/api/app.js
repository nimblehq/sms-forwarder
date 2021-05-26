let express = require('express'),
  router = express.Router();

let ApiExt = require('./api-ext');

router.post('/v1/sms/forward', function (req, res) {
  req.checkBody('sms_body').notEmpty().len({min: 1, max: 100});
  req.checkBody('email').optional().len({min: 1, max: 1000}).isEmail();
  req.checkBody('slack_webhook').optional().len({min: 1, max: 1000}).isURL();

  req.sanitizeBody('email').toLowerCase();

  ApiExt.validateRequest(req, res, function () {
    // TODO
    let result = {};
    ApiExt.buildSuccessResponse(res, result);
  });
});

module.exports = router;
