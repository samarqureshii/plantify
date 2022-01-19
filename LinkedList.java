package com.company.finalProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;

public class LinkedList {
    private static Node head; //head of the list
    private int size; //the size of the linked list
    public static final NumberFormat percent = NumberFormat.getPercentInstance();

    public LinkedList(){
        //constructor that contains general initial info about the list
        head = null;
        size = 0;
    }

    /*
    * Returns the number of Nodes in the LinkedList
    *Pre: nothing
    *Post: size of the list
    */
    public int getSize(){
        return size;
    }

    public Plant getFront(){
        return head.getPlant();
    }


    public void addAtFront(Plant newPlant){
        Node newNode = new Node(newPlant);
        newNode.setNext(head);
        head = newNode; 

        //System.out.println("\nA " + newPlant.getSpecies() + " " + newPlant.getGenus() + " has been added to your collection.");
        size++;
    }
    /*
    *Removes any dead plants
    */
    public void removeDeadPlants(){
        Node current = head;

        if(isEmpty()){ //if the head is null
            return;
        }

        current.getPlant().checkPlantNeglect();
        if(!current.getPlant().isAlive()){
            //if the plant to remove is found in the head
            // System.out.println(current.getPlant().getName() + " (" + current.getPlant().getGenus() + " " + current.getPlant().getSpecies() + ") has been removed from your collection.");
            head = current.getNext(); //the head becomes the next Node
            size--; //number of Nodes decreases
            return;
        }

        //current.getPlant().checkPlantNeglect();
        while(current.getNext() != null &&!isEmpty()){ //iterating through every Node until the end of the list
            current.getNext().getPlant().checkPlantNeglect();
           
            if(!current.getNext().getPlant().isAlive()){ // if the Plant that needs to be removed is found 
                System.out.println("\nAs a result, " + current.getNext().getPlant().getName() 
                + " (" + current.getNext().getPlant().getSpecies() + " " + current.getNext().getPlant().getGenus() + ") "
                + "was removed from your collection.");
                if(current.getNext().getNext()!= null){ //if we are removing any other node 
                    Node temp = current.getNext();
                    current.setNext(temp.getNext()); //current Node is set to the next next Node 
                }

                else{ //if we are removing the last node in the list
                    current.setNext(null);
                }

                size--; //number of Nodes decreases
            }
            
            current = current.getNext(); //iterating through every Node
        }

    }

    public void remove(Plant plantToRemove){
        Node current = head;

        if(isEmpty()){ //if the head is null
            return;
        }

        if(current.getPlant().equals(plantToRemove)){
            //if the Pook to remove is found in the head
            head = current.getNext(); //the head becomes the next Node
            size--; //number of Nodes decreases
            return;
        }

        while(current.getNext() != null && !isEmpty()){ //iterating through every Node until the end of the list

            if(current.getNext().getPlant().equals(plantToRemove)){ // if the Book that needs to be removed is found 
                if(current.getNext().getNext()!= null){ //if we are removing any other node 
                    Node temp = current.getNext();
                    current.setNext(temp.getNext()); //current Node is set to the next next Node 
                }

                else{ //if we are removing the last node in the list
                    current.setNext(null);
                }

                size--; //number of Nodes decreases
                return;
            }
            
            current = current.getNext(); //iterating through every Node
        }
    
        return ;
    }
    
    public int getDailyRep(){
        Node current = head;
        int rep = 0;

        while(current!=null){
            rep+=current.getPlant().getRep();
            current = current.getNext();
        }

        return rep;
    }
    
    public Plant searchByName(String name){
        Node current = head;

        if(isEmpty()){ //if the head is null
            System.out.println("\nIt appears that you do not currently own any plants!");
            return null; //returns null if there are no plants in the list 
        }

        while(current!= null){ 
            if(current.getPlant().getName().equalsIgnoreCase(name)){
                return current.getPlant();
            }
            current = current.getNext(); //iterating through every Node
        }
       
        // System.out.println("\nLooks like " + name + " is not one of the plants you currently own!");
        return null;
}
    
    //____________________________________________________________________________________

    public String searchByWaterLevel(int min, int max){ //shows user all plants within a certain water level range
        Node current = head;
        String plantsInRange = "\nPlants inside the water range of " 
        + percent.format((double)min/100) + " to " + percent.format((double)max/100) + "↓\n";
        boolean found = false;

        if(isEmpty()){ //if the head is null
            return "\nYou do not have any plants at the moment!";
        }

        while(current!=null){
            if(current.getPlant().getWaterLevel() >= min && current.getPlant().getWaterLevel() <= max){
                plantsInRange += "\n" + current.getPlant().getName() 
                + " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() 
                + ") with a water level of " + percent.format((double)current.getPlant().getWaterLevel()/100);
                found = true;
            }
            current = current.getNext();
        }
    
        if(found){
            return plantsInRange;
        }

        else{
            return "\nNone of your plants are in the specified range!";
        }
    }  

