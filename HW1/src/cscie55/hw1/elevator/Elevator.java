package cscie55.hw1.elevator;


/**
 * Create an Elevator class.
 * The class has a no-argument constructor that sets up the elevator's initial state.
 * Use the toString method to print out the elevator moving states. 
 * The elevator starts on the first floor and goes up.
 * When the elevator reaches the top floor, it starts going down.
 * Any number of passengers may board the elevator. There is no elevator capacity limit. 
 * This Elevator class tracks the number of passengers for each floor destination.
 * It stops at a floor when a passenger wants to get off there, and discharges them.
 * @author:Linghong Chen, assignment work for CSCIE55 HW1
 * @version 1.0.0
 */

public class Elevator {
    
    //the floor number the elevator currently arrives at
    private int currentFloor;
    
    //the number of passengers currently inside the elevator
    private int passengerInElevator; 
    
    //the building highest floor number
    private final int MAX_ELEVATOR_FLOOR =7;
    
    //define a constant, variables and enum
    private enum Direction{UP, DOWN};
    Direction direction =Direction.UP;  
        
    // define an array to record each floor's number of passengers who plan to get off at that floor
    private int[] passengerDestinationFloor = new int[7];
       
    
    /**
     * Create a no-argument Elevator constructor
     * It records the state before boarding any passenger. 
     * Assume the elevator starts from the first floor.
     * Assigned the array passengerDestinationFloor for current floor to 0. 
     */
    public Elevator(){
        currentFloor=1;
        passengerInElevator =0;
        //array passengerDestinationFloor starts from 0, thus the index for current floor is currentFloor-1
        passengerDestinationFloor[currentFloor-1]=0;
    }
    
    /**
     * A get methods to make fields is reachable by other classes.
     * @return the maximum number of building floor
     */
    public int getMaxFloorNumber(){
        return MAX_ELEVATOR_FLOOR;
    }
    
    /**
     * A get methods to make fields is reachable by other classes.
     * @return the current floor number the elevator arrives at
     */
    public int getCurrentFloor(){
        return currentFloor;
    }
    
    /**
     * A get method to make the field reachable by other classes.
     * @return the current passenger number currently inside the elevator 
     */
    public int getPassengerNumber(){
        return passengerInElevator;
    }
        
    
    /**
     * Create a method for the elevator movement.
     * If direction is up, each move of elevator move up one elevator floor; 
     * if direction is down,each move of elevator move down one elevator floor.
     * When reach the top floor or bottom floor, the elevator will change the direction.
     * The elevator only stops at the floor where there are passengers need to get off.
     * When the elevator stops, all passengers whose destination is that floor must leave.
     * passengerDestinationFloor is an array that records the passenger number who will get off each floor.
     */
    public void move(){
        //elevator moves one floor by one floor
        if (direction==Direction.UP){
            currentFloor++;       
        } else {
            currentFloor--;    
        }
        //change direction from UP to DOWN when elevator arrives at top floor
        if (currentFloor==MAX_ELEVATOR_FLOOR){
            direction=Direction.DOWN;
        }
        //change direction from Down to Up when elevator arrives at  first floor
        if (currentFloor==1){
            direction=Direction.UP;
        }

       passengerInElevator = passengerInElevator -passengerDestinationFloor[currentFloor-1];
       passengerDestinationFloor[currentFloor-1]=0;
    }
        
    
    /**
     * create a method to add a passenger to the destination floor.
     * When a new passenger boards, adds the passenger number to their destined floor, 
     * in the meantime increases passengerNumber.
     * @param floor        the floor that a passenger will get off the elevator
     */ 
    public void boardPassenger(int floor){
        passengerInElevator++;
        passengerDestinationFloor[floor-1]++;
    }
    
    /**
     * create a toString method
     * @see java.lang.Object#toString()
     */
    public String toString(){       
        //find whether using "passenger" or "passengers"
        String passengerPl;
        if(passengerInElevator >1 ||passengerInElevator==0 ){
            passengerPl = " passengers";
        } else{
            passengerPl =" passenger"; 
        }
        return "Floor " + currentFloor + ":" + passengerInElevator +passengerPl;
    }
    
}

