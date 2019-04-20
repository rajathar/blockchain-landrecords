/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * The sample smart contract for documentation topic:
 * Trade Finance Use Case - WORK IN  PROGRESS
 */

package main

import (
	"encoding/json"
	"fmt"
	"strconv"
	"bytes"
	"time"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	sc "github.com/hyperledger/fabric/protos/peer"
)

// Define the Smart Contract structure
type SmartContract struct {
}

type AssetType int

const (
	LAND      AssetType = 1
	APARTMENT AssetType = 2
	WAREHOUSE AssetType = 3
)

type Asset struct {
	AssetId                 string     `json:"assetId"`
	Latitude float64 `json:"latitude"`
	Longitude  float64 `json:"longitude"`
	Length float64 `json:"length"`
	Breadth float64 `json:"breadth"`
	Address                 string  `json:"address"`
	OwnerId					string   `json:"ownerId"`
	OwnerName				string `json:"ownerName"`
	//AssetType               int     `json:"assetType"`
}

type Person struct {
	PersonId     int    `json:"personId"`
	Name         string `json:"name"`
	AadharNumber string `json:"aadharNumber"`
	PhoneNumber  string `json:"phoneNumber"`
	Address      string `json:"address"`
}

type Owner struct {
	OwnerId       int    `json:"ownerId"`
	PersonId      int    `json:"personId"`
	AssetId       int    `json:"assetId"`
	OwnershipDate string `json:"date"`
	Amount        int    `json:"amount"`
}

func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {

	/*persons := []Person{
		Person{PersonId: 1, Name: "Rajatha", PhoneNumber: "7893516033", AadharNumber: "234-134-222", Address: "Fortune Samrat 402"},
		Person{PersonId: 2, Name: "Fathima", PhoneNumber: "7989909547", AadharNumber: "434-134-222", Address: "Fortune Samrat 403"},
		Person{PersonId: 3, Name: "Pavana", PhoneNumber: "8008255226", AadharNumber: "534-134-222", Address: "Fortune Samrat 404"},
		Person{PersonId: 4, Name: "Srinivas", PhoneNumber: "8055224858", AadharNumber: "634-134-222", Address: "Fortune Samrat 405"},
		Person{PersonId: 5, Name: "Shashi", PhoneNumber: "9866158008", AadharNumber: "734-134-222", Address: "Fortune Samrat 406"},
		Person{PersonId: 6, Name: "Sailaesh", PhoneNumber: "8197883388", AadharNumber: "834-134-222", Address: "Fortune Samrat 407"},
		Person{PersonId: 7, Name: "Vali", PhoneNumber: "9666999031", AadharNumber: "934-134-222", Address: "Fortune Samrat 408"},
	}

	i := 1*/
	/*for i <= len(persons) {
		fmt.Println("i is ", i)
		ICsAsBytes, _ := json.Marshal(persons[i])
		APIstub.PutState(i, ICsAsBytes)
		fmt.Println("Added", persons[i])
		i = i + 1
	}*/

	assets := []Asset{
		Asset{AssetId: "1", Latitude: 10, Longitude: 20, Length: 29,
		Breadth: 10, Address: "Fortune Samrat 403", OwnerId: "234-762", OwnerName:"Srinivas"},
		Asset{AssetId: "2", Latitude: 10, Longitude: 20, Length: 29,
		Breadth: 10, Address: "Fortune Samrat 402", OwnerId:"347-983", OwnerName:"Fathima"},
	}

	i := 0
	for i < len(assets) {
		fmt.Println("i is ", i)
		ICsAsBytes, _ := json.Marshal(assets[i])
		APIstub.PutState(assets[i].AssetId, ICsAsBytes)
		fmt.Println("Added", assets[i])
		i = i + 1
	}

	/*owners := []Owner{
		Owner{OwnerId: 1, PersonId: 1, AssetId: 1, OwnershipDate: "20190101", Amount: 50000000},
		Owner{OwnerId: 2, PersonId: 2, AssetId: 1, OwnershipDate: "20150101", Amount: 75000000},
	}*/

	/*i = 1
	for i <= len(owners) {
		fmt.Println("i is ", i)
		ICsAsBytes, _ := json.Marshal(owners[i])
		APIstub.PutState(owners[i].assetId, ICsAsBytes)
		fmt.Println("Added", owners[i])
		i = i + 1
	}*/

	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {

	// Retrieve the requested Smart Contract function and arguments
	function, args := APIstub.GetFunctionAndParameters()
	// Route to the appropriate handler function to interact with the ledger appropriately
	if function == "getLandRecordsForAssetId" {
		return s.getLandRecordsForAssetId(APIstub, args)
	} else if function == "transferAsset" {
		return s.transferAsset(APIstub, args)
	} else if function == "getAssetHistory" {
		return s.getAssetHistory(APIstub, args)
	} else if function == "getMyLandRecords" {
		return s.getMyLandRecords(APIstub, args)
	} else if function == "addLandRecords" {
		return s.addLandRecords(APIstub, args)
	}

	return shim.Error("Invalid Smart Contract function name.")
}

// This function is getLandRecordsForAssetId
func (s *SmartContract) getLandRecordsForAssetId(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	assetId := args[0]

	ICAsBytes, _ := APIstub.GetState(assetId)

	return shim.Success(ICAsBytes)
}

func (s *SmartContract) getMyLandRecords(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	
	if len(args) < 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	ownerId := args[0]

	queryString := fmt.Sprintf("{\"selector\":{\"ownerId\":\"%s\"}}", ownerId)

	resultsIterator, err := APIstub.GetQueryResult(queryString);
	if err != nil {
		return shim.Error(err.Error())
	}

	// buffer is a JSON array containing historic values for the marble
        var buffer bytes.Buffer
        buffer.WriteString("[")

        bArrayMemberAlreadyWritten := false
        for resultsIterator.HasNext() {
                response, err := resultsIterator.Next()
                if err != nil {
                        return shim.Error("Error retrieving Asset history.")
                }
                // Add a comma before array members, suppress it for the first array member
                if bArrayMemberAlreadyWritten == true {
                        buffer.WriteString(",")
                }
                
		// if it was a delete operation on given key, then we need to set the
                //corresponding value null. Else, we will write the response.Value
                //as-is (as the Value itself a JSON marble)
                buffer.WriteString(string(response.Value))

                bArrayMemberAlreadyWritten = true
        }
        buffer.WriteString("]")

        fmt.Printf("- getMyLandRecords returning:\n%s\n", buffer.String())

        return shim.Success(buffer.Bytes())

}



// This function is transferAsset Claim
func (s *SmartContract) transferAsset(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	//return shim.Success(nil)
	assetId := args[0]
	//ownerId := args[1]
	personId := args[1]
	personName:= args[2]

	ICAsBytes, _ := APIstub.GetState(assetId)
	var lr Asset

	err := json.Unmarshal(ICAsBytes, &lr)

	if err != nil {
		return shim.Error("Issue with LC json unmarshaling")
	}


	LR := Asset{AssetId: lr.AssetId, Latitude: lr.Latitude, Longitude:lr.Longitude, Length: lr.Length, Breadth:lr.Breadth, Address: lr.Address, OwnerId:personId,OwnerName:personName}
	LRBytes, err := json.Marshal(LR)

	if err != nil {
		return shim.Error("Issue with LR json marshaling")
	}

        APIstub.PutState(lr.AssetId,LRBytes)
	fmt.Println("Land Registration Completed -> ", LR)

	return shim.Success(LRBytes)

}

func (s *SmartContract) addLandRecords(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
 	assetId := args[0];
	latitude,err := strconv.ParseFloat(args[1],64);
	longitude,err := strconv.ParseFloat(args[2],64);
	length,err := strconv.ParseFloat(args[3],64);
	breadth,err := strconv.ParseFloat(args[4],64);
	ownerId := args[5];
	ownerName := args[6];
	address := args[7];

	if err != nil {
		return shim.Error("Not able to parse Coordinates")
	}

	ICAsBytes, _ := APIstub.GetState(assetId)

	if ICAsBytes != nil {
		return shim.Error("Asset already exists")
	}

	AssetObj := Asset{AssetId: assetId, Latitude: latitude, Longitude:longitude, Length: length, Breadth:breadth, Address: address, OwnerId:ownerId,OwnerName:ownerName}
	ICBytes, err := json.Marshal(AssetObj)

	APIstub.PutState(assetId, ICBytes)
	fmt.Println("Asset Requested -> ", AssetObj)

	return shim.Success(nil)
}

// The main function is only relevant in unit test mode. Only included here for completeness.
func main() {

	// Create a new Smart Contract
	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error creating new Smart Contract: %s", err)
	}
}

