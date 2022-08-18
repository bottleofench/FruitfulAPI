package api.bottleofench.fruitfulapi;

import api.bottleofench.fruitfulapi.events.FarmlandTrampleEvent;
import api.bottleofench.fruitfulapi.events.FrostWalkerUseEvent;
import api.bottleofench.fruitfulapi.events.ItemFrameCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class FruitfulAPI extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {}

    @EventHandler
    public void onFarmlandTrample(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.PHYSICAL))) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.FARMLAND)) return;
        FarmlandTrampleEvent e = new FarmlandTrampleEvent(event.getPlayer(), event.getClickedBlock());
        getServer().getPluginManager().callEvent(e);
        if (e.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    public void onItemFrameItemPlace(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getClickedBlock() == null) return;
        if (event.getItem() == null) return;
        Location loc = event.getInteractionPoint();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            for (Entity entity : loc.getNearbyEntities(0.5, 0.5, 0.5)) {
                if (!(entity instanceof ItemFrame frame)) continue;
                ItemFrameCreateEvent e = new ItemFrameCreateEvent(event.getPlayer(), frame);
                getServer().getPluginManager().callEvent(e);
                if (e.isCancelled()) event.setCancelled(true);
                return;
            }
        }, 2);
    }

    @EventHandler
    public void onFrostWalkerUse(EntityBlockFormEvent event) {
        if (!event.getNewState().getType().equals(Material.FROSTED_ICE)) return;
        if (!(event.getEntity() instanceof Player player)) return;
        FrostWalkerUseEvent e = new FrostWalkerUseEvent(player, event.getNewState().getBlock());
        getServer().getPluginManager().callEvent(e);
        if (e.isCancelled()) event.setCancelled(true);
    }
}
