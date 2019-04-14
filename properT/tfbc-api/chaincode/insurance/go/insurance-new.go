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

	"github.com/hyperledger/fabric/core/chaincode/shim"
	sc "github.com/hyperledger/fabric/protos/peer"
)

// Define the Smart Contract structure
type SmartContract struct {
}

// Define the letter of credit
type InsuranceClaim struct {
	ClaimId     string    `json:"claimId"`
	Date        string `json:"date"`
	InvoiceID   string   `json:"invoiceId"`
	Amount      int    `json:"amount"`
	PatienceId  string    `json:"patientId"`
	PatientName string `json:"patientName"`
	Status      string `json:"status"`
	HospitalId  string    `json:"hospitalId"`
	DoctorId    string    `json:"doctorId"`
}

func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
	claims := []InsuranceClaim{
		InsuranceClaim{ClaimId: "1", Date: "20190228", InvoiceID: "123", PatienceId: "1234", PatientName: "RR", HospitalId: "123", DoctorId: "1", Amount: 50000, Status: "Success"},
		InsuranceClaim{ClaimId: "2", Date: "20190301", InvoiceID: "342", PatienceId: "3455", PatientName: "RA", HospitalId: "123", DoctorId: "2", Amount: 4000, Status: "Success"},
	}

	i := 0
	for i < len(claims) {
		fmt.Println("i is ", i)
		ICsAsBytes, _ := json.Marshal(claims[i])
		//APIstub.PutState("Claim"+strconv.Itoa(i), ICsAsBytes)
                APIstub.PutState(claims[i].InvoiceID, ICsAsBytes)
		fmt.Println("Added", claims[i])
		i = i + 1
	}

	return shim.Success(nil)

	//return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {

	// Retrieve the requested Smart Contract function and arguments
	function, args := APIstub.GetFunctionAndParameters()
	// Route to the appropriate handler function to interact with the ledger appropriately
	if function == "addNewClaim" {
		return s.addNewClaim(APIstub, args)
	} else if function == "getClaimStatus" {
		return s.getClaimStatus(APIstub, args)
	}

	/*
		// Retrieve the requested Smart Contract function and arguments
		function, args := APIstub.GetFunctionAndParameters()
		// Route to the appropriate handler function to interact with the ledger appropriately
		if function == "requestLC" {
			return s.requestLC(APIstub, args)
		} else if function == "issueLC" {
			return s.issueLC(APIstub, args)
		} else if function == "acceptLC" {
			return s.acceptLC(APIstub, args)
		} else if function == "getLC" {
			return s.getLC(APIstub, args)
		} else if function == "getLCHistory" {
			return s.getLCHistory(APIstub, args)
		}*/

	return shim.Error("Invalid Smart Contract function name.")
}

// This function is getClaimStatus
func (s *SmartContract) getClaimStatus(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	invoiceId := args[0]

	// if err != nil {
	// 	return shim.Error("No Amount")
	// }

	ICAsBytes, _ := APIstub.GetState(invoiceId)

	return shim.Success(ICAsBytes)
}

// This function is addNew Claim
func (s *SmartContract) addNewClaim(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	/*lcId := args[0];
		expiryDate := args[1];
		buyer := args[2];
		bank := args[3];
		seller := args[4];
		amount, err := strconv.Atoi(args[5]);
		if err != nil {
			return shim.Error("Not able to parse Amount")
		}


		LC := LetterOfCredit{LCId: lcId, ExpiryDate: expiryDate, Buyer: buyer, Bank: bank, Seller: seller, Amount: amount, Status: "Requested"}
		LCBytes, err := json.Marshal(LC)

	    APIstub.PutState(lcId,LCBytes)
		fmt.Println("LC Requested -> ", LC)


	ClaimId     int    `json:"claimId"`
	Date        string `json:"date"`
	InvoiceID   int    `json:"invoiceId"`
	Amount      int    `json:"amount"`
	PatienceId  int    `json:"patientId"`
	PatientName string `json:"patientName"`
	Status      string `json:"status"`
	HospitalId  int    `json:"hospitalId"`
	DoctorId    int    `json:"doctorId"`
	*/

	claimId := args[0]
	date := args[1]
	invoiceId := args[2]
	patientId := args[4]
	patientName := args[5]
	hospitalId := args[6]
	doctorId := args[7]
	amount, err := strconv.Atoi(args[3])
	if err != nil {
		return shim.Error("Not able to parse Amount")
	}

	ICAsBytes, _ := APIstub.GetState(claimId)

	if ICAsBytes != nil {
		return shim.Error("Claim already exists")
	}

	IC := InsuranceClaim{ClaimId: claimId, Date: date, InvoiceID: invoiceId, PatienceId: patientId, PatientName: patientName, HospitalId: hospitalId, DoctorId: doctorId, Amount: amount, Status: "Processing"}
	ICBytes, err := json.Marshal(IC)

	APIstub.PutState(invoiceId, ICBytes)
	fmt.Println("LC Requested -> ", IC)

	return shim.Success(nil)
}

/*
func (s *SmartContract) acceptLC(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	lcId := args[0]

	LCAsBytes, _ := APIstub.GetState(lcId)

	var lc LetterOfCredit

	err := json.Unmarshal(LCAsBytes, &lc)

	if err != nil {
		return shim.Error("Issue with LC json unmarshaling")
	}

	LC := LetterOfCredit{LCId: lc.LCId, ExpiryDate: lc.ExpiryDate, Buyer: lc.Buyer, Bank: lc.Bank, Seller: lc.Seller, Amount: lc.Amount, Status: "Accepted"}
	LCBytes, err := json.Marshal(LC)

	if err != nil {
		return shim.Error("Issue with LC json marshaling")
	}

	APIstub.PutState(lc.LCId, LCBytes)
	fmt.Println("LC Accepted -> ", LC)

	return shim.Success(nil)
}

func (s *SmartContract) getLC(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	lcId := args[0]

	// if err != nil {
	// 	return shim.Error("No Amount")
	// }

	LCAsBytes, _ := APIstub.GetState(lcId)

	return shim.Success(LCAsBytes)
}

func (s *SmartContract) getLCHistory(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	lcId := args[0]

	resultsIterator, err := APIstub.GetHistoryForKey(lcId)
	if err != nil {
		return shim.Error("Error retrieving LC history.")
	}
	defer resultsIterator.Close()

	// buffer is a JSON array containing historic values for the marble
	var buffer bytes.Buffer
	buffer.WriteString("[")

	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		response, err := resultsIterator.Next()
		if err != nil {
			return shim.Error("Error retrieving LC history.")
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

	fmt.Printf("- getLCHistory returning:\n%s\n", buffer.String())

	return shim.Success(buffer.Bytes())
}*/

func (s *SmartContract) initLedger(APIstub shim.ChaincodeStubInterface) sc.Response {
/*	claims := []InsuranceClaim{
		InsuranceClaim{ClaimId: 1, Date: "20190228", InvoiceID: 123, PatienceId: 1234, PatientName: "RR", HospitalId: 123, DoctorId: 1, Amount: 50000, Status: "Success"},
		InsuranceClaim{ClaimId: 2, Date: "20190301", InvoiceID: 342, PatienceId: 3455, PatientName: "RA", HospitalId: 123, DoctorId: 2, Amount: 4000, Status: "Success"},
	}

	i := 0
	for i < len(cars) {
		fmt.Println("i is ", i)
		ICsAsBytes, _ := json.Marshal(claims[i])
		APIstub.PutState("Claim"+strconv.Itoa(i), ICsAsBytes)
		fmt.Println("Added", claims[i])
		i = i + 1
	}*/

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

