rm -R crypto-config/*

../bin/cryptogen generate --config=crypto-config.yaml

rm config/*

../bin/configtxgen -profile ProperTOrgOrdererGenesis -outputBlock ./config/genesis.block

../bin/configtxgen -profile ProperTOrgChannel -outputCreateChannelTx ./config/channel.tx -channelID mychannel
