package com.cargo.ships;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;

import static com.cargo.ships.ShipsTestUtil.filterJsonShipsByOwner;
import static com.cargo.ships.ShipsTestUtil.findJsonShipById;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

/**
 * The goal of the following tests is to ensure that the different endpoints
 * are behaving accordingly and return correct data in real conditions.
 *
 * In order to achieve the goals real web server is used and all
 * data assertions are done against the original json resource file.
 *
 * @author Stoimen Stoimenov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
public class ShipsApplicationTests {

    /* Test client used to query the server. */
    @Autowired
    private WebTestClient webTestClient;

    /* JSON array keeping information of all ships from the static json file. */
    private static JSONArray allShips;

    @BeforeClass
    public static void initExpectedJsonFile() throws Exception {
        File file = new ClassPathResource("ships.json").getFile();
        String array = JsonPath.read(file, "$.ships").toString();
        allShips = new JSONArray(array);
    }

    /* Tests if the /ships endpoint returns all available ships from the file. */
    @Test
    public void shipsReturnsAllShips() throws Exception {

        byte[] responseBody = webTestClient.get().uri("/ships")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$").isArray()
                .returnResult()
                .getResponseBody();

        JSONArray data = new JSONArray(new String(responseBody));
        JSONAssert.assertEquals(allShips, data, false);
    }

    /* Tests if the /ship/owner/{owner name} endpoint returns all of the owners' ships. */
    @Test
    public void shipsByOwnerReturnsCorrectData() throws Exception {

        String owner = "OOCL Germany";

        byte[] responseBody = webTestClient.get().uri("/ships/owner/" + owner)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$").isArray()
                .returnResult()
                .getResponseBody();

        JSONArray expected = filterJsonShipsByOwner(allShips, owner);
        JSONArray data = new JSONArray(new String(responseBody));
        JSONAssert.assertEquals(expected, data, false);
    }

    /* Tests if the /ship/owner/{owner name} endpoint returns empty array when wrong owner is given. */
    @Test
    public void shipsByOwnerReturnsEmptyArray() throws Exception {

        String owner = "OOCL Line";

        byte[] responseBody = webTestClient.get().uri("/ships/owner/" + owner)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$").isArray()
                .returnResult()
                .getResponseBody();

        JSONAssert.assertEquals("[]", new String(responseBody), false);
    }

    /* Tests if the /ships/{id} endpoint returns the correct ship information. */
    @Test
    public void shipByIdReturnsCorrectData() throws Exception {

        int shipId = 9;

        byte[] responseBody = webTestClient.get().uri("/ships/" + shipId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$").exists()
                .returnResult()
                .getResponseBody();

        JSONObject expected = findJsonShipById(allShips, shipId);
        JSONObject data = new JSONObject(new String(responseBody));
        JSONAssert.assertEquals(expected, data, false);
    }

    /* Tests if the /ships/{id} endpoint returns BAD_REQUEST code when invalid id is given. */
    @Test
    public void shipByIdReturnsBadRequestOnInvalidId() throws Exception {

        int shipId = 0;

        webTestClient.get().uri("/ships/" + shipId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isBadRequest();
    }

}