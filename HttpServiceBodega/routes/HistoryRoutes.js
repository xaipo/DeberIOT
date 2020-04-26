var express = require('express');
var router = express.Router();
var HistoryController = require('../controllers/CotrolleHistoryProducts');


router.post('/saveHistory', HistoryController.saveHistory);
router.post('/getAllHistory', HistoryController.getHistory);



module.exports = router;