package com.cargo.ships;

import com.cargo.ships.domain.Ship;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides useful static methods for filtering and finding ships in
 * both serialized and deserialized forms.
 *
 * @author Stoimen Stoimenov
 */
class ShipsTestUtil {

    static Ship[] filterShipsByOwner(Ship[] ships, String owner) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getOwner().equals(owner)) {
                result.add(ship);
            }
        }
        return result.toArray(new Ship[0]);
    }

    static JSONArray filterJsonShipsByOwner(JSONArray ships, String owner) throws JSONException {
        JSONArray expected = new JSONArray();
        for (int i = 0; i < ships.length(); i++) {
            JSONObject ship = ships.getJSONObject(i);
            if (ship.getString("owner").equals(owner)) {
                expected.put(ship);
            }
        }
        return expected;
    }

    static Ship findShipById(Ship[] ships, long id) {
        for (Ship ship : ships) {
            if (ship.getId() == id) {
                return ship;
            }
        }
        return null;
    }

    static JSONObject findJsonShipById(JSONArray ships, long id) throws JSONException {
        for (int i = 0; i < ships.length(); i++) {
            JSONObject ship = ships.getJSONObject(i);
            if (ship.getInt("id") == id) {
                return ship;
            }
        }
        return null;
    }

}
