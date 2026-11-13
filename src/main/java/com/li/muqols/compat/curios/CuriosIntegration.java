package com.li.muqols.compat.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * 真正引用 Curios API 的实现。
 * <p>
 * 只有在 Curios 已加载时才会被 {@link CuriosCompat} 调用，因此未安装 Curios 的环境
 * 不会尝试解析这里的类。
 */
final class CuriosIntegration {
    private CuriosIntegration() {
    }

    static void registerFlightRing(Item ring) {
        // ICurioItem 的所有方法都有默认实现，这里只需要把物品与能力关联起来。
        CuriosApi.registerCurio(ring, new ICurioItem() {
        });
    }

    static boolean isRingEquipped(Player player, Item ring) {
        return CuriosApi.getCuriosInventory(player)
                .map(inventory -> !inventory.findCurios(ring).isEmpty())
                .orElse(false);
    }
}
