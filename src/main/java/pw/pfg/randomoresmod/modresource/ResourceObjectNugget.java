package pw.pfg.randomoresmod.modresource;

import java.util.Random;

public class ResourceObjectNugget extends ResourceObject {
	public static Style[] STYLE = StyleSet.Builder()
		.resourceBase("randomoresmod:item/nugget/")
		.languageKeyBase("name.randomoresmod.nugget.")
		.baseOverlay("horizontal")
		.baseOverlay("teardrop")
		.baseOverlay("vertical")
		.build();

	public static ResourceObjectNugget random(String baseId) {
		String id = baseId + "_nugget";
		Random random = new Random(id.hashCode());

		Style style = STYLE[random.nextInt(STYLE.length)];

		return new ResourceObjectNugget(id, style);
	}

	public ResourceObjectNugget(String id, Style style) {
		super(id, style);
	}
}
