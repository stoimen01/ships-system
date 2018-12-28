package com.cargo.ships.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This class represents a POJO containing
 * all available information for a given ship.
 *
 * @author Stoimen Stoimenov
 */
public class Ship {

    // The unique id of the ship.
    private long id;

    // The year when the ship was built.
    private int built;

    // The name of the ship.
    private String name;

    // The length of the ship in meters.
    private float lengthMeters;

    // The length of the beam of the ship in meters.
    private float beamMeters;

    // The maximum cargo capacity of the ship.
    private int maxTEU;

    // The name of the ship's owner.
    private String owner;

    // The gross weight of the ship represented as String because it might not be available.
    @JsonSerialize(using = TonnageSerializer.class)
    private String grossTonnage;

    /**
     * Default constructor to be used by {@link com.fasterxml.jackson.databind.ObjectMapper}
     * to deserialize this object from JSON.
     */
    public Ship() { }

    /**
     * Copy constructor.
     *
     * @param ship the ship to be copied.
     */
    public Ship(Ship ship) {
        this.id = ship.getId();
        this.built = ship.getBuilt();
        this.name = ship.getName();
        this.lengthMeters = ship.getLengthMeters();
        this.beamMeters = ship.getBeamMeters();
        this.maxTEU = ship.getMaxTEU();
        this.owner = ship.getOwner();
        this.grossTonnage = ship.getGrossTonnage();
    }

    /* Standard getters and setters. */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBuilt() {
        return built;
    }

    public void setBuilt(int built) {
        this.built = built;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLengthMeters() {
        return lengthMeters;
    }

    public void setLengthMeters(float lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public float getBeamMeters() {
        return beamMeters;
    }

    public void setBeamMeters(float beamMeters) {
        this.beamMeters = beamMeters;
    }

    public int getMaxTEU() {
        return maxTEU;
    }

    public void setMaxTEU(int maxTEU) {
        this.maxTEU = maxTEU;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGrossTonnage() {
        return grossTonnage;
    }

    public void setGrossTonnage(String grossTonnage) {
        this.grossTonnage = grossTonnage;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", built=" + built +
                ", name='" + name + '\'' +
                ", lengthMeters=" + lengthMeters +
                ", beamMeters=" + beamMeters +
                ", maxTEU=" + maxTEU +
                ", owner='" + owner + '\'' +
                ", grossTonnage='" + grossTonnage + '\'' +
                '}';
    }

}
