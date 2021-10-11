package com.restdoc.restassured;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@AutoConfigureRestDocs
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
public class RestAssuredControllerTest {

    private RequestSpecification documentationSpec;

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    public void sample() throws Exception {
        given(this.documentationSpec)
                .contentType("application/json")
                .filter(document("create",
                        preprocessRequest(prettyPrint(), modifyUris().scheme("http").host("localhost").port(8080)),
                        preprocessResponse(prettyPrint(), modifyUris().scheme("http").host("localhost").port(8080))
                ))

                .when()
                .body(objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(new Note(1L, "title", "details")))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .port(port)
                .post("/notes/create")

                .then()
                .assertThat().statusCode(201);

    }
}
