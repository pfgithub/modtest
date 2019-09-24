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

public class ModResourceNugget extends Item implements IRegisterable {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceNugget(ResourceDetails resource) {
		super(
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
				.rarity(resource.rarityMC)
		);
		this.resource = resource;
		this.id = resource.nugget.id;
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return resource.isShiny || super.hasEnchantmentGlint(itemStack_1);
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
		return new TranslatableText(
			resource.nugget.style.languageKey,
			new TranslatableText(resource.resourceTranslationKey)
		);
	}

	@Override
	public Text getName(ItemStack itemStack) {
		return this.getName();
	}

	@Override
	public void registerTranslations(TranslationBuilder trans) {}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		data.addShapedRecipe(
			new Identifier("randomoresmod", resource.gem.id + "_from_nugget"),
			shaped -> {
				shaped.group(new Identifier("randomoresmod", this.id))
					.pattern("###", "###", "###")
					.ingredientItem('#', new Identifier("randomoresmod", this.id))
					.result(new Identifier("randomoresmod", resource.gem.id), 1);
			}
		);
		data.addShapelessRecipe(
			new Identifier("randomoresmod", this.id + "_from_gem"),
			shapeless -> {
				shapeless.ingredientItem(
					new Identifier("randomoresmod", resource.gem.id)
				)
					.result(new Identifier("randomoresmod", this.id), 9);
			}
		);
	}

	@Override
	public void registerAssets(ClientResourcePackBuilder pack) {
		pack.addItemModel(
			new Identifier("randomoresmod", this.id),
			model -> {
				model.parent(new Identifier("minecraft", "item/generated"));
				int i = 0;
				for (TextureLayer texture : resource.nugget.style.texture) {
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
		if (resource.isFuel) {
			FuelRegistry.INSTANCE.add(this, resource.fuelSmeltingTime);
		}
	}

	@Override
	public void registerClient() {
		ColorProviderRegistry.ITEM.register(
			(stack, layer) -> layer < resource.nugget.style.texture.length
				? resource.nugget.style.texture[layer].tinted ? resource.color
				: 16777215
				: 16777215,
			this
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}
}
