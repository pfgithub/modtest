package pw.pfg.randomoresmod.modbiome.plant;

import java.util.Random;
import pw.pfg.randomoresmod.TextureInfo;
import pw.pfg.randomoresmod.Style;
import pw.pfg.randomoresmod.modresource.StyleSet;

public class ResourceObjectLeaves extends TextureInfo {
	public static Style[] STYLE = StyleSet.Builder()
		.resourceBase("minecraft:block/")
		.languageKeyBase("name.randomoresmod.leaves.")
		.add("oak_leaves") // maybe we should combine two leaves textures?
		.add("birch_leaves") // like one with oak but half the leaves in it and one birch but half the leaves
		.add("spruce_leaves")
		.add("jungle_leaves")
		.add("dark_oak_leaves")
		.add("acacia_leaves")
		.build();

	public static ResourceObjectLeaves random(String baseId) {
		String id = baseId + "_leaves";
		Random random = new Random(id.hashCode());

		Style style = STYLE[random.nextInt(STYLE.length)];

		return new ResourceObjectLeaves(id, style);
	}

	public ResourceObjectLeaves(String id, Style style) {
		super(id, style);
	}
}
