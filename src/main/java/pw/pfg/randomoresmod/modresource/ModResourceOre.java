package pw.pfg.randomoresmod.modresource;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import pw.pfg.randomoresmod.IRegisterable;

public class ModResourceOre extends OreBlock implements IRegisterable {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceOre(ResourceDetails resource) {
		// new OreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)))
		super(
			Block.Settings.of(Material.STONE)
				.strength(resource.materialHardness, resource.materialResistance)
		);
		this.resource = resource;
		this.id = resource.oreId;
		this.blockItem =
			new NamedBlockItem(this, new Item.Settings().group(ItemGroup.MATERIALS));
	}

	// public void registerData(ServerResourcePackBuilder pack) {}
	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	@Override
	public String getTranslationKey() {
		return "this.randomoresmod.should.never.happen";
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName() {
		return new TranslatableText(
			"block.randomoresmod.oreblock",
			new TranslatableText(resource.resourceTranslationKey),
			new TranslatableText(resource.oreTranslationKey)
		);
	}

	// public void registerAssets(ClientResourcePackBuilder pack) {
	// 	// pack.addItemModel(id, f);
	// // pack.addTranslations(new Identifier("randomoresmod", "en_US"), trans -> {});
	// // todo: do a thing that adds a slab for every block as an easy example test
	// }
	@Override
	public void register() {
		Registry.register(
			Registry.BLOCK,
			new Identifier("randomoresmod", this.id),
			this
		);
		Registry.register(
			Registry.ITEM,
			new Identifier("randomoresmod", this.id),
			this.blockItem
		);
	}

	// view (find . -name "iron_ore.json")
	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		pack.addBlockModel(
			new Identifier("randomoresmod", this.id),
			model -> {
				model.parent(new Identifier("minecraft", "block/block"));
				model.texture("particle", new Identifier("minecraft", "block/stone"));
				int layerNumber = 0;
				for (String style : resource.oreStyle) {
					String varName = "layer" + (layerNumber++);
					final int nextLayerNumber = layerNumber;
					model.texture(varName, new Identifier(style));
					model.element(
						elem -> {
							elem.from(0, 0, 0).to(16, 16, 16);
							for (Direction dir : Direction.values()) {
								elem.face(
									dir,
									s -> {
										s.uv(0, 0, 16, 16).texture(varName).cullface(dir);
										if (nextLayerNumber == resource.oreStyle.length) {
											s.tintindex(0);
										}
									}
								);
							}
						}
					);
				}
			}
		);
		pack.addBlockState(
			new Identifier("randomoresmod", this.id),
			f -> {
				f.variant(
					"",
					v -> {
						v.model(new Identifier("randomoresmod", "block/" + this.id));
					}
				);
			}
		);
		pack.addItemModel(
			new Identifier("randomoresmod", this.id),
			model -> model.parent(new Identifier("randomoresmod", "block/" + this.id))
		);
	}

	public boolean isOpaque(BlockState blockState_1) {
		return true;
	}

	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
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
							} else {
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
							}
						}
					);
			}
		);
		data.addSmeltingRecipe(
			new Identifier("randomoresmod", resource.gemId + "_from_smelting"),
			smelting -> {
				smelting.type(new Identifier("minecraft", "smelting"))
					.ingredientItem(new Identifier("randomoresmod", this.id))
					.result(new Identifier("randomoresmod", resource.gemId))
					.experience(0.7)
					.cookingTime(resource.smeltingTime);
			}
		);
		data.addBlastingRecipe(
			new Identifier("randomoresmod", resource.gemId + "_from_smelting"),
			smelting -> {
				smelting.type(new Identifier("minecraft", "blasting"))
					.ingredientItem(new Identifier("randomoresmod", this.id))
					.result(new Identifier("randomoresmod", resource.gemId))
					.experience(0.7)
					.cookingTime(resource.smeltingTime / 2);
			}
		);
	}

	@Override
	public void registerClient() {
		// it should be possible for one ore to be the same color as grass
		ColorProviderRegistry.BLOCK.register((block, world, pos, layer) -> layer == 0 ? resource.color : 0, this);
		ColorProviderRegistry.ITEM.register(
			(stack, layer) -> layer == 0 ? resource.color : 0,
			this.blockItem
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
				Biome.configureFeature(
					Feature.ORE,
					new OreFeatureConfig(
						OreFeatureConfig.Target.NATURAL_STONE,
						this.getDefaultState(),
						8 //Ore vein size
					),
					Decorator.COUNT_RANGE,
					new RangeDecoratorConfig(8, 0, 0, 64) //Number of veins per chunk //Bottom Offset //Min y level //Max y level
				)
			);
		}
	}
}
