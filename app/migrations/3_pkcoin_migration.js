const PKCoin = artifacts.require("PKCoin");

module.exports = function (deployer) {
  deployer.deploy(PKCoin);
};