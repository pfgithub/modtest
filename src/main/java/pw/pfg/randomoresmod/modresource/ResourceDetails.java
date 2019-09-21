package pw.pfg.randomoresmod.modresource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResourceDetails {
	static String[][] ORE_STYLE = new String[][] {
		// this information should be loaded from a datapack or something
		{ "randomoresmod:block/ore_base_dull", "randomoresmod:block/ore_overlay_dull" },
		{
			"randomoresmod:block/ore_base_shiny",
			"randomoresmod:block/ore_overlay_shiny"
		},
		{
			"randomoresmod:block/ore_base_vein",
			"randomoresmod:block/ore_overlay_vein"
		},
		{
			"randomoresmod:block/ore_base_gem",
			"randomoresmod:block/ore_overlay_gem"
		},
		{
			"randomoresmod:block/ore_base_chunky",
			"randomoresmod:block/ore_overlay_chunky"
		}
	};

	static String[][] STORAGE_BLOCK_STYLE = new String[][] {
		// this information should be loaded from a datapack or something
		{ "randomoresmod:block/storage_dusty_base", "randomoresmod:block/storage_dusty_overlay" },
		{
			"randomoresmod:block/storage_lined_base",
			"randomoresmod:block/storage_lined_overlay"
		},
		{
			"randomoresmod:block/storage_rectangular_base",
			"randomoresmod:block/storage_rectangular_overlay"
		},
		{
			"randomoresmod:block/storage_shiny_base",
			"randomoresmod:block/storage_shiny_overlay"
		},
		{
			"randomoresmod:block/storage_streaky_base",
			"randomoresmod:block/storage_streaky_overlay"
		}
	};

	// 1.. is no color, last is color
	static String[][] GEM_STYLE = new String[][] {
		// this information should be loaded from a datapack or something
		{ "", "randomoresmod:item/multipart_stone" },
		{ "shard", "randomoresmod:item/shard" },
		{ "", "randomoresmod:item/flower" },
		{ "", "randomoresmod:item/tree" },
		{
			"bowl",
			"randomoresmod:item/bowl_base",
			"randomoresmod:item/bowl_overlay"
		},
		{ "", "randomoresmod:item/tooth_stone" },
		{ "powder", "randomoresmod:item/burning_powder" },
		{ "", "randomoresmod:item/peanut_stone" },
		{
			"cream",
			"randomoresmod:item/cream_base",
			"randomoresmod:item/cream_overlay"
		}, // mixed_cream (transparency can be used in items)
		{
			"",
			"randomoresmod:item/diamond_base",
			"randomoresmod:item/diamond_overlay"
		},
		{ "chunk", "randomoresmod:item/chunk" },
		{ "chunk", "randomoresmod:item/burnt_chunk" },
		{ "chip", "randomoresmod:item/sharp_chip" },
		{ "dust", "randomoresmod:item/dust" },
		{ "", "randomoresmod:item/seeds" },
		{ "strand", "randomoresmod:item/strand" },
		{ "strand", "randomoresmod:item/thick_strand" },
		{ "pebble", "randomoresmod:item/pebble_pile" },
		{
			"orb",
			"randomoresmod:item/shiny_orb_base",
			"randomoresmod:item/shiny_orb_overlay"
		},
		{ "cream", "randomoresmod:item/glob" },
		{ "sheet", "randomoresmod:item/sheet" },
		{
			"powder",
			"randomoresmod:item/shiny_powder_base",
			"randomoresmod:item/shiny_powder_overlay"
		},
		{ "", "randomoresmod:item/decorative_stone" },
		{ "", "randomoresmod:item/decorative_stone_2" },
		{
			"infused_stone",
			"randomoresmod:item/infused_stone_base",
			"randomoresmod:item/infused_stone_overlay"
		},
		{ "crystal", "randomoresmod:item/crystal_pile" },
		{ "", "randomoresmod:item/multipart_stone_2" },
		{
			"",
			"randomoresmod:item/emerald_base",
			"randomoresmod:item/emerald_overlay"
		},
		{ "orb", "randomoresmod:item/orb" },
		{ "", "randomoresmod:item/pebbles" }
	};

	static String[][] INGOT_STYLE = new String[][] {
		{ "ingot", "randomoresmod:item/ingot" }
	};

	// !!!!!
	// (fixed by using transparent)
	//
	public int color;
	public float materialHardness;
	public float materialResistance;

	public String resourceId;
	public String resourceTranslationKey;
	public String resourceEnglishName;

	public String oreId;
	public String[] oreStyle;
	public String oreTranslationKey;

	public String gemId;
	public String[] gemStyle;
	public String gemTranslationKey;

	public String storageBlockId;
	public String[] storageBlockStyle;
	public String storageBlockTranslationKey;

	public boolean requiresSmelting;
	public boolean dropsMany;

	public boolean isFuel;
	public int fuelSmeltingTime;

	public int smeltingTime;

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

		// hasNugget = isIngot
		boolean isIngot = requiresSmelting && random.nextBoolean();
		if (isIngot) {
			gemStyleFull.addAll(
				Arrays.asList(INGOT_STYLE[random.nextInt(INGOT_STYLE.length)])
			);
		} else {
			gemStyleFull.addAll(
				Arrays.asList(GEM_STYLE[random.nextInt(GEM_STYLE.length)])
			);
		}
		String gemName = gemStyleFull.remove(0);
		String[] gemStyle = gemStyleFull.toArray(new String[0]);

		boolean isFuel = random.nextInt(4) == 0;
		int fuelTime = random.nextInt(64) * 100; // 200 smelts 1 item in a furnace or 2 items in a blast furnace

		boolean hasUnusualSmeltingTime = random.nextInt(4) == 0;
		boolean unusualSmeltingTimeIsLong = random.nextInt(4) == 0; // maybe unusualsmeltingtime should be related to materialhardness
		int smeltingTime = hasUnusualSmeltingTime
			? (unusualSmeltingTimeIsLong ? random.nextInt(2200) + 200
			: random.nextInt(180) + 20)
			: 200; // anywhere from 1s to 100s, defaults to 10s

		String[] storageBlockStyle = STORAGE_BLOCK_STYLE
		[random.nextInt(STORAGE_BLOCK_STYLE.length)];

		// TODO: isOvergrown - adds an overlay to ore and block that uses the grass color and all items that makes it look overgrown
		// inspired by https://i.redd.it/6lalhnzbann31.png
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
			fuelTime,
			smeltingTime,
			storageBlockStyle
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
		int fuelTime,
		int smeltingTime,
		String[] storageBlockStyle
	) {
		this.color = color;
		this.oreStyle = oreStyle;
		this.oreTranslationKey =
			oreName == "" ? "" : "name.randomoresmod.ore." + oreName;
		this.gemStyle = gemStyle;
		this.gemTranslationKey =
			gemName == "" ? "" : "name.randomoresmod.gem." + gemName;
		this.requiresSmelting = requiresSmelting;
		this.dropsMany = dropsMany;
		this.materialHardness = materialHardness;
		this.materialResistance = materialResistance;
		this.resourceTranslationKey = "name.randomoresmod.resource." + id;
		this.resourceEnglishName = englishName;
		this.resourceId = id;
		this.oreId = this.resourceId + "_" + oreName;
		this.gemId = this.resourceId + (gemName == "" ? "" : "_" + gemName);
		this.isFuel = isFuel;
		this.fuelSmeltingTime = fuelTime;
		this.smeltingTime = smeltingTime;

		this.storageBlockId = this.resourceId + "_block";
		this.storageBlockStyle = storageBlockStyle;
		this.storageBlockTranslationKey =
			"name.randomoresmod.storageblock.storageblock";
	}
}
