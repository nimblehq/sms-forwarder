const env = process.env.NODE_ENV || 'development';
const config = require('../config/config.json')[env];
const request = require('request');

exports.sendMessage = function (slackWebhookUrl, incomingNumber, messageBody, callback) {
  let json = {
    text: 'New SMS message',
    attachments: [{
      fields: [{
        title: incomingNumber,
        value: messageBody,
        short: false
      }]
    }]
  };

  request
    .post(
      slackWebhookUrl, {
        json: json
      },
      function (error, response, body) {
        callback(error);
      });
}
