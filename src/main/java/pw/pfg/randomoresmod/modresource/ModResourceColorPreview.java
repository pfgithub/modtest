package pw.pfg.randomoresmod.modresource;

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
import net.minecraft.text.LiteralText;
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
			String resourceToString = resource.toString();
			for (String v : resourceToString.split("\n")) {
				tooltip.add(new LiteralText(v).formatted(Formatting.DARK_GRAY));
			}
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
			"name.randomoresmod.colorpreview.colorpreview",
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
	public void registerData(ServerResourcePackBuilder data) {}

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
