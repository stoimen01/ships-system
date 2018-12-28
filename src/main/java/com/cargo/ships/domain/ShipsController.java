package com.cargo.ships.domain;

import com.cargo.ships.data.ShipsStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ShipsController {

    private final ShipsStore store;

    public ShipsController(ShipsStore store) {
        this.store = store;
    }

    @RequestMapping(value = "/ships", method = GET)
    public ResponseEntity<Ship[]> ships() {
        return ResponseEntity.ok(store.allShips());
    }

    @RequestMapping(value = "/ships/{id}", method = GET)
    public ResponseEntity<Ship> shipById(@PathVariable long id) {
        Ship ship = store.getShip(id);
        if (ship == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(ship);
    }

    @RequestMapping(value = "/ships/owner/{owner}", method = GET)
    public ResponseEntity<Ship[]> shipsByOwner(@PathVariable String owner) {
        return ResponseEntity.ok(store.shipsByOwner(owner));
    }

}
