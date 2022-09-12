package ua.itea;

import java.util.ArrayList;
import java.util.List;

public class Shop {

	private List<Phone> phones;
	private String name;

	public List<Phone> getPhones() {
		return phones;
	}

	public String getName() {
		return name;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Shop [phones=" + phones + ", name=" + name + "]";
	}
	

}
