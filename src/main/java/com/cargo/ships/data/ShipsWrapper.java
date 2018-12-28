package com.cargo.ships.data;

import com.cargo.ships.ShipsProperties;
import com.cargo.ships.domain.Ship;

/**
 * This class is used for deserialization of the JSON data contained in
 * the JSON file pointed by {@link ShipsProperties#getPath()}.
 *
 * @author Stoimen Stoimenov
 */
public class ShipsWrapper {

    private Ship[] ships;

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }

}
