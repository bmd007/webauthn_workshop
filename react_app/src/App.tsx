import React from 'react';
import logo from './logo.svg';
import './App.css';
import {create} from "@github/webauthn-json";

function App() {

    async function getHello() {
        const response = await fetch('http://localhost:8080/v1');
        const data = await response.text();
        alert(data);
    }

    async function registerWebAuthnOnThisDevice() {
        const registerRequestBody = {
            displayName: 'mahdi',
            credentialNickname: 'this-device-credential-123',
            authenticatorAttachment: "platform" // or "cross-platform" based on your requirement
        };
        const registrationRequestResponseBody = (await fetch('https://localhost:8080/v1/credentials/registrations/requests', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(registerRequestBody)
        }));
        const registrationRequest = await registrationRequestResponseBody.json();
        const options = {
            publicKey: registrationRequest.publicKeyCredentialCreationOptions,
        };
        const credential = await create(options);
        const registrationResultResponseBody = await fetch('https://localhost:8080/v1/credentials/registrations/results', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({requestId: registrationRequest.requestId, credential})
        });
        const finishRegistrationResponse = await registrationResultResponseBody.json();
        if (finishRegistrationResponse.success && finishRegistrationResponse.username) {
            alert(`Enabled password less login for ${finishRegistrationResponse.username}`);
        } else {
            alert(`Failed to enable password less login for ${finishRegistrationResponse.username}`);
        }
    }

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                <button onClick={getHello}>Hello</button>
                <button onClick={registerWebAuthnOnThisDevice}>registerWebAuthnOnThisDevice</button>
            </header>
        </div>
    );
}

export default App;
