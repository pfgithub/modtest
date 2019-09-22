package pw.pfg.randomoresmod.modresource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResourceDetails {
	static String[][] ORE_STYLE = new String[][] {
		// this information should be loaded from a datapack or something
		{ "randomoresmod:block/ore_dull_base", "randomoresmod:block/ore_dull_overlay" },
		{
			"randomoresmod:block/ore_shiny_base",
			"randomoresmod:block/ore_shiny_overlay"
		},
		{
			"randomoresmod:block/ore_vein_base",
			"randomoresmod:block/ore_vein_overlay"
		},
		{
			"randomoresmod:block/ore_gem_base",
			"randomoresmod:block/ore_gem_overlay"
		},
		{
			"randomoresmod:block/ore_chunky_base",
			"randomoresmod:block/ore_chunky_overlay"
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
		},
		{
			"randomoresmod:block/storage_decorative_base",
			"randomoresmod:block/storage_decorative_overlay"
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
		{ "ingot", "randomoresmod:item/ingot" },
		{ "ingot", "randomoresmod:item/alternate_ingot" },
		{ "ingot", "randomoresmod:item/broken_ingot" }
	};

	static String[][] NUGGET_STYLE = new String[][] {
		{
			"randomoresmod:item/nugget/horizontal_base",
			"randomoresmod:item/nugget/horizontal_overlay"
		},
		{
			"randomoresmod:item/nugget/teardrop_base",
			"randomoresmod:item/nugget/teardrop_overlay"
		},
		{
			"randomoresmod:item/nugget/vertical_base",
			"randomoresmod:item/nugget/vertical_overlay"
		}
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

	public boolean hasNugget;
	public String nuggetId;
	public String[] nuggetStyle;

	public static ResourceDetails random(String resourceNameId) {
		// note that new features must be inserted at the bottom of a section
		Random setupRandom = new Random(resourceNameId.hashCode());

		// feature
		Random featureRandom = new Random(setupRandom.nextInt());

		boolean requiresSmelting = featureRandom.nextBoolean();
		boolean dropsMany = requiresSmelting ? false : featureRandom.nextBoolean();
		boolean isIngot = requiresSmelting && featureRandom.nextBoolean();

		float materialHardness = featureRandom.nextFloat() * 10.0f;
		float materialResistance = featureRandom.nextFloat() * 10.0f; // it should be possible for some materials to have a very high resistance

		boolean hasUnusualSmeltingTime = featureRandom.nextInt(4) == 0; // 25% chance, another 25% for if it's extra long
		boolean unusualSmeltingTimeIsLong = featureRandom.nextInt(4) == 0; // maybe unusualsmeltingtime should be related to materialhardness
		int longUnusualSmeltingTime = featureRandom.nextInt(1800) + 200;
		int shortUnusualSmeltingTime = featureRandom.nextInt(190) + 10;

		// ability
		Random abilityRandom = new Random(setupRandom.nextInt());

		boolean hasNugget = isIngot &&
		(abilityRandom.nextBoolean() || abilityRandom.nextBoolean());

		boolean isFuel = abilityRandom.nextInt(4) == 0;
		int fuelSmeltingTime = abilityRandom.nextInt(64) * 100; // 200 smelts 1 item in a furnace or 2 items in a blast furnace

		// cosmetic
		Random cosmeticRandom = new Random(setupRandom.nextInt());

		int color = cosmeticRandom.nextInt(16777215);

		String[] oreStyle = ORE_STYLE[cosmeticRandom.nextInt(ORE_STYLE.length)];
		String[] storageBlockStyle = STORAGE_BLOCK_STYLE
		[cosmeticRandom.nextInt(STORAGE_BLOCK_STYLE.length)];
		String[] nuggetStyle = NUGGET_STYLE
		[cosmeticRandom.nextInt(NUGGET_STYLE.length)];

		String[] ingotStyleIn = INGOT_STYLE
		[cosmeticRandom.nextInt(INGOT_STYLE.length)];
		String[] gemStyleIn = GEM_STYLE[cosmeticRandom.nextInt(GEM_STYLE.length)];

		// computed
		String resourceEnglishName = resourceNameId.substring(0, 1).toUpperCase() + resourceNameId.substring(1);

		List<String> gemStyleFull = new ArrayList<>();
		if (isIngot) {
			gemStyleFull.addAll(Arrays.asList(ingotStyleIn));
		} else {
			gemStyleFull.addAll(Arrays.asList(gemStyleIn));
		}

		String gemTypeId = gemStyleFull.remove(0);
		String[] gemStyle = gemStyleFull.toArray(new String[0]);

		int smeltingTime = hasUnusualSmeltingTime
			? (unusualSmeltingTimeIsLong ? longUnusualSmeltingTime // 10s to 100s
			: shortUnusualSmeltingTime) // 0.5s to 10s
			: 200; // default 10s

		// ---
		//
		//
		//
		// minHeight
		// maxHeight
		// (it might be interesting to have something with a min height of 100)
		// SmeltingProduct smeltingProduces = 90%gem | 5%block | 5%nugget if hasNugget
		// TODO: isOvergrown - adds an overlay to ore and block that uses the grass color and all items that makes it look overgrown
		// inspired by https://i.redd.it/6lalhnzbann31.png
		return new ResourceDetails(
			color,
			oreStyle,
			/*oreTypeId*/"ore",
			gemStyle,
			gemTypeId,
			requiresSmelting,
			dropsMany,
			materialHardness,
			materialResistance,
			resourceEnglishName,
			resourceNameId,
			isFuel,
			fuelSmeltingTime,
			smeltingTime,
			storageBlockStyle,
			hasNugget,
			nuggetStyle
		);
	}

	private ResourceDetails(
		int color,
		String[] oreStyle,
		String oreTypeId,
		String[] gemStyle,
		String gemTypeId,
		boolean requiresSmelting,
		boolean dropsMany,
		float materialHardness,
		float materialResistance,
		String resourceEnglishName,
		String resourceNameId,
		boolean isFuel,
		int fuelSmeltingTime,
		int smeltingTime,
		String[] storageBlockStyle,
		boolean hasNugget,
		String[] nuggetStyle
	) {
		this.color = color;
		this.oreStyle = oreStyle;
		this.oreTranslationKey =
			oreTypeId == "" ? "" : "name.randomoresmod.ore." + oreTypeId;
		this.gemStyle = gemStyle;
		this.gemTranslationKey =
			gemTypeId == "" ? "" : "name.randomoresmod.gem." + gemTypeId;
		this.requiresSmelting = requiresSmelting;
		this.dropsMany = dropsMany;
		this.materialHardness = materialHardness;
		this.materialResistance = materialResistance;
		this.resourceTranslationKey =
			"name.randomoresmod.resource." + resourceNameId;
		this.resourceEnglishName = resourceEnglishName;
		this.resourceId = resourceNameId;
		this.oreId = this.resourceId + "_" + oreTypeId;
		this.gemId = this.resourceId + "_resource"; // + (gemTypeId == "" ? "" : "_" + gemTypeId); // (to prevent conflicts by changing gem types)
		this.isFuel = isFuel;
		this.fuelSmeltingTime = fuelSmeltingTime;
		this.smeltingTime = smeltingTime;

		this.storageBlockId = this.resourceId + "_block";
		this.storageBlockStyle = storageBlockStyle;
		this.storageBlockTranslationKey =
			"name.randomoresmod.storageblock.storageblock";

		this.hasNugget = hasNugget;
		this.nuggetId = this.resourceId + "_nugget";
		this.nuggetStyle = nuggetStyle;
	}
}
