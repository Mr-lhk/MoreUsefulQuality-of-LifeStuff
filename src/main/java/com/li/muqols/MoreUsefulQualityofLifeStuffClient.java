package com.li.muqols;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = MoreUsefulQualityofLifeStuff.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = MoreUsefulQualityofLifeStuff.MOD_ID, value = Dist.CLIENT)
public class MoreUsefulQualityofLifeStuffClient {
    public MoreUsefulQualityofLifeStuffClient(ModContainer container) {
        // 让 NeoForge 的模组列表为该模组的配置生成配置界面（Mods -> 模组 -> Config）。
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        MoreUsefulQualityofLifeStuff.LOGGER.debug("MuQoLS client initialized");
    }
}
