function validateRequest(req, res, next) {
  req.getValidationResult().then(function (result) {
    if (!result.isEmpty()) {
      let error = result.array()[0];
      buildFailedResponse(res, error.msg + ' ' + error.param);
      return;
    }

    next();
  });
}

function buildSuccessResponse(res, data) {
  buildResponse(res, 'success', 1, data);
}

function buildFailedResponse(res, message) {
  buildResponse(res, message, 0, null);
}

function buildResponse(res, message, success, data) {
  res.json({
    message: message,
    success: success,
    data: data
  });
}

module.exports = {
  validateRequest: validateRequest,
  buildSuccessResponse: buildSuccessResponse,
  buildFailedResponse: buildFailedResponse,
};
