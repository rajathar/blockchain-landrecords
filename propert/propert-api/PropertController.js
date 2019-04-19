var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

var ProperT = require("./FabricHelper")


// Request LC
router.post('/transferAsset', function (req, res) {

ProperT.transferAsset(req, res);

});

// Issue LC
router.post('/getMyLandRecords', function (req, res) {

    ProperT.getMyLandRecords(req, res);
    
});

// Accept LC
router.post('/getAssetHistory', function (req, res) {

    ProperT.getAssetHistory(req, res);
    
});

// Get LC
router.post('/getLandRecordsForAssetId', function (req, res) {

    ProperT.getLandRecordsForAssetId(req, res);
    
});


router.post('/addLandRecords', function (req, res) {

    ProperT.addLandRecords(req, res);

});


/*
// Get LC history
router.post('/getLCHistory', function (req, res) {

    ProperT.getLCHistory(req, res);
    
});*/


module.exports = router;
