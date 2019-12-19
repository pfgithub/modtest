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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.ObjectDetails;
import pw.pfg.randomoresmod.RandomOresMod;
import pw.pfg.randomoresmod.RegistrationHelper;
import pw.pfg.randomoresmod.TextureInfo;
import pw.pfg.randomoresmod.modresource.IItemBlock;
import pw.pfg.randomoresmod.modresource.NamedBlockItem;
import pw.pfg.randomoresmod.modresource.RegisterableBlockDefaults;

public class TreeLeavesBlock
	extends Block
	implements IRegisterable, IItemBlock, RegisterableBlockDefaults { // maybe instead of extending ColoredBlock, have static ColoredBlock.register... methods called by this
	TreeDetails resource;
	TextureInfo texture;
	Item item;

	public TreeLeavesBlock(TreeDetails resource) {
		super(
			FabricBlockSettings.of(Material.LEAVES)
				.strength(0.2F, 0.2F)
				.sounds(BlockSoundGroup.GRASS)
				.build()
		);
		this.resource = resource;
		this.texture = resource.leaves;
		this.item =
			new NamedBlockItem(
				this,
				new Item.Settings().group(RandomOresMod.RESOURCES)
			);
	}

	public boolean isOpaque(BlockState blockState_1) {
		return false;
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return false;
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

	// --------- boilerplate code ---------
	@Override
	public final String getTranslationKey() {
		return RegistrationHelper.getTranslationKey();
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName() {
		return RegistrationHelper.getName(this.texture, this.resource);
	}

	// public boolean isOpaque(BlockState blockState_1) {
	// 	return true;
	// }
	public BlockRenderLayer getRenderLayer() {
		return RegistrationHelper.getRenderLayer();
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
