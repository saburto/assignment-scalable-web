package com.github.saburto.assignment.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.saburto.assignment.rest.DiffResponse.Detail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiffControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String base;

    @Autowired
    private TestRestTemplate template;

    private String id;

    @Before
    public void setUp() throws Exception {
        this.base = "http://localhost:" + port + "/";
        id = UUID.randomUUID().toString();
    }

    @After
    public void tearDown() throws Exception {
        deleteFile("RIGHT");
        deleteFile("LEFT");
    }

    @Test
    public void postTwoDataEqualsResponseIsEqualAndEqualSizeTrueAndDiffEmpty() throws Exception {

        postData("left", stringToBase64("hello"));
        postData("right", stringToBase64("hello"));

        ResponseEntity<DiffResponse> response = getDiffResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEqual()).isTrue();
        assertThat(response.getBody().isEqualSize()).isTrue();
        assertThat(response.getBody().getDiffs()).isEmpty();
    }

    @Test
    public void postTwoDataDifferentsSizeResponseIsEqualAndEqualSizeFalseAndDiffEmpty()
            throws Exception {
        postData("left", stringToBase64("hello"));
        postData("right", stringToBase64("hello!!"));

        ResponseEntity<DiffResponse> response = getDiffResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEqual()).isFalse();
        assertThat(response.getBody().isEqualSize()).isFalse();
        assertThat(response.getBody().getDiffs()).isEmpty();
    }

    @Test
    public void postTwoDataOneDifferenceButEqualSizeResponseIsEqualFalseEqualSizeTrueAndDiffNotEmpty()
            throws Exception {
        postData("left", stringToBase64("hello"));
        postData("right", stringToBase64("hella"));

        ResponseEntity<DiffResponse> response = getDiffResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEqual()).isFalse();
        assertThat(response.getBody().isEqualSize()).isTrue();
        assertThat(response.getBody().getDiffs()).isNotEmpty()
            .extracting(Detail::getIndex, Detail::getLength)
            .contains(tuple(4, 1));
    }

    @Test
    public void postTwoDataMultipleDifferenceButEqualSizeResponseIsEqualFalseEqualSizeTrueAndDiffNotEmpty()
            throws Exception {
        postData("left", stringToBase64("hello World"));
        postData("right", stringToBase64("hella Woood"));

        ResponseEntity<DiffResponse> response = getDiffResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEqual()).isFalse();
        assertThat(response.getBody().isEqualSize()).isTrue();
        assertThat(response.getBody().getDiffs()).isNotEmpty()
            .extracting(Detail::getIndex, Detail::getLength)
            .containsExactly(tuple(4, 1), tuple(8, 2));
    }

    @Test
    public void postTwoDataSameIdToLeftResponseError() throws Exception {
        String side = "left";
        postData(side, stringToBase64("hello"));

        ResponseEntity<String> response = excutePostData(side, stringToBase64("hello"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("already exists for side LEFT");
    }

    @Test
    public void postTwoDataSameIdToRighttResponseError() throws Exception {
        String side = "right";
        postData(side, stringToBase64("hello"));

        ResponseEntity<String> response = excutePostData(side, stringToBase64("hello"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("already exists for side RIGHT");
    }

    @Test
    public void getDiffWithNoDataUploadedResponseError() throws Exception {
        assertErrorResponseGetDiffContains("not exists yet for side LEFT");
    }

    @Test
    public void getDiffWithOnlyLeftDataUploadedResponseError() throws Exception {
        postData("left", stringToBase64("hello"));
        assertErrorResponseGetDiffContains("not exists yet for side RIGHT");
    }

    @Test
    public void getDiffWithOnlyRightDataUploadedResponseError() throws Exception {
        postData("right", stringToBase64("hello"));
        assertErrorResponseGetDiffContains("not exists yet for side LEFT");
    }

    private ResponseEntity<String> excutePostData(String side, String data) {
        String path = urlForPostDataSide(side);

        HttpHeaders headers = getContentJsonHeader();
        String json = "{\"data\":\"" + data + "\"}";
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);

        return template.exchange(path, HttpMethod.POST, httpEntity, String.class);
    }

    private HttpHeaders getContentJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private String urlForGetDiff() {
        return base.toString() + "v1/diff/" + id;
    }

    private void deleteFile(String side) throws IOException {
        Files.deleteIfExists(Paths.get(System.getProperty("java.io.tmpdir"), id + side + ".tmp"));
    }

    private String stringToBase64(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    private ResponseEntity<DiffResponse> getDiffResponse() {
        return template.getForEntity(urlForGetDiff(), DiffResponse.class);
    }

    private void postData(String side, String data) {
        ResponseEntity<String> response = excutePostData(side, data);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String urlForPostDataSide(String side) {
        return base.toString() + "v1/diff/" + id + "/" + side;
    }

    private void assertErrorResponseGetDiffContains(String message) {
        ResponseEntity<String> response = template.getForEntity(urlForGetDiff(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains(message);
    }
}
