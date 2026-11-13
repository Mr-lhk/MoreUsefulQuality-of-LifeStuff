package com.li.muqols.item;

import java.util.List;

import com.li.muqols.compat.curios.CuriosCompat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

/**
 * 飞行指环。物品本身没有逻辑，飞行能力由 {@link FlightRingHandler} 在服务端按 tick 维护。
 */
public class FlightRingItem extends Item {
    public FlightRingItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.muqols.flight_ring").withStyle(ChatFormatting.GRAY));
        if (CuriosCompat.isLoaded()) {
            tooltip.add(Component.translatable("tooltip.muqols.flight_ring.curios").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
