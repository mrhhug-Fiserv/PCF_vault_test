package com.fiserv.hug.vaultTester;

/**
 * @author michael.hug@fiserv.com
 * Fiserv Internal Software
 */
public class TreasureHunt {
    private String startingCity;
    private String numberOfPaces;

    public TreasureHunt() {
        //jackson needs the default constructor
    }

    public TreasureHunt(String startingCity, String numberOfPaces) {
        this.startingCity = startingCity;
        this.numberOfPaces = numberOfPaces;
    }

    public String getStartingCity() {
        return startingCity;
    }

    public String getNumberOfPaces() {
        return numberOfPaces;
    }

    @Override
    public String toString() {
        return "TreasureHunt{" + "startingCity=" + startingCity + ", numberOfPaces=" + numberOfPaces + '}';
    }
}