package com.cargo.ships;

import com.cargo.ships.data.ShipsStore;
import com.cargo.ships.data.ShipsWrapper;
import com.cargo.ships.domain.Ship;
import com.cargo.ships.domain.ShipsController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static com.cargo.ships.ShipsTestUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This file contains all of the tests in regards to the {@link ShipsController}.
 *
 * The goal of the tests is to ensure that the controller behaves correctly
 * on different kinds of requests.
 *
 * In order to isolate the controller from the rest of the system mock {@link ShipsStore} is used.
 *
 * @author Stoimen Stoimenov
 */

@RunWith(SpringRunner.class)
@WebMvcTest(ShipsController.class)
public class ShipsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipsStore service;

    /* JSON array keeping information of all ships from the static json file. */
    private static JSONArray allJsonShips;

    /* Keeps all ships deserialized. */
    private static Ship[] allShips;

    @BeforeClass
    public static void initShips() throws Exception {
        File file = new ClassPathResource("ships.json").getFile();
        String array = JsonPath.read(file, "$.ships").toString();
        allJsonShips = new JSONArray(array);
        final ObjectMapper mapper = new ObjectMapper();
        allShips = mapper.readValue(file, ShipsWrapper.class).getShips();
    }

    /* Tests if the controller returns empty json array when the store provides no ships. */
    @Test
    public void shouldReturnEmptyArray() throws Exception {
        when(service.allShips()).thenReturn(new Ship[0]);

        mockMvc.perform(get("/ships"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, times(1)).allShips();
    }

    /* Tests if the controller returns all ships in the same form as provided by the store. */
    @Test
    public void shouldReturnAllShips() throws Exception {
        when(service.allShips()).thenReturn(allShips);

        String bodyContent = mockMvc.perform(get("/ships"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).allShips();

        JSONAssert.assertEquals(allJsonShips, new JSONArray(bodyContent), false);
    }

    /* Tests if the controller returns empty json array when asked for wrong owners' ships. */
    @Test
    public void shouldReturnEmptyShipsWhenOwnerIsWrong() throws Exception {
        String wrongOwner = "Mitsui O.S.K. Trust";
        when(service.shipsByOwner(wrongOwner)).thenReturn(new Ship[0]);

        mockMvc.perform(get("/ships/owner/" + wrongOwner))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, times(1)).shipsByOwner(wrongOwner);
    }

    /* Tests if the controller returns the same ships as the ships provided by the store for given owner. */
    @Test
    public void shouldReturnAllShipsByOwner() throws Exception {
        String owner = "Mitsui O.S.K. Lines";
        when(service.shipsByOwner(owner)).thenReturn(filterShipsByOwner(allShips, owner));

        String bodyContent = mockMvc.perform(get("/ships/owner/" + owner))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).shipsByOwner(owner);

        JSONArray expected = filterJsonShipsByOwner(allJsonShips, owner);
        JSONAssert.assertEquals(expected, new JSONArray(bodyContent), false);
    }

    /* Tests if the controller returns the same ship by id as provided by the store. */
    @Test
    public void shouldReturnCorrectShipById() throws Exception {
        long shipId = 15;
        when(service.getShip(shipId)).thenReturn(findShipById(allShips, shipId));

        String body = mockMvc.perform(get("/ships/" + shipId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).getShip(shipId);

        JSONObject expected = findJsonShipById(allJsonShips, shipId);
        JSONAssert.assertEquals(expected, new JSONObject(body), false);
    }

    /* Tests if the controller returns BAD_REQUEST when asked for ship with wrong id. */
    @Test
    public void shouldReturnBadRequestOnInvalidId() throws Exception {
        long invalidId = 0;
        when(service.getShip(invalidId)).thenReturn(null);

        mockMvc.perform(get("/ships/" + invalidId))
                .andExpect(status().isBadRequest());
    }

}
