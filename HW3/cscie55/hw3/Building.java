package cscie55.hw3;

import cscie55.hw3.Elevator;
import cscie55.hw3.Floor;

/**
 * Create a Building class.
 * The class has a no-argument constructor that sets up the bbuilding's initial state.
 * @author:Linghong Chen, assignment work for CSCIE55 HW3
 * @version 1.0.0
 */

public class Building {
    
    //A static final field storing the number of floors in the building
    //the building highest floor number
    static public final int FLOORS =7;
    
    //elevator object
    private Elevator elevator ;
    
    //Floor object array
    private Floor[] floors= new Floor[Building.FLOORS];
    
            
    //Building constructor, creates an Elevator, and one floor for each floor number
    /**
     * Create a no-argument Building constructor
     * It records the state before boarding any passenger. 
     * Assigned the elevator.
     * Assigned the initial state for each floor. 
     */
    public Building(){
        //
        for(int i=0; i<Building.FLOORS;i++){
        //the 2nd parameter of Floor object is floor number, thus it is i+1 not i
            floors[i] = new Floor(this, i+1);
        }
       elevator = new Elevator(this); 
    }
    
    /**
     * A get methods to make elevator reachable by other classes.
     * @return return the building's elevator
     */
    public Elevator elevator(){
        return elevator;
    }
    
    /**
     * A get method to make object floor for a certain floor reachable by other classes.
     * @return returns the floor object for the given floor number
     * @param  floorNumber  return floor number
     */
    public Floor floor(int floorNumber){
      return floors[floorNumber-1]; 
    }
    
    //call the floor representing the ground floor
    public void enter(Passenger passenger){
        floor(1).enterGroundFloor(passenger);
    }
}
