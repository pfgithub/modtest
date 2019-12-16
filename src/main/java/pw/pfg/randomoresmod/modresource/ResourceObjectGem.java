package pw.pfg.randomoresmod.modresource;

import java.util.Random;
import pw.pfg.randomoresmod.TextureInfo;
import pw.pfg.randomoresmod.Style;

public class ResourceObjectGem extends TextureInfo {
	public static Style[] STYLE = StyleSet.Builder()
		.resourceBase("randomoresmod:item/gem/")
		.languageKeyBase("name.randomoresmod.gem.")
		.baseOverlay("bowl") // bowl of ..
		.add("burning_powder")
		.add("burnt_chunk")
		.add("chunk")
		.baseOverlay("cream") // cream
		.add("crystal_pile") // crystal
		.add("decorative_stone")
		.add("decorative_stone_2")
		.baseOverlay("diamond")
		.add("dust") // dust
		.baseOverlay("emerald")
		.add("flower")
		.add("glob")
		.baseOverlay("infused_stone") // infused stone
		.add("multipart_stone")
		.add("multipart_stone_2")
		.add("orb") // orb
		.add("peanut_stone")
		.add("pebble_pile")
		.add("pebbles")
		.add("seeds")
		.add("shard") // shard
		.add("sharp_chip") // chip
		.add("sheet")
		.baseOverlay("shiny_orb")
		.baseOverlay("shiny_powder") // powder
		.add("strand") // strand
		.add("thick_strand") // strand
		.add("tooth_stone")
		.add("tree")
		.build();

	public static ResourceObjectGem random(String baseId) {
		String id = baseId + "_gem";
		Random random = new Random(id.hashCode());

		Style style = STYLE[random.nextInt(STYLE.length)];

		return new ResourceObjectGem(id, style);
	}

	public ResourceObjectGem(String id, Style style) {
		super(id, style);
	}
}
