package io.github.bmd007.workshop.webauthn.server.dto;

import lombok.Value;

@Value
public class StartRegistrationActions {
    String finish = "https://localhost.localdomain/register/finish";
}