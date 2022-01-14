package com.company.finalProject;
import java.util.Scanner;

public class FruitVeg extends Plant{
    public static final Scanner input = new Scanner(System.in);
    private boolean eatenByAnimal;

    public FruitVeg(){
        super();
        eatenByAnimal = false;
    }

    public FruitVeg(String n, String s, String g, int water){
        super(n, s, g, water);
        eatenByAnimal = false;
    }

    @Override
    public String tendMenu(){
        return super.tendMenu() + "\n4. Check if there is/are any " + species + "(s) to collect from the mother plant";
    }

    public boolean collectFood(){
        int chance = randomNumber(1, 10);
        boolean success = false;

        System.out.println("Let's try collect some " + species + "(s)!" 
        + "\n\nYou approached the mother plant, and checked for any fruits or veggies that could be collected. Hit enter to see if there are!");
        input.nextLine();

        if(chance <= 7){ //collection is successful
            System.out.println("You were able to find and collect " + super.randomNumber(1,4) + " " + species + "(s)." 
            + "\n\nI guess we're having " + species + " pasta for dinner tonight!");
            success = true;
        }

        else { //collection was not successful
            System.out.println("Unfortunately, there was/were no " + species + "(s) on the mother plant. Check back at a later time!");
        }

        return success;

    }

    public void dailyEvent(){
        String []animals = {"squirrel", "fox", "raccoon", "fox", "rabbit"};
        super.dailyEvent();
        if(randomNumber(1,50)% 4 == 0 && !eatenByAnimal){
            eatenByAnimal = true;
            plantInfo+= "\nThis plant is currently suffering from moderate damage as a result of being partially eaten by a " 
            + animals[randomNumber(0,4)];
        }

        if(plantAlive && eatenByAnimal && randomNumber(1,20) % 3 == 0){
            System.out.println("\nAfter another deadly " + animals[randomNumber(0,4)] + " attack, " + name + " succumbed to its injuries and died.");
            plantAlive = false;
        }

    }

    public boolean getAnimalEaten(){
        return eatenByAnimal;
    }
    // public boolean selfGerminate(){
    //     if(randomNumber(1,100) % 9 == 0){
    //         System.out.println()
    //         return true;
    //     }
    //     return false;
    // }
}
