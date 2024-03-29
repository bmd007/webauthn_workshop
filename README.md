# Webauthn workshop

In this repository is a playground for learning how to use Webauthn. 
The project contains a web application that allows users to register and authenticate using Webauthn. 
The backend built using Spring Boot.
In addition to the simple (reactJs) web application, there will be a Flutter application as client too.


# Start locally:
## certificate setup
```shell
echo '127.0.0.1 local.bmd007.github.io' | sudo tee -a /etc/hosts
brew install mkcert
mkcert -install
cd react_app/ssl/generated
mkcert local.bmd007.github.io '*.local.bmd007.github.io' localhost 127.0.0.1 ::1
#choose bmd007 for the password of keystore:
openssl pkcs12 -export -in local.bmd007.github.io+4.pem -inkey local.bmd007.github.io+4-key.pem -out keystore.p12 -name localdev
cp keystore.p12 ../../webauthn-server/src/main/resources/ssl/generated/keystore.p12
``` 

