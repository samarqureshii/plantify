package com.company.finalProject;

import java.text.NumberFormat;
import java.util.Random;
import java.util.Scanner;

public class ICS4U_FP {

    public static Scanner input = new Scanner(System.in);
    public static LinkedList myPlantList = new LinkedList();
    public static Random rand = new Random();
    public static final NumberFormat percent = NumberFormat.getPercentInstance();
    public static int rep = 0, day = 0;
    public static void main(String[]args){
        boolean flag = true;

        //display all instructions and how to "play"
        //package A, B, C
        //which package would you like?

        String numPlants = "";
        do{ 
            System.out.println("\nHow many plants would you like to initially generate? Note that not all of them will be healthy.");
            numPlants = input.nextLine();
        }while(invalidInt(numPlants));

        generatePlants(Integer.parseInt(numPlants));

        while(flag){
            String option = "";
            do{
                String menuSelect = "Please select an option from the menu below";
                System.out.print("\n\n" + line(menuSelect.length()) + "\n\n" + menuSelect + 
                    "\n->1. Tend to a certain plant" + 
                    "\n->2. See all plants" + //from day ... in file ...
                    "\n->3. Show me plants by..." + //searching and sorting 
                    "\n->4. Buy new plant" +
                    "\n->5. Sell plant" + 
                    "\n->6. Log into Planstagram" +
                    "\nCurrent amount of reputation: " + rep + 
                    "\n\nChoice: ");
                    option = input.nextLine();
            } while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") 
                & !option.equals("5") && !option.equals("6"));

            if(option.equals("1")){ //user chooses to tend to a plant
                //show user list of options
                String opt = "";

                System.out.println("\n" + line(100) + "\n\nGlad you decided to take care of your plants! " + 
                    "\n\nBefore you select an option, hit enter to see a list of plants that you can choose to tend to.");
                    input.nextLine();

                    System.out.println(line(100) + "\n\n" + myPlantList.toString()); 

                    if(!myPlantList.isEmpty()){
                        System.out.println("\nEnter the nickname of the plant you would like to tend to.");
                        String plantName = input.nextLine();
                        Plant plantToTend = myPlantList.searchByName(plantName);

                        if(plantToTend != null){
                            String op = "";
                            do{
                                System.out.println("\nAwesome. What would you like to do with " + plantName + "?");
                                System.out.println(plantToTend.tendMenu());
                                System.out.print("Choice: ");
                                op = input.nextLine();
                                System.out.println();
                            } while(!op.equals("1") && !op.equals("2") && !op.equals("3") && !op.equals("4")); //does not equal 1 2 3 4
                            
                            if(op.equals("1")){ //water the plant
                                plantToTend.setWaterLevel(plantToTend.getWaterLevel() + randomNumber(5,40));
                                System.out.println("\nNicely done! " + plantToTend.getName() + " (" + plantToTend.getSpecies() 
                                + " " + plantToTend.getGenus() + ") has been successfully watered." +
                                "\nNew water level: " + percent.format((double)plantToTend.getWaterLevel()/100));
                            }

                            else if(op.equals("2")){ //see the plant info (starts off as null, user gets to write it)
                                System.out.println("\nWhat would you like to add to the plant information for " + plantToTend.getName() + " (" + plantToTend.getSpecies() 
                                + " " + plantToTend.getGenus() + ")?");
                                plantToTend.addInfo("\n" + input.nextLine());

                                System.out.println(line(20) + "\n\nNew info for " + plantToTend.getName() + " (" + plantToTend.getSpecies() 
                                + " " + plantToTend.getGenus() + "): " + plantToTend.getInfo());
                            }

                            else if(op.equals("3")){ //user can rename the plant
                                //search to ensure plant has not already been named that
                                namePlant(plantToTend);
                                System.out.println("\nPlant has been successfully renamed. An exquisite name choice indeed.");
                            }

                            else if(op.equals("4")){ //special to each type of Plant
                                if(plantToTend instanceof Houseplant){ //user can attempt to propagate the Houseplant
                                    if(((Houseplant) plantToTend).propagate() == true){
                                        myPlantList.addAtFront(new Houseplant("", plantToTend.getSpecies(), plantToTend.getGenus(), 80));
                                        namePlant(myPlantList.getFront()); //allow user to name new propagation
                                        myPlantList.getFront().addInfo("\nThis plant is a successful propagation of " + plantToTend.getName() + "!");
                                    }
                                }

                                else if(plantToTend instanceof Tree){ 
                                    //user can attempt to collect flowers or leaves (depending on genus)
                                    ((Tree)plantToTend).collectFoliage();
                                }

                                else if(plantToTend instanceof FruitVeg){ 
                                    // user can attempt to collect vegetables 
                                    ((FruitVeg)plantToTend).collectFood();
                                }
                            }
                        }

                        else{ //if plant name is not in the inventory
                            System.out.println("\nLooks like " + plantName + " is not one of the plants you currently own!");
                        }
                    }
                    
            }

            else if(option.equals("2")){// file IO to see 2D array spreadsheet of plants

            }

            else if(option.equals("3")){ //searching and sorting of plants 
                System.out.println("\n" + line(100) + "\n\nGlad you've decided to keep your plants organized! \n\n" 
                + "Hit enter to see a list of all the different ways you can view your plants.");
                input.nextLine();

                String ssOpt = "";
                do{
                    System.out.print("Select an option from the menu below.\n" + doubleLine(10) + "Searching/Filtering" + doubleLine(10) +
                        "\n->1. Show me all plants with a certain water level " +  
                        "\n->2. Show me plants by genus" + 
                        "\n\n" + doubleLine(10) + "Sorting" + doubleLine(10) +
                        "\n->3. Sort plants by water level" + 
                        "\n->4. Sort plants by genus" + 
                        "\nChoice: ");
                        ssOpt = input.nextLine();
                        System.out.println();
                }while(!ssOpt.equals("1") && !ssOpt.equals("2") && !ssOpt.equals("3") && !ssOpt.equals("4"));
            
                if(ssOpt.equals("1")){ //shows plants with certain water level
                    String min, max = "";
                    do{ 
                        System.out.println("\nEnter minimum water level % to look for:");
                        min = input.nextLine();

                        System.out.println("\nEnter maximum water level % to look for:");
                        max = input.nextLine();
                    }while(invalidInt(min) && invalidInt(max));
                
                    System.out.println(myPlantList.searchByWaterLevel(Integer.parseInt(min), Integer.parseInt(max)));
                }

                else if(ssOpt.equals("2")){ //shows plants by genus
                    System.out.print("\nEnter the genus you would like to search for: ");
                    String genus = input.nextLine();
                    System.out.println(myPlantList.searchByGenus(genus));
                }

                else if(ssOpt.equals("3")){ //sorts plants by water level

                }

                else if(ssOpt.equals("4")){ //sorts plants by genus (alphabetically)

                }

            }

            else if(option.equals("4")){ //user wants to buy a new plant 

            }
            
            else if(option.equals("5")){ //user wants to sell a plant 

            }
            
            else if(option.equals("6")){ //user wants to log into virtual plant marketplace

            }
            
            System.out.println("\nHit enter to continue.\n" + line(100));
            input.nextLine();
            myPlantList.dailyEvent(); //does a random daily event for every plant in the list
            myPlantList.removeDeadPlants(); //removes any dead plants from the list
            day++;
        }
    }


    public static void generateRandomPlant(LinkedList list){
        int sub = randomNumber(1, 3);
        int genus = randomNumber(1, 5);
        String [] species = {"Cebu Blue", "Neon", "Manjula", "Marble Queen", "Golden", //pothos species
                            "Brasil", "Pink Princess", "Florida Beauty", "Micans", "Gloriosum" //philodendron species
                            , "Elastica", "Lyrata", "Benjamina", "Altissmia", "Audrey" //ficus species
                            , "Song of India", "Marginata", "Fragans", "Warneckii", "Cintho" //dracena species
                            , "Watermelon", "Pilea", "Caperata", "Obtusifolia", "Ginny" //peperomia species
                            
                            , "Yoshino Cherry", "Winter-flowering", "Fuji Cherry", "Prunus Kanzan", "Fugenzo" //blossom
                            , "Albicaulis", "Eastern white", "Ponderosa", "Jack", "Red" //pine
                            , "Weeping", "White", "Peachleaf", "Salix", "Scouler's" //willow
                            , "Sugar", "Silver", "Douglas", "Hedge", "Red", "Norway", "Manitoba" //maple species
                        
                            , "Eggplant", "Potatoe", "Tomatoe" //solcanaceae 
                            , "Cabbage", "Watercress", "Turnip", "Radish", "Kale" //brassicaceae
                            , "Garlic", "Asparagus", "Shallot", "Leek", "Chive" //liliaceae
                            , "Pumpkin", "Squash", "Cucumber", "Melon", "Zucchini" //cucurbitaceae
                            , "Corn", "Rice", "Barley", "Millet", "Oat"}; //poacaeae
        
        if(sub == 1){ // will generate a Houseplant
            if(genus == 1){ //Pothos
                list.addAtFront(new Houseplant("", species[randomNumber(0, 4)], "Pothos", randomNumber(0, 100)));
            }

            else if(genus == 2){ //philodendron
                list.addAtFront(new Houseplant("", species[randomNumber(5, 9)], "Philodendron", randomNumber(0, 100)));
            }

            else if(genus == 3){// ficus
                list.addAtFront(new Houseplant("", species[randomNumber(10, 14)], "Ficus", randomNumber(0, 100)));
            }

            else if(genus == 4){// dracena
                list.addAtFront(new Houseplant("", species[randomNumber(15, 19)], "Dracena", randomNumber(0, 100)));
            }

            else if(genus == 5){ //peperomia
                list.addAtFront(new Houseplant("", species[randomNumber(20, 24)], "Peperomia", randomNumber(0, 100)));
            }

        }

        else if(sub == 2){ //will generate Tree 
            if(genus == 1){ //blossom
                list.addAtFront(new Tree("", species[randomNumber(25, 29)], "Blossom", randomNumber(0, 100)));
            }

            else if(genus == 2){ //pine
                list.addAtFront(new Tree("", species[randomNumber(30, 34)], "Pine", randomNumber(0, 100)));
            }

            else if(genus == 3){ //willow
                list.addAtFront(new Tree("", species[randomNumber(35, 39)], "Willow", randomNumber(0, 100)));
            }

            else if(genus == 4 || genus == 5){ //maple
                list.addAtFront(new Tree("", species[randomNumber(40, 46)], "Maple", randomNumber(0, 100)));
            }

        }

        else if(sub == 3){ // will generate FruitVeg plant
            if(genus == 1){ //solcanaceae 
                list.addAtFront(new FruitVeg("", species[randomNumber(47, 49)], "Solcanaceae", randomNumber(0, 100)));
            }

            if(genus == 2){ //brassicaceae
                list.addAtFront(new FruitVeg("", species[randomNumber(50, 54)], "Brassicaceae", randomNumber(0, 100)));
            }

            if(genus == 3){ ////liliaceae
                list.addAtFront(new FruitVeg("", species[randomNumber(55, 59)], "Liliaceae", randomNumber(0, 100)));
            }

            if(genus == 4){ //cucurbitaceae
                list.addAtFront(new FruitVeg("", species[randomNumber(60, 64)], "Cucurbitaceae", randomNumber(0, 100)));
            }

            else if(genus == 5){ //poacaeae
                list.addAtFront(new FruitVeg("", species[randomNumber(65, 69)], "Poacaeae", randomNumber(0, 100)));
            }
        }
    }

    /*
    *Random number generator method
    *Pre: Takes in the minimum and maximum constraints
    *Post: Returns a random number
    */
    public static int randomNumber(int min, int max){
        Random rand = new Random();
        return (min + rand.nextInt(Math.abs(max-min+1)));
    }

    public static int generatePlants(int n){ //where n is the number of plants
        int i = 0;
        if(i == n){
            return 0;
        }

        else{
            generateRandomPlant(myPlantList);
            System.out.println("What would you like to name your new " + myPlantList.getFront().getSpecies() + " " + myPlantList.getFront().getGenus() + "?");
            String newName = input.nextLine();

            while(myPlantList.doesNameExist(newName)){
                System.out.println("\nHm. It seems that you've already named another plant " + newName + ". \nTry a different name:");
                newName = input.nextLine();
            }

            myPlantList.getFront().setName(newName);
            return generatePlants(n-1);
        }
        
    }

    public static int generatePlants(int n, LinkedList list){ //where n is the number of plants
        int i = 0;

        if(i == n){
            return 0;
        }

        else{
            generateRandomPlant(list);
            return generatePlants(n, list);
        }
    }

    public static boolean invalidInt(String input){
        try{
            Integer.parseInt(input);
            return false;

        } catch(NumberFormatException e){
            return true;
        }
    }

    public static String line(int len){
        if(len == 0){
           return "";
        }

        return line(len-1) + "_";
    }

    public static String doubleLine(int len){
        if(len == 0){
           return "";
        }

        return doubleLine(len-1) + "=";
    }

    public static void namePlant(Plant plant1){
        String newName = "";
        boolean nullFlag = false;

        do{
            System.out.println("What would you like to (re)name your " + plant1.getSpecies() + " " + plant1.getGenus() + "?");
            newName = input.nextLine();

            if(myPlantList.searchByName(newName) == null){ 
                //if the method returns null, that means the plant name does not already exist and it is valid
                plant1.setName(newName);
                nullFlag = true;
            } 

            else{
                System.out.println("\nAnother plant has already been named '" + newName + "'. Try again.");
            }

        }while(!nullFlag);
    }
}
