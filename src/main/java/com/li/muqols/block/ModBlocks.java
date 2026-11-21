package com.li.muqols.block;

import com.li.muqols.MoreUsefulQualityofLifeStuff;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MoreUsefulQualityofLifeStuff.MOD_ID);

    // 注册模组方块。
    // 这里只负责方块本身；对应的 BlockItem 一律在 ModItems 中注册，
    // 因此本类不需要（也不应该）引用物品注册表，两个注册表之间保持单向依赖。

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
