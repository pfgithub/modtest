package pw.pfg.randomoresmod;

import java.util.List;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.modresource.TextureLayer;

public class ColoredItem
	extends Item
	implements IRegisterable {
	protected String id;
	protected Item blockItem;
	protected ResourceObject resourceObject;
	protected ObjectDetails details;

	public ColoredItem(
		ObjectDetails details,
		ResourceObject resourceObject,
		Item.Settings itemSettings
	) {
		super(itemSettings);
		this.details = details;
		this.resourceObject = resourceObject;
		this.id = resourceObject.id;
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(this));
	}

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
	public Text getName(ItemStack itemStack) {
		return this.getName();
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	@Override
	public void registerData(ServerResourcePackBuilder data) {}

	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		pack.addItemModel(
			new Identifier("randomoresmod", this.id),
			model -> {
				model.parent(new Identifier("minecraft", "item/generated"));
				int i = 0;
				for (TextureLayer texture : resourceObject.style.texture) {
					model.texture("layer" + i, texture.texture); // max 4
					i++;
				}
			}
		);
	}

	@Override
	public void register() {
		Registry.register(
			Registry.ITEM,
			new Identifier("randomoresmod", this.id),
			this
		);
	}

	@Override
	public void registerClient() {
		ColorProviderRegistry.ITEM.register(
			(stack, layer) -> layer < resourceObject.style.texture.length
				? resourceObject.style.texture[layer].tinted ? details.color : 16777215
				: 16777215,
			this
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}
}
