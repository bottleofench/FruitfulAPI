package api.bottleofench.fruitfulapi.itemstack;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

public class TropicalFishBucketBuilder extends ItemStackBuilder {
    public TropicalFishBucketBuilder() {
        super(Material.TROPICAL_FISH_BUCKET);
    }

    public TropicalFishBucketBuilder setPattern(TropicalFish.Pattern pattern) {
        item.editMeta(itemMeta -> {
            TropicalFishBucketMeta meta = (TropicalFishBucketMeta) itemMeta;
            meta.setPattern(pattern);
        });
        return this;
    }

    public TropicalFishBucketBuilder setPatternColor(DyeColor dyeColor) {
        item.editMeta(itemMeta -> {
            TropicalFishBucketMeta meta = (TropicalFishBucketMeta) itemMeta;
            meta.setPatternColor(dyeColor);
        });
        return this;
    }

    public TropicalFishBucketBuilder setBodyColor(DyeColor dyeColor) {
        item.editMeta(itemMeta -> {
            TropicalFishBucketMeta meta = (TropicalFishBucketMeta) itemMeta;
            meta.setBodyColor(dyeColor);
        });
        return this;
    }
}
