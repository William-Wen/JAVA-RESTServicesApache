package domain;

import java.util.Optional;

public class FoodManager {

	private static FoodList oFoodList = new FoodList();

	static {
		oFoodList.add(new Food("onion", 20, "2 slices"));
		oFoodList.add(new Food("Beans", 60, "1 cup"));
		oFoodList.add(new Food("Orange", 80, "1 whole"));
		oFoodList.add(new Food("Cereal", 120, "1 cup"));
	}

	public static FoodList getFoods() {
		return oFoodList;
	}

	public static Food find(String name) {
		final Optional<Food> oFoundFood = oFoodList.stream().filter(food -> food.getName().equalsIgnoreCase(name))
				.findFirst();
		return oFoundFood.orElse(null);
	}

	public static void add(Food food) {
		oFoodList.add(food);
	}

	public static void delete(String name) {
		Food oFood = find(name);
		oFoodList.remove(oFood);
	}

}
