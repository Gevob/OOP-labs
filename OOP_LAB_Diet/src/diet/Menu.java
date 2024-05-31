package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;



/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	String name;
//	Collection <Recipe> recipes;
//	Collection <RawMaterial> preconf;
	private List<String> recipes;
	private double qto;
	private List<Double> quantities;
	private static Collection<NutritionalElement> recips;
	private static Collection<NutritionalElement> products;
    private double calories;
    private double proteins;
    private double carbs;
    private double fat;
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu(String name,Collection <NutritionalElement> rp,Collection <NutritionalElement> prod) {
		this.name=name;
		if(recips==null)
			recips=rp;
		if(products==null)
			products=prod;
	}
	
	public Menu addRecipe(String recipe, double quantity) {
		if(recipes==null)
			recipes=new ArrayList<>();
		if(quantities==null)
			quantities=new ArrayList<>();
		Iterator <NutritionalElement> rc=recips.iterator();
		while(rc.hasNext())
		{
			Recipe tmp=(Recipe)rc.next();
			if(tmp.getName().equals(recipe)) {
				recipes.add(recipe);
				quantities.add(quantity);
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

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		if(recipes==null)
			recipes=new ArrayList<>();
		if(quantities==null)
			quantities=new ArrayList<>();
		Iterator <NutritionalElement> pr=products.iterator();
		while(pr.hasNext()){
			RawMaterial tmp=(RawMaterial)pr.next();
			if(tmp.getName().equals(product)) {
				recipes.add(product);
				quantities.add(1.0);
				calories+=tmp.getCalories();
				proteins+=tmp.getProteins();
				carbs+=tmp.getCarbs();
				fat+=tmp.getFat();
				break;
			}
		}
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
