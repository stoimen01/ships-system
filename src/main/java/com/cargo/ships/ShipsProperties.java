package com.cargo.ships;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;

@ConfigurationProperties(prefix = "app.ships")
@Validated
public class ShipsProperties {

    @Pattern(regexp = ".+(.json)$")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
