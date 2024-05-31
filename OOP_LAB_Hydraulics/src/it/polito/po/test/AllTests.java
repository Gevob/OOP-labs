package it.polito.po.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestR1_Elements.class, 
				TestR2_SimpleElements.class, 
				TestR3_ComplexElements.class,
				TestR4_Simulation.class
				})
public class AllTests {

}
