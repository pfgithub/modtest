package net.fabricmc.example.modresource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResourceDetails {
	static List<String> ORE_STYLE = Arrays.asList(
		"dull",
		"shiny",
		"vein",
		"gem",
		"chunky"
	);
	static List<String> RESOURCE_BASE = Arrays.asList(
		"minecraft:item/iron_ingot",
		"minecraft:item/emerald",
		"minecraft:item/redstone_dust",
		"minecraft:item/lapis_lazuli",
		"minecraft:item/spawn_egg_overlay"
	);

	// !!!!!
	// Unfortunately, because tint is an overlay instead of a hue shift,
	// white translates to solid tint color
	// This means that shiny parts don't work
	// because the white gets replaced with the
	// tint color.
	// This could possibly be solved by using an image manipulation
	// library to hue shift and stack textures
	//
	// ore_base_dull, ore_base_shiny
	// ore_base_vein
	// ore_base_gem
	//
	// ore_dull (coal,iron,redstone)
	// ore_shiny (diamond,gold)
	// ore_vein (quartz)
	// ore_gem (emerald)
	// ore_chunky (lapis)
	//
	public int color;
	public String oreStyle;
	public String name;
	public String id;
	public float materialHardness;
	public float materialResistance;

	public static ResourceDetails random(int seed) {
		Random random = new Random(seed);
		String name = NameGenerator.generate(random.nextInt());
		String id = name;
		int color = random.nextInt(16777215); // note that color is less than a 32 bit int. additionally, it should be possible for colors to change slightly (hue/brightness) over time
		String oreStyle = ORE_STYLE.get(random.nextInt(ORE_STYLE.size()));
		float materialHardness = random.nextFloat() * 10.0f;
		float materialResistance = random.nextFloat() * 10.0f; // it should be possible for some materials to have a very high resistance

		return new ResourceDetails(
			/*color*/color,
			/*oreStyle*/oreStyle,
			/*materialHardness*/materialHardness,
			/*materialResistance*/materialResistance,
			/*name*/name,
			/*id*/id
		/*miningRecieves: resource[countMin,countMax] | ore*/
		/*hasNugget: boolean*/
		);
	}

	private ResourceDetails(
		int color,
		String oreStyle,
		float materialHardness,
		float materialResistance,
		String name,
		String id
	) {
		this.color = color;
		this.oreStyle = oreStyle;
		this.materialHardness = materialHardness;
		this.materialResistance = materialResistance;
		this.name = name;
		this.id = id;
	}
}