func (s *SmartContract) getAssetHistory(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	assetId := args[0];

	resultsIterator, err := APIstub.GetHistoryForKey(assetId)
	if err != nil {
		return shim.Error("Error retrieving Asset history.")
	}
	defer resultsIterator.Close()

	// buffer is a JSON array containing historic values for the marble
	var buffer bytes.Buffer
	buffer.WriteString("[")

	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		response, err := resultsIterator.Next()
		if err != nil {
			return shim.Error("Error retrieving Asset history.")
		}
		// Add a comma before array members, suppress it for the first array member
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"TxId\":")
		buffer.WriteString("\"")
		buffer.WriteString(response.TxId)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Value\":")
		// if it was a delete operation on given key, then we need to set the
		//corresponding value null. Else, we will write the response.Value
		//as-is (as the Value itself a JSON marble)
		if response.IsDelete {
			buffer.WriteString("null")
		} else {
			buffer.WriteString(string(response.Value))
		}

		buffer.WriteString(", \"Timestamp\":")
		buffer.WriteString("\"")
		buffer.WriteString(time.Unix(response.Timestamp.Seconds, int64(response.Timestamp.Nanos)).String())
		buffer.WriteString("\"")

		buffer.WriteString(", \"IsDelete\":")
		buffer.WriteString("\"")
		buffer.WriteString(strconv.FormatBool(response.IsDelete))
		buffer.WriteString("\"")

		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")

	fmt.Printf("- getAssetHistory returning:\n%s\n", buffer.String())

	return shim.Success(buffer.Bytes())
}
