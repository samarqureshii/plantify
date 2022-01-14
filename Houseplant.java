package com.company.finalProject;

import java.util.Random;
import java.util.Scanner;

public class Houseplant extends Plant{
    public static Random rand = new Random();
    public static Scanner input = new Scanner(System.in);
    private boolean rootRot, gnatInfestation, spiderInfestation;

    public Houseplant(){
        super();
        rootRot = gnatInfestation = spiderInfestation = false;
    }

    public Houseplant(String n, String s, String g, int water){
        super(n, s, g, water);
        rootRot = gnatInfestation = spiderInfestation = false;
    }

    public boolean propagate(){
        int chance = randomNumber(1, 10);
        boolean success = false;

        System.out.println("Let's try to propagate this " + genus + " " + species + "!" 
        + "\n\nYou took a cutting, and placed it in water. Hit enter to see what happened!");
        input.nextLine();

        if(chance <= 7){ //propagation is successful
            System.out.println("After " + randomNumber(4,16) + " weeks, there appears to be a small rot! You now have a new plant!");
            success = true;
        }

        else { //propgation was not successful
            System.out.println("After " + randomNumber(4,16) + " weeks, all the leaves of the " + species + " died, and so did the propagation attempt. \n\nBetter luck next time!");
        }

        return success;

    }

    @Override
    public String tendMenu(){
        return super.tendMenu() + "\n4. Attempt to propagate plant";
    }

    public void dailyEvent(){
        super.dailyEvent();
        if(randomNumber(1,50) % 5 == 0 && !rootRot){
            rootRot = true;
            plantInfo += "\nThis plant is currently suffering from root rot.";
        }

        if(randomNumber(1,50) % 6 == 0 && !gnatInfestation){
            gnatInfestation = true;
            plantInfo += "\nThis plant is currently suffering from a fungus gnat infestation.";
        }

        if(randomNumber(1,50) % 4 == 0 && !spiderInfestation){
            spiderInfestation = true;
            plantInfo += "\nThis plant is currently suffering from a spider mite infestation.";
        }

        if(rootRot || gnatInfestation || spiderInfestation && randomNumber(1,20) % 3 == 0 && plantAlive){
            //cases where the plant actually died from the issue

            if(rootRot && plantAlive){
                System.out.println("\nIt appears that root rot got the best of " + name + 
                ", and your plant succumbed.");
                plantAlive = false;
            }

            else if(gnatInfestation && plantAlive){
                System.out.println("\n" + name + " has died from a severe fungus gnat infestation!");
                plantAlive = false;
            }

            else if(spiderInfestation && plantAlive){
                System.out.println("\n" + name + " was crawling with spider mites, and has died!");
                plantAlive = false;
            }
        }

        
    }

    @Override
    public boolean getRootRot(){
        return rootRot;
    }

    //@Override
    public boolean getFungusGnat(){
        return gnatInfestation;
    }

    public boolean getSpider(){
        return spiderInfestation;
    }
}
