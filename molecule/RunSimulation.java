package molecule;

import molecule.Carbon;
import molecule.Propane;
import molecule.Hydrogen;

public class RunSimulation {

	/**
	 This class is sent the number of 
	 */
	public static void main(String[] args){
		int no_hydrogens = Integer.parseInt(args[0]);
		int no_carbons = Integer.parseInt(args[1]);


		if((no_hydrogens%8==0 && no_carbons%3==0) && (no_hydrogens-no_carbons)%5==0)
		{
			System.out.println("Starting simulation with "+no_hydrogens+" H and "+no_carbons + " C");

			Carbon[] carbons = new Carbon[no_carbons];
			Hydrogen[] hydrogens = new Hydrogen[no_hydrogens];
			Propane sharedPropane = new Propane();

			for (int i=0;i<no_carbons;i++) {

				carbons[i]=new Carbon(sharedPropane); // call constructor
				carbons[i].start(); // start thread
			}
			for (int i=0;i<no_hydrogens;i++) {
				hydrogens[i]= new Hydrogen(sharedPropane);
				hydrogens[i].start();
			}
		}
		else
		{
			System.out.println("Oops, the number of Hydrogen atoms must be a multiple of 8 and that of Carbon atoms a multiple of 3, their difference has to be a multiple of 5!!!");
		}

	}

}