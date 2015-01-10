# Simple-Chat-Server 

Simple chat server for G54SQM module

`Requires JRE 1.7`

Tested on MAC OSX but should be compatable with all java supported OS

Chat server will run on port 9000, mutliple client connecitons can be made using
a supported chat client applicaiton or telenet

The following commands are accepted: 

1. `STAT`
1. `IDEN <username>`
1. `LIST`
1. `MESG <username> <message>`
1. `HAIl <broadbcast message>`
1. `QUIT`