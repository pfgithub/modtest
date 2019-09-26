package pw.pfg.randomoresmod.modresource;

import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import pw.pfg.randomoresmod.ColoredItem;
import pw.pfg.randomoresmod.RandomOresMod;

public class ModResourceNugget extends ColoredItem {
	ResourceDetails resource;

	public ModResourceNugget(ResourceDetails resource) {
		super(
			resource,
			resource.nugget,
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
				.rarity(resource.rarityMC)
		);
		this.resource = resource;
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return resource.isShiny || super.hasEnchantmentGlint(itemStack_1);
	}

	@Override
	public void register() {
		super.register();
		if (resource.isFuel) {
			FuelRegistry.INSTANCE.add(this, resource.fuelSmeltingTime / 9);
		}
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		super.registerData(data);
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
}
