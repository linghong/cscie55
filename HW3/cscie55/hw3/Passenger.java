package cscie55.hw3;


  /**
  * This class creates passenger objects in order to load them into the elevator
  * 
  * @author Linghong Chen
  * Date: 4/7/2016
  *
  */

  public class Passenger {

      private final        int ID;
      private              int currentFloor;
      private              int destinedFloor;
      
      
      /**
       * constructor
       * start as current floor =1 destination floor undefined
       * @param id   passenger id
       * 
       */
      public Passenger(int id){
          ID = id;
          currentFloor = 1;
          destinedFloor = -1;
      }
      
    //the passenger's current floor
      public int currentFloor(){
          return currentFloor;
      }
      
      //the passenger's destination floor
      public int destinationFloor(){
          return destinedFloor;
      }
     
      /**
       * Set the passenger's destination floor to a new floor 
       * @param newDestinationFloor the passenger's new destination floor 
       */
      public void waitForElevator(int newDestinationFloor){      
          destinedFloor = newDestinationFloor;       
      }
      
      /**
       * Called when passengers get on Elevator
       * set the passenger's current floor to undefined
       */
      public void boardElevator(){
          currentFloor = -1;
      }    
      
      /**
       * Called when passenger arrives at his destined floor
       * copy the value of the destination floor to the current floor
       * set the destination floor to be undefined, i.e. -1
       */
      public void arrive(){
          currentFloor = destinedFloor;
          destinedFloor = -1;
      }
      
      //toString method
      public String toString(){
          String passengerInfo = 
                  "PASSENGER: current floor: " + currentFloor + "  destination floor: " + destinedFloor;      
          return passengerInfo;
      }
}
