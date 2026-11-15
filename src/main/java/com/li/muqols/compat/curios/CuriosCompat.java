package com.li.muqols.compat.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;

/**
 * Curios 联动的安全入口。
 * <p>
 * 本类刻意不引用任何 Curios 类型，因此即使没有安装 Curios，加载本类也不会触发
 * {@code NoClassDefFoundError}。真正调用 Curios API 的代码在 {@link CuriosIntegration}，
 * 只有确认 Curios 已加载后才会被类加载器加载。
 */
public final class CuriosCompat {
    private static final String CURIOS_MOD_ID = "curios";

    private CuriosCompat() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded(CURIOS_MOD_ID);
    }

    /**
     * 把飞行指环注册为 Curios 物品，使其可以放进“戒指”槽位。
     * 未安装 Curios 时什么也不做。
     */
    public static void registerFlightRing(Item ring) {
        if (isLoaded()) {
            CuriosIntegration.registerFlightRing(ring);
        }
    }

    /**
     * 玩家是否把指环戴在 Curios 的任意槽位中（通常是戒指槽）。
     * 未安装 Curios 时恒为 {@code false}。
     */
    public static boolean isRingEquipped(Player player, Item ring) {
        return isLoaded() && CuriosIntegration.isRingEquipped(player, ring);
    }
}
