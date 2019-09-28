package pw.pfg.randomoresmod.modbiome.plant;

import java.util.Random;

import pw.pfg.randomoresmod.ResourceObject;
import pw.pfg.randomoresmod.Style;
import pw.pfg.randomoresmod.modresource.StyleSet;

public class ResourceObjectPlanks extends ResourceObject {

	public static Style[] STYLE = StyleSet.Builder()
		.resourceBase("minecraft:block/")
		.languageKeyBase("name.randomoresmod.planks.")
		.add("oak_planks") // planks
		.add("birch_planks")
		.add("spruce_planks")
		.add("jungle_planks")
		.add("dark_oak_planks")
		.add("acacia_planks")
		.build();

	public static ResourceObjectPlanks random(String baseId) {
		String id = baseId + "_planks";
		Random random = new Random(id.hashCode());

		Style style = STYLE[random.nextInt(STYLE.length)];

		return new ResourceObjectPlanks(id, style);
	}

	public ResourceObjectPlanks(String id, Style style) {
		super(id, style);
	}
}
