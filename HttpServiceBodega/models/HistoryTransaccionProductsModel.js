'use strict'
var mongoose= require('mongoose');
var Schema = mongoose.Schema();

var HistoryProductsTransaccionModel = mongoose.Schema({
    description: String,
    dateTransaccion: Date,
    products: Array,
    tipo:String
});

module.exports = mongoose.model('history_transaccion_products_store', HistoryProductsTransaccionModel);