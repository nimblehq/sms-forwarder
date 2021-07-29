#!/usr/bin/env bash

# echo 'Setting subdir-heroku-buildpack'
# heroku buildpacks:clear
# heroku buildpacks:set https://github.com/timanovsky/subdir-heroku-buildpack
# heroku buildpacks:add heroku/nodejs
# heroku config:set PROJECT_PATH=web

echo 'Deploying code'
git push heroku master -f

heroku run sequelize db:migrate --env production

echo 'Setting at least one running instance of the app'
heroku ps:scale web=1

echo 'Opening app'
heroku open
