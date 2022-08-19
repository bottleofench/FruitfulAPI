# FruitfulAPI

An additional API that simplifies and extends the Spigot / Paper API.

**Warning! The API works on Paper, but it isn't work on Spigot!**

## Features

### ItemStackBuilder
ItemStackBuilder is a class that makes it easy to create ItemStacks.

The class supports most operations with ItemStacks, including listening to events involving a created ItemStack.

#### Examples

```java
ItemStack item = new ItemStackBuilder(Material.STONE_SWORD, 100)
    .setDisplayName(Component.text("The Biggest Sword In The World!"))
    .setDamage(new Random().nextInt(1, 20))
    .build();
```

```java
ItemStack item = new ItemStackBuilder(Material.WRITTEN_BOOK)
    .addPages(Component.text("Page 1"), Component.text("Page 2")/*, ... */)
    .build();
```

```java
ItemStack item = new ItemStackBuilder(Material.STONE_SWORD).addInteractHandler(onInteract -> {
    onInteract.setCancelled(true);
    onInteract.getPlayer().sendMessage(Component.text("Hello!"));
}).build();
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

#### Examples

```java
new EntityBuilder(EntityType.ZOMBIE, location)
    .setCustomName(Component.text("Super Zombie"))
    .setHealth(100).setMaxHealth(100)
    .setVisualFire(true)
    .addDeathListener(onDeath -> {
        onDeath.setCancelled(true);
        onDeath.getEntity().getWorld().playSound(
                onDeath.getEntity().getEyeLocation(), Sound.ITEM_TOTEM_USE, 1, 1
        );
    });
```

```java
new EntityBuilder(EntityType.ZOMBIE, location)
    .setCustomName(Component.text("Fire Zombie"))
    .setVisualFire(true)
    .addAttackListener(onDeath -> {
        onDeath.getEntity().setFireTicks(100);
    });
```

### Custom Events

1. FarmlandTrampleEvent fires when a player tries to trample a farmland.
2. FrostWalkerUseEvent fires when the player transforms a block of water into ice when he is wearing boots with a "Frost Walker" enchantment.
3. ItemFrameCreateEvent fires when a player sets the item frame on a block.

You should listen to custom events just as you listen to normal vanilla events.

## Working with API

1. Add our API as a library to your project. There are three ways to do this: using Maven / Gradle or manually adding a jar file to your project.

### Maven

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.bottleofench</groupId>
    <artifactId>FruitfulAPI</artifactId>
    <version>-SNAPSHOT</version>
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
    implementation 'com.github.bottleofench:FruitfulAPI:-SNAPSHOT'
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