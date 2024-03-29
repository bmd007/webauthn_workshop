package io.github.bmd007.workshop.webauthn.server.dto;

import lombok.Value;

@Value
public class StartAuthenticationActions {
    String finish = "https://localhost.localdomain/authenticate/finish";
}
