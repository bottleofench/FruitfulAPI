package api.bottleofench.fruitfulapi.builders.entity;

import api.bottleofench.fruitfulapi.exceptions.EntityBuildException;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.function.Consumer;

public class LivingEntityBuilder extends EntityBuilder {
    public LivingEntityBuilder(EntityType entityType, Location location) {
        super(entityType.getEntityClass().isAssignableFrom(LivingEntity.class) ? entityType : null, location);
    }

    public LivingEntityBuilder setAI(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setAI() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setAI(flag);
        return this;
    }

    public LivingEntityBuilder setAttributeBaseValue(Attribute attribute, double value) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setAttributeBaseValue() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.getAttribute(attribute).setBaseValue(value);
        return this;
    }

    public LivingEntityBuilder setInvisible(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setInvisible() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setInvisible(flag);
        return this;
    }

    public LivingEntityBuilder setHealth(double health) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setHealth() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setHealth(health);
        return this;
    }

    public LivingEntityBuilder setMaxHealth(double maxHealth) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setMaxHealth() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        return this;
    }

    public LivingEntityBuilder setRemoveWhenFarAway(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setRemoveWhenFarAway() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setRemoveWhenFarAway(flag);
        return this;
    }

    public LivingEntityBuilder setCanPickupItems(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setRemoveWhenFarAway() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setCanPickupItems(flag);
        return this;
    }

    public LivingEntityBuilder addPotionEffects(PotionEffect... effects) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("addPotionEffects() was not successfully executed because entity isn't instance of LivingEntity!");
        }

        livingEntity.addPotionEffects(List.of(effects));
        return this;
    }

    public LivingEntityBuilder editEquipment(Consumer<EntityEquipment> equipmentConsumer) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("editEquipment() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        equipmentConsumer.accept(livingEntity.getEquipment());
        return this;
    }
}
