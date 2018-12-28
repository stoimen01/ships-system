package com.cargo.ships.data;

import com.cargo.ships.ShipsProperties;
import com.cargo.ships.domain.Ship;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

/**
 * This class implements {@link ShipsStore} by using data from JSON
 * file contained in the resources directory.
 *
 * It uses {@link ObjectMapper} to deserialize the file.
 *
 * @author Stoimen Stoimenov
 */
@Repository
public class ShipsFileStore implements ShipsStore {

    /**
     * Keeps all ships from the json file by id.
     */
    private final Map<Long, Ship> shipsMap;

    /**
     * Initializes {@link #shipsMap} containing all ships by id
     * from json file located by {@link ShipsProperties#getPath()}.
     *
     * @param props the properties to be used for access to the file path
     *
     * @throws IOException if a low-level error occurs while reading the file
     * @throws IllegalArgumentException if provided @{@link ShipsProperties} is null or
     * if it is containing wrong path or that path is pointed to invalid file
     */
    public ShipsFileStore(ShipsProperties props) throws IOException {

        if (props == null) {
            throw new IllegalArgumentException("ShipsProperties must not be null !");
        }

        String path = props.getPath();

        if (path == null || !path.endsWith(".json")) {
            throw new IllegalArgumentException("Path must not be null and must end with .json suffix !");
        }

        try {
            File file = new ClassPathResource(path).getFile();
            final ObjectMapper mapper = new ObjectMapper();
            Ship[] ships = mapper.readValue(file, ShipsWrapper.class).getShips();
            this.shipsMap = stream(ships).collect(toConcurrentMap(Ship::getId, s -> s));
        } catch (JsonParseException|JsonMappingException e) {
            throw new IllegalArgumentException("File must contain valid JSON structure !");
        }

    }

    /**
     * Returns array of all available ships or empty array if there are no ships.
     *
     * @return copy of all ships contained in the {@link #shipsMap}
     */
    @Override
    public Ship[] allShips() {
        return shipsMap.values().toArray(new Ship[0]);
    }

    /**
     * Returns a ship with specific id or {@code null} if such a ship is not found.
     *
     * @param id the id of the ship to be returned
     * @return the ship with the specified id or {@code null} if it is not found
     */
    @Override
    public Ship getShip(long id) {
        Ship ship = shipsMap.get(id);
        if (ship == null) {
            return null;
        }
        return new Ship(ship);
    }

    /**
     * Return array of all ships with specific owner or empty array if such ships are not found.
     *
     * @param owner the owner whose ships are to be returned
     * @return the ships with the specified owner
     */
    @Override
    public Ship[] shipsByOwner(String owner) {
        return shipsMap.values()
                .stream()
                .filter(s -> s.getOwner().equals(owner))
                .toArray(Ship[]::new);
    }

}
