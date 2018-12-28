package com.cargo.ships.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Ship {

    private long id;
    private int built;
    private String name;
    private float lengthMeters;
    private float beamMeters;
    private int maxTEU;
    private String owner;
    @JsonSerialize(using = TonnageSerializer.class)
    private String grossTonnage;

    public Ship() { }

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
