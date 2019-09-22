package pw.pfg.randomoresmod.modresource;

import java.util.Arrays;
import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.RandomOresMod;

public class ModResourceColorPreview extends Item implements IRegisterable {
	ResourceDetails resource;
	String id;
	Item blockItem;

	public ModResourceColorPreview(ResourceDetails resource) {
		super(
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
				.rarity(resource.rarityMC)
		);
		this.resource = resource;
		this.id = resource.resourceId + "_color_preview";
	}

	@Override
	public void registerItemGroup(List<ItemStack> stacks) {
		stacks.add(new ItemStack(this));
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
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.rarity",
					resource.rarity
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.randomoresmod.gem.debug.ore_spawn",
					resource.oreMinSpawn,
					resource.oreMaxSpawn,
					resource.oreVeinSize,
					resource.oresPerChunk
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
		return new TranslatableText(
			"item.randomoresmod.colorpreview",
			new TranslatableText(resource.resourceTranslationKey),
			new TranslatableText("name.randomoresmod.colorpreview.colorpreview")
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
				model.texture(
					"layer" + 0,
					new Identifier("randomoresmod:item/debug_color_preview")
				);
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
			(stack, layer) -> layer == 0 ? resource.color : 16777215,
			this
		);
	}

	@Override
	public void registerBiomeFeatures(Biome biome) {}
}
