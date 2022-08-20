package api.bottleofench.fruitfulapi.itemstack;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.List;

public class MapBuilder extends ItemStackBuilder {
    public MapBuilder() {
        super(Material.FILLED_MAP);
    }

    public MapBuilder setScaling(boolean flag) {
        item.editMeta(itemMeta -> {
            MapMeta meta = (MapMeta) itemMeta;
            meta.setScaling(flag);
        });
        return this;
    }

    public MapBuilder setColor(Color color) {
        item.editMeta(itemMeta -> {
            MapMeta meta = (MapMeta) itemMeta;
            meta.setColor(color);
        });
        return this;
    }

    public MapBuilder setMapView(MapView mapView) {
        item.editMeta(itemMeta -> {
            MapMeta meta = (MapMeta) itemMeta;
            meta.setMapView(mapView);
        });
        return this;
    }

    public MapBuilder setLocationName(String name) {
        item.editMeta(itemMeta -> {
            MapMeta meta = (MapMeta) itemMeta;
            meta.setLocationName(name);
        });
        return this;
    }
}
