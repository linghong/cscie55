package cscie55.hw3;
/**
 * Create a Floor class.
 * The class has a two-argument constructor.
 * @author:Linghong Chen, assignment work for CSCIE55 HW3
 * @version 1.0.0
 */
import cscie55.hw3.Building;
import cscie55.hw3.Passenger;
import java.util.ArrayList;


/**
 * Create a Floor class.
 * The class has a two-arguments constructor.
 * @author:Linghong Chen, assignment work for CSCIE55 HW3
 * @version 1.0.0
 */

public class Floor {
    
    private final Building building;
    
    //current floor number
    private int floorNumber;
        
    
    /**
     * the passengers  on a floor belongs to one of the three arrayLists
     */   
   ArrayList <Passenger> residentPassengers = new ArrayList<Passenger>();
   ArrayList <Passenger> upPassengers = new ArrayList<Passenger>();
   ArrayList <Passenger> downPassengers = new ArrayList<Passenger>();
    
    /**
     * Create a two-arguments Floor constructor
     * It records the state for a floor. 
     * Assume the building to Building variable building.
     * Assigned the floor number equals to integer variable floorNumber. 
     * @param   building        the building that has one elevator and many floors
     * @param   floorNumber     the number for the floor where passengers can waiting for the elevator and elevator can stops
     */
    public Floor(Building building, int floorNumber){
        this.building =building;
        this.floorNumber =floorNumber;
    }
    
    //return true if the passenger is neither waiting to go up nor go down
   public  boolean isResident(Passenger passenger){
     return residentPassengers.contains(passenger);
   }
   
   /**
    * adds a passenger to the floor's residents
    * @param passenger      passengers who enter on ground floor 
    */
   public void enterGroundFloor(Passenger passenger){      
           residentPassengers.add(passenger);           
   }    
   

   /**
    * create a method to allow the floor to know which passenger is waiting for the elevator 
    * calling this should cause the elevator to stop the next time it moves to the floor with the right direction
    * @param    passenger           the passenger who are waiting for elevator    
    * @param    destinationFloor    the passenger's destination floor 
    */ 
    public void waitForElevator(Passenger passenger, int destinationFloor){
        /**
         * For the passenger waiting for the elevator, set the passenger's destination floor to new destination floor
         * waitForElevator(destinationFloor) is a method for Passenger class
        */
        passenger.waitForElevator(destinationFloor);
        
        //enter the passenger to one of the three passenger collections
        if(destinationFloor<floorNumber){
            downPassengers.add(passenger);
            
            //remove passenger from resident if the passenger belongs to the residents in the floor
            if(residentPassengers.remove(passenger)){
                residentPassengers.remove(passenger);
            }
        }else if(destinationFloor>floorNumber){
            upPassengers.add(passenger);
         
            //remove passenger from resident if the passenger belongs to the residents in the floor
            if(residentPassengers.remove(passenger)){
                residentPassengers.remove(passenger);
            }
        }else{
            throw new RuntimeException("A passenger that has the same destionation floor as he resident shouldn't wait for the elevator.");
        }       
    }     
}

