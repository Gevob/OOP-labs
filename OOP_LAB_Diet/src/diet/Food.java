package diet;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {
	private Collection <NutritionalElement> rawmat=new TreeSet<NutritionalElement>((a,b)->(a.getName().compareTo(b.getName())));
	private Collection <NutritionalElement> product=new TreeSet<NutritionalElement>((a,b)->(a.getName().compareTo(b.getName())));
	private Collection <NutritionalElement> ricette=new TreeSet<NutritionalElement>((a,b)->(a.getName().compareTo(b.getName())));
	private Collection <NutritionalElement> menu=new TreeSet<NutritionalElement>((a,b)->(a.getName().compareTo(b.getName())));
	/**
	 * Define a new raw material.
	 * 
	 * The nutritional values are specified for a conventional 100g amount
	 * @param name 		unique name of the raw material
	 * @param calories	calories per 100g
	 * @param proteins	proteins per 100g
	 * @param carbs		carbs per 100g
	 * @param fat 		fats per 100g
	 */
	public void defineRawMaterial(String name,
									  double calories,
									  double proteins,
									  double carbs,
									  double fat){
		if (rawmat==null)
			rawmat=new TreeSet<NutritionalElement>((a,b)->(a.getName().compareTo(b.getName())));
			rawmat.add(new RawMaterial(name,calories,proteins,carbs,fat,true));
		
	}
	
	/**
	 * Retrieves the collection of all defined raw materials
	 * 
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials(){
		return rawmat;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * 
	 * @param name  name of the raw material
	 * 
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name){
		Iterator<NutritionalElement> tree=rawmat.iterator();
		while(tree.hasNext()) {
			RawMaterial tmp=(RawMaterial)tree.next();
			if(tmp.getName().equals(name))
				return tmp;
		}
		return null;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * 
	 * @param name 		unique name of the product
	 * @param calories	calories for a product unit
	 * @param proteins	proteins for a product unit
	 * @param carbs		carbs for a product unit
	 * @param fat 		fats for a product unit
	 */
	public void defineProduct(String name,
								  double calories,
								  double proteins,
								  double carbs,
								  double fat){
		if (product==null)
			product=new TreeSet<NutritionalElement>((a,b)->(a.getName().compareTo(b.getName())));
		product.add(new RawMaterial(name,calories,proteins,carbs,fat,false));
	}
	
	/**
	 * Retrieves the collection of all defined products
	 * 
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products(){
		return product;
	}
	
	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name){
		Iterator<NutritionalElement> tree=product.iterator();
		while(tree.hasNext()) {
			RawMaterial tmp=(RawMaterial)tree.next();
			if(tmp.getName().equals(name))
				return tmp;
		}
		return null;
	}
	
	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * 
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe tmp=new Recipe(name,rawmat);
		ricette.add(tmp);
		return tmp;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * 
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes(){
		return ricette;
	}
	
	/**
	 * Retrieves a specific recipe, given its name
	 * 
	 * @param name  name of the recipe
	 * 
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name){	
		Iterator<NutritionalElement> tree=ricette.iterator();
		Recipe tmp;
		while(tree.hasNext()) {
			tmp=(Recipe)tree.next();
			if(tmp.getName().equals(name))
				return tmp;
		}
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu tmp=new Menu(name,ricette,product);
		if(menu==null)
			menu=new TreeSet<NutritionalElement>((a,b)->(a.getName().compareTo(b.getName())));
		menu.add(tmp);
		return tmp;
	}
	
}
