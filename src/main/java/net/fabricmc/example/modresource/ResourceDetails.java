package net.fabricmc.example.modresource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResourceDetails {
	static String[][] ORE_STYLE = new String[][] {
		// this information should be loaded from a datapack or something
		{ "modid:block/ore_base_dull", "modid:block/ore_overlay_dull" },
		{ "modid:block/ore_base_shiny", "modid:block/ore_overlay_shiny" },
		{ "modid:block/ore_base_vein", "modid:block/ore_overlay_vein" },
		{ "modid:block/ore_base_gem", "modid:block/ore_overlay_gem" },
		{ "modid:block/ore_base_chunky", "modid:block/ore_overlay_chunky" }
	};

	// 1.. is no color, last is color
	static String[][] GEM_STYLE = new String[][] {
		// this information should be loaded from a datapack or something
		{ "", "minecraft:item/popped_chorus_fruit" }, // popcorn
		{ "shard", "minecraft:item/prismarine_shard" }, // shard
		{ "", "minecraft:item/azure_bluet" }, // broccoli
		{ "", "minecraft:block/fire_coral" }, // tree
		{ "bowl", "minecraft:item/bowl", "minecraft:item/mushroom_stew" }, // bowl
		{ "", "minecraft:item/fermented_spider_eye" }, // tooth
		{ "powder", "minecraft:item/blaze_powder" }, // burning_powder
		{ "", "minecraft:item/rabbit_foot" }, // peanut
		{ "cream", "minecraft:item/magma_cream", "minecraft:item/magma_cream" }, // mixed_cream (transparency can be used in items)
		{ "", "minecraft:item/diamond" }, // diamond
		{ "chunk", "minecraft:item/coal" }, // chunk
		{ "chunk", "minecraft:item/charcoal" }, // burnt_chunk
		{ "ingot", "minecraft:item/iron_ingot" }, // ingot
		{ "chip", "minecraft:item/flint" }, // sharp
		{ "powder", "minecraft:item/gunpowder" }, // powder
		{ "", "minecraft:item/wheat_seeds" }, // dots
		{ "strand", "minecraft:item/string" }, // strand
		{ "strand", "minecraft:item/feather" }, // thick_strand
		{ "pebble", "minecraft:item/prismarine_crystals" }, // pebble_pile
		{ "orb", "minecraft:item/slime_ball" }, // shiny_orb
		{ "glob", "minecraft:item/clay_ball" }, // glob
		{ "sheet", "minecraft:item/paper" }, // sheet
		{ "dust", "minecraft:item/glowstone_dust" }, // shiny_dust
		{ "", "minecraft:item/lapis_lazuli" }, // shaped_rock_1
		{ "", "minecraft:item/yellow_dye" }, // shaped_rock_2
		{
			"infused_stone",
			"minecraft:item/fire_charge",
			"minecraft:item/fire_charge"
		}, // infued_stone
		{ "crystal", "minecraft:item/quartz" }, // crystal_pile
		{ "", "minecraft:item/phantom_membrane" }, // popcorn_2
		{ "", "minecraft:item/emerald" }, // crystal
		{ "orb", "minecraft:item/snowball" }, // orb
		{ "", "minecraft:item/spawn_egg_overlay" } // dots
	};

	// !!!!!
	// Unfortunately, because tint is an overlay instead of a hue shift,
	// white translates to solid tint color
	// This means that shiny parts don't work
	// because the white gets replaced with the
	// tint color.
	// This could possibly be solved by using an image manipulation
	// library to hue shift and stack textures
	//
	public int color;
	public String[] oreStyle;
	public String oreName;
	public String[] gemStyle;
	public String gemName;
	public String name;
	public String id;
	public boolean requiresSmelting;
	public boolean dropsMany;
	public float materialHardness;
	public float materialResistance;
	public String oreId;
	public String gemId;
	public String englishName;
	public boolean isFuel;
	public int fuelTime;

	public static ResourceDetails random(int seed) {
		Random random = new Random(seed);
		String id = NameGenerator.generate(random.nextInt());
		String englishName = id.substring(0, 1).toUpperCase() + id.substring(1);
		int color = random.nextInt(16777215); // note that color is less than a 32 bit int. additionally, it should be possible for colors to change slightly (hue/brightness) over time
		String[] oreStyle = ORE_STYLE[random.nextInt(ORE_STYLE.length)];
		float materialHardness = random.nextFloat() * 10.0f;
		float materialResistance = random.nextFloat() * 10.0f; // it should be possible for some materials to have a very high resistance
		boolean requiresSmelting = random.nextBoolean();
		boolean dropsMany = requiresSmelting ? false : random.nextBoolean();
		List<String> gemStyleFull = new ArrayList<>();
		gemStyleFull.addAll(
			Arrays.asList(GEM_STYLE[random.nextInt(GEM_STYLE.length)])
		);
		String gemName = gemStyleFull.remove(0);
		String[] gemStyle = gemStyleFull.toArray(new String[0]);

		boolean isFuel = random.nextInt(4) == 0;
		int fuelTime = random.nextInt(64) * 100; // 200 smelts 1 item in a furnace or 2 items in a blast furnace

		return new ResourceDetails(
			color,
			oreStyle,
			"ore",
			gemStyle,
			gemName,
			requiresSmelting,
			dropsMany,
			materialHardness,
			materialResistance,
			englishName,
			id,
			isFuel,
			fuelTime
		);
	}

	private ResourceDetails(
		int color,
		String[] oreStyle,
		String oreName,
		String[] gemStyle,
		String gemName,
		boolean requiresSmelting,
		boolean dropsMany,
		float materialHardness,
		float materialResistance,
		String englishName,
		String id,
		boolean isFuel,
		int fuelTime
	) {
		this.color = color;
		this.oreStyle = oreStyle;
		this.oreName = oreName == "" ? "" : "name.modid.ore." + oreName;
		this.gemStyle = gemStyle;
		this.gemName = gemName == "" ? "" : "name.modid.gem." + gemName;
		this.requiresSmelting = requiresSmelting;
		this.dropsMany = dropsMany;
		this.materialHardness = materialHardness;
		this.materialResistance = materialResistance;
		this.name = "name.modid.resource." + id;
		this.englishName = englishName;
		this.id = id;
		this.oreId = this.id + "_" + oreName;
		this.gemId = this.id + (gemName == "" ? "" : "_" + gemName);
		this.isFuel = isFuel;
		this.fuelTime = fuelTime;
	}
}
