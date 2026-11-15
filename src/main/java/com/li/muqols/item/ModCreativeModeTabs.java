package com.li.muqols.item;

import com.li.muqols.MoreUsefulQualityofLifeStuff;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreUsefulQualityofLifeStuff.MOD_ID);

    // 注册创造模式物品栏。被配置关闭的内容不会出现在这里。
    public static final Supplier<CreativeModeTab> MUQOLS_TAB =
            CREATIVE_MOD_TABS.register("muqols_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.TAB_ICON.get()))
                    .title(Component.translatable("itemGroup.muqols_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        for (DeferredItem<? extends Item> item : ModItems.creativeItems()) {
                            output.accept(item);
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
