rm -R crypto-config/*

../bin/cryptogen generate --config=crypto-config.yaml

rm config/*

../bin/configtxgen -profile InsuranceOrgOrdererGenesis -outputBlock ./config/genesis.block

../bin/configtxgen -profile InsuranceOrgChannel -outputCreateChannelTx ./config/channel.tx -channelID mychannel
