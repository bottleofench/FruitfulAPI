package api.bottleofench.fruitfulapi.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class SuspiciousStewBuilder extends ItemStackBuilder {
    public SuspiciousStewBuilder() {
        super(Material.SUSPICIOUS_STEW);
    }

    public SuspiciousStewBuilder addCustomEffects(boolean override, PotionEffect... effects) {
        item.editMeta(itemMeta -> {
            SuspiciousStewMeta meta = (SuspiciousStewMeta) itemMeta;
            List.of(effects).forEach(effect -> meta.addCustomEffect(effect, override));
        });
        return this;
    }
}
