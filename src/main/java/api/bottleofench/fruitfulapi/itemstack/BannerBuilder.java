package api.bottleofench.fruitfulapi.itemstack;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.List;

public class BannerBuilder extends ItemStackBuilder {
    public BannerBuilder(Material material) {
        super(Tag.BANNERS.isTagged(material) ? material : Material.WHITE_BANNER);
    }

    public ItemStackBuilder setPattern(int index, Pattern pattern) {
        item.editMeta(itemMeta -> {
            BannerMeta meta = (BannerMeta) itemMeta;
            meta.setPattern(index, pattern);
        });
        return this;
    }

    public ItemStackBuilder setPatterns(Pattern... patterns) {
        item.editMeta(itemMeta -> {
            BannerMeta meta = (BannerMeta) itemMeta;
            meta.setPatterns(List.of(patterns));
        });
        return this;
    }
}