    public String searchByProblems(){ //shows the user all plants that have problems
        Node current = head;
        String problematicPlants = "\nAll plants with problems↓";

        if(isEmpty()){ //if the head is null
            return "\nYou do not have any plants at the moment!";
        }

        while(current!=null){
            if(current.getPlant().getRootRot()){
                problematicPlants += "\n" + current.getPlant().getName() 
                + " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() 
                + ") is suffering from root rot.";
            }

            // else if(((Houseplant) current.getPlant()).getFungusGnat()){
            //     problematicPlants += "\n" + current.getPlant().getName() 
            //     + " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() 
            //     + ") is suffering from a fungus gnat infestation.";
            // }

            // else if(((Houseplant) current.getPlant()).getSpider()){
            //     problematicPlants += "\n" + current.getPlant().getName() 
            //     + " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() 
            //     + ") is suffering from a spider mite infestation.";
            // }

            // else if(((Tree) current.getPlant()).getCold()){
            //     problematicPlants += "\n" + current.getPlant().getName() 
            //     + " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() 
            //     + ") is suffering from exposure to the cold.";
            // }

            // else if(((FruitVeg) current.getPlant()).getAnimalEaten()){
            //     problematicPlants += "\n" + current.getPlant().getName() 
            //     + " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() 
            //     + ") is suffering from mild damage from an animal attack.";
            // }

            current = current.getNext();
        }
    
        if(problematicPlants.length() > 26){ //some plants have issues 
            problematicPlants += "\n\nUnfortunately, there is nothing you can do to address these problems.";
            return problematicPlants;
        } 

        else{ //all plants are healthy
            return "\nAll your plants appear to be healthy at the moment!";
        }
    }

    public String searchByGenus(String genus){ //shows user all the plants under a certain genus
        Node current = head;
        String genusPlants = "\nPlants with a genus of " + genus + "↓";
        boolean found = false;

        if(isEmpty()){ //if the head is null
            return "\nYou do not have any plants at the moment!";
        }

        while(current!=null){
            if(current.getPlant().getGenus().equalsIgnoreCase(genus)){
                genusPlants += "\n" + current.getPlant().getName() + " is apart of the " + genus + " plant family.";
                found = true;
            }
            current = current.getNext();
        }
    
        if(found){
            return genusPlants;
        }

        else{
            return "\nYou do not currently own any plants of the '" + genus + "' genus!";
        }
    }

    public String sortByWaterLevel(){
        if (isEmpty()) {
            //if there are no Plants to sort
            return "\nIt appears that you do not own any plants!";
        }

        for (Node iter = head; iter != null; iter = iter.getNext()) {
            //stops iterating through the Nodes when the list has been exhausted

            Node prev = null;
            Node current = head;

            for (Node next = current.getNext(); next != null; next = current.getNext()) { 
                //stops iterating through the Nodes when the next Node is null
                if (current.getPlant().getWaterLevel() > next.getPlant().getWaterLevel()) {
                    if (prev == null) {
                        //first run through
                        head = next;
                    } 
                    
                    else {
                        //every other run through case
                        prev.setNext(next);
                    }

                    //iterating through each Node while keeping track of the current, next, and previous Nodes
                    current.setNext(next.getNext());
                    next.setNext(current);
                    prev = next;

                } 
                
                else { //if the Nodes do not need to be swapped
                    prev = current;
                    current = next;
                }

            }
        }

        Node current = head;
        String sorted = "\nHere is the list of plants sorted by water level:\n";
        int i = 1;
        while(current != null){
            sorted += "\nPlant " + i + ": " + current.getPlant().getName() +
            " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() + ") " +
            " with a water level of " + percent.format((double)current.getPlant().getWaterLevel()/100);
            current = current.getNext();
            i++;
        }

