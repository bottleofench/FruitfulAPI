package api.bottleofench.fruitfulapi.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import java.util.function.Consumer;

public class BlockEditor {
    private BlockEditor() {}

    public static Block editBlockData(Block block, Consumer<BlockData> blockData) {
        blockData.accept(block.getBlockData());
        return block;
    }

    public static Block editBlockState(Block block, Consumer<BlockState> blockState) {
        blockState.accept(block.getState());
        block.getState().update();
        return block;
    }
}
