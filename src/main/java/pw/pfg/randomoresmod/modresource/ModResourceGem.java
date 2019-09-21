package pw.pfg.randomoresmod.modresource;

import java.util.Arrays;
import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;

public class ModResourceGem extends Item implements IRegisterable {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceGem(ResourceDetails resource) {
		// new OreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)))
		super(new Item.Settings().group(ItemGroup.MATERIALS));
		this.resource = resource;
		this.id = resource.gemId;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(
		ItemStack itemStack,
		World world,
		List<Text> tooltip,
		TooltipContext tooltipContext
	) {
		if (tooltipContext.isAdvanced()) {
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.color",
					resource.color
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.smelting",
					resource.requiresSmelting
						? new TranslatableText(
						"item.randomoresmod.gem.debug.smelting.requires"
					)
						: new TranslatableText(
						"item.randomoresmod.gem.debug.smelting.automatic"
					)
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.drops",
					resource.dropsMany
						? new TranslatableText("item.randomoresmod.gem.debug.drops.one")
						: new TranslatableText("item.randomoresmod.gem.debug.drops.many")
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.english_name",
					resource.resourceEnglishName
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.is_fuel",
					resource.isFuel
						? new TranslatableText("item.randomoresmod.gem.debug.is_fuel.is")
						: new TranslatableText(
						"item.randomoresmod.gem.debug.is_fuel.is_not"
					)
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.fuel_time",
					resource.fuelSmeltingTime
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.main_id",
					resource.resourceId
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.main_translation_key",
					resource.resourceTranslationKey
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.gem_item_id",
					resource.gemId
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.gem_translation_key",
					resource.gemTranslationKey
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.ore_item_id",
					resource.oreId
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.ore_translation_key",
					resource.oreTranslationKey
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.hardness",
					resource.materialHardness
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.resistance",
					resource.materialResistance
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.ore_style",
					Arrays.toString(resource.oreStyle)
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.gem_style",
					Arrays.toString(resource.gemStyle)
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.smelting_time",
					resource.smeltingTime
				)
					.formatted(Formatting.DARK_GRAY)
			);
		}
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
	public void registerTranslations(TranslationBuilder trans) {
		// TODO Auto-generated method stub
	}

	@Override
	public void registerData(ServerResourcePackBuilder data) {
		// TODO Auto-generated method stub
	}

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
				: 0,
			this
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}
}
