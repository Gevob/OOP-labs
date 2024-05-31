package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    private String name;
    private double qto;
    private Collection <String> Ingredient;
    private Collection <Double> Ingredient_quantity;
    private static Collection <NutritionalElement> rm;
    private double calories;
    private double proteins;
    private double carbs;
    private double fat;
	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
    public Recipe(String name,Collection <NutritionalElement> r) {
    	this.name=name;
    	calories=0.0;
    	proteins=0.0;
    	carbs=0.0;
    	fat=0.0;
    	rm=r;
    }
    
	public Recipe addIngredient(String material, double quantity) {
		if(Ingredient==null)
			Ingredient=new ArrayList<>();
		if(Ingredient_quantity==null)
			Ingredient_quantity=new ArrayList<>();
		Iterator<NutritionalElement> raw=rm.iterator();
		
		while(raw.hasNext()) {
			RawMaterial tmp=(RawMaterial)raw.next();
			
			if(tmp.getName().equals(material)) {
				Ingredient.add(material);
				
				Ingredient_quantity.add(quantity);
				calories+=tmp.getCalories()*(quantity/100);
				proteins+=tmp.getProteins()*(quantity/100);
				carbs+=tmp.getCarbs()*(quantity/100);
				fat+=tmp.getFat()*(quantity/100);
				qto+=quantity;
				break;
			}
		}
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getCalories() {
		if(qto>100)
			 return calories/(qto/100);
		else if(qto<100)
			return calories*(qto/100);
		return calories;
	}

	@Override
	public double getProteins() {
		if(qto>100)
			 return proteins/(qto/100);
		else if(qto<100)
			return proteins*(qto/100);
		return proteins;
	}

	@Override
	public double getCarbs() {
		if(qto>100)
			 return carbs/(qto/100);
		else if(qto<100)
			return carbs*(qto/100);
		return carbs;
	}

	@Override
	public double getFat() {
		if(qto>100)
			 return fat/(qto/100);
		else if(qto<100)
			return fat*(qto/100);
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		String s="";
		Iterator<String> NameR=Ingredient.iterator();
		Iterator<Double> Qto=Ingredient_quantity.iterator();
		System.out.println(Ingredient.size());
		while(NameR.hasNext()&&Qto.hasNext()) {
			s=s+NameR.next()+" : "+Qto.next()+"\n";
			
		}
		
		return s;
	}
}
