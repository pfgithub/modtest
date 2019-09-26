package pw.pfg.randomoresmod.modresource;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pw.pfg.randomoresmod.ColoredItem;
import pw.pfg.randomoresmod.RandomOresMod;

public class ModResourceGem extends ColoredItem {
	ResourceDetails resource;

	public ModResourceGem(ResourceDetails resource) {
		super(
			resource,
			resource.gem,
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
			FuelRegistry.INSTANCE.add(this, resource.fuelSmeltingTime);
		}
	}
}
