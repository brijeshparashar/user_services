package com.demo.user.pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.demo.user.model.UserAddress;
import com.demo.user.model.UserDetails;
import com.google.gson.Gson;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConsumerContract {

    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("userProvider", "localhost", 8081, this);


    @Pact(provider = "userProvider",
            consumer = "userConsumer")
    public RequestResponsePact getUserPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        DslPart buildBody = LambdaDsl.newJsonBody(obj -> obj
                .stringType("title", "pactTitle")
                .stringType("gender", "pactGender")
                .stringType("firstName", "pactFirstName")
                .stringType("lastName", "pactLastName"))
                .build();

        return builder
                .given("get user")
                .uponReceiving("a request to the user retrieval resource")
                .path("/users/1000001")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(buildBody)
                .toPact();
    }

    @Pact(provider = "userProvider",
            consumer = "userConsumer")
    public RequestResponsePact updateUserPact(PactDslWithProvider builder) {
        Gson gson= new Gson();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return builder
                .given("update user")
                .uponReceiving("a request to the user save resource")
                .path("/users")
                .method("PUT")
                .body(gson.toJson(mockUser()))
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(LambdaDsl.newJsonBody(obj -> obj
                        .stringType("title", "Mr")
                        .stringType("firstName", "updatedFirstName")
                        .stringType("lastName", "updatedLastName")
                        .stringType("gender","male"))
                        .build())
                .toPact();
    }


    @Test
    @PactVerification(fragment = "getUserPact")
    public void verifyUserPact() {
        UserDetails userDetails = new RestTemplate().getForObject(mockProvider.getUrl()+"/users/1000001",UserDetails.class);
        assertNotNull(userDetails);
        assertThat(userDetails.getFirstName()).isEqualTo("pactFirstName");
        assertThat(userDetails.getLastName()).isEqualTo("pactLastName");
    }

    @Test
    @PactVerification(fragment = "updateUserPact")
    public void verifyUserUpdatePact() {
        Gson gson= new Gson();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<UserDetails> userDetailResponseEntity = new RestTemplate().exchange(mockProvider.getUrl()+"/users", HttpMethod.PUT,new HttpEntity<>(gson.toJson(mockUser()),headers),UserDetails.class);
        assertNotNull(userDetailResponseEntity);
        UserDetails userDetails = userDetailResponseEntity.getBody();
        assertThat(userDetails.getFirstName()).isEqualTo("updatedFirstName");
        assertThat(userDetails.getLastName()).isEqualTo("updatedLastName");
    }

    private UserDetails mockUser() {
        return UserDetails.builder()
                .firstName("mockFirstName")
                .lastName("mockLastname")
                .gender("male")
                .title("Mr")
                .address(mockAddress())
                .build();
    }

    private UserAddress mockAddress() {
        return UserAddress.builder()
                .city("mockCity")
                .postcode("2000")
                .state("mockState")
                .street("mockStreet")
                .build();
    }
}
