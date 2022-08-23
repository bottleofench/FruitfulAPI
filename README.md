# FruitfulAPI

An additional API that simplifies and extends the Paper API.

**Warning! The API works on Paper, but it isn't work on Spigot!**

## Features

### ItemStackBuilder
ItemStackBuilder is a class that makes it easy to create ItemStacks.

The class supports many operations with ItemStacks, including listening to events involving a created ItemStack.

Many builders are inherited from the class, extending the functionality of this class.

You can see all available builders [here](https://github.com/bottleofench/FruitfulAPI/tree/master/src/main/java/api/bottleofench/fruitfulapi/builders).

#### Examples

```java
ItemStack item = new ItemStackBuilder(Material.STONE_SWORD, 100)
    .setDisplayName(Component.text("The Biggest Sword In The World!"))
    .setDamage(new Random().nextInt(1, 20))
    .build();
```

```java
ItemStack item = new BookBuilder()
        .addPages(Component.text("Page 1"),
        Component.text("Page 2")/*, ... */)
        .build();
```

```java
ItemStack item = new WeaponBuilder(Material.NETHERITE_SWORD).addAttackHandler(event -> {
            event.setDamage(1000);
        })
        .setDisplayName(Component.text("God Sword!").color(NamedTextColor.GOLD))
        .build();
```

### InventoryBuilder
InventoryBuilder is a class that makes it easy to create Bukkit inventories.

The class supports most operations with inventories, including listening to events involving a created inventory.

#### Examples

```java
Inventory inventory = new InventoryBuilder(Component.text("Super Mega Plugin Menu"))
    .addOpenHandler(onOpen -> onOpen.getPlayer().sendMessage(Component.text("Menu is opened!")))
    .addCloseHandler(onClose -> onClose.getPlayer().sendMessage(Component.text("Menu is closed!")))
    .addInventoryClickHandler(onInventoryClick -> onInventoryClick.setCancelled(true))
    .build();
```

```java
Inventory inventory = new InventoryBuilder(Component.text("default title"))
    .setItem(0, new ItemStackBuilder(Material.BARRIER)
    .setDisplayName(Component.text("Don't touch this!")).build(), onClick -> {
        onClick.getWhoClicked().sendMessage(Component.text("Don't touch this!"));
    }).build();
```

### EntityBuilder
EntityBuilder is a class that makes it easy to spawn entities.

The class supports most operations with entities, including listening to events involving a created entity.

The LivingEntityBuilder, a class that allows you to work with living creatures, is inherited from the class.

#### Examples

```java
new LivingEntityBuilder(EntityType.ZOMBIE, location)
        .setHealth(100).setMaxHealth(100)
        .addDeathListener(onDeath -> {
            onDeath.setCancelled(true);
            onDeath.getEntity().getWorld().playSound(
                onDeath.getEntity().getEyeLocation(), Sound.ITEM_TOTEM_USE, 1, 1
            );
        })
        .setCustomName(Component.text("Super Zombie"))
        .setVisualFire(true);
```

```java
new LivingEntityBuilder(EntityType.ZOMBIE, location)
        .addAttackListener(onDeath -> {
            onDeath.getEntity().setFireTicks(100);
        })
        .setCustomName(Component.text("Fire Zombie"))
        .setVisualFire(true);
```

### EventBuilder (Experimental)
EventBuilder is an experimental class that makes it easy to listen to the most popular events.

At the moment, it only allows you to listen to a few events, as it is in experimental status.

#### Examples
```java
new EventBuilder(this).join(playerJoinEvent -> {
        playerJoinEvent.getPlayer().sendMessage(Component.text("Hello!"));
    }).blockBreak(blockBreakEvent -> {
        blockBreakEvent.getPlayer().sendMessage(Component.text("You're broken the block!"));
    });
```

### Custom Events

1. **FarmlandTrampleEvent** fires when a player tries to trample a farmland.
2. **FrostWalkerUseEvent** fires when the player transforms a block of water into ice when he is wearing boots with a "Frost Walker" enchantment.
3. **ItemFrameCreateEvent** fires when a player sets the item frame on a block.
4. **ArmorStandCreateEvent** fires when the player places an armorstand on a block.
5. **PaintingCreateEvent** fires when the player places a painting on the wall.
6. **PlayerUseSpawnEggEvent** fires when the player uses a spawn egg.

You should listen to custom events just as you listen to normal vanilla events.

## Working with API

1. Add our API as a library to your project. There are two ways to do this: using Maven / Gradle.

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.bottleofench</groupId>
    <artifactId>FruitfulAPI</artifactId>
    <version>0.2</version>
    <scope>provided</scope>
</dependency>
```

### Gradle
```
allprojects {
    repositories {
    	...
    	maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.bottleofench:FruitfulAPI:0.2'
}
```

2. Add the API as a plugin to your server, and then in your plugin specify our API in the depend section (or softdepend):

```yml
name: MegaTestPlugin
version: 0.1
main: api.bottleofench.megatestplugin.MegaTestPlugin
api-version: 1.19
depend:
  - FruitfulAPI
```

## TODO
- Check EventBuilder for usefulness;
- Check setAuthor() in BookBuilder.

## Ideas
- Add the ability to create "pages" for inventories;
- Improve EventBuilder class.