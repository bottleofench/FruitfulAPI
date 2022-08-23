package api.bottleofench.fruitfulapi.builders.itemstack;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WeaponBuilder extends ItemStackBuilder implements Listener {
    protected List<Consumer<EntityDamageByEntityEvent>> attackHandlers = new ArrayList<>();

    public WeaponBuilder(Material material) {
        super(MaterialTags.AXES.isTagged(material) || MaterialTags.SWORDS.isTagged(material)
                ? material : Material.STONE_SWORD);
    }

    @SafeVarargs
    public final WeaponBuilder addAttackHandler(Consumer<EntityDamageByEntityEvent>... consumers) {
        this.attackHandlers.addAll(List.of(consumers));
        return this;
    }

    @EventHandler
    private void handleAttack(EntityDamageByEntityEvent event) {
        this.attackHandlers.forEach(attackHandler -> attackHandler.accept(event));
    }
}
