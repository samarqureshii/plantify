package com.company.finalProject;

import java.util.Scanner;

public class Tree extends Plant{
    public static final Scanner input = new Scanner(System.in);

    private boolean cold;

    public Tree(){
        super();
        cold = false;

    }

    public Tree(String n, String s, String g, int water){
        super(n, s, g, water);
        cold = false;
    }

    @Override
    public String tendMenu(){
        return super.tendMenu() + "\n4. Collect foliage from tree";
    }

    public boolean collectFoliage(){
        int chance = randomNumber(1, 10);
        int change = randomNumber(1,5);
        boolean success = false;

        System.out.println("Let's try collect some foliage off this " + genus + " " + species + "!" 
        + "\n\nYou approached the tree, and attempted to pick off some foliage. Hit enter to see if you were successful!");
        input.nextLine();

        if(chance <= 6){ //collection is successful
            System.out.println("After " + randomNumber(4,16) + " minutes, you were able to gain the courage to collect a small sample from the tree!" 
            + "\n\nThis will be a valuable addition to your scrapbook.\n+" + change + " reputation");
            rep+=change;
            success = true;
        }

        else { //collection was not successful
            System.out.println("After " + randomNumber(4,16) + " minutes, you were not able to collect any foliage. Better luck next time!");
        }

        return success;

    }

    @Override
    public void dailyEvent(){
        super.dailyEvent();
        if(randomNumber(1,50)%5==0 && !cold){
            cold = true;
            plantInfo += "\nThis plant is currently very cold, and is dropping leaves.";
        }

        if(cold && plantAlive &&randomNumber(1,20) % 4 == 0 ){
            System.out.println("After holding up for a while, " + name + " finally succumbed to the cold.");
            plantAlive = false;
        }
    }
    
    public boolean getCold(){
        return cold;
    }
}
