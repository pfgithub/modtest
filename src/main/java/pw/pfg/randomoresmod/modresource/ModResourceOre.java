package pw.pfg.randomoresmod.modresource;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import pw.pfg.randomoresmod.ColoredBlock;
import pw.pfg.randomoresmod.RandomOresMod;

public class ModResourceOre extends ColoredBlock {
	ResourceDetails resource;
	Item blockItem;

	public ModResourceOre(ResourceDetails resource) {
		// new OreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)))
		super(
			resource,
			resource.ore,
			Block.Settings.of(Material.STONE)
				.strength(resource.materialHardness, resource.materialResistance),
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
				.rarity(resource.rarityMC)
		);
		this.resource = resource;
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return resource.isShiny;
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	// public void registerAssets(ClientResourcePackBuilder pack) {
	// 	// pack.addItemModel(id, f);
	// // pack.addTranslations(new Identifier("randomoresmod", "en_US"), trans -> {});
	// // todo: do a thing that adds a slab for every block as an easy example test
	// }
	// view (find . -name "iron_ore.json")
	public boolean isOpaque(BlockState blockState_1) {
		return true;
	}

	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	// return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		super.registerData(data);
		data.addLootTable(
			new Identifier("randomoresmod", "blocks/" + this.id),
			ltp -> {
				ltp.type(new Identifier("randomoresmod", "block/" + this.id))
					.pool(
						pool -> {
							pool.rolls(1);
							if (resource.requiresSmelting || true) {
								pool.entry(
									e -> e.name(new Identifier("randomoresmod", this.id))
										.type(new Identifier("minecraft:item"))
								);
								pool.condition(
									new Identifier("minecraft:survives_explosion"),
									cond -> {}
								);
							}
						// else {
						// https://github.com/artificemc/artifice/issues/12
						// pool.entry(
						// 	entry -> {
						// 		entry.type(new Identifier("minecraft", "alternative"));
						// 		entry.child(
						// 			child -> {
						// 				child.type(new Identifier("minecraft", "item"));
						// 				child.
						// 			}
						// 		);
						// 	}
						// );
						//}
						}
					);
			}
		);
		data.addSmeltingRecipe(
			new Identifier("randomoresmod", resource.gem.id + "_from_smelting"),
			smelting -> {
				smelting.type(new Identifier("minecraft", "smelting"))
					.ingredientItem(new Identifier("randomoresmod", this.id))
					.result(new Identifier("randomoresmod", resource.gem.id))
					.experience(0.7)
					.cookingTime(resource.smeltingTime);
			}
		);
		data.addBlastingRecipe(
			new Identifier("randomoresmod", resource.gem.id + "_from_blasting"),
			smelting -> {
				smelting.type(new Identifier("minecraft", "blasting"))
					.ingredientItem(new Identifier("randomoresmod", this.id))
					.result(new Identifier("randomoresmod", resource.gem.id))
					.experience(0.7)
					.cookingTime(resource.smeltingTime / 2);
			}
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {
		super.registerBiomeFeatures(biome);

		if (
			biome.getCategory() != Biome.Category.NETHER &&
			biome.getCategory() != Biome.Category.THEEND
		) {
			biome.addFeature(
				GenerationStep.Feature.UNDERGROUND_ORES,
				Biome.configureFeature(
					Feature.ORE,
					new OreFeatureConfig(
						OreFeatureConfig.Target.NATURAL_STONE,
						this.getDefaultState(),
						resource.oreVeinSize //Ore vein size
					),
					Decorator.COUNT_RANGE,
					new RangeDecoratorConfig(
						resource.oresPerChunk,
						0,
						resource.oreMinSpawn, // TODO: THIS DOES NOT WORK. A y=32 to y=42 ore was geenerated at y=6 with this
						resource.oreMaxSpawn
					) //Number of veins per chunk //Bottom Offset //Min y level //Max y level
				)
			);
		}
	}
}
