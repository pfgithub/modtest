package pw.pfg.randomoresmod.modresource;

import java.util.Random;

public class ResourceObjectOre extends ResourceObject {
	public static Style[] STYLE = StyleSet.Builder()
		.resourceBase("randomoresmod:block/ore/")
		.languageKeyBase("name.randomoresmod.ore.")
		.baseOverlay("dull")
		.baseOverlay("shiny")
		.baseOverlay("vein")
		.baseOverlay("gem")
		.baseOverlay("chunky")
		.build();

	public static ResourceObjectOre random(String baseId) {
		String id = baseId + "_ore";
		Random random = new Random(id.hashCode());

		Style style = STYLE[random.nextInt(STYLE.length)];

		return new ResourceObjectOre(id, style);
	}

	public ResourceObjectOre(String id, Style style) {
		super(id, style);
	}
}
