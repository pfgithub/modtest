package pw.pfg.randomoresmod.modresource;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.ColoredBlock;
import pw.pfg.randomoresmod.RandomOresMod;

public class ModResourceStorageBlock extends ColoredBlock {
	ResourceDetails resource;
	Item blockItem;

	public ModResourceStorageBlock(ResourceDetails resource) {
		super(
			resource,
			resource.storageBlock,
			Block.Settings.of(Material.METAL, MaterialColor.IRON)
				.strength(
					resource.materialHardness * 9,
					resource.materialResistance * 9
				),
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
				.rarity(resource.rarityMC)
		);
		this.resource = resource;
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return false;
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		super.registerItemGroup(stacks);
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {
		super.registerTranslations(trans);
	}

	@Override
	public void register() {
		super.register();
		if (resource.isFuel) {
			FuelRegistry.INSTANCE.add(
				this,
				Math.min(resource.fuelSmeltingTime * 9, 32767)
			);
		}
	}

	@Override
	public void registerMainBlockModel(ModelBuilder model) {
		super.registerMainBlockModel(model);

		if (this.resource.isShiny) {
			String layerVarname = "enchantment_glint";
			model.texture(
				layerVarname,
				new Identifier("randomoresmod", "block/enchantment_glint")
			);
			model.element(
				elem -> {
					elem.from(0, 0, 0).to(16, 16, 16);
					for (Direction dir : Direction.values()) {
						elem.face(
							dir,
							s -> {
								s.uv(0, 0, 16, 16).texture(layerVarname).cullface(dir);
							}
						);
					}
				}
			);
		}
	}

	// view (find . -name "iron_ore.json")
	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		super.registerAssets(pack);
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
							pool.entry(
								e -> {
									e.name(new Identifier("randomoresmod", this.id))
										.type(new Identifier("minecraft:item"));
								}
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
			new Identifier("randomoresmod", resource.storageBlock.id + "_from_gem"),
			shapeless -> {
				shapeless.group(new Identifier("randomoresmod", resource.gem.id))
					.pattern("###", "###", "###")
					.ingredientItem('#', new Identifier("randomoresmod", resource.gem.id))
					.result(new Identifier("randomoresmod", this.id), 1);
			}
		);
		data.addShapelessRecipe(
			new Identifier("randomoresmod", resource.gem.id + "_from_storage_block"),
			shapeless -> {
				shapeless.ingredientItem(new Identifier("randomoresmod", this.id))
					.result(new Identifier("randomoresmod", resource.gem.id), 9);
			}
		);
	}

	@Override
	public void registerClient() {
		super.registerClient();
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {
		super.registerBiomeFeatures(biome);
	}
}
