package io.github.bmd007.workshop.webauthn.server.resource;

import com.yubico.webauthn.data.AuthenticatorAttachment;
import com.yubico.webauthn.data.ByteArray;
import io.github.bmd007.workshop.webauthn.server.dto.AssertionRequestWrapper;
import io.github.bmd007.workshop.webauthn.server.dto.AssertionResponse;
import io.github.bmd007.workshop.webauthn.server.dto.RegistrationRequest;
import io.github.bmd007.workshop.webauthn.server.dto.RegistrationResponse;
import io.github.bmd007.workshop.webauthn.server.dto.SuccessfulAuthenticationResult;
import io.github.bmd007.workshop.webauthn.server.dto.SuccessfulRegistrationResult;
import io.github.bmd007.workshop.webauthn.server.service.WebAuthNService;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = {
        "http://localhost",
        "http://localhost:3000",
        "https://localhost",
        "https://localhost:3000",
}, originPatterns = {"https://*.localhost"})
@Slf4j
@RestController
@RequestMapping("v1")
public class WebAuthNResource {
    //todo use rsocket instead of rest ==> rename the rest style path to verb style path
    private final WebAuthNService webAuthNService;

    public WebAuthNResource(WebAuthNService webAuthNService) {
        this.webAuthNService = webAuthNService;
    }

    public record RegisterRequestBody(@NotBlank String displayName,
                                      @NotBlank String credentialNickname,
                                      @NonNull AuthenticatorAttachment authenticatorAttachment) {
    }

    @GetMapping
    public String hello() {
        return "Hello, WebAuthN!";
    }

    @DeleteMapping
    public void deleteAll() {
        webAuthNService.deleteAll();
    }

    @PostMapping("credentials/registrations/requests")
    public RegistrationRequest startRegistration(@RequestBody RegisterRequestBody registerRequest, @RequestParam String validatedUsername) {
        return webAuthNService.startRegistration(
                validatedUsername,
                registerRequest.displayName,
                Optional.ofNullable(registerRequest.credentialNickname),
                registerRequest.authenticatorAttachment);
    }

//    @PostMapping("credential/cross-platform")
//    public RegistrationRequest startRegistrationCrossPlatform(@RequestBody RegisterRequestBody registerRequest,
//                                                              @RequestParam String validatedUsername) {
//        return webAuthNService.startRegistration(
//                validatedUsername,
//                registerRequest.displayName,
//                Optional.ofNullable(registerRequest.credentialNickname),
//                AuthenticatorAttachment.CROSS_PLATFORM);
//    }

    @PostMapping("credentials/registrations/results")
    public SuccessfulRegistrationResult finishRegistration(@RequestBody RegistrationResponse registrationResponse) {
        return webAuthNService.finishRegistration(registrationResponse);
    }

    public record AuthenticateRequestBody(@Nullable String username, @Nullable ByteArray userHandle) {
    }

    @PostMapping(value = "authentications/assertions/requests")
    public AssertionRequestWrapper startAuthentication(@RequestBody AuthenticateRequestBody authenticateRequestBody) {
        return Optional.ofNullable(authenticateRequestBody.username())
                .map(webAuthNService::startAuthentication)
                .orElseGet(() ->
                        Optional.ofNullable(authenticateRequestBody.userHandle)
                                .map(webAuthNService::startAuthentication)
                                .orElseGet(webAuthNService::startAuthentication)
                );
    }

    @PostMapping(value = "authentications/results")
    public SuccessfulAuthenticationResult finishAuthentication(@RequestBody AssertionResponse assertionResponse) {
        return webAuthNService.finishAuthentication(assertionResponse);
    }
}
