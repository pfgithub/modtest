package pw.pfg.randomoresmod.modresource;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.ObjectDetails;
import pw.pfg.randomoresmod.RandomOresMod;
import pw.pfg.randomoresmod.RegistrationHelper;
import pw.pfg.randomoresmod.TextureInfo;

public class ModResourceOre
	extends Block
	implements IRegisterable, IItemBlock, RegisterableBlockDefaults {
	Item item;
	ResourceDetails resource;
	protected TextureInfo texture;

	public ModResourceOre(ResourceDetails resource) {
		// new OreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)))
		super(
			Block.Settings.of(Material.STONE)
				.strength(resource.materialHardness, resource.materialResistance)
				// .nonOpaque()
				// .solidBlock(ModResourceOre::never)
				// .suffocates(ModResourceOre::never)
				// .blockVision(ModResourceOre::never)
		);
		this.resource = resource;
		this.texture = resource.ore;
		this.item =
			new NamedBlockItem(
				this,
				new Item.Settings()
					.group(RandomOresMod.RESOURCES)
					.rarity(resource.rarityMC)
			);
	}
	// private static boolean never(BlockState state, BlockView world, BlockPos pos) {
	//    return false;
	// }

	// public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
	//    return true;
	// }

	// @Environment(EnvType.CLIENT)
	// public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
	// 	return stateFrom.isOf(this) ? true : super.isSideInvisible(state, stateFrom, direction);
	// }

	@Override
	public boolean hasGlint(ItemStack itemStack) {
		return resource.isShiny;
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		data.addLootTable(
			new Identifier("randomoresmod", "blocks/" + this.texture.id),
			ltp -> {
				ltp.type(new Identifier("randomoresmod", "block/" + this.texture.id))
					.pool(pool -> {
						pool.rolls(1);
						if (resource.requiresSmelting) {
							pool.entry(e ->{
								e.name(new Identifier("randomoresmod", resource.ore.id));
								e.type(new Identifier("minecraft:item"));
							});
							pool.condition(new Identifier("minecraft:survives_explosion"), cond -> {});
						} else {
							pool.entry(entry -> {
								entry.type(new Identifier("minecraft", "alternatives"));
								entry.child(child -> {
									child.type(new Identifier("minecraft", "item"));
									child.condition(new Identifier("minecraft:match_tool"), condition -> {
										condition.addObject("predicate", predicate -> {
											predicate.addArray("enchantments", enchantments -> {
												enchantments.addObject(enchantment -> {
													enchantment.add("enchantment", "minecraft:silk_touch");
													enchantment.addObject("levels", levels -> {
														levels.add("min", 1);
													});
												});
											});
										});
									});
									child.name(new Identifier("randomoresmod", resource.ore.id));
								});
								entry.child(child -> {
									child.type(new Identifier("minecraft:item"));
									if(resource.dropsMany) {
										child.function(new Identifier("minecraft:set_count"), settings -> {
											settings.addObject("count", count -> {
												count.add("min", 4.0);
												count.add("max", 5.0);
											});
										});
									}
									child.function(new Identifier("minecraft:apply_bonus"), settings -> {
										settings.add("enchantment", "minecraft:fortune");
										if(resource.dropsMany) {
											settings.add("formula", "minecraft:uniform_bonus_count");
											settings.addObject("parameters", params -> params.add("bonusMultiplier", 1));
										} else {
											settings.add("formula", "minecraft:ore_drops");
										}
									});
									child.function(new Identifier("minecraft:explosion_decay"), settings -> {});
									child.name(new Identifier("randomoresmod", resource.gem.id));
								});
							});
						}
					});
			}
		);
		data.addSmeltingRecipe(
			new Identifier("randomoresmod", resource.gem.id + "_from_smelting"),
			smelting -> {
				smelting.type(new Identifier("minecraft", "smelting"))
					.ingredientItem(new Identifier("randomoresmod", this.texture.id))
					.result(new Identifier("randomoresmod", resource.gem.id))
					.experience(0.7)
					.cookingTime(resource.smeltingTime);
			}
		);
		data.addBlastingRecipe(
			new Identifier("randomoresmod", resource.gem.id + "_from_blasting"),
			smelting -> {
				smelting.type(new Identifier("minecraft", "blasting"))
					.ingredientItem(new Identifier("randomoresmod", this.texture.id))
					.result(new Identifier("randomoresmod", resource.gem.id))
					.experience(0.7)
					.cookingTime(resource.smeltingTime / 2);
			}
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {
		if (
			biome.getCategory() != Biome.Category.NETHER &&
			biome.getCategory() != Biome.Category.THEEND
		) {
			biome.addFeature(
				GenerationStep.Feature.UNDERGROUND_ORES,
				Feature.ORE.configure(
					new OreFeatureConfig(
						OreFeatureConfig.Target.NATURAL_STONE,
						this.getDefaultState(),
						resource.oreVeinSize //Ore vein size
					)).createDecoratedFeature(
					Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(
						resource.oresPerChunk,  // veins per chunk
						0,                     // bottom offset
						resource.oreMinSpawn, // supposedly min y level. doesn't seem to work.
						resource.oreMaxSpawn // supposedly max y level
					))
				)
			);
		}
	}

	// --------- boilerplate code ---------
	@Override
	public final String getTranslationKey() {
		return RegistrationHelper.getTranslationKey();
	}

	@Environment(EnvType.CLIENT)
	@Override
	public MutableText getName() {
		return RegistrationHelper.getName(this.texture, this.resource);
	}

	public boolean isOpaque(BlockState blockState_1) {
		return true;
	}

	@Override
	public ObjectDetails getResource() {
		return resource;
	}

	@Override
	public TextureInfo getTexture() {
		return texture;
	}

	@Override
	public Item getItem() {
		return item;
	}

	@Override
	public Block getBlock() {
		return this;
	}
}
