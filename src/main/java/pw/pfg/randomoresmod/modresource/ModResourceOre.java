package pw.pfg.randomoresmod.modresource;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.RandomOresMod;
import pw.pfg.randomoresmod.RegistrationHelper;
import pw.pfg.randomoresmod.TextureInfo;

public class ModResourceOre
	extends Block
	implements IRegisterable, IItemBlock<Block> {
	Item blockItem;
	ResourceDetails resource;
	protected TextureInfo texture;

	public ModResourceOre(ResourceDetails resource) {
		// new OreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)))
		super(
			Block.Settings.of(Material.STONE)
				.strength(resource.materialHardness, resource.materialResistance)
		);
		this.resource = resource;
		this.texture = resource.ore;
		this.blockItem =
			new NamedBlockItem(
				this,
				new Item.Settings()
					.group(RandomOresMod.RESOURCES)
					.rarity(resource.rarityMC)
			);
	}

	@Override
	public final Block self() {
		return this;
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack) {
		return resource.isShiny;
	}

	@Override
	public final String getTranslationKey() {
		return RegistrationHelper.getTranslationKey();
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName() {
		return RegistrationHelper.getName(this.texture, this.resource);
	}

	@Override
	public void register() {
		RegistrationHelper.register(this.texture.id, this, this.blockItem);
	}

	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		RegistrationHelper.registerModels(
			pack,
			this.texture,
			model -> {
				RegistrationHelper.registerDefaultBlockModel(model, this.texture);
			}
		);
	}

	public boolean isOpaque(BlockState blockState_1) {
		return true;
	}

	public BlockRenderLayer getRenderLayer() {
		return RegistrationHelper.getRenderLayer();
	}

	@Override
	public void registerClient() {
		RegistrationHelper.registerColorProvider(
			this,
			this.blockItem,
			this.resource
		);
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		data.addLootTable(
			new Identifier("randomoresmod", "blocks/" + this.texture.id),
			ltp -> {
				ltp.type(new Identifier("randomoresmod", "block/" + this.texture.id))
					.pool(
						pool -> {
							pool.rolls(1);
							if (resource.requiresSmelting || true) {
								pool.entry(
									e -> e.name(new Identifier("randomoresmod", this.texture.id))
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

	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(this));
	}
}
