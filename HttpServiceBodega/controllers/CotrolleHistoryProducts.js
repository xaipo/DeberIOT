'use strict'

var HistoryTransaccionProductsModel = require('../models/HistoryTransaccionProductsModel');
var Productos = require('../controllers/ControllerProducts');


async function saveHistory(req, res) {

    if (
        req.body.description !== undefined && req.body.description !== null && req.body.description !== ""
        &&req.body.tipo !== undefined && req.body.tipo !== null && req.body.tipo !== ""
        &&req.body.products.length>0

    ) {
        let history = new HistoryTransaccionProductsModel();

        history.description = req.body.description;
        history.dateTransaccion = new Date();
        history.products= req.body.products;
        history.tipo= req.body.tipo;
        //products.status = true;

        let guardado= await history.save();
        console.log(guardado);
        let resp
        for(var i=0; i<history.products.length;i++){
            resp= await Productos.simpleUpdateProducts(req.body.products[i],history.tipo,res);

        }
        res.status(200).send({
            product: "OK"
        })
    }else{
        res.status(200).send({
            message: "One field is empty or it havent format required"
        })
    }
}












function getHistory(req, res) {

    HistoryTransaccionProductsModel.find({}, (err, resp) => {
        if (err) {
            res.status(500).send({message:err})
        } else {
            res.status(200).send({
                products: resp
            });
        }
    });

}





module.exports = {
    saveHistory
    , getHistory

};