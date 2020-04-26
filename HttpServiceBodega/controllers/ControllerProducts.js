'use strict'

var Products = require('../models/Product');


function saveProducts(req, res) {

    if (
        req.body.description !== undefined && req.body.description !== null && req.body.description !== ""
        &&req.body.sku !== undefined && req.body.sku !== null && req.body.sku !== ""
        &&req.body.stock !== undefined && req.body.stock !== null && req.body.stock !== ""

    ) {
        let products = new Products();

        products.description = req.body.description;
        products.sku = req.body.sku;
        products.stock= req.body.stock;
        products.status = true;

        products.save((err, resp) => {
            if (err) {
                res.status(500).send({
                    message: 'Error save product',
                    error: err.message
                })
            } else {
                if (!resp) {
                    res.status(404).send({
                        message: 'error product'
                    })
                } else {
                    res.status(200).send({
                        product: resp
                    })
                }
            }
        })

    }else{
        res.status(200).send({
            message: "One field is empty or it havent format required"
        })
    }
}

function getAllProducts(req, res) {

    Products.find({}, (err, resp) => {
        if (err) {
            res.status(500).send({message:err})
        } else {
            res.status(200).send({
                products: resp
            });
        }
    });

}


function updateProducts(req, res) {

    if (
        req.body.description !== undefined && req.body.description !== null && req.body.description !== ""
        &&req.body.sku !== undefined && req.body.sku !== null && req.body.sku !== ""
        &&req.body.stock !== undefined && req.body.stock !== null && req.body.stock !== ""

    ) {
        let products = new Products();
        products._id = req.body._id;
        products.description = req.body.description;
        //TypeQuestion.update({_id: },typeQuestion
        products.findByIdAndUpdate(req.body._id,{$set:products} ,{new:true}, (err, resp) => {
            if (err) {
                res.status(500).send({message: err})
            } else {
                res.status(200).send({
                    products: resp,
                    status:'modified'
                });
            }
        });
    }else{
        res.status(200).send({
            message: "One field is empty or it havent format required"
        })
    }
}

async function simpleUpdateProducts(item,tipo,res) {
        try {
            if (
                //  item.description !== undefined && item.description !== null && item.description !== ""
                item.sku !== undefined && item.sku !== null && item.sku !== ""
                && item.stock !== undefined && item.stock !== null && item.stock !== ""

            ) {


                let productResult = await Products.find({sku: item.sku});
                let products = new Products();
                if (tipo == "+") {
                    var aux = item.stock + productResult[0].stock;
                    products = productResult[0];
                    products.stock = aux;
                } else {
                    var aux = item.stock - productResult[0].stock;
                    products = productResult[0];
                    products.stock = aux;
                }
                let respUpdate = await Products.findByIdAndUpdate(products._id, {$set: products}, {new: true});
            } else {
                console.log("no tiene parametros");
            }
        }
        catch (e) {
            res.status(500).send({
                message: e.message
            })
        }
            //TypeQuestion.update({_id: },typeQuestion


        //console.log(respUpdate);

}
module.exports = {
    saveProducts
    , getAllProducts
    ,updateProducts
    ,simpleUpdateProducts
};