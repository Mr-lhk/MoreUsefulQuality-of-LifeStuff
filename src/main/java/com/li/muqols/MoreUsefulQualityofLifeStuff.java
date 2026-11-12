package com.li.muqols;

import com.li.muqols.block.ModBlocks;
import com.li.muqols.item.ModCreativeModeTabs;
import com.li.muqols.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MoreUsefulQualityofLifeStuff.MOD_ID)
public class MoreUsefulQualityofLifeStuff {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "muqols";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The first code that is run when the mod is loaded.
    public MoreUsefulQualityofLifeStuff(IEventBus modEventBus, ModContainer modContainer) {
        // Register mod's ModConfigSpec so that FML can load the config file for us.
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // 配置在注册阶段之后才会被 NeoForge 加载，所以这里用启动早期读取到的值决定是否注册。
        boolean multitoolsEnabled = Config.isMultitoolEnabled();
        if (!multitoolsEnabled) {
            LOGGER.info("Multitool items are disabled in the config; skipping their registration and item tags.");
        }

        // Register blocks first: items may reference block entries (e.g. BlockItems).
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus, multitoolsEnabled);
        ModCreativeModeTabs.register(modEventBus);

        modEventBus.addListener(this::addPackFinders);
    }

    /**
     * 多功能工具的原版物品标签（{@code #minecraft:axes} 等）引用的是这六件物品；
     * 关闭功能时这些物品不存在，若标签仍被加载会输出 “missing references” 错误。
     * 因此标签被放在模组内嵌的 {@code multitool_pack} 数据包里，仅在启用时才挂载。
     */
    private void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA || !Config.isMultitoolEnabled()) {
            return;
        }
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "multitool_pack"),
                PackType.SERVER_DATA,
                Component.translatable("pack.muqols.multitool"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP);
    }
}
