package api.bottleofench.fruitfulapi.itemstack;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.List;

public class CrossbowBuilder extends ItemStackBuilder {
    public CrossbowBuilder() {
        super(Material.CROSSBOW);
    }

    public CrossbowBuilder setChargedProjectiles(ItemStack... projectile) {
        item.editMeta(itemMeta -> {
            CrossbowMeta meta = (CrossbowMeta) itemMeta;
            meta.setChargedProjectiles(List.of(projectile));
        });
        return this;
    }
}
