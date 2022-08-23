package api.bottleofench.fruitfulapi;

import api.bottleofench.fruitfulapi.builders.entity.LivingEntityBuilder;
import api.bottleofench.fruitfulapi.builders.event.EventBuilder;
import api.bottleofench.fruitfulapi.builders.itemstack.BookBuilder;
import api.bottleofench.fruitfulapi.builders.itemstack.WeaponBuilder;
import api.bottleofench.fruitfulapi.events.*;
import com.destroystokyo.paper.MaterialTags;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class FruitfulAPI extends JavaPlugin implements Listener {

    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Location location = null;

        new EventBuilder(this).join(playerJoinEvent -> {
            playerJoinEvent.getPlayer().sendMessage(Component.text("Hello!"));
        }).blockBreak(blockBreakEvent -> {
            blockBreakEvent.getPlayer().sendMessage(Component.text("You're broken the block!"));
        });

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onFarmlandTrample(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.PHYSICAL))) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.FARMLAND)) return;
        FarmlandTrampleEvent e = new FarmlandTrampleEvent(event.getPlayer(), event.getClickedBlock());
        getServer().getPluginManager().callEvent(e);
        if (e.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    private void onItemFrameItemPlace(HangingPlaceEvent event) {
        if (!(event.getEntity() instanceof ItemFrame frame)) return;
        ItemFrameCreateEvent e = new ItemFrameCreateEvent(event.getPlayer(), frame);
        getServer().getPluginManager().callEvent(e);
        if (e.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    private void onPaintingItemPlace(HangingPlaceEvent event) {
        if (!(event.getEntity() instanceof Painting painting)) return;
        PaintingCreateEvent e = new PaintingCreateEvent(event.getPlayer(), painting);
        getServer().getPluginManager().callEvent(e);
        if (e.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    private void onArmorStandItemPlace(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getClickedBlock() == null) return;
        if (event.getItem() == null) return;
        if (!(event.getItem().getType().equals(Material.ARMOR_STAND))) return;
        Location loc = event.getInteractionPoint();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            for (Entity entity : loc.getNearbyEntities(0.5, 0.5, 0.5)) {
                if (!(entity instanceof ArmorStand armorStand)) continue;
                ArmorStandCreateEvent e = new ArmorStandCreateEvent(event.getPlayer(), armorStand);
                getServer().getPluginManager().callEvent(e);
                if (e.isCancelled()) event.setCancelled(true);
                return;
            }
        }, 2);
    }

    @EventHandler
    private void onPlayerUseSpawnEgg(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getClickedBlock() == null) return;
        if (event.getItem() == null) return;
        if (!MaterialTags.SPAWN_EGGS.isTagged(event.getItem())) return;
        Location loc = event.getInteractionPoint();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            for (Entity entity : loc.getNearbyEntities(0.25, 0.25, 0.25)) {
                if (!(entity instanceof LivingEntity livingEntity)) continue;
                PlayerUseSpawnEggEvent e = new PlayerUseSpawnEggEvent(event.getPlayer(), event.getItem(), livingEntity);
                getServer().getPluginManager().callEvent(e);
                if (e.isCancelled()) event.setCancelled(true);
                return;
            }
        }, 2);
    }

    @EventHandler
    private void onFrostWalkerUse(EntityBlockFormEvent event) {
        if (!event.getNewState().getType().equals(Material.FROSTED_ICE)) return;
        if (!(event.getEntity() instanceof Player player)) return;
        FrostWalkerUseEvent e = new FrostWalkerUseEvent(player, event.getNewState().getBlock());
        getServer().getPluginManager().callEvent(e);
        if (e.isCancelled()) event.setCancelled(true);
    }
}
