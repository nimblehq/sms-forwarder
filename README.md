# sms-forwarder

Building the application to install on Android devices with Thai mobile numbers and forward incoming SMS to the group of developers/PM on development

## Features

- Forward SMS to an email or Slack channel (webhook).
- Filter SMS by sender number or regex pattern to forward.
- Admin dashboard to manage user and filter list for remote config.

## Backend

API Documentation: https://documenter.getpostman.com/view/6401217/TzseHkXG

- Platform: Nodejs + [express](http://expressjs.com/)
- Hosting: [Heroku](https://nimble-sms-forwarder.herokuapp.com)
- Database: [Postgres](https://addons-sso.heroku.com/apps/4102a673-6a21-48b3-bd2b-dcdaef05916d/addons/5e97d337-aa08-4af5-bd4b-a9b2b2eec0b6)
- Admin: [express-admin](https://simov.github.io/express-admin/)

## App

- Platform: Android

## License

This project is Copyright (c) 2021 and onwards. It is free software,
and may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: /LICENSE

## About

![Nimble](https://assets.nimblehq.co/logo/dark/logo-dark-text-160.png)

This project is maintained and funded by Nimble.

We love open source and do our part in sharing our work with the community!
See [our other projects][community] or [hire our team][hire] to help build your product.

[community]: https://github.com/nimblehq
[hire]: https://nimblehq.co/
