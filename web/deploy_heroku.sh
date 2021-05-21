#!/usr/bin/env bash
echo 'Deploying code'
git push heroku master -f

heroku run sequelize db:migrate --env production

echo 'Setting at least one running instance of the app'
heroku ps:scale web=1

echo 'Opening app'
heroku open
