package molecule;

public class Carbon extends Thread {
	
	private static int carbonCounter =0;
	private int id;
	private Propane sharedPropane;
	
	public Carbon(Propane propane_obj) {
		Carbon.carbonCounter+=1;
		id=carbonCounter;
		this.sharedPropane = propane_obj;
	}
	
	public void run() {
	    try {
			// you will need to fix below
			sharedPropane.mutex.acquire();//Semaphore to allow only one atom(thread) to execute at a time
			sharedPropane.addCarbon();//Incrementing the carbon counter.

			//Release the mutex semaphore if there are no enough hydrogen atoms to bond with
			if(sharedPropane.getHydrogen()<8)
			{
				sharedPropane.mutex.release();//Releasing the mutex semaphore
			}
			//Checking if we have enough hydrogen atoms to react with, there has to be 8 hydrogen atoms.
			else if(sharedPropane.getHydrogen()>=8)
			{
				System.out.println("---Group ready for bonding---");
				sharedPropane.carbonQ.release(3); //Releasing 3 carbonQ semaphores.
				sharedPropane.removeCarbon(3);//Reducing the carbon atom counter.

				sharedPropane.hydrogensQ.release(8);//Releasing the 8 hydrogenQ semaphores.
				sharedPropane.removeHydrogen(8);//Reducing the hydrogen atom counter.
			}
			sharedPropane.carbonQ.acquire();//Wait at barrier for correct number of atoms to bond with
			sharedPropane.bond("C"+ this.id);  //bond
			sharedPropane.barrier.b_wait();//Barrier
			sharedPropane.mutex.release();//Releasing the mutex
		}
	    catch (InterruptedException ex) { /* not handling this  */}
	   // System.out.println(" ");
	}
}