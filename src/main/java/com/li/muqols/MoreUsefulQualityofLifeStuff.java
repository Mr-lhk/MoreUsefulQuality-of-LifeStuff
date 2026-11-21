package com.li.muqols;

import com.li.muqols.block.ModBlocks;
import com.li.muqols.compat.curios.CuriosCompat;
import com.li.muqols.item.FlightRingHandler;
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
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
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
        boolean flightRingEnabled = Config.isFlightRingEnabled();
        if (!multitoolsEnabled) {
            LOGGER.info("Multitool items are disabled in the config; skipping their registration and item tags.");
        }
        if (!flightRingEnabled) {
            LOGGER.info("The flight ring is disabled in the config; skipping its registration.");
        }

        // Register blocks first: items may reference block entries (e.g. BlockItems).
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus, multitoolsEnabled, flightRingEnabled);
        ModCreativeModeTabs.register(modEventBus);

        // 飞行指环的飞行能力维护
        NeoForge.EVENT_BUS.addListener(FlightRingHandler::onPlayerTick);

        modEventBus.addListener(this::addPackFinders);
        modEventBus.addListener(this::registerCapabilities);
    }

    /**
     * Curios 是可选联动：只有检测到 Curios 时才把飞行指环注册为“戒指”物品。
     * 此时物品已经完成注册，可以安全地取得 {@link net.minecraft.world.item.Item} 实例。
     */
    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        if (!CuriosCompat.isLoaded() || ModItems.flightRing() == null) {
            return;
        }
        CuriosCompat.registerFlightRing(ModItems.flightRing().get());
        LOGGER.info("Curios detected; the flight ring can be worn in the ring slot.");
    }

    /**
     * 关闭某项功能时，相关的数据文件不能跟着加载，否则会引用不存在的物品并输出
     * “missing references” 错误。因此这些数据都放在模组内嵌的数据包里，按需挂载：
     * <ul>
     *   <li>{@code multitool_pack}：多功能工具的原版物品标签（{@code #minecraft:axes} 等）；</li>
     *   <li>{@code flight_ring_pack}：飞行指环的 Curios 戒指标签（{@code #curios:ring}）。</li>
     * </ul>
     */
    private void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }
        if (Config.isMultitoolEnabled()) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "multitool_pack"),
                    PackType.SERVER_DATA,
                    Component.translatable("pack.muqols.multitool"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP);
        }
        if (Config.isFlightRingEnabled()) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "flight_ring_pack"),
                    PackType.SERVER_DATA,
                    Component.translatable("pack.muqols.flight_ring"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP);
        }
    }
}
