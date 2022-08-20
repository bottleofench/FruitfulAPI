package api.bottleofench.fruitfulapi.itemstack;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookBuilder extends ItemStackBuilder {
    public BookBuilder() {
        super(new ItemStack(Material.WRITTEN_BOOK));
    }

    public ItemStackBuilder addPages(Component... pages) {
        super.item.editMeta(itemMeta -> {
            BookMeta meta = (BookMeta) itemMeta;
            meta.addPages(pages);
        });
        return this;
    }

    public ItemStackBuilder setPage(int id, Component page) {
        super.item.editMeta(itemMeta -> {
            BookMeta meta = (BookMeta) itemMeta;
            meta.page(id, page);
        });
        return this;
    }

    public ItemStackBuilder setAuthor(Component author) {  // TODO is it working?
        super.item.editMeta(itemMeta -> {
            BookMeta meta = (BookMeta) itemMeta;
            meta.author(author);
        });
        return this;
    }

    public ItemStackBuilder setGeneration(BookMeta.Generation generation) {
        super.item.editMeta(itemMeta -> {
            BookMeta meta = (BookMeta) itemMeta;
            meta.setGeneration(generation);
        });
        return this;
    }
}
