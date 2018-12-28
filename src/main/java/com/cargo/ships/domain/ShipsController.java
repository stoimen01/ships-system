package com.cargo.ships.domain;

import com.cargo.ships.data.ShipsStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * This class is used for handling all requests for information about ships.
 * It uses an instance of {@link ShipsStore} in order to retrieve the needed data.
 *
 * Every method in this class returns {@link ResponseEntity} containing the
 * requested information as entity objects. In turn all information is
 * serialized to JSON and put in the response body before it is sent back to the client.
 *
 * @author Stoimen Stoimenov
 */
@RestController
public class ShipsController {

    private final ShipsStore store;

    /**
     * Constructor used for injecting data provider.
     *
     * @param store an instance of {@link ShipsStore}
     *              to be used for data provisioning.
     */
    public ShipsController(ShipsStore store) {
        this.store = store;
    }

    /**
     * Provides response with all of the available ships in the system.
     *
     * @return an {@link ResponseEntity} containing
     *         array of all available {@link Ship} instances.
     */
    @RequestMapping(value = "/ships", method = GET)
    public ResponseEntity<Ship[]> ships() {
        return ResponseEntity.ok(store.allShips());
    }

    /**
     * Provides response containing information about a single ship specified by a given id.
     * If the ship is not available {@link HttpStatus#BAD_REQUEST} is sent back.
     *
     * @param id the id of the ship to be provided.
     * @return an {@link ResponseEntity} containing the specified Ship object.
     */
    @RequestMapping(value = "/ships/{id}", method = GET)
    public ResponseEntity<Ship> shipById(@PathVariable long id) {
        Ship ship = store.getShip(id);
        if (ship == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(ship);
    }

    /**
     * Provides response with all of the available ships owned by specified owner.
     * @param owner the owner of the ships to be provided
     * @return an {@link ResponseEntity} containing array of
     *         all ships owned by the specified owner.
     */
    @RequestMapping(value = "/ships/owner/{owner}", method = GET)
    public ResponseEntity<Ship[]> shipsByOwner(@PathVariable String owner) {
        return ResponseEntity.ok(store.shipsByOwner(owner));
    }

}
