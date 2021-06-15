package molecule;

public class Hydrogen extends Thread {

	private static int hydrogenCounter =0;
	private int id;
	private Propane sharedPropane;
	
	
	public Hydrogen(Propane propane_obj) {
		Hydrogen.hydrogenCounter+=1;
		id=hydrogenCounter;
		this.sharedPropane = propane_obj;
		
	}
	
	public void run() {
	    try {
	    	// you will need to fix below
			sharedPropane.mutex.acquire();//Semaphore to allow only one atom(thread) to execute at a time
			sharedPropane.addHydrogen();//Incrementing the hydrogen counter.

			//Release the mutex if there are no enough hydrogen or carbon atoms to react
			if(sharedPropane.getHydrogen()<8 || sharedPropane.getCarbon()<3)
			{
				sharedPropane.mutex.release();//Releasing the mutex semaphore
			}
			else if(sharedPropane.getHydrogen()>=8 && sharedPropane.getCarbon()>=3)
			{
				System.out.println("---Group ready for bonding---");
				sharedPropane.hydrogensQ.release(8);//Releasing the 8 hydrogenQ semaphores.
				sharedPropane.removeHydrogen(8);//Reducing the hydrogen atom counter.

				sharedPropane.carbonQ.release(3); //Releasing 3 carbonQ semaphores.
				sharedPropane.removeCarbon(3);//Reducing the carbon atom counter.
			}

			sharedPropane.hydrogensQ.acquire();//Wait at barrier for correct number of carbon atoms to bond with.
			sharedPropane.bond("H"+ this.id);  //bond
			sharedPropane.barrier.b_wait();//Barrier
			sharedPropane.mutex.release();
	    }
	   catch (InterruptedException ex) { /* not handling this  */}
	    //System.out.println(" ");
	}
}