package application;

import java.util.ArrayList;

public class Rule {
	boolean state;
	ArrayList<String> P; //partie premisse de regle
	ArrayList<String> C; //partie conclusion de regle
	int name;
	
	public Rule(int i, ArrayList<String> a1, ArrayList<String> a2) {
		name = i;
		state = true;
		P = a1;
		C = a2;
			
	}
	
}