package pw.pfg.randomoresmod.modresource;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IItemBlock<T extends Block> {
    public T self();
    public boolean hasEnchantmentGlint(ItemStack itemStack_1);
}