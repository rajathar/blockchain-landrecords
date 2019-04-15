var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

var ProperT = require("./FabricHelper")


// Request LC
/*router.post('/', function (req, res) {

ProperT.requestLC(req, res);

});

// Issue LC
router.post('/issueLC', function (req, res) {

    ProperT.issueLC(req, res);
    
});

// Accept LC
router.post('/acceptLC', function (req, res) {

    ProperT.acceptLC(req, res);
    
});*/

// Get LC
router.post('/getLandRecordsForAssetId', function (req, res) {

    ProperT.getLandRecordsForAssetId(req, res);
    
});

/*
// Get LC history
router.post('/getLCHistory', function (req, res) {

    ProperT.getLCHistory(req, res);
    
});*/


module.exports = router;
