package com.li.muqols.item;

import com.li.muqols.MoreUsefulQualityofLifeStuff;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MoreUsefulQualityofLifeStuff.MOD_ID);

    // 注册模组物品。

    // 创造模式物品栏图标（仅作为图标使用，不放入物品栏）
    public static final DeferredItem<Item> TAB_ICON =
            ITEMS.register("tab_icon", () -> new Item(new Item.Properties()));

    // 方块的 BlockItem 也在本类注册（依赖方向固定为 ModItems -> ModBlocks），例如：
    //   public static final DeferredItem<BlockItem> BLOCK_ITEM = ITEMS.registerSimpleBlockItem(ModBlocks.BLOCK);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
