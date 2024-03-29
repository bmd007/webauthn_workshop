const glob = require('glob');

module.exports = glob
  .sync('./pine-tasks/*.js')
  .reduce((prev, cur) => {
    const name = cur
      .split('/')
      .pop()
      .replace('.js', '');
    return { ...prev, [name]: require(cur) };
  }, {});
