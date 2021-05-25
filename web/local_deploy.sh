#!/usr/bin/env bash
export NODE_ENV="development"

node_modules/.bin/sequelize db:migrate

echo 'Starting app'
npm run monitor
