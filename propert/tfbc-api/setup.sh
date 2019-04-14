

echo "Creating channel genesis block.."

# Create the channel
docker exec -e "CORE_PEER_LOCALMSPID=KYCCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/kycCompany.properT.com/users/Admin@kycCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.kycCompany.properT.com:7051" cli peer channel create -o orderer.properT.com:7050 -c mychannel -f /etc/hyperledger/configtx/channel.tx


#sleep 5

echo "Channel genesis block created."

echo "peer0.kycCompany.properT.com joining the channel..."
# Join peer0.kycCompany.properT.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=KYCCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/kycCompany.properT.com/users/Admin@kycCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.kycCompany.properT.com:7051" cli peer channel join -b mychannel.block

#echo "peer0.kycCompany.properT.com joined the channel"

echo "peer1.kycCompany.properT.com joining the channel..."
# Join peer1.kycCompany.properT.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=KYCCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/kycCompany.properT.com/users/Admin@kycCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer1.kycCompany.properT.com:7051" cli peer channel join -b mychannel.block

echo "peer1.kycCompany.properT.com joined the channel"


echo "peer0.resgistrarCompany.properT.com joining the channel..."

# Join peer0.resgistrarCompany.properT.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=ResgistrarCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/resgistrarCompany.properT.com/users/Admin@resgistrarCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.resgistrarCompany.properT.com:7051" cli peer channel join -b mychannel.block

echo "peer0.resgistrarCompany.properT.com joined the channel"

echo "peer1.resgistrarCompany.properT.com joining the channel..."

# Join peer1.resgistrarCompany.properT.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=ResgistrarCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/resgistrarCompany.properT.com/users/Admin@resgistrarCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer1.resgistrarCompany.properT.com:7051" cli peer channel join -b mychannel.block

echo "peer1.resgistrarCompany.properT.com joined the channel"

echo "peer0.financialCompany.properT.com joining the channel..."
# Join peer0.financialCompany.properT.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=FinancialCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/financialCompany.properT.com/users/Admin@financialCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.financialCompany.properT.com:7051" cli peer channel join -b mychannel.block
sleep 5

echo "peer0.financialCompany.properT.com joined the channel"

echo "peer1.financialCompany.properT.com joining the channel..."
# Join peer1.financialCompany.properT.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=FinancialCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/financialCompany.properT.com/users/Admin@financialCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer1.financialCompany.properT.com:7051" cli peer channel join -b mychannel.block
sleep 5

echo "peer1.financialCompany.properT.com joined the channel"

echo "Installing properT chaincode to peer0.kycCompany.properT.com..."

# install chaincode
# Install code on kycCompany peer
docker exec -e "CORE_PEER_LOCALMSPID=KYCCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/kycCompany.properT.com/users/Admin@kycCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.kycCompany.properT.com:7051" cli peer chaincode install -n properT -v 1.0 -p github.com/properT/go -l golang

echo "Installed properT chaincode to peer0.kycCompany.properT.com"

echo "Installing properT chaincode to peer1.kycCompany.properT.com..."

# install chaincode
# Install code on kycCompany peer
docker exec -e "CORE_PEER_LOCALMSPID=KYCCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/kycCompany.properT.com/users/Admin@kycCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer1.kycCompany.properT.com:7051" cli peer chaincode install -n properT -v 1.0 -p github.com/properT/go -l golang

echo "Installed properT chaincode to peer1.kycCompany.properT.com"


echo "Installing properT chaincode to peer0.resgistrarCompany.properT.com...."

# Install code on resgistrarCompany peer
docker exec -e "CORE_PEER_LOCALMSPID=ResgistrarCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/resgistrarCompany.properT.com/users/Admin@resgistrarCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.resgistrarCompany.properT.com:7051" cli peer chaincode install -n properT -v 1.0 -p github.com/properT/go -l golang

echo "Installed properT chaincode to peer0.resgistrarCompany.properT.com"

echo "Installing properT chaincode to peer1.resgistrarCompany.properT.com...."

# Install code on resgistrarCompany peer
docker exec -e "CORE_PEER_LOCALMSPID=ResgistrarCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/resgistrarCompany.properT.com/users/Admin@resgistrarCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer1.resgistrarCompany.properT.com:7051" cli peer chaincode install -n properT -v 1.0 -p github.com/properT/go -l golang

sleep 5

echo "Installed properT chaincode to peer1.resgistrarCompany.properT.com"

echo "Installing properT chaincode to peer0.financialCompany.properT.com..."
# Install code on financialCompany peer
docker exec -e "CORE_PEER_LOCALMSPID=FinancialCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/financialCompany.properT.com/users/Admin@financialCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.financialCompany.properT.com:7051" cli peer chaincode install -n properT -v 1.0 -p github.com/properT/go -l golang

sleep 5

echo "Installed properT chaincode to peer0.financialCompany.properT.com"

echo "Installing properT chaincode to peer1.financialCompany.properT.com..."
# Install code on financialCompany peer
docker exec -e "CORE_PEER_LOCALMSPID=FinancialCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/financialCompany.properT.com/users/Admin@financialCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer1.financialCompany.properT.com:7051" cli peer chaincode install -n properT -v 1.0 -p github.com/properT/go -l golang

sleep 5

echo "Installed properT chaincode to peer1.financialCompany.properT.com"


echo "Instantiating properT chaincode.."

docker exec -e "CORE_PEER_LOCALMSPID=KYCCompanyMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/kycCompany.properT.com/users/Admin@kycCompany.properT.com/msp" -e "CORE_PEER_ADDRESS=peer0.kycCompany.properT.com:7051" cli peer chaincode instantiate -o orderer.properT.com:7050 -C mychannel -n properT -l golang -v 1.0 -c '{"Args":[""]}' -P "OR ('KYCCompanyMSP.member','ResgistrarCompanyMSP.member','FinancialCompanyMSP.member')"

echo "Instantiated properT chaincode."

echo "Following is the docker network....."

docker ps
