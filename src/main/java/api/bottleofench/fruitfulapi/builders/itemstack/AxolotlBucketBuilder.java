package api.bottleofench.fruitfulapi.builders.itemstack;

import org.bukkit.Material;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.meta.AxolotlBucketMeta;

public class AxolotlBucketBuilder extends ItemStackBuilder {
    public AxolotlBucketBuilder() {
        super(Material.AXOLOTL_BUCKET);
    }

    public AxolotlBucketBuilder setPattern(Axolotl.Variant pattern) {
        item.editMeta(itemMeta -> {
            AxolotlBucketMeta meta = (AxolotlBucketMeta) itemMeta;
            meta.setVariant(pattern);
        });
        return this;
    }
}
