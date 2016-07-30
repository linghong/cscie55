package cscie55.hw2;


/**
 * Create an Elevator class.
 * The class has a one-argument constructor.
 * Use the toString method to print out the elevator moving states. 
 * The elevator starts on the first floor and goes up.
 * When the elevator reaches the top floor, it starts going down.
 * Passengers may board on the elevator. Once it reaches elevator capacity, the elevator will throw an exception. 
 * This Elevator stops and discharge the patient at a floor a passenger wants to get off.
 * This Elevator can also stop at a floor that has passengers waiting, and board the passengers until it reaches it full boarding capacity.
 * @author:Linghong Chen, assignment work for CSCIE55 HW1
 * @version 1.0.0
 */

public class Elevator {
    
    //the floor number the elevator currently arrives at
    private int currentFloor;
    
    //the number of passengers currently inside the elevator
    private int passengerInElevator; 
    
    //the building for this elevator
    private final Building building;    
    
    //number of the passengers that the elevator can accommodate
    static public final int CAPACITY =10;
    
    //define a constant enum for the elevator moving direction
    private enum Direction{UP, DOWN};
    Direction direction =Direction.UP;  
        
    // define an array to record each floor's number of passengers who plan to get off at that floor
    private int[] passengerDestinationFloor = new int[Building.FLOORS];
       
    
    /**
     * Create one argument Elevator constructor
     * It records the state for the elevator. 
     * Assume the elevator starts from the first floor.
     * @param building    the building has elevator and many floors
     */
    public Elevator(Building building){
        this.building =building;
        currentFloor=1;    
    }
    
      
    /**
     * A get methods to make currentFloor reachable by other classes.
     * @return the current floor number the elevator arrives at
     */
    public int currentFloor(){
        return currentFloor;
    }
    
    /**
     * A get method to make the passengerInElevator reachable by other classes.
     * @return the passenger number currently inside the elevator 
     */
    public int passengers(){
        return passengerInElevator;
    }
        
    
    /**
     * Create a method for the elevator movement.
     * If direction is up, each move of elevator move up one elevator floor; 
     * if direction is down,each move of elevator move down one elevator floor.
     * When reach the top floor or bottom floor, the elevator will change the direction.
     * The elevator stops at the floor where there are passengers need to get off.
     * When the elevator stops, all passengers whose destination is that floor must leave.
     * passengerDestinationFloor is an array that records the passenger number who will get off each floor.
     *  The elevator also stops at the floor where passengers are waiting, and board them onto the elevator
     *  When the elevator reaches full capacity, it stops loading passengers, and throws an exception
     */    
    
    public void move(){
       //elevator moves one floor by one floor
       if (direction==Direction.UP){
           currentFloor++;       
       } else {
           currentFloor--;    
       }
       //change direction from UP to DOWN when elevator arrives at top floor
       if (currentFloor==Building.FLOORS){
           direction=Direction.DOWN;
       }
       //change direction from Down to Up when elevator arrives at  first floor
       if (currentFloor==1){
           direction=Direction.UP;
       }
       
       //stop elevator if someone needs to get off
       if(passengerDestinationFloor[currentFloor-1]!=0){
           passengerInElevator = passengerInElevator -passengerDestinationFloor[currentFloor-1];
           passengerDestinationFloor[currentFloor-1]=0;   
       }
            
       //stops and picks up passengers when someone is waiting
       if(building.floor(currentFloor).passengersWaiting()> 0){
           //stop elevator if someone needs to get off
           while(building.floor(currentFloor).passengersWaiting() > 0){
               try {
                   boardPassenger(1);
                   building.floor(currentFloor).passengersWaitingBoarded();
               } catch (ElevatorFullException e) {
                   break;
               }                             
           }                 
       }            
            
    }
        
    
    /**
     * create a method to add a passenger to the destination floor.
     * When a new passenger boards, adds the passenger number to their destined floor, 
     * in the meantime increases passengerNumber in the elevator.
     * @param destionationFloorNumber        the floor that a passenger will get off the elevator
     */ 
    public void boardPassenger(int destionationFloorNumber)throws ElevatorFullException{
        if(passengerInElevator<CAPACITY){
            passengerInElevator++;
            passengerDestinationFloor[destionationFloorNumber-1]++;           
        }else{
            throw new ElevatorFullException("Elevator Passenger Capacity is Full"); 
        }           
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

