package com.li.muqols.item;

import com.li.muqols.MoreUsefulQualityofLifeStuff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;

/**
 * 飞行指环的飞行逻辑。
 * <p>
 * 指环在主背包或副手中时，生存/冒险模式的玩家获得创造式飞行。
 * 实现上使用 NeoForge 的 {@code neoforge:creative_flight} 属性（可同步），
 * 通过一个专属的临时修饰符授予/撤销，不会影响创造模式或其它模组提供的飞行。
 * 指环被移出背包后修饰符立即移除，服务端会在同一 tick 停止飞行。
 */
public final class FlightRingHandler {
    private static final ResourceLocation FLIGHT_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(MoreUsefulQualityofLifeStuff.MOD_ID, "flight_ring");
    private static final AttributeModifier FLIGHT_MODIFIER =
            new AttributeModifier(FLIGHT_MODIFIER_ID, 1.0D, AttributeModifier.Operation.ADD_VALUE);

    private FlightRingHandler() {
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        DeferredItem<FlightRingItem> ringHolder = ModItems.flightRing();
        if (ringHolder == null) {
            return; // 功能未启用
        }

        Item ring = ringHolder.get();
        boolean canUseRing = !player.isCreative() && !player.isSpectator();
        boolean hasRing = canUseRing && (player.getInventory().contains(stack -> stack.is(ring))
                || player.getOffhandItem().is(ring));

        AttributeInstance flight = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (flight == null) {
            return;
        }

        if (hasRing) {
            if (!flight.hasModifier(FLIGHT_MODIFIER_ID)) {
                flight.addTransientModifier(FLIGHT_MODIFIER);
            }
        } else if (flight.hasModifier(FLIGHT_MODIFIER_ID)) {
            flight.removeModifier(FLIGHT_MODIFIER_ID);
        }
    }
}
