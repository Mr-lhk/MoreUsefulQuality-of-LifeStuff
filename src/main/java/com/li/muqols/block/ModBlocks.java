package com.li.muqols.block;

import com.li.muqols.MoreUsefulQualityofLifeStuff;
import com.li.muqols.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MoreUsefulQualityofLifeStuff.MOD_ID);

    // 注册模组方块

    public static final DeferredBlock<Block> EXAMPLE_BLOCK =
            registerBlocks("example_block", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F, 6.0F)));

    // 注册 BlockItem
    private static <T extends Block> void registerBlockItems(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // 同步注册 Block 与 BlockItem
    private static <T extends Block> DeferredBlock<T> registerBlocks(String name, Supplier<T> block) {
        DeferredBlock<T> blocks = BLOCKS.register(name, block);
        registerBlockItems(name, blocks);
        return blocks;
    }

    // 主类调用
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
