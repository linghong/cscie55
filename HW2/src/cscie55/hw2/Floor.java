package cscie55.hw2;

/**
 * Create a Floor class.
 * The class has a two-arguments constructor.
 * @author:Linghong Chen, assignment work for CSCIE55 HW1
 * @version 1.0.0
 */

public class Floor {
    private final Building building;
    private int floorNumber;
    
    //number of the passengers waiting on a floor
    private int waitingPassengers=0;
    
    /**
     * Create a two-arguments Floor constructor
     * It records the state for a floor. 
     * Assume the building to Building variable building.
     * Assigned the floor number equals to int variable floorNumber. 
     * @param   building        the building that has one elevator and many floors
     * @param   floorNumber     the number for the floor where passengers can waiting for the elevator and elevator can stops
     */
    public Floor(Building building, int floorNumber){
        this.building =building;
        this.floorNumber =floorNumber;
    }
    

    /**
     * A get methods to make waitingPassengers reachable by other classes.
     * @return the passenger number that are waiting on that floor
     */
    public int  passengersWaiting(){
       return waitingPassengers; 
    }
    
    /**
     * create a method to decrease a passenger to the waitingPassengers.
     * When a new passenger boards, minus 1 from the passengers waiting on that floor
     */ 
   protected void passengersWaitingBoarded(){
       waitingPassengers--;  
    }
   

   /**
    * create a method to add a passenger to the waitingPassengers 
    * calling this should cause the elevator to stop the next time it moves to the floor
    */ 
    public void waitForElevator(){
        waitingPassengers++;   
    }

}
