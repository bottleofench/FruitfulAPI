package api.bottleofench.fruitfulapi.itemstack;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class PotionBuilder extends ItemStackBuilder {
    public PotionBuilder() {
        super(Material.POTION);
    }

    public PotionBuilder(Material material) {
        super(material.equals(Material.POTION) ||
                material.equals(Material.LINGERING_POTION) ||
                material.equals(Material.SPLASH_POTION) ? material : Material.POTION);
    }

    public PotionBuilder setPotionColor(Color color) {
        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            meta.setColor(color);
        });
        return this;
    }

    public PotionBuilder setBasePotionData(PotionData data) {
        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            meta.setBasePotionData(data);
        });
        return this;
    }

    public PotionBuilder setCustomEffects(boolean override, PotionEffect... effects) {
        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            meta.clearCustomEffects();
            List.of(effects).forEach(potionEffect -> meta.addCustomEffect(potionEffect, override));
        });
        return this;
    }

    public PotionBuilder addCustomEffects(boolean override, PotionEffect... effects) {
        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            List.of(effects).forEach(potionEffect -> meta.addCustomEffect(potionEffect, override));
        });
        return this;
    }
}
