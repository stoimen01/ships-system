package com.cargo.ships;

import com.cargo.ships.data.ShipsFileStore;
import com.cargo.ships.data.ShipsStore;
import com.cargo.ships.data.ShipsWrapper;
import com.cargo.ships.domain.Ship;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static com.cargo.ships.ShipsTestUtil.filterShipsByOwner;
import static com.cargo.ships.ShipsTestUtil.findShipById;
import static org.junit.Assert.*;

/**
 * This class contains tests whose goal is to ensure that the
 * {@link ShipsFileStore} implementation behaves correctly and
 * respects the {@link ShipsStore} contact.
 *
 * @author Stoimen Stoimenov
 */
@RunWith(SpringRunner.class)
public class ShipsFileStoreTest {

    @TestConfiguration
    static class ShipsFileStoreConfig {
        @Bean
        public ShipsStore store() throws IOException {
            ShipsProperties props = new ShipsProperties();
            props.setPath("ships.json");
            return new ShipsFileStore(props);
        }
    }

    @Autowired
    private ShipsStore store;

    /* Keeps all ships from the json file deserialized. */
    private static Ship[] allShips;

    @BeforeClass
    public static void initShips() throws Exception {
        File file = new ClassPathResource("ships.json").getFile();
        allShips = new ObjectMapper().readValue(file, ShipsWrapper.class).getShips();
    }

    /* This test ensures that all ships are serialized correctly. */
    @Test
    public void returnsAllShipsCorrectly() {
        assertArrayEquals(store.allShips(), allShips);
    }

    /* This test ensures that the store returns all ships owned by given owner correctly. */
    @Test
    public void returnsOwnerShipsCorrectly() {
        String owner = "MOL Triumph";
        assertArrayEquals(store.shipsByOwner(owner), filterShipsByOwner(allShips, owner));
    }

    /* This test ensures that the store returns empty array when wrong owner name is given. */
    @Test
    public void returnsEmptyArrayOnWrongOwner() {
        String wrongOwner = "MOL Wrong";
        assertArrayEquals(store.shipsByOwner(wrongOwner), new Ship[0]);
    }

    /* This class ensures that the store returns null when asked for ship with invalid ship id. */
    @Test
    public void returnsNullOnInvalidShipId() {
        assertNull(store.getShip(0));
    }

    /* This test ensures that the store returns the correct ship when valid ship id is provided. */
    @Test
    public void returnsCorrectShipById() {
        long id = 21;
        assertEquals(store.getShip(id), findShipById(allShips, id));
    }

}