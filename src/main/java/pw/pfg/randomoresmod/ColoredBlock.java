package pw.pfg.randomoresmod;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.modresource.IItemBlock;
import pw.pfg.randomoresmod.modresource.NamedBlockItem;
import pw.pfg.randomoresmod.modresource.TextureLayer;

public class ColoredBlock
	extends Block
	implements IRegisterable, IItemBlock<Block> {
	protected String id;
	protected Item blockItem;
	protected ResourceObject resourceObject;
	protected ObjectDetails details;

	public ColoredBlock(
		ObjectDetails details,
		ResourceObject resourceObject,
		Block.Settings blockSettings,
		Item.Settings itemSettings
	) {
		super(blockSettings);
		this.details = details;
		this.resourceObject = resourceObject;
		this.id = resourceObject.id;
		this.blockItem = new NamedBlockItem(this, itemSettings);
	}

	@Override
	public final Block self() {
		return this;
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(this.blockItem));
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	@Override
	public final String getTranslationKey() {
		return "this.randomoresmod.should.never.happen";
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName() {
		return new TranslatableText(
			resourceObject.style.languageKey,
			new TranslatableText(details.translationKey)
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

	public void registerMainBlockModel(ModelBuilder model) {
		model.parent(new Identifier("minecraft", "block/block"));
		model.texture("particle", new Identifier("minecraft", "block/stone"));
		int layerNumber = 0;
		for (TextureLayer layer : resourceObject.style.texture) {
			String varName = "layer" + (layerNumber++);
			model.texture(varName, layer.texture);
			model.element(
				elem -> {
					elem.from(0, 0, 0).to(16, 16, 16);
					for (Direction dir : Direction.values()) {
						elem.face(
							dir,
							s -> {
								s.uv(0, 0, 16, 16).texture(varName).cullface(dir);
								if (layer.tinted) {
									s.tintindex(0);
								}
							}
						);
					}
				}
			);
		}
	}

	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		pack.addBlockModel(
			new Identifier("randomoresmod", this.id),
			model -> {
				this.registerMainBlockModel(model);
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
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {}

	@Override
	public void registerClient() {
		ColorProviderRegistry.BLOCK.register(
			(block, world, pos, layer) -> layer == 0 ? details.color : 0,
			this
		);
		ColorProviderRegistry.ITEM.register(
			(item, layer) -> layer == 0 ? details.color : 0,
			this
		);
	// ColorProviderRegistry.ITEM.register(
	// 	(stack, layer) -> layer < resourceObject.style.texture.length
	// 		? resourceObject.style.texture[layer].tinted ? details.color : 16777215
	// 		: 16777215,
	// 	this.blockItem
	// );
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return itemStack_1.hasEnchantmentGlint();
	}
}
