package com.cargo.ships.data;

import com.cargo.ships.domain.Ship;

/**
 * An object that provides information about the available ships in the system.
 *
 * This interface is implemented by the {@link ShipsFileStore} class up to this moment.
 *
 * @author Stoimen Stoimenov
 */
public interface ShipsStore {

    /**
     * Provides all available ships in the system.
     *
     * @return an array of the available ships
     */
    Ship[] allShips();

    /**
     * Provides a ship specified by a given id.
     *
     * @param id the id of the ship to be returned.
     * @return the ship specified by the given id or
     *         {@code null} if such a ship is not found.
     */
    Ship getShip(long id);

    /**
     * Provides all available ships owned by a given owner.
     *
     * @param owner the owner of the ships to be returned
     * @return an array of the ships owned by a given owner
     *         or an empty if such ships are not found
     */
    Ship[] shipsByOwner(String owner);

}
