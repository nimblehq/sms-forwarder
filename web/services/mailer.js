'use strict';
var nodemailer = require('nodemailer');
var emailTemplates = require('email-templates');

var path = require('path');
var templatesDir = path.resolve(__dirname, '..', 'views/mailer');
var config = require('../config/config.json');

var mailConfig = config.mail;
var transporter = nodemailer.createTransport({
  host: mailConfig.host,
  port: mailConfig.port,
  secure: mailConfig.secure,
  auth: {
    user: mailConfig.user,
    pass: mailConfig.pass
  }
});

exports.send = function (templateName, binder, next) {
  // make sure that we have an user email
  if (!binder.email) {
    return next('email address is required');
  }
  // make sure that we have a message
  if (!binder.subject) {
    return next('subject is required');
  }

  emailTemplates(templatesDir, function (err, template) {
    if (err) {
      return next(err);
    }

    template(templateName, binder, function (err, html, text) {
      if (err) {
        return next(err);
      }

      var mailOptions = {
        from: config.app_name + ' Team <' + mailConfig.user + '>',
        to: binder.email,
        subject: binder.subject,
        html: html,
        text: text
      };

      transporter.sendMail(mailOptions, function (err, info) {
        if (err) {
          return next(err);
        }
        return next(null, info.message, html, text);
      });
    });
  });
};
