'use strict'
var mongoose= require('mongoose');
var Schema = mongoose.Schema();

var ProductModel = mongoose.Schema({
    description: String,
    stock: Number,
    sku: String,
    status: Boolean
});

module.exports = mongoose.model('product', ProductModel);