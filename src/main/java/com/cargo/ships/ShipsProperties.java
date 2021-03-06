package com.cargo.ships;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;


/**
 * This class is used for definition of a custom application property
 * used for providing information about the location of the ships JSON file.
 *
 * The property should be set in the application.properties file
 * and the JSON file must be located at a shipsFileName relative to the resources directory.
 */
@ConfigurationProperties(prefix = "app.ships")
@Validated
public class ShipsProperties {

    @Pattern(regexp = ".+(\\.json)$")
    private String shipsFileName;

    public String getShipsFileName() {
        return shipsFileName;
    }

    public void setShipsFileName(String shipsFileName) {
        this.shipsFileName = shipsFileName;
    }

}
