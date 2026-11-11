package com.li.muqols;

import com.li.muqols.block.ModBlocks;
import com.li.muqols.item.ModCreativeModeTabs;
import com.li.muqols.item.ModItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
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
        // Register blocks first: items may reference block entries (e.g. BlockItems).
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
    }
}