        return sorted;
    }

    public String sortByGenus(){
        if (isEmpty()) {
            //if there are no Books to sort
            return "\nIt appears that you do not own any plants!";
        }

        for (Node iter = head; iter != null; iter = iter.getNext()) {
            //stops iterating through the Nodes when the list has been exhausted

            Node prev = null;
            Node current = head;

            for (Node next = current.getNext(); next != null; next = current.getNext()) { 
                //stops iterating through the Nodes when the next Node is null
                if (current.getPlant().getGenus().compareTo(next.getPlant().getGenus()) > 0) {
                    //if comparison returns a value less than 0, swap the Nodes
                    if (prev == null) {
                        //first run through
                        head = next;
                    } 
                    
                    else {
                        //every other run through case
                        prev.setNext(next);
                    }

                    //itereting through each Node while keeping track of the current, next, and previous Nodes
                    current.setNext(next.getNext());
                    next.setNext(current);
                    prev = next;

                } 
                
                else { //if the Nodes do not need to be swapped
                    prev = current;
                    current = next;
                }

            }
        }

        Node current = head;
        String sorted = "\nHere is the list of plants sorted lexicographically by their genus (second word in the brackets):\n";
        int i = 1;
        while(current != null){
            sorted += "\nPlant " + i + ": " + current.getPlant().getName() +
            " (" + current.getPlant().getSpecies() + " " + current.getPlant().getGenus() + ") ";
            current = current.getNext();
            i++;
        }

        return sorted;
    }
    
    //____________________________________________________________________________________

    public void dailyEvent(){
        Node current = head;

        if(isEmpty()){
            return;
        }

        while(current.getNext() != null){
            current.getPlant().dailyEvent();
            current = current.getNext(); //iterating through every Node 
        }
    }

    /*
    *Checks to see if a a name has already been given to a plant
    */
    public boolean doesNameExist(String name){
        Node current = head;

        if(isEmpty()){
            return false;
        }

        while(current.getNext() != null){ 
    
            if(current.getPlant().getName().equalsIgnoreCase(name)){
                return true;
            }
            
            current = current.getNext(); //iterating through every Node
        }

        return false;
    }


    public String toString(){
        Node current = head;
        String info = "";

        if(isEmpty()){ //if the list is empty
            return "It appears that you do not curently own any plants, or the one(s) you had have died!";
        }

        info += current.toString();

        while(current.getNext() != null){ 
            //while the next node is not null, loop through the nodes
            current = current.getNext();
            info += "\n" +current.getPlant() + "\n"; //information from each plant is added to info
        }

        return info + "\n\nTotal number of plants: " + size; //returns growing info chain
    }

    /*
    *Determines if the list is empty or not
    *Pre: nothing
    *Post: returns a true or false boolean based on the data in the head of the list
    */
    public boolean isEmpty(){
        if(head == null){ //no elements in the list
            return true;
        }
        return false;
    }

    public void writeFile(String dayNum){
        if(isEmpty()){
            return;
        }

        Node current = head;
        String[][]arr = new String[size*2 + 1][6];
        arr[0][0] = "";
        arr[1][0] = "";
        arr[0][1] = "Name";
        arr[0][2] = "Species";
        arr[0][3] = "Genus";
        arr[0][4] = "Water level";
        arr[0][5] = "Healthy?";

        for(int i = 1; i<arr[1].length; i++){
            arr[1][i] = "↓";
        }

        int ii = 1;
        for(int i = 2; i<arr.length; i++){
            if(i%2==0){
                arr[i][0] = "Plant " + ii + ":";
                ii++;
            }

            else{
                arr[i][0] = line(17);
            }
        }


        for(int i = 2; i<arr.length && current!=null; i++){ //starts at the third line of the array
            int j = 1;
            if(i%2==0){
                arr[i][j] = current.getPlant().getName();
                arr[i][j+1] = current.getPlant().getSpecies();
                arr[i][j+2] = current.getPlant().getGenus();
                int waterLevel = current.getPlant().getWaterLevel();
                arr[i][j+3] = percent.format((double)waterLevel/100);
                
                if(current.getPlant().getInfo() == ""){
                    arr[i][j+4] = "yes";
                }
                else if(current.getPlant().getInfo() != null){
                    arr[i][j+4] = "no";
                }

                current = current.getNext();
            }

            else{
                while(j<arr[i].length){
                    arr[i][j] = line(17);
                    j++;
                }
            }
        }

        String fileName = "day" + dayNum + ".txt";
        try{
            //declaring and initializing BufferedWriter and FileWriter objects
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false)); //false overwrites any info in the file
            bw.write("\n" + line(100) + "\n\nPlant collection at the start of day " + dayNum + ":\n");
            bw.newLine();

            for(int i = 0; i<arr.length; i++){
                for(int j = 0; j<arr[i].length; j++){
                    bw.write(String.format("%-17s", arr[i][j]));
                }
                bw.newLine();
            }

            bw.close(); //closing the writer

        } catch(IOException ex){  //catches failure in writing to the file (e.g if it does not exist)
            System.out.println("\nHmm. There seems to be a problem with writing to " + fileName + ".");
            System.out.println("IOException: " + ex.getMessage());
            return;
        } 
    }
    
    public static String line(int len){
        if(len == 0){
           return "";
        }

        return line(len-1) + "=";
    }

    /*
    * Each Node object creates a new Plant object that corresponds to its location in the LinkedList
    */
    public class Node{
        private Node next;
        private Plant plant;

        public Node(Plant newPlant){
            //associates a Plant object with each Node
            plant = newPlant;
            next = null; //always points to a null object
        }

        public void setNext(Node newNode){
            next = newNode;
        }

        public Node getNext(){
            return next;
        }

        /*
        * Accessor method that returns a Plant in relation to a given Node
        *Pre: nothing
        *Post: returns Plant
        */
        public Plant getPlant(){
            return plant;
        }

        /*
        * Elegantly displays the information from the Plant as a String
        *Pre: nothing
        *Post: String display of a given Plant
        */
        public String toString(){
            return plant.toString();
        }

    }
}
