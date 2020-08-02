package pw.pfg.randomoresmod;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import pw.pfg.randomoresmod.modresource.TextureLayer;

public class RegistrationHelper {
	protected String id;
	protected Item blockItem;
	protected TextureInfo resourceObject;
	protected ObjectDetails details;

	public static interface BlockModelRegisterer {
		void call(ModelBuilder builder);
	}

	public static void addToItemGroup(Item blockItem, List<ItemStack> stacks) {
		stacks.add(new ItemStack(blockItem));
	}

	public static void registerTranslations(TranslationBuilder trans) {}

	public static String getTranslationKey() {
		return "this.randomoresmod.should.never.happen";
	}

	@Environment(EnvType.CLIENT)
	public static MutableText getName(TextureInfo texture, ObjectDetails resource) {
		return new TranslatableText(
			texture.style.languageKey,
			new TranslatableText(resource.translationKey)
		);
	}

	public static void register(String id, Block block, Item item) {
		RegistrationHelper.register(id, block);
		RegistrationHelper.register(id, item);
	}

	public static void register(String id, Item item) {
		Registry.register(Registry.ITEM, new Identifier("randomoresmod", id), item);
	}

	public static void register(String id, Block block) {
		Registry.register(
			Registry.BLOCK,
			new Identifier("randomoresmod", id),
			block
		);
	}

	public static void registerDefaultBlockModel(
		ModelBuilder model,
		TextureInfo texture
	) {
		model.parent(new Identifier("minecraft", "block/block"));
		model.texture("particle", new Identifier("minecraft", "block/stone"));
		int layerNumber = 0;
		for (TextureLayer layer : texture.style.texture) {
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

	public static void registerItemModels(
		ClientResourcePackBuilder pack,
		TextureInfo texture
	) {
		pack.addItemModel(
			new Identifier("randomoresmod", texture.id),
			model -> {
				model.parent(new Identifier("minecraft", "item/generated"));
				int i = 0;
				for (TextureLayer texture2 : texture.style.texture) {
					model.texture("layer" + i, texture2.texture); // max 4
					i++;
				}
			}
		);
	}

	public static void registerModels(
		ClientResourcePackBuilder pack,
		TextureInfo texture,
		BlockModelRegisterer registermodelblockregistererregister
	) {
		pack.addBlockModel(
			new Identifier("randomoresmod", texture.id),
			model -> {
				registermodelblockregistererregister.call(model);
			}
		);
		pack.addBlockState(
			new Identifier("randomoresmod", texture.id),
			f -> {
				f.variant(
					"",
					v -> {
						v.model(new Identifier("randomoresmod", "block/" + texture.id));
					}
				);
			}
		);
		pack.addItemModel(
			new Identifier("randomoresmod", texture.id),
			model -> model.parent(
				new Identifier("randomoresmod", "block/" + texture.id)
			)
		);
	}

	public static boolean isOpaque(BlockState blockState_1) {
		return true;
	}

	public static void registerColorProvider(
		Block block,
		ObjectDetails resource
	) {
		ColorProviderRegistry.BLOCK.register(
			(blockB, world, pos, layer) -> layer == 0 ? resource.color : 0,
			block
		);
	}

	public static void registerColorProvider(Item item, ObjectDetails resource) {
		ColorProviderRegistry.ITEM.register(
			(itemStackS, layer) -> layer == 0 ? resource.color : 0,
			item
		);
	}

	public static void registerColorProvider(
		Block block,
		Item item,
		ObjectDetails resource
	) {
		RegistrationHelper.registerColorProvider(block, resource);
		RegistrationHelper.registerColorProvider(item, resource);
	}
}
