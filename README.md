# Wonderland https://wonderland-007.web.app

### Stack:
 * Java
 * Spring family
 * Flutter
 * RSocket, AMQP, STOMP, HTTP
 * Firebase, GCP
 * Docker
 * Javascript, Typescript, Dart

# start locally:
## certificate
```shell
echo '127.0.0.1 local.bmd007.github.io' | sudo tee -a /etc/hosts
brew install mkcert
mkcert local.bmd007.github.io '*.local.bmd007.github.io' localhost 127.0.0.1 ::1
mkcert -install
openssl pkcs12 -export -in local.bmd007.github.io+4.pem -inkey local.bmd007.github.io+4-key.pem -out keystore.p12 -name localdev
``` 
