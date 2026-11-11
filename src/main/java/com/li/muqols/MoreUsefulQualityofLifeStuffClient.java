package com.li.muqols;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = MoreUsefulQualityofLifeStuff.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = MoreUsefulQualityofLifeStuff.MOD_ID, value = Dist.CLIENT)
public class MoreUsefulQualityofLifeStuffClient {
    public MoreUsefulQualityofLifeStuffClient() {
        // Client-only setup (screens, key mappings, renderers) goes here.
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        MoreUsefulQualityofLifeStuff.LOGGER.debug("MuQoLS client initialized");
    }
}
