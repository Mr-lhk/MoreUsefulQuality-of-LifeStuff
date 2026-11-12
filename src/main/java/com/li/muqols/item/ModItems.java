package com.li.muqols.item;

import com.li.muqols.MoreUsefulQualityofLifeStuff;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
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

    // 多功能工具：耐久/挖掘速度/附魔能力取自对应原版材质，
    // 攻击伤害取同材质原版斧的数值，攻速由 MultitoolItem 固定为剑的 1.6。
    public static final DeferredItem<MultitoolItem> WOODEN_MULTITOOL =
            ITEMS.register("wooden_multitool", () -> new MultitoolItem(Tiers.WOOD, 6.0F, new Item.Properties()));

    public static final DeferredItem<MultitoolItem> STONE_MULTITOOL =
            ITEMS.register("stone_multitool", () -> new MultitoolItem(Tiers.STONE, 7.0F, new Item.Properties()));

    public static final DeferredItem<MultitoolItem> IRON_MULTITOOL =
            ITEMS.register("iron_multitool", () -> new MultitoolItem(Tiers.IRON, 6.0F, new Item.Properties()));

    public static final DeferredItem<MultitoolItem> GOLDEN_MULTITOOL =
            ITEMS.register("golden_multitool", () -> new MultitoolItem(Tiers.GOLD, 6.0F, new Item.Properties()));

    public static final DeferredItem<MultitoolItem> DIAMOND_MULTITOOL =
            ITEMS.register("diamond_multitool", () -> new MultitoolItem(Tiers.DIAMOND, 5.0F, new Item.Properties()));

    public static final DeferredItem<MultitoolItem> NETHERITE_MULTITOOL =
            ITEMS.register("netherite_multitool", () -> new MultitoolItem(Tiers.NETHERITE, 5.0F, new Item.Properties().fireResistant()));

    // 方块的 BlockItem 也在本类注册（依赖方向固定为 ModItems -> ModBlocks），例如：
    //   public static final DeferredItem<BlockItem> BLOCK_ITEM = ITEMS.registerSimpleBlockItem(ModBlocks.BLOCK);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
