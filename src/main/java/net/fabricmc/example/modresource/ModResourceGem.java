package net.fabricmc.example.modresource;

import java.util.Arrays;
import java.util.List;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.IRegisterable;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

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
				new TranslatableText("item.modid.gem.debug.color", resource.color)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.smelting",
					resource.requiresSmelting
						? new TranslatableText("item.modid.gem.debug.smelting.requires")
						: new TranslatableText("item.modid.gem.debug.smelting.automatic")
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.drops",
					resource.dropsMany
						? new TranslatableText("item.modid.gem.debug.drops.one")
						: new TranslatableText("item.modid.gem.debug.drops.many")
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.english_name",
					resource.englishName
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.is_fuel",
					resource.isFuel
						? new TranslatableText("item.modid.gem.debug.is_fuel.is")
						: new TranslatableText("item.modid.gem.debug.is_fuel.is_not")
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.fuel_time",
					resource.fuelTime
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText("item.modid.gem.debug.main_id", resource.id)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.main_translation_key",
					resource.name
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText("item.modid.gem.debug.gem_item_id", resource.gemId)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.gem_translation_key",
					resource.gemName
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText("item.modid.gem.debug.ore_item_id", resource.oreId)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.ore_translation_key",
					resource.oreName
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.hardness",
					resource.materialHardness
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.resistance",
					resource.materialResistance
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.ore_style",
					Arrays.toString(resource.oreStyle)
				)
					.formatted(Formatting.DARK_GRAY)
			);
			tooltip.add(
				new TranslatableText(
					"item.modid.gem.debug.gem_style",
					Arrays.toString(resource.gemStyle)
				)
					.formatted(Formatting.DARK_GRAY)
			);
		}
	}

	@Override
	public String getTranslationKey() {
		return "this.modid.should.never.happen";
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName() {
		if (resource.gemName == "") {
			return new TranslatableText(
				"item.modid.gem.onepart",
				new TranslatableText(resource.name)
			);
		}
		return new TranslatableText(
			"item.modid.gem",
			new TranslatableText(resource.name),
			new TranslatableText(resource.gemName)
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
			new Identifier("modid", this.id),
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
		Registry.register(Registry.ITEM, new Identifier("modid", this.id), this);
		if (resource.isFuel) {
			FuelRegistry.INSTANCE.add(this, resource.fuelTime);
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
}
