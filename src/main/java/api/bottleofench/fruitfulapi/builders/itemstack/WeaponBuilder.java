package api.bottleofench.fruitfulapi.builders.itemstack;

import api.bottleofench.fruitfulapi.builders.event.EventBuilder;
import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WeaponBuilder<T extends ItemMeta> extends ItemStackBuilder<T> {
    protected List<Consumer<EntityDamageByEntityEvent>> attackHandlers = new ArrayList<>();

    public WeaponBuilder(Material material, Consumer<T> consumer) {
        super(MaterialTags.AXES.isTagged(material) || MaterialTags.SWORDS.isTagged(material)
                ? material : Material.STONE_SWORD, consumer);

        new EventBuilder<EntityDamageByEntityEvent>(event -> {
            if (!(event.getDamager() instanceof LivingEntity entity)) return;
            if (entity.getEquipment() == null) return;
            if (!entity.getEquipment().getItemInMainHand().isSimilar(super.build())) return;
            this.attackHandlers.forEach(attackHandler -> attackHandler.accept(event));
        });
    }

    @SafeVarargs
    public final WeaponBuilder<T> addAttackHandler(Consumer<EntityDamageByEntityEvent>... consumers) {
        this.attackHandlers.addAll(List.of(consumers));
        return this;
    }
}
