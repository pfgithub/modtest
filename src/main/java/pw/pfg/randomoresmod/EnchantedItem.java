package pw.pfg.randomoresmod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem extends Item {

    public EnchantedItem(Settings item$Settings_1) {
        super(item$Settings_1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean hasGlint(ItemStack itemStack_1) {
        return true;
    }

}
