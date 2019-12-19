package pw.pfg.randomoresmod.modresource;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class NamedBlockItem extends BlockItem {
	IItemBlock block;

	public NamedBlockItem(IItemBlock block_1, Settings item$Settings_1) {
		super(block_1.getBlock(), item$Settings_1);
		this.block = block_1;
	}

	@Override
	public Text getName() {
		return this.getBlock().getName();
	}

	@Override
	public Text getName(ItemStack stack) {
		return this.getBlock().getName();
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack itemStack_1) {
		return this.block.hasEnchantmentGlint(itemStack_1) ||
		super.hasEnchantmentGlint(itemStack_1);
	}
}
