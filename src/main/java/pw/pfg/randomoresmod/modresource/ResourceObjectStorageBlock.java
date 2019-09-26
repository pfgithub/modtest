package pw.pfg.randomoresmod.modresource;

import java.util.Random;
import pw.pfg.randomoresmod.ResourceObject;
import pw.pfg.randomoresmod.Style;

public class ResourceObjectStorageBlock extends ResourceObject {
	public static Style[] STYLE = StyleSet.Builder()
		.resourceBase("randomoresmod:block/storage_block/")
		.languageKeyBase("name.randomoresmod.storage_block.")
		.baseOverlay("decorative")
		.baseOverlay("dusty")
		.baseOverlay("lined")
		.baseOverlay("rectangular")
		.baseOverlay("shiny")
		.baseOverlay("streaky")
		.build();

	public static ResourceObjectStorageBlock random(String baseId) {
		String id = baseId + "_block";
		Random random = new Random(id.hashCode());

		Style style = STYLE[random.nextInt(STYLE.length)];

		return new ResourceObjectStorageBlock(id, style);
	}

	public ResourceObjectStorageBlock(String id, Style style) {
		super(id, style);
	}
}
