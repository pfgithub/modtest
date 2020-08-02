package pw.pfg.randomoresmod.modresource;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.RandomOresMod;
import pw.pfg.randomoresmod.RegistrationHelper;
import pw.pfg.randomoresmod.TextureInfo;

public class ModResourceStorageBlock
	extends Block
	implements IRegisterable, IItemBlock, RegisterableBlockDefaults {
	ResourceDetails resource;
	protected TextureInfo texture;
	Item item;

	public ModResourceStorageBlock(ResourceDetails resource) {
		super(
			Block.Settings.of(Material.METAL, MaterialColor.IRON)
				.strength(
					resource.materialHardness * 9,
					resource.materialResistance * 9
				)
		);
		this.resource = resource;
		this.texture = resource.storageBlock;
		this.item =
			new NamedBlockItem(
				this,
				new Item.Settings()
					.group(RandomOresMod.RESOURCES)
					.rarity(resource.rarityMC)
			);
	}

	@Override
	public boolean hasGlint(ItemStack itemStack_1) {
		return false;
	}

	@Override
	public void register() {
		RegisterableBlockDefaults.super.register();
		if (resource.isFuel) {
			FuelRegistry.INSTANCE.add(
				this,
				Math.min(resource.fuelSmeltingTime * 9, 32767)
			);
		}
	}

	// view (find . -name "iron_ore.json")
	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		RegistrationHelper.registerModels(
			pack,
			this.texture,
			model -> {
				RegistrationHelper.registerDefaultBlockModel(model, this.texture);
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
							pool.entry(
								e -> {
									e.name(new Identifier("randomoresmod", this.texture.id))
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
					.result(new Identifier("randomoresmod", this.texture.id), 1);
			}
		);
		data.addShapelessRecipe(
			new Identifier("randomoresmod", resource.gem.id + "_from_storage_block"),
			shapeless -> {
				shapeless.ingredientItem(
					new Identifier("randomoresmod", this.texture.id)
				)
					.result(new Identifier("randomoresmod", resource.gem.id), 9);
			}
		);
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
	public ResourceDetails getResource() {
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
