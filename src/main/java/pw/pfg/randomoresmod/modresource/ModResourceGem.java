package pw.pfg.randomoresmod.modresource;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.RandomOresMod;

public class ModResourceGem extends Item implements IRegisterable {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceGem(ResourceDetails resource) {
		super(new Item.Settings().group(RandomOresMod.RESOURCES));
		this.resource = resource;
		this.id = resource.gemId;
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(this));
	}

	@Override
	public String getTranslationKey() {
		return "this.randomoresmod.should.never.happen";
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName() {
		if (resource.gemTranslationKey == "") {
			return new TranslatableText(
				"item.randomoresmod.gem.onepart",
				new TranslatableText(resource.resourceTranslationKey)
			);
		}
		return new TranslatableText(
			"item.randomoresmod.gem",
			new TranslatableText(resource.resourceTranslationKey),
			new TranslatableText(resource.gemTranslationKey)
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
				for (String style : resource.gemStyle) {
					model.texture("layer" + i, new Identifier(style)); // max 4
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
		if (resource.isFuel) {
			FuelRegistry.INSTANCE.add(this, resource.fuelSmeltingTime);
		}
	}

	@Override
	public void registerClient() {
		ColorProviderRegistry.ITEM.register(
			(stack, layer) -> layer == resource.gemStyle.length - 1 ? resource.color
				: 16777215,
			this
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}
}
