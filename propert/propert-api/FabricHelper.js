'use strict';
/*
* Copyright IBM Corp All Rights Reserved
*
* SPDX-License-Identifier: Apache-2.0
*/
/*
 * Chaincode Invoke
 */

var Fabric_Client = require('fabric-client');
var path = require('path');
var util = require('util');
var os = require('os');

//
// var fabric_client = new Fabric_Client();

// // setup the fabric network
// var channel = fabric_client.newChannel('mychannel');
// var order = fabric_client.newOrderer('grpc://localhost:7050')
// channel.addOrderer(order);
// //add buyer peer
// var peer = fabric_client.newPeer('grpc://localhost:8051');
// channel.addPeer(peer);

//
var member_user = null;
var store_path = path.join(__dirname, 'hfc-key-store');
console.log('Store path:'+store_path);
var tx_id = null;


// Get current state of LC using Bank user
function getLandRecordsForAssetId(req, res){
	//Init fabric client
	var fabric_client = new Fabric_Client();
	
	// setup the fabric network
	var channel = fabric_client.newChannel('mychannel');
	var order = fabric_client.newOrderer('grpc://localhost:7050')
	channel.addOrderer(order);
	
	//add buyer peer
	var peer = fabric_client.newPeer('grpc://localhost:7051');
	channel.addPeer(peer);


	Fabric_Client.newDefaultKeyValueStore({ path: store_path
	}).then((state_store) => {
		
		// assign the store to the fabric client
		fabric_client.setStateStore(state_store);
		var crypto_suite = Fabric_Client.newCryptoSuite();
		// use the same location for the state store (where the users' certificate are kept)
		// and the crypto store (where the users' keys are kept)
		var crypto_store = Fabric_Client.newCryptoKeyStore({path: store_path});
		crypto_suite.setCryptoKeyStore(crypto_store);
		fabric_client.setCryptoSuite(crypto_suite);
	
		// get the enrolled user from persistence, this user will sign all requests
		return fabric_client.getUserContext('registrarUser', true);
	}).then((user_from_store) => {
		if (user_from_store && user_from_store.isEnrolled()) {
			console.log('Successfully loaded registrarUser from persistence');
			member_user = user_from_store;
		} else {
			throw new Error('Failed to get registrarUser.... run registerUser.js');
		}
	
		// queryCar chaincode function - requires 1 argument, ex: args: ['CAR4'],
		// queryAllCars chaincode function - requires no arguments , ex: args: [''],
		var request = {chaincodeId: 'properT',
		fcn: 'getLandRecordsForAssetId',
		args: [req.body.assetId],
		chainId: 'mychannel',
		};
	
		// send the query proposal to the peer
		return channel.queryByChaincode(request);
	}).then((query_responses) => {
		console.log("Query has completed, checking results");
		// query_responses could have more than one  results if there multiple peers were used as targets
		if (query_responses && query_responses.length == 1) {
			if (query_responses[0] instanceof Error) {
				console.error("error from query = ", query_responses[0]);
				res.send({code:"500", data: "Issue with getting LC details"});
			} else {
				
				console.log("Response is ", query_responses[0].toString());
				res.send({code:"200", data: JSON.parse(query_responses[0].toString())});
			}
		} else {
			console.log("No payloads were returned from query");
			res.send({code:"500", data: "No LC found"});
		}
	}).catch((err) => {
		console.error('Failed to query successfully :: ' + err);
		res.send({code:"500", data: "Issue with getting LC details"});
	});
	
}

// Get current state of LC using Bank user
function getMyLandRecords(req, res){
	//Init fabric client
	var fabric_client = new Fabric_Client();
	
	// setup the fabric network
	var channel = fabric_client.newChannel('mychannel');
	var order = fabric_client.newOrderer('grpc://localhost:7050')
	channel.addOrderer(order);
	
	//add buyer peer
	var peer = fabric_client.newPeer('grpc://localhost:7051');
	channel.addPeer(peer);


	Fabric_Client.newDefaultKeyValueStore({ path: store_path
	}).then((state_store) => {
		
		// assign the store to the fabric client
		fabric_client.setStateStore(state_store);
		var crypto_suite = Fabric_Client.newCryptoSuite();
		// use the same location for the state store (where the users' certificate are kept)
		// and the crypto store (where the users' keys are kept)
		var crypto_store = Fabric_Client.newCryptoKeyStore({path: store_path});
		crypto_suite.setCryptoKeyStore(crypto_store);
		fabric_client.setCryptoSuite(crypto_suite);
	
		// get the enrolled user from persistence, this user will sign all requests
		return fabric_client.getUserContext('registrarUser', true);
	}).then((user_from_store) => {
		if (user_from_store && user_from_store.isEnrolled()) {
			console.log('Successfully loaded registrarUser from persistence');
			member_user = user_from_store;
		} else {
			throw new Error('Failed to get registrarUser.... run registerUser.js');
		}
	
		// queryCar chaincode function - requires 1 argument, ex: args: ['CAR4'],
		// queryAllCars chaincode function - requires no arguments , ex: args: [''],
		var request = {chaincodeId: 'properT',
		fcn: 'getMyLandRecords',
		args: [req.body.ownerId],
		chainId: 'mychannel',
		};
	
		// send the query proposal to the peer
		return channel.queryByChaincode(request);
	}).then((query_responses) => {
		console.log("Query has completed, checking results");
		// query_responses could have more than one  results if there multiple peers were used as targets
		if (query_responses && query_responses.length == 1) {
			if (query_responses[0] instanceof Error) {
				console.error("error from query = ", query_responses[0]);
				res.send({code:"500", data: "Issue with getting LC details"});
			} else {
			        console.log(query_response)	
				console.log("Response is ", query_responses[0]);
				res.send({code:"200", data: JSON.parse(query_responses[0].toString())});
			}
		} else {
			console.log("No payloads were returned from query");
			res.send({code:"500", data: "No LC found"});
		}
	}).catch((err) => {
		console.error('Failed to query successfully :: ' + err);
		res.send({code:"500", data: "Issue with getting LC details"});
	});
	
}

