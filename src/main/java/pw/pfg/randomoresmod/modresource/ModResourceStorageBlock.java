package pw.pfg.randomoresmod.modresource;

import java.util.List;
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
import net.minecraft.block.MaterialColor;
import net.minecraft.block.OreBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
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
import pw.pfg.randomoresmod.RandomOresMod;

public class ModResourceStorageBlock extends Block implements IRegisterable {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceStorageBlock(ResourceDetails resource) {
		super(
			Block.Settings.of(Material.METAL, MaterialColor.IRON)
				.strength(
					resource.materialHardness * 9,
					resource.materialResistance * 9
				)
		);
		this.resource = resource;
		this.id = resource.storageBlockId;

		this.blockItem =
			new NamedBlockItem(
				this,
				new Item.Settings().group(RandomOresMod.RESOURCES)
			);
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(this.blockItem));
	}

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
			"block.randomoresmod.storageblock",
			new TranslatableText(resource.resourceTranslationKey),
			new TranslatableText(resource.storageBlockTranslationKey)
		);
	}

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
				for (String style : resource.storageBlockStyle) {
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
										if (nextLayerNumber == resource.storageBlockStyle.length) {
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
		return BlockRenderLayer.TRANSLUCENT;
	// return BlockRenderLayer.CUTOUT_MIPPED;
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
							pool.entry(
								e -> e.name(new Identifier("randomoresmod", this.id))
									.type(new Identifier("minecraft:item"))
							);
							pool.condition(
								new Identifier("minecraft:survives_explosion"),
								cond -> {}
							);
						}
					);
			}
		);
		data.addShapedRecipe(
			new Identifier("randomoresmod", resource.storageBlockId + "_from_gem"),
			shapeless -> {
				shapeless.group(new Identifier("randomoresmod", resource.gemId))
					.pattern("###", "###", "###")
					.ingredientItem('#', new Identifier("randomoresmod", resource.gemId))
					.result(new Identifier("randomoresmod", this.id), 1);
			}
		);
		data.addShapelessRecipe(
			new Identifier("randomoresmod", resource.gemId + "_from_storage_block"),
			shapeless -> {
				shapeless.ingredientItem(new Identifier("randomoresmod", this.id))
					.result(new Identifier("randomoresmod", resource.gemId), 9);
			}
		);
	}

	@Override
	public void registerClient() {
		// it should be possible for one ore to be the same color as grass
		ColorProviderRegistry.BLOCK.register((block, world, pos, layer) -> layer == 0 ? resource.color : 0, this);
		ColorProviderRegistry.ITEM.register(
			(stack, layer) -> layer == 0 ? resource.color : 16777215,
			this.blockItem
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}
}
