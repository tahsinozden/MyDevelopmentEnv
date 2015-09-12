package apps.general;

import interfacepkg.*;

public class Human implements Compare, Identity{
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public Human(int age){
		this.age = age;
	}

	@Override
	public int doCompare(Object obj) {
		if (this.getAge() > ((Human)obj).getAge() ) return 1;
		else if (this.getAge() < ((Human)obj).getAge() ) return -1;
		return 0;
	}

	@Override
	public String yieldName() {
		return "My name is " + this.getClass().getName();
	}
	
}