// Get current state of LC using Bank user
function getAssetHistory(req, res){

	//Init fabric client
	var fabric_client = new Fabric_Client();
	
	// setup the fabric network
	var channel = fabric_client.newChannel('mychannel');
	var order = fabric_client.newOrderer('grpc://localhost:7050')
	channel.addOrderer(order);
	
	//add buyer peer
	var peer = fabric_client.newPeer('grpc://localhost:7051');
	channel.addPeer(peer);

	Fabric_Client.newDefaultKeyValueStore({ path: store_path
	}).then((state_store) => {
		

		// assign the store to the fabric client
		fabric_client.setStateStore(state_store);
		var crypto_suite = Fabric_Client.newCryptoSuite();
		// use the same location for the state store (where the users' certificate are kept)
		// and the crypto store (where the users' keys are kept)
		var crypto_store = Fabric_Client.newCryptoKeyStore({path: store_path});
		crypto_suite.setCryptoKeyStore(crypto_store);
		fabric_client.setCryptoSuite(crypto_suite);
	
		// get the enrolled user from persistence, this user will sign all requests
		return fabric_client.getUserContext('registrarUser', true);
	}).then((user_from_store) => {
		if (user_from_store && user_from_store.isEnrolled()) {
			console.log('Successfully loaded registrarUser from persistence');
			member_user = user_from_store;
		} else {
			throw new Error('Failed to get registrarUser.... run registerUser.js');
		}
	
		// queryCar chaincode function - requires 1 argument, ex: args: ['CAR4'],
		// queryAllCars chaincode function - requires no arguments , ex: args: [''],
		var request = {chaincodeId: 'properT',
		fcn: 'getAssetHistory',
		args: [req.body.assetId],
		chainId: 'mychannel',
		};
	
		// send the query proposal to the peer
		return channel.queryByChaincode(request);
	}).then((query_responses) => {
		console.log("Query has completed, checking results");
		// query_responses could have more than one  results if there multiple peers were used as targets
		if (query_responses && query_responses.length == 1) {
			if (query_responses[0] instanceof Error) {
				console.error("error from query = ", query_responses[0]);
				res.send({code:"500", message: "Issue with getting LC details"});
			} else {
				
				console.log("Response is ", query_responses[0].toString());
				res.send({code:"200", data: JSON.parse(query_responses[0].toString())});
			}
		} else {
			console.log("No payloads were returned from query");
			res.send({code:"500", message: "No LC found"});
		}
	}).catch((err) => {
		console.error('Failed to query successfully :: ' + err);
		res.send({code:"500", message: "Issue with getting LC details"});
	});
	
}

// Get current state of LC using Bank user
function transferAsset(req, res){

	//Init fabric client
	var fabric_client = new Fabric_Client();
	
	// setup the fabric network
	var channel = fabric_client.newChannel('mychannel');
	var order = fabric_client.newOrderer('grpc://localhost:7050')
	channel.addOrderer(order);
	
	//add buyer peer
	var peer = fabric_client.newPeer('grpc://localhost:7051');
	channel.addPeer(peer);

	Fabric_Client.newDefaultKeyValueStore({ path: store_path
	}).then((state_store) => {
		

		// assign the store to the fabric client
		fabric_client.setStateStore(state_store);
		var crypto_suite = Fabric_Client.newCryptoSuite();
		// use the same location for the state store (where the users' certificate are kept)
		// and the crypto store (where the users' keys are kept)
		var crypto_store = Fabric_Client.newCryptoKeyStore({path: store_path});
		crypto_suite.setCryptoKeyStore(crypto_store);
		fabric_client.setCryptoSuite(crypto_suite);
	
		// get the enrolled user from persistence, this user will sign all requests
		return fabric_client.getUserContext('registrarUser', true);
	}).then((user_from_store) => {
		if (user_from_store && user_from_store.isEnrolled()) {
			console.log('Successfully loaded registrarUser from persistence');
			member_user = user_from_store;
		} else {
			throw new Error('Failed to get registrarUser.... run registerUser.js');
		}

		// get a transaction id object based on the current user assigned to fabric client
		tx_id = fabric_client.newTransactionID();
		console.log("Assigning transaction_id: ", tx_id._transaction_id);

		// queryCar chaincode function - requires 1 argument, ex: args: ['CAR4'],
		// queryAllCars chaincode function - requires no arguments , ex: args: [''],
		var request = {chaincodeId: 'properT',
		fcn: 'transferAsset',
		args: [req.body.assetId, req.body.ownerId, req.body.ownerName],
		chainId: 'mychannel',
		txId: tx_id
		};

		console.log("called with params" + req.body.assetId + req.body.ownerId + req.body.ownerName);

		// send the query proposal to the peer
		return channel.queryByChaincode(request);
		//return channel.sendTransactionProposal(request);
	}).then((query_responses) => {
		console.log("Query has completed, checking results");
		// query_responses could have more than one  results if there multiple peers were used as targets
		if (query_responses && query_responses.length == 1) {
			if (query_responses[0] instanceof Error) {
				console.error("error from query = ", query_responses[0]);
				res.send({code:"500", message: "Issue with getting LC details"});
			} else {
				
				console.log("Response is ", query_responses[0].toString());
				res.send({code:"200", data: JSON.parse(query_responses[0].toString())});
			}
		} else {
			console.log("No payloads were returned from query");
			res.send({code:"500", message: "No LC found"});
		}
	}).catch((err) => {
		console.error('Failed to query successfully :: ' + err);
		res.send({code:"500", message: "Issue with getting LC details"});
	});
	
}

function addLandRecords(req, res){

	//Init fabric client
	var fabric_client = new Fabric_Client();
	
	// setup the fabric network
	var channel = fabric_client.newChannel('mychannel');
	var order = fabric_client.newOrderer('grpc://localhost:7050')
	channel.addOrderer(order);
	
	//add buyer peer
	var peer = fabric_client.newPeer('grpc://localhost:7051');
	channel.addPeer(peer);

	Fabric_Client.newDefaultKeyValueStore({ path: store_path
	}).then((state_store) => {
		

		// assign the store to the fabric client
		fabric_client.setStateStore(state_store);
		var crypto_suite = Fabric_Client.newCryptoSuite();
		// use the same location for the state store (where the users' certificate are kept)
		// and the crypto store (where the users' keys are kept)
		var crypto_store = Fabric_Client.newCryptoKeyStore({path: store_path});
		crypto_suite.setCryptoKeyStore(crypto_store);
		fabric_client.setCryptoSuite(crypto_suite);
	
		// get the enrolled user from persistence, this user will sign all requests
		return fabric_client.getUserContext('registrarUser', true);
	}).then((user_from_store) => {
		if (user_from_store && user_from_store.isEnrolled()) {
			console.log('Successfully loaded registrarUser from persistence');
			member_user = user_from_store;
		} else {
			throw new Error('Failed to get registrarUser.... run registerUser.js');
		}
	
		// queryCar chaincode function - requires 1 argument, ex: args: ['CAR4'],
		// queryAllCars chaincode function - requires no arguments , ex: args: [''],
		var request = {chaincodeId: 'properT',
		fcn: 'addLandRecords',
		args: [req.body.assetId, req.body.assetEastCoordinatesId, req.body.assetWestCoordinatesId , req.body.assetNorthCoordinatesId, req.body.assetSouthCoordinatesId, req.body.ownerId, req.body.ownerName, req.body.address],
		chainId: 'mychannel',
		};

		console.log("Query params:"+ req.body.assetId+ req.body.east+ req.body.west + req.body.north+ req.body.south+ req.body.ownerId+ req.body.ownerName + req.body.address);
		// send the query proposal to the peer
		return channel.queryByChaincode(request);
	}).then((query_responses) => {
		console.log("Query has completed, checking results");
		// query_responses could have more than one  results if there multiple peers were used as targets
		if (query_responses && query_responses.length == 1) {
			if (query_responses[0] instanceof Error) {
				console.error("error from query = ", query_responses[0]);
				res.send({code:"500", message: "Issue with getting LC details"});
			} else {
				
				console.log("Response is ", query_responses[0].toString());
				res.send({code:"200", data: JSON.parse(query_responses[0].toString())});
			}
		} else {
			console.log("No payloads were returned from query");
			res.send({code:"500", message: "No LC found"});
		}
	}).catch((err) => {
		console.error('Failed to query successfully :: ' + err);
		res.send({code:"500", message: "Issue with getting LC details"});
	});
	
}

let propert = {
 	getLandRecordsForAssetId: getLandRecordsForAssetId,
	transferAsset: transferAsset,
	getMyLandRecords: getMyLandRecords,
	getAssetHistory: getAssetHistory,
    addLandRecords: addLandRecords
}

module.exports = propert;

