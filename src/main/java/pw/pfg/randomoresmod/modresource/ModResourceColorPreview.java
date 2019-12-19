package pw.pfg.randomoresmod.modresource;

import java.util.List;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;
import com.swordglowsblue.artifice.api.ArtificeResourcePack.ServerResourcePackBuilder;
import com.swordglowsblue.artifice.api.builder.assets.TranslationBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import pw.pfg.randomoresmod.IRegisterable;
import pw.pfg.randomoresmod.RandomOresMod;
import pw.pfg.randomoresmod.RegistrationHelper;
import pw.pfg.randomoresmod.Style;
import pw.pfg.randomoresmod.TextureInfo;

public class ModResourceColorPreview
	extends Item
	implements IRegisterable, RegisterableItemDefaults {
	ResourceDetails resource;
	TextureInfo texture;

	public ModResourceColorPreview(ResourceDetails resource) {
		super(
			new Item.Settings()
				.group(RandomOresMod.RESOURCES)
				.rarity(resource.rarityMC)
		);
		this.resource = resource;
		this.texture =
			new TextureInfo(
				resource.baseID + "_color_preview",
				new Style(
					new TextureLayer.SetBuilder()
						.add(new Identifier("randomoresmod:item/debug_color_preview"), true)
						.build(),
					"name.randomoresmod.debug.colorpreview"
				)
			);
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

	// --------- boilerplate code ---------
	@Override
	public final String getTranslationKey() {
		return RegistrationHelper.getTranslationKey();
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName() {
		return RegistrationHelper.getName(texture, resource);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public Text getName(ItemStack itemStack) {
		return this.getName();
	}

	public ResourceDetails getResource() {
		return resource;
	}

	public TextureInfo getTexture() {
		return texture;
	}

	public Item getItem() {
		return this;
	}
}
