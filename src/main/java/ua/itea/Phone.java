package ua.itea;

public class Phone {
	private int price;
	private String name;
	private String brand;
	private String system;
	private int ram;
	private int memory;
	private String processor;
	private int cores;
	private double diagonal;

	public Phone() {
	}

	public Phone(int price, String name, String brand, String system, int ram, int memory, String processor, int cores,
			double diagonal) {
		super();
		this.price = price;
		this.name = name;
		this.brand = brand;
		this.system = system;
		this.ram = ram;
		this.memory = memory;
		this.processor = processor;
		this.cores = cores;
		this.diagonal = diagonal;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public double getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(double diagonal) {
		this.diagonal = diagonal;
	}

	@Override
	public String toString() {
		return "Phone [price=" + price + ", name=" + name + ", brand=" + brand + ", system=" + system + ", ram=" + ram
				+ ", memory=" + memory + ", processor=" + processor + ", cores=" + cores + ", diagonal=" + diagonal
				+ "]";
	}

}
