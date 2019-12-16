package pw.pfg.randomoresmod.modbiome.plant;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.RandomOresMod;
import pw.pfg.randomoresmod.RegistrationHelper;
import pw.pfg.randomoresmod.TextureInfo;
import pw.pfg.randomoresmod.modresource.IItemBlock;
import pw.pfg.randomoresmod.modresource.NamedBlockItem;

public class TreePlankBlock
	extends Block
	implements IRegisterable, IItemBlock<Block> {
	TreeDetails resource;
	TextureInfo texture;
	Item item;

	public TreePlankBlock(TreeDetails resource) {
		super(
			FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
				.strength(2.0F, 3.0F)
				.sounds(BlockSoundGroup.WOOD)
				.build()
		);
		this.resource = resource;
		this.texture = resource.planks;
		this.item =
			new NamedBlockItem(
				this,
				new Item.Settings().group(RandomOresMod.RESOURCES)
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
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return false;
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(this.item, 1));
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	@Override
	public void register() {
		RegistrationHelper.register(this.texture.id, this, this.item);
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

	@Override
	public void registerClient() {
		RegistrationHelper.registerColorProvider(this, this.item, this.resource);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}

	@Override
	public Block self() {
		return this;
	}

	public boolean isOpaque(BlockState blockState_1) {
		return true;
	}

	public BlockRenderLayer getRenderLayer() {
		return RegistrationHelper.getRenderLayer();
	}
}
