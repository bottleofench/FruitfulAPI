package api.bottleofench.fruitfulapi.block;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.function.Consumer;

public class BlockDataEditor {
    public Block block;

    public BlockDataEditor(Block block) {
        this.block = block;
    }

    public Block editBlockData(Consumer<BlockData> blockData) {
        blockData.accept(block.getBlockData());
        return block;
    }
}
