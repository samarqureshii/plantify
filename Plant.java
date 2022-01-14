package com.company.finalProject;

import java.text.NumberFormat;
import java.util.Random;

public class Plant {
    protected String name;
    protected String species;
    protected String genus;
    protected int waterLevel;
    protected boolean plantAlive;
    protected String plantInfo;
    public static final NumberFormat percent = NumberFormat.getPercentInstance();

    public Plant(){
        name = species = genus = "";
        waterLevel = 100;
        plantAlive = true;
        plantInfo = "";
    }

    public Plant(String n, String s, String g, int water){
        name = n;
        species = s;
        genus = g;
        waterLevel = water;
        plantAlive = true;
        plantInfo = "";     
    }

    public boolean isAlive(){
        return plantAlive;
    }

    public void addInfo(String i){
        plantInfo += i;
    }

    public String getInfo(){
        return plantInfo;
    }

    public void setName(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    public void setSpecies(String s){
        species = s;
    }

    public String getSpecies(){
        return species;
    }

    public void setGenus(String g){
        genus = g;
    }

    public String getGenus(){
        return genus;
    }

    public int getWaterLevel(){
        return waterLevel;
    }

    public void setWaterLevel(int w){
        waterLevel = w;
    }

    public String toString(){
        return "\nUser given plant name: " + name + 
                "\nSpecies: " + species +
                "\nGenus: " + genus +
                "\nCurrent hydration/humidity level: " + percent.format((double)waterLevel/100) +
                "\nAdditional information↓" + plantInfo;
    }

    public String tendMenu(){
        return "1. Water plant \n2. See/modify plant info \n3. Rename plant";
    }

    public boolean equals(Plant plant1){ //checks if two plants are equal to one another
        if(species.equals(plant1.getSpecies()) && genus.equals(plant1.getGenus()) && name.equals(plant1.getName())){
            return true;
        }

        return false;
    }

    public void dailyEvent(){
        String [] causes = {"\nYour " + species + " " + genus + " (" + name + ") was accidentally left exposed to direct sunlight and has died!",
                        "\nYour dog accidentally ate your " + species + " " + genus + " (" + name + ") and it has died!",
                        "\nYou accidentally stepped on your " + species + " " + genus + " (" + name + ") and it has died!"};
        waterLevel-=randomNumber(3, 10); //water level randomly changes

        if(randomNumber(1,150) > 140){
            System.out.println(causes[randomNumber(0,2)]);
            plantAlive = false;
        }
    }

    public void checkPlantNeglect(){
        String[] deathMess = {"You are certainly an awful plant parent!", 
                                "" + genus + " type plants seem quite the challenge for you!", 
                                "Do you even deserve to care for " + genus + " type plants anymore?",
                                "What a shame."};
        if(waterLevel < 20 && waterLevel > 10 && plantAlive){
            System.out.println("\n" + name + " (" + genus + " " + species + ") is a little thirsty. Make sure to water " + name + "!");
        }

        else if(waterLevel <= 10 && plantAlive){
            System.out.println("\n" + name + " (" + genus + " " + species + ") died from underwatering, and has been removed from your collection!\n" + deathMess[randomNumber(0,3)]);
            plantAlive = false;
        }

        else if(waterLevel > 90 && waterLevel < 100 && plantAlive){
            System.out.println("\n" + name + " (" + genus + " " + species + ") is a little overhydrated. Relax with the watering!");
        }

        else if(waterLevel >= 100 && plantAlive){
            System.out.println("\n" + name + " (" + genus + " " + species + ") died from overwatering, and has been removed from your collection!\n" + deathMess[randomNumber(0,3)]);
            plantAlive = false;
        }

    }

    /*
    *Random number generator method
    *Pre: Takes in the minimum and maximum constraints
    *Post: Returns a random number
    */
    public int randomNumber(int min, int max){
        Random rand = new Random();
        return (min + rand.nextInt(Math.abs(max-min+1)));
    }

    public boolean getRootRot(){
        return false;
    }
}
