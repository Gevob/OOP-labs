package it.polito.oop.vaccination;

import java.util.ArrayList;
import java.util.List;

public class Hub {
	private int nDoctors;
	private int nNurses;
	private int o;
	private String name;
	private boolean setted=false;
	
	public Hub(String nam) {
		name=nam;
	}
	public int getnDoctors() {
		return nDoctors;
	}
	public int getnNurses() {
		return nNurses;
	}
	public int getO() {
		return o;
	}
	public String getName() {
		return name;
	}
	public void setnNursers(int n) {
		nNurses=n;
	}
	public void setnDoctors(int n) {
		nDoctors=n;
	}
	public void setno(int n) {
		o=n;
	}
	public void setSetted() {
		setted=true;
	}
	
	public boolean getpersonality() {
		return setted;
	}
	
	public int minH() {
		int[] hours=new int[3];
		hours[0]=nDoctors*10;
		hours[1]=nNurses*12;
		hours[2]=o*20;
		int min=hours[0];
		for(int  h : hours) {
			if(h<min)
				min=h;
		}
		
		return min;
	}
}
