var express = require('express');
var router = express.Router();
var ProductsController = require('../controllers/ControllerProducts');


router.post('/saveProducts', ProductsController.saveProducts);
router.post('/getAllProducts', ProductsController.getAllProducts);
router.post('/updateProducts', ProductsController.updateProducts);


module.exports = router;