package it.polito.po.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestR1_AltitudeRanges.class, TestR2_MountainHuts.class, TestR3_ReadData.class, TestR4_Queries.class })
public class AllTests {

}