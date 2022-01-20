package com.company.finalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ICS4U_FP {

    public static Scanner input = new Scanner(System.in);
    public static LinkedList myPlantList = new LinkedList(); //linked list where all the user's plants will be stored
    public static Random rand = new Random();
    public static final NumberFormat percent = NumberFormat.getPercentInstance();
    public static int rep = 0, day = 1;
    public static boolean housePlantYes, treeYes, gardenYes;
    public static ArrayList <LinkedList> friendList = new ArrayList(); //array list where all the plant lists of all the user's friends will be stored
    //array of species that can be accessed throughout any method in the main
    public static String [] species = {"Cebu Blue", "Neon", "Manjula", "Marble Queen", "Golden", //pothos species
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
    public static void main(String[]args){
        boolean flag = true;

        introMessage(); //allows user to select and name their initial plants, as well as explains the rules 

        while(flag){ //as long as user still has plants
            String option = "";
            do{
                String menuSelect = "Please select an option from the menu below";
                System.out.print("\n\n" + line(menuSelect.length()) + "\n\n" + menuSelect + 
                    "\n->1. Tend to a certain plant" + //user can water, rename, add info to, or do a special action
                    "\n->2. See all plants" + //from day ... in file ...
                    "\n->3. Show me plants by..." + //searching and sorting 
                    "\n->4. Buy new plant" +
                    "\n->5. Sell plant" + 
                    "\n->6. Log into Planstagram" + //virtual plant
                    "\n->7. Abandon all plants" + 
                    "\nCurrent amount of reputation: " + rep + 
                    "\n\nChoice: ");
                    option = input.nextLine();
            } while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") 
                & !option.equals("5") && !option.equals("6") && !option.equals("7"));

            if(option.equals("1")){ //user chooses to tend to a plant
                //show user list of options
                String opt = "";

                System.out.println("\n" + line(100) + "\n\nGlad you decided to take care of your plants! " + 
                    "\n\nBefore you select an option, hit enter to see a list of plants that you can choose to tend to.");
                    input.nextLine();

                    System.out.println(line(100) + "\n\n->>Current plant inventory:\n\n" + myPlantList.toString()); 

                    if(!myPlantList.isEmpty()){ //user can only tend to a plant if their plant list has >=1 plant(s)
                        Plant plantToTend = null;
                        do{
                            System.out.println("\n->Enter the nickname of the plant you would like to tend to.");
                            String plantName = input.nextLine();
                            plantToTend = myPlantList.searchByName(plantName);

                            if(plantToTend == null){
                                System.out.println("\nLooks like " + plantName + " is not one of the plants you currently own!");
                            }
                
                        }while(plantToTend==null);

                        String op = "";
                            do{
                                System.out.println("\nAwesome. What would you like to do with " + plantToTend.getName() + "?");
                                System.out.println(plantToTend.tendMenu());
                                System.out.print("Choice: ");
                                op = input.nextLine();
                                System.out.println();
                            } while(!op.equals("1") && !op.equals("2") && !op.equals("3") && !op.equals("4")); //does not equal 1 2 3 4
                            
                            if(op.equals("1")){ //water the plant
                                int change = randomNumber(1,10);
                                plantToTend.setWaterLevel(plantToTend.getWaterLevel() + randomNumber(5,40));
                                //adds water and reputation to plant
                                System.out.println("\nNicely done! " + plantToTend.getName() + " (" + plantToTend.getSpecies() 
                                + " " + plantToTend.getGenus() + ") has been successfully watered." +
                                "\nNew water level: " + percent.format((double)plantToTend.getWaterLevel()/100) + "\n+" + change + " reputation.");
                                rep+=change;
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
                                rep+=randomNumber(0,5);
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

                    else{
                        System.out.println("\nYou do not currently own any plants!");
                    }
                    
            }

            else if(option.equals("2")){// file IO to see 2D array spreadsheet of plants
                System.out.println("\n\n" + line(100) + "\n");
                String dayNum = "";

                do{
                    System.out.println("\nWhich day would you like to see your plant collection from? " 
                    + "\n\nYou can only see files from before day " + day + ".");
                    dayNum = input.nextLine();
                }while(invalidInt(dayNum) || dayNum == null); //to ensure a file that exists can be read
           
                if(Integer.parseInt(dayNum) < day && Integer.parseInt(dayNum) > 0){ // where the file actually exists and can be read
                    readFile("day" + dayNum + ".txt");
                }

                else{
                    System.out.println("\nLooks like you're trying to access a file from the future, or too far in the past. Unable to read file.");
                }
            }

            else if(option.equals("3")){ //searching and sorting of plants 
                System.out.println("\n" + line(100) + "\n\nGlad you've decided to keep your plants organized! \n\n" 
                + "Hit enter to see a list of all the different ways you can view your plants.");
                input.nextLine();
                rep+=randomNumber(0,5);

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
                    }while(invalidInt(min) || invalidInt(max));
                
                    System.out.println(myPlantList.searchByWaterLevel(Integer.parseInt(min), Integer.parseInt(max)));
                }

                else if(ssOpt.equals("2")){ //shows plants by genus
                    System.out.print("\nEnter the genus you would like to search for: ");
                    String genus = input.nextLine();
                    System.out.println(myPlantList.searchByGenus(genus));
                }

                else if(ssOpt.equals("3")){ //sorts plants by water level
                    System.out.println(myPlantList.sortByWaterLevel());
                }

                else if(ssOpt.equals("4")){ //sorts plants by genus (alphabetically)
                    System.out.println(myPlantList.sortByGenus());
                }   

            }

            else if(option.equals("4")){ //user wants to buy a new plant 
                System.out.println("\n" + line(100) + "\n\nGlad you've decided to buy a new plant!"
                + "\n\nOur fancy AI will now ask you a series of questions to help determine which type of plant is best for your lifestyle." +
                "\n\nHit enter to begin the questionnaire.");
                input.nextLine();

                housePlantYes = false; 
                treeYes = false; 
                gardenYes = false;

                pickingPlant();

                if(!housePlantYes && !treeYes && !gardenYes){ //none of the conditions were met
                    System.out.println("\nBased on your resposnes, you do not deserve to care for plants at all!\n\n" 
                    + "It would be unethical to sell you a plant.");
                }

                else if(treeYes){
                    int cost = randomNumber(1,5);
                    System.out.println("\nYou are the perfect fit for a new tree." + 
                    "\n\nThe tree for sale will cost you " + cost + " reputation. Hit enter to purchase.");
                    input.nextLine();
                    
                    if(rep >= cost){
                        generateTree();
                        rep-=cost;
                    }

                    else{
                        System.out.println("\nUh oh, looks like you can't purchase this plant! Work on building some reputation before looking for new plants.");
                    }
                    
                }

                else if(gardenYes){
                    int cost = randomNumber(1,5);
                    System.out.println("\nYou are the perfect fit for a new garden plant." + 
                    "\n\nThe garden plant for sale will cost you " + cost + " reputation. Hit enter to purchase.");
                    input.nextLine();
                    
                    if(rep >= cost){
                        generateFruitVeg();
                        rep-=cost;
                    }

                    else{
                        System.out.println("\nUh oh, looks like you can't purchase this plant! Work on building some reputation before looking for new plants.");
                    }
                    
                }

                else if(housePlantYes){
                    int cost = randomNumber(1,5);
                    System.out.println("\nYou are the perfect fit for a new houseplant." + 
                    "\n\nThe houseplant for sale will cost you " + cost + " reputation. Hit enter to purchase.");
                    input.nextLine();
                    
                    if(rep >= cost){
                        generateHousePlant();
                        rep-=cost;
                    }

                    else{
                        System.out.println("\nUh oh, looks like you can't purchase this plant! Work on building some reputation before looking for new plants.");
                    }
                    
                }

            }
            
            else if(option.equals("5")){ //user wants to sell a plant 
                System.out.println("\n" + line(100) + "\n\nSelling precious plants just for some reputation? How utterly pathetic...\n\n" 
                + "Hit enter to see a list of plants you can sell.\n");
                input.nextLine();
                
                if(!myPlantList.isEmpty()){
                    System.out.println(myPlantList.toString() + "\n\n" + line(100));

                    Plant plantToSell = null;
                    do{
                        System.out.println("\n->Enter the nickname of the plant you would like to sell.");
                        String plantName = input.nextLine();
                        plantToSell = myPlantList.searchByName(plantName);

                        if(plantToSell == null){
                            System.out.println("\nHmmm. You do not currently own any plants named '" + plantName + "'.");
                        }
            
                    }while(plantToSell==null);
                    
                    int price = randomNumber(1,10);

                    System.out.println("\nYou will be selling " + 
                    plantToSell.getName() + " (" + plantToSell.getSpecies() + " " + plantToSell.getGenus() 
                            + ") for " + price + " reputation.");

                    String ipt = "";
                    do{
                        System.out.println("\nAre you sure you want to sell this plant? (yes/no)");
                        ipt = input.nextLine();

                    }while(!ipt.equalsIgnoreCase("yes") && !ipt.equalsIgnoreCase("no"));
                    
                    if(ipt.equalsIgnoreCase("yes")){
                        System.out.println("\nHit enter to proceed with the transaction.");
                        input.nextLine();
                        myPlantList.remove(plantToSell);
                        rep+=price;
                        System.out.println("\nPlant successfully sold. New reputation: " + rep);
                    }
                }

                else{
                    System.out.println("\nYou do not own any plants that you can sell!");
                }
            }
            
            else if(option.equals("6")){ //user wants to log into Planstagram
                addToFriendList(randomNumber(4,8)); //adds 4-8 random friends everytime user logs in
                boolean stay = true;
                int actionCount = 0;
                if(rep > 10 || !myPlantList.isEmpty()){
                    System.out.println("\n\n" + line(100) + 
                    "\n\nWelcome to Planstagram, a virtual plant community where you can connect with others through the shared love of plants!" + 
                    "\n\nThe more friends you have, the more reputation you can gain! However, some 'friends' can say nasty things and lower your reputation!" 
                    + "\n\nHit enter to see a list of options you can chose from.\n");
                    input.nextLine();

                    while(stay){
                        actionCount++;
                        String op = "";

                        do{
                            System.out.println(line(50) 
                                + "\n\n1. See the plant list of one of your friends " + 
                                "\n2. Leave a comment on one of your friends' plants " + 
                                "\n3. Connect with new friends" +
                                "\n4. Unfriend someone" + 
                                "\n5. Remove all friends from friend list" + 
                                "\n6. Log out");
                                op = input.nextLine();
                        }while(!op.equals("1") && !op.equals("2") && !op.equals("3") && !op.equals("4")
                                && !op.equals("5") && !op.equals("6"));

                        if(op.equals("1")){ //see the plant list of one of your friends
                            String friend = "";
                            do{ 
                                System.out.println("\nEnter the friend # whose plants you would like to see. You only have " + friendList.size() + " friends at the moment.");
                                friend = input.nextLine();
                            }while(invalidInt(friend) || Integer.parseInt(friend)-1 > friendList.size() || Integer.parseInt(friend)-1 < 0);

                            seeFriendsPlants(Integer.parseInt(friend)-1);
                        }

                        else if(op.equals("2")){ //leave a comment on on of your friend's plants
                            System.out.println("\n-->Below are the number of plants each of your friends own:\n");
                            numOfFriendPlants();

                            String friend = "";
                            do{ 
                                System.out.println("\nEnter the friend # whose plants you would like to comment on:");
                                friend = input.nextLine();
                            }while(invalidInt(friend));

                            System.out.println("\n-->Friend's plant inventory: ");
                            seeFriendsPlants(Integer.parseInt(friend)-1);

                            System.out.println("\nWhat would you like to comment?");
                            input.nextLine();

                            int r = randomNumber(1,5);
                            rep+=r;
                            System.out.println("\nNicely done! +" + r + " reputation.");
                        }

                        else if(op.equals("3")){ //connect with a new friend
                            System.out.println("\nLet's connect with some new friends! Hit enter to add some new plant buddies to your friend list.");
                            input.nextLine();

                            int newFriends = randomNumber(1,5);
                            rep+=newFriends;
                            addToFriendList(newFriends);
                            System.out.println("\n" + newFriends + " new friends were made. +" + newFriends + " reputation.");
                        
                        }

                        else if(op.equals("4")){ //user wants to unfriend someone
                            String friendToRemove = "";
                            do{ 
                                System.out.println("\nEnter the friend # you would like to remove. Remember, you only have " + friendList.size() + " friends!");
                                friendToRemove = input.nextLine();
                            }while(invalidInt(friendToRemove));
                        
                            friendList.remove(Integer.parseInt(friendToRemove)-1);
                            System.out.println("\nFriend successfully removed.");
                        }

                        else if(op.equals("5")){ //uses removes all friends from friend list
                            friendList.clear();
                            System.out.println("\nAll plant buddies removed from friend list.");
                        }

                        else if(op.equals("6")){ //user chooses to log out
                            System.out.println("\nSee you next time!");
                            stay = false;
                        }

                        if(actionCount == 4){
                            System.out.println("\nYou've been on Planstagram for too long! We're logging you out.");
                            stay = false;
                        }
                    
                        if(randomNumber(1,10)%2 == 0){
                            randomPlanstagram();
                        }
                    }
                }

                else{
                    System.out.println("\nYou have not gained enough reputation with your plants, to access Planstagram." + 
                    "\nCome back when you have more than 10 reputation, and at least one plant.");
                }
            }
            
            if(option.equals("7")){ //user wants to exit the program
                flag = false;
            }
            
            System.out.println("\nHit enter to continue.\n" + line(100));
            input.nextLine();

            myPlantList.dailyEvent(); //does a random daily event for every plant in the list
            rep += myPlantList.getDailyRep(); //tallies up all the reputation from every plant and adds to the total
            myPlantList.removeDeadPlants(); //removes any dead plants from the list
            
            if(!myPlantList.isEmpty()){ //will write to file as long as there are plants 
                myPlantList.writeFile("" + day);
            }

            day++; //used to keep track of files 

            if(myPlantList.isEmpty() && rep < 5){ //program ends as user cannot buy any more plants and does not have any
                System.out.println("\nYou have lost all your plants and do not have enough reputation to continue!");
                flag = false;
            }
        }

        System.out.println("\n" + line(100) + "\n\nIts a shame to see you leave. We hope to see you again soon!");
    }

    /*
    *Random event occurs after every "action" on Planstagram.
    *Pre: Nothing
    *Post: Shows the user if anyone has commented on their posts, or if they have been gifted a plant
    */
    private static void randomPlanstagram() {
        int rando = randomNumber(1,10);
        String [] rudeMessages = {" looks like its been neglected for a while. How are you even on Plantagram lolol", 
                                " is crawling with pests, EWWWW", 
                                " isn't looking too good",
                                " should be thrown out.", 
                                " is ugly."};

        String [] niceMessages = {" looks amazing!!!!!!", 
                                " looking super healthy :)", 
                                " isn't infected anymore! doing great~", 
                                " has grown alot! wow", 
                                " is a beautiful addition!"};
        
        if(rando<=4){ //negative comment left on post, lose reputation
            System.out.println("\nOne of your friends added a new comment on your post. Hit enter to view the comment.");
            input.nextLine();
            System.out.println("\nyour " + myPlantList.getFront().getSpecies() + " " + myPlantList.getFront().getGenus() 
            + rudeMessages[randomNumber(0,4)] + "'\n-" + rando + " reputation.");
            rep-=rando;
        }

        else if(rando == 5 || rando == 6){ //random friend decides to gift the user a new plant!
            generateRandomPlant(myPlantList);
            System.out.println("\nOne of your friends decided to gift you a new plant!");
            nameNewPlants();
            rep+=rando;
            System.out.println("+" + rando + " reputation.");
        }

        else if(rando>=7){// positive comment left on post, gain reputation
            System.out.println("\nOne of your friends added a new comment on your post. Hit enter to view the comment.");
            input.nextLine();
            System.out.println("\n'your " + myPlantList.getFront().getSpecies() + " " + myPlantList.getFront().getGenus() 
            + niceMessages[randomNumber(0,4)] + "'\n+" + rando + " reputation.");
            rep+=rando;
        }

    }

    /*
    * Generates any type of plant and adds it to the indicated linked list
    *Pre: Takes in the LinkedList as parameter that the plant will be added to
    *Post: Updates LinkedList with new Plant and its associated genus at the front of the list
    */
    public static void generateRandomPlant(LinkedList list){
        int sub = randomNumber(1, 3);
        int genus = randomNumber(1, 5);

        
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
    * Generates any type of houseplant to be added to the front of the user's plant list
    *Pre: nothing
    *Post: Updates user's plant list with new houselant and its associated genus at the front of the list
    */
    public static void generateHousePlant(){
        int genus = randomNumber(1, 5);

        if(genus == 1){ //Pothos
            myPlantList.addAtFront(new Houseplant("", species[randomNumber(0, 4)], "Pothos", randomNumber(0, 100)));
        }

        else if(genus == 2){ //philodendron
            myPlantList.addAtFront(new Houseplant("", species[randomNumber(5, 9)], "Philodendron", randomNumber(0, 100)));
        }

        else if(genus == 3){// ficus
            myPlantList.addAtFront(new Houseplant("", species[randomNumber(10, 14)], "Ficus", randomNumber(0, 100)));
        }

        else if(genus == 4){// dracena
            myPlantList.addAtFront(new Houseplant("", species[randomNumber(15, 19)], "Dracena", randomNumber(0, 100)));
        }

        else if(genus == 5){ //peperomia
            myPlantList.addAtFront(new Houseplant("", species[randomNumber(20, 24)], "Peperomia", randomNumber(0, 100)));
        }
    
        nameNewPlants();
    }

    /*
    * Generates any type of Tree to be added to the front of the user's plant list
    *Pre: nothing
    *Post: Updates user's plant list with new tree and its associated genus at the front of the list
    */
    public static void generateTree(){
        int genus = randomNumber(1,5);
        
        if(genus == 1){ //blossom
            myPlantList.addAtFront(new Tree("", species[randomNumber(25, 29)], "Blossom", randomNumber(0, 100)));
        }

        else if(genus == 2){ //pine
            myPlantList.addAtFront(new Tree("", species[randomNumber(30, 34)], "Pine", randomNumber(0, 100)));
        }

        else if(genus == 3){ //willow
            myPlantList.addAtFront(new Tree("", species[randomNumber(35, 39)], "Willow", randomNumber(0, 100)));
        }

        else if(genus == 4 || genus == 5){ //maple
            myPlantList.addAtFront(new Tree("", species[randomNumber(40, 46)], "Maple", randomNumber(0, 100)));
        }

        nameNewPlants();
    }
    
    /*
    * Generates any type of garden plant to be added to the front of the user's plant list
    *Pre: nothing
    *Post: Updates user's plant list with new garden plant and its associated genus at the front of the list
    */
    public static void generateFruitVeg(){
        int genus = randomNumber(1,5);

        if(genus == 1){ //solcanaceae 
            myPlantList.addAtFront(new FruitVeg("", species[randomNumber(47, 49)], "Solcanaceae", randomNumber(0, 100)));
        }

        if(genus == 2){ //brassicaceae
            myPlantList.addAtFront(new FruitVeg("", species[randomNumber(50, 54)], "Brassicaceae", randomNumber(0, 100)));
        }

        if(genus == 3){ ////liliaceae
            myPlantList.addAtFront(new FruitVeg("", species[randomNumber(55, 59)], "Liliaceae", randomNumber(0, 100)));
        }

        if(genus == 4){ //cucurbitaceae
            myPlantList.addAtFront(new FruitVeg("", species[randomNumber(60, 64)], "Cucurbitaceae", randomNumber(0, 100)));
        }

        else if(genus == 5){ //poacaeae
            myPlantList.addAtFront(new FruitVeg("", species[randomNumber(65, 69)], "Poacaeae", randomNumber(0, 100)));
        }
    
        nameNewPlants();
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

    /*
    *Generates any number of plants recursively and adds to front of user plant list, allows user to name each one
    *Pre: number of plants to add to list
    *Post: updated user plant list with new plants
    */
    public static int generatePlants(int n){ //where n is the number of plants
        int i = 0;
        if(i == n){
            return 0;
        }

        else{
            generateRandomPlant(myPlantList);
            nameNewPlants();
            return generatePlants(n-1);
        }
        
    }

    /*
    *Generates any number of plants recursively and adds to front of any plant list
    *Pre: number of plants to add to list
    *Post: updated plant list with new plants
    */
    public static LinkedList generatePlants(int n, LinkedList list){ //where n is the number of plants
        for(int i = 0; i<n; i++){
            generateRandomPlant(list);
        }

        return list;
    }

    
    public static int addToFriendList(int n){ //where n is the number of lists
        int i = 0;
        if(i == n){
            return 0;
        }

        else{
            friendList.add(generatePlants(randomNumber(2,8), new LinkedList()));
            return addToFriendList(n-1);
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

    public static void nameNewPlants(){
        System.out.println("What would you like to name your new " + myPlantList.getFront().getSpecies() + " " + myPlantList.getFront().getGenus() + "?");
            String newName = input.nextLine();

            while(myPlantList.doesNameExist(newName)){
                System.out.println("\nHm. It seems that you've already named another plant " + newName + ". \nTry a different name:");
                newName = input.nextLine();
            }

            myPlantList.getFront().setName(newName);
    }

    public static void pickingPlant(){
        String q = "";

        do{ //question 1
            System.out.println("\nHow many windows do you have in your house?" + 
                                "\n1. Lots. I like to bring the outside inside, as I am a CS student and rarely leave my house." + 
                                "\n2. A moderate amount." + 
                                "\n3. Maybe one or two. I enjoy living in a dungeon.");
            q = input.nextLine();
        }while( !q.equals("1") && !q.equals("2") && !q.equals("3"));

        if(q.equals("1")){
            housePlantYes = true;
        }

        else if(q.equals("1")){
            housePlantYes = true;
        }

        else if(q.equals("3")){
            housePlantYes = false;
        }

        q = "";
        do{ //question 2
            System.out.println("\nAre you looking to eat more sustainable food?" + 
                                "\n1. Of course." + 
                                "\n2. It's not really on my mind, but sure." + 
                                "\n3. Absolutely not. I take pride in contributing to climate change.");
            q = input.nextLine();
        }while(!q.equals("1") && !q.equals("2") && !q.equals("3"));

        if(q.equals("1")){
            gardenYes = true;
        }

        else if(q.equals("2")){
            gardenYes = true;
        }

        else if(q.equals("3")){
            housePlantYes = false;
        }

        q="";

        do{ //question 3
            System.out.println("\nHow lazy are you?" + 
                                "\n1. Yes." + 
                                "\n2. I have my days..." + 
                                "\n3. I consider myself to be productive all the time.");
            q = input.nextLine();
        }while(!q.equals("1") && !q.equals("2") && !q.equals("3"));

        if(q.equals("1")){
            housePlantYes = false;
            treeYes = true;
        }

        else if(q.equals("2")){
            gardenYes = true;
        }

        else if(q.equals("3")){
            housePlantYes = true;
        }

        q = "";
        do{ //question 4
            System.out.println("\nWhich direction does your house face?" + 
                                "\n1. North" + 
                                "\n2. South" + 
                                "\n3. East or West");
            q = input.nextLine();
        }while(!q.equals("1") && !q.equals("2") && !q.equals("3"));

        if(q.equals("1")){
            housePlantYes = false;
            treeYes = true;
        }

        else if(q.equals("2")){
            gardenYes = true;
        }

        else if(q.equals("3")){
            housePlantYes = true;
        }

        q = "";
        do{ //question 5
            System.out.println("\nWhat season is it?" + 
                                "\n1. Winter" + 
                                "\n2. Spring" + 
                                "\n3. Samar"
                                +"\n4. Fall");
            q = input.nextLine();
        }while(!q.equals("1") && !q.equals("2") && !q.equals("3") && !q.equals("4"));

        if(q.equals("1")){
            gardenYes = false;
            housePlantYes = true;
        }

        else if(q.equals("2")){
            gardenYes = true;
        }

        else if(q.equals("3")){
            gardenYes = true;
        }
        else if(q.equals("4")){
            gardenYes = false;
            treeYes = true;
        }
    }

    public static void readFile(String fileName){
        try{
            BufferedReader br = new BufferedReader(
            new FileReader(fileName));
            
            String s;
            while((s = br.readLine()) != null){
                    System.out.println(s);
            }

            br.close();

        } catch(IOException ex){ //catches failure in reading the file (e.g if it does not exist)
            System.out.println("\nIt appears that you did not own any plants on this day.");
            return;
        } 
    }

    public static void introMessage(){
        System.out.println("\nHit enter after every line to continue.\n\n\n" 
        + "While on vacation in Madagascar, you won a lottery!");
        input.nextLine();
        System.out.println("What kind of lottery, you ask?");
        input.nextLine();
        System.out.println("Only every plant lover's dream!");
        input.nextLine();
        System.out.println("That's right, you've won a TON of plants!\n\n" + 
        "To claim your prize, hit enter to see a list of surprise packages you can choose from!");
        input.nextLine();
        System.out.println(line(24) 
        + "\n|           |           |"
        + "\n| Surprise  | Surprise  |" 
        + "\n| Package A | Package B |" 
        // + "\n|           |           |"
        + "\n" + line (24) 
        + "\n|           |           |"
        + "\n| Surprise  | Surprise  |" 
        + "\n| Package C | Package D |" 
        // + "\n|           |           |"
        + "\n" + line (24));

        String packge = "";
        do{
            System.out.println("\n\nChoose from any of the four packages, A, B, C or D.");
            packge = input.nextLine();

        }while(!packge.equalsIgnoreCase("A") && !packge.equalsIgnoreCase("B") 
        && !packge.equalsIgnoreCase("C") && !packge.equalsIgnoreCase("D"));

        if(packge.equalsIgnoreCase("A")){
            int A = randomNumber(1,3);
            System.out.println("\nPackage A chosen. You won " + A + " plant(s)!");
            generatePlants(A);
        }

        else if(packge.equalsIgnoreCase("B")){
            int B = randomNumber(10,12);
            System.out.println("\nPackage B chosen. You won " + B + " plants!");
            generatePlants(B);
        }

        else if(packge.equalsIgnoreCase("C")){
            int C = randomNumber(7,9);
            System.out.println("\nPackage C chosen. You won " + C + " plants!");
            generatePlants(C);
        }

        else if(packge.equalsIgnoreCase("D")){
            int D = randomNumber(4,6);
            System.out.println("\nPackage D chosen. You won " + D + " plants!");
            generatePlants(D);
        }

        System.out.println("\nWhen you do things like take care of your plants or organize, them, you can gain reputation. [Hit enter for more]");
        input.nextLine();
        System.out.println("Reputation can be utilized in the the Planstagram interface, or to buy new plants. [Hit enter for more]");
        input.nextLine();
        System.out.println("However, reputation can be lost when you neglect your plants. [Hit enter for more]");
        input.nextLine();
        System.out.println("If you lose all your plants and have less than 5 reputation, you will be no longer be able to obtain and care for new plants. \n[Hit enter for more]");
        input.nextLine();
        System.out.println("Goodluck, and remember to take good care of your plants!");
    
    }

    public static void seeFriendsPlants(int index){ //where index is the index number in the list
        if(friendList.size() <= 0){
            System.out.println("\nYou currently have no plants!");
            return;
        }
        
        System.out.println("\n->>Current plant inventory of friend " + (index + 1) + ": \n\n" + friendList.get(index).toString());
    }

    public static void numOfFriendPlants(){
        if(friendList.size() <= 0){
            System.out.println("\nYou currently have no plants!");
            return;
        }

        for(int i = 0; i<friendList.size(); i++){
            System.out.println("\nFriend " + (i + 1) + " has " + friendList.get(i).getSize() + " plants.");
        }
    }
}


