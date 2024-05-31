package diet;

public class RawMaterial implements NutritionalElement {
private String name;
private double calories;
private double proteins;
private double carbs;
private double fat;
private boolean g;

public RawMaterial(String nome,double calories,double proteins,double carbs,double fat,boolean b) {
	name=nome;
	this.calories=calories;
	this.proteins=proteins;
	this.carbs=carbs;
	this.fat=fat;
	g=b;
}

public String getName() {
	return name;
}

public double getCalories() {
	return calories;
}

public double getProteins() {
	return proteins;
}

public double getCarbs() {
	return carbs;
}

public double getFat() {
	return fat;
}

public boolean per100g() {
	return g;
}

}
