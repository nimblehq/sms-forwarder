'use strict';
module.exports = (sequelize, DataTypes) => {
  const Filter = sequelize.define('Filter', {
    userId: DataTypes.INTEGER,
    sender: DataTypes.STRING,
    template: DataTypes.STRING,
    forwardEmailAddress: DataTypes.STRING,
    forwardSlackChannel: DataTypes.STRING
  }, {});
  Filter.associate = function (models) {
    // associations can be defined here
  };
  return Filter;
};
