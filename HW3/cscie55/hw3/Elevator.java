package cscie55.hw3;

/**
 * Create an Elevator class.
 * The class has a one-argument constructor.
 * Use the toString method to print out the elevator moving states. 
 * The elevator starts on the first floor, but won't board any person before it takes a round trip 
 * The elevator first goes up. When the elevator reaches the top floor, it starts going down.
 * Passengers may board on the elevator. Once it reaches elevator capacity, the elevator will throw an exception. 
 * This Elevator stops and discharge the patient at a floor a passenger wants to get off.
 * This Elevator can also stop at a floor that has passengers waiting, and board the passengers until it reaches it full boarding capacity.
 * @author:Linghong Chen, assignment work for CSCIE55 HW3
 * @version 1.0.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import cscie55.hw3.ElevatorFullException;

                    
public class Elevator {
    
    /**
     * currentFloo: the floor number the elevator currently arrives at
     * Assume the elevator starts from the first floor.
     */
    private int currentFloor=1;
    
    //the number of passengers currently inside the elevator
    private int passengerInElevator; 
    
    //the building for this elevator
    private final Building building;    
    
    //number of the passengers that the elevator can accommodate
    static public final int CAPACITY =10;
    
    //define a constant enum for the elevator moving direction
    private enum Direction{UP, DOWN};
    Direction direction =Direction.UP;  
        
   /** define a set to record each floor's passengers who plan to get off at that floor
    * key:floor number, value the passenger that will get off on that floor
    */
    private Map<Integer, ArrayList<Passenger>> passengersAsDestination =new HashMap<Integer, ArrayList<Passenger>>();
     
    // define a set to record all passengers in the elevator
    Set<Passenger> passengersInElevator= new HashSet<Passenger>();
    
    
    /**
     * Create one argument Elevator constructor
     * It records the state for the elevator. 
     *
     * @param building    the building has elevator and many floors
     */
    public Elevator(Building building){
        this.building =building; 
        
        //initiate each floor's original destination passengers
        for(int i=1; i<=Building.FLOORS; i++){
            passengersAsDestination.put(i, new ArrayList<Passenger>());
        }  
    }
      
    /**
     * A get methods to make currentFloor reachable by other classes.
     * @return the current floor number the elevator arrives at
     */
    public int currentFloor(){
        return currentFloor;
    }
    
    /**
     * the passengers method
     * @return the passengers currently inside the elevator 
     */        
    public Set<Passenger> passengers(){
       
        for(int i=1; i<building.FLOORS; i++){
            for(Passenger passenger : passengersAsDestination.get(i)){
                passengersInElevator.add(passenger); 
            }          
        }
        return passengersInElevator;
    }
   
    //if the elevator is going up, return true, false otherwise
    public boolean goingUp(){
        if(direction==Direction.UP) {
            return true;
        }
        else {
            return false;        
        }
    }
    
    
  //if the elevator is going down, return true, false otherwise
    public boolean goingDown(){
        if(direction==Direction.DOWN) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Create a method for the elevator movement.
     * If direction is up, each move of elevator move up one elevator floor; 
     * if direction is down,each move of elevator move down one elevator floor.
     * When reach the top floor or bottom floor, the elevator will change the direction.
     * The elevator stops at the floor where there are passengers need to get off.
     * When the elevator stops, all passengers whose destination is that floor must leave.
     * passengerAsDestination is an collection that records the passengers who will get off each floor.
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
       if(passengersAsDestination.get(currentFloor).size()>0){
           for(Passenger passenger : this.passengersAsDestination.get(currentFloor)){
           //change passenger status as leaving the elevator
           passenger.arrive();
           
           //add passenger to the floor
           this.building.floor(currentFloor).residentPassengers.add(passenger);
           
           //remove passenger from elevator passenger collection
           passengersInElevator.remove(passenger);
           }
           //remove passengers in the elevator from this destination floor
           this.passengersAsDestination.put(currentFloor,new ArrayList<Passenger>());                      
       }
       
    
       /**
        * stops and picks up passengers when someone is waiting
        * @exception ElevatorFullException elevator exceed its full capacity
        */
       
       //add a list to temporary hold the upPassengers or downPassengers collection.
       ArrayList<Passenger> passengersInwaiting =new  ArrayList <Passenger>();
      
       //when elevator going up, it will stop on the floor where ther are passengers waiting
       if(goingUp() && building.floor(currentFloor).upPassengers.size()>0){                      
          passengersInwaiting= building.floor(currentFloor).upPassengers;
       } 
       //when elevator going down, it will stop on the floor where there are passengers waiting
       if(goingDown() && this.building.floor(currentFloor).downPassengers.size()>0){                           
          passengersInwaiting = building.floor(currentFloor).downPassengers; 
       } 
      
      Iterator<Passenger> boardingPassengers=passengersInwaiting.iterator();
           while(boardingPassengers.hasNext()){
              try{
                  Passenger nextPassenger=boardingPassengers.next();
                  
                  //change passenger status
                  boardPassenger(nextPassenger);  
                  
                  //remove the passengers from the elevator waiting pool;
                  boardingPassengers.remove();
                  
              }catch (ElevatorFullException e){
                  System.out.println("Elevator Passenger Capacity for this acending elevator is Full"); 
              }               
          }                
      }
   
    
    /**
     * create a method to add a passenger to the destination floor.
     * When a new passenger boards, adds the passenger to the collection of their destined floor, 
     * in the meantime add the boarded passenger in the passengerAsDestination collection.
     * in the meantime add the boarded passenger in the all passengers in elevator collection.
     * @param passenger        the passenger who will board on the elevator
     * @exception ElevatorFullException elevator exceed its full capacity
     */ 
    private void boardPassenger(Passenger passenger)throws ElevatorFullException{
        if(passengersInElevator.size()<CAPACITY){
          //when a passenger boards on the elevator, his current floor need to change to undefined
            passenger.boardElevator();
            
            //add passenger to the collection of the elevator's destination floor
            passengersAsDestination.get(passenger.destinationFloor()).add(passenger);    

          //add passenger to the passengers in elevator collection
            passengersInElevator.add(passenger); 
                        
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

