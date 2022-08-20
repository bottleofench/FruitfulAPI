package api.bottleofench.fruitfulapi.itemstack;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherArmorBuilder extends ItemStackBuilder {
    public LeatherArmorBuilder(Material material) {
        super(material == Material.LEATHER_HELMET
                || material == Material.LEATHER_CHESTPLATE
                || material == Material.LEATHER_LEGGINGS
                || material == Material.LEATHER_BOOTS ? material : Material.LEATHER_CHESTPLATE);
    }

    public LeatherArmorBuilder setColor(Color color) {
        item.editMeta(itemMeta -> {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;
            meta.setColor(color);
        });
        return this;
    }

    public LeatherArmorBuilder clearColor() {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(null);
        item.setItemMeta(meta);
        return this;
    }
}
