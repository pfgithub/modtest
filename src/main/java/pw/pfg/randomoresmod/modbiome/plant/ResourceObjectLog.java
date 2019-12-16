package pw.pfg.randomoresmod.modbiome.plant;

import java.util.Random;
import pw.pfg.randomoresmod.TextureInfo;
import pw.pfg.randomoresmod.Style;
import pw.pfg.randomoresmod.modresource.StyleSet;

public class ResourceObjectLog extends TextureInfo {
	public static Style[] STYLE = StyleSet.Builder()
		.resourceBase("minecraft:block/")
		.languageKeyBase("name.randomoresmod.log.")
		.add("oak_log") // vertical_log
		.add("birch_log") // ..._log
		.add("spruce_log")
		.add("jungle_log") // horizontal_log
		.add("dark_oak_log")
		.add("acacia_log")
		.build();

	public static ResourceObjectLog random(String baseId) {
		String id = baseId + "_log";
		Random random = new Random(id.hashCode());

		Style style = STYLE[random.nextInt(STYLE.length)];

		return new ResourceObjectLog(id, style);
	}

	public ResourceObjectLog(String id, Style style) {
		super(id, style);
	}
}
