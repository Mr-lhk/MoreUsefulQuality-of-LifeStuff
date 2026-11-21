package com.li.muqols.item;

import java.util.ArrayList;
import java.util.List;

import com.li.muqols.MoreUsefulQualityofLifeStuff;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MoreUsefulQualityofLifeStuff.MOD_ID);

    // 注册模组物品。

    // 创造模式物品栏图标（仅作为图标使用，不放入物品栏）
    public static final DeferredItem<Item> TAB_ICON =
            ITEMS.register("tab_icon", () -> new Item(new Item.Properties()));

    // 创造模式物品栏的展示顺序（多功能工具 + 飞行指环），关闭的功能不会出现在这里。
    private static final List<DeferredItem<? extends Item>> CREATIVE_ITEMS = new ArrayList<>();

    // 飞行指环只在配置开启时注册；关闭时为 null。
    @Nullable
    private static DeferredItem<FlightRingItem> flightRing;

    public static void register(IEventBus eventBus, boolean includeMultitools, boolean includeFlightRing) {
        if (includeMultitools) {
            registerMultitool("wooden_multitool", Tiers.WOOD, 6.0F, new Item.Properties());
            registerMultitool("stone_multitool", Tiers.STONE, 7.0F, new Item.Properties());
            registerMultitool("iron_multitool", Tiers.IRON, 6.0F, new Item.Properties());
            registerMultitool("golden_multitool", Tiers.GOLD, 6.0F, new Item.Properties());
            registerMultitool("diamond_multitool", Tiers.DIAMOND, 5.0F, new Item.Properties());
            registerMultitool("netherite_multitool", Tiers.NETHERITE, 5.0F, new Item.Properties().fireResistant());
        }

        if (includeFlightRing) {
            flightRing = ITEMS.register("flight_ring",
                    () -> new FlightRingItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
            CREATIVE_ITEMS.add(flightRing);
        }

        ITEMS.register(eventBus);
    }

    private static void registerMultitool(String name, Tier tier, float attackDamage, Item.Properties properties) {
        DeferredItem<MultitoolItem> item = ITEMS.register(name, () -> new MultitoolItem(tier, attackDamage, properties));
        CREATIVE_ITEMS.add(item);
    }

    public static List<DeferredItem<? extends Item>> creativeItems() {
        return CREATIVE_ITEMS;
    }

    @Nullable
    public static DeferredItem<FlightRingItem> flightRing() {
        return flightRing;
    }

    // 方块的 BlockItem 也在本类注册（依赖方向固定为 ModItems -> ModBlocks），例如：
    //   public static final DeferredItem<BlockItem> BLOCK_ITEM = ITEMS.registerSimpleBlockItem(ModBlocks.BLOCK);
}
