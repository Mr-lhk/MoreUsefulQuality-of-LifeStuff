package com.li.muqols;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * 模组配置。
 * <p>
 * 注意时序：NeoForge 在<b>物品注册之后</b>才加载配置（{@code CommonModLoader#begin} 里先
 * {@code GameData#postRegisterEvents()}，再 {@code ConfigTracker#loadConfigs}），
 * 所以“是否注册多功能工具”必须在注册阶段之前决定。这里在类加载时（模组构造器内）
 * 直接读取上一次保存的配置文件，用于本次启动的注册判断；配置界面中显示的值来自
 * {@link #SPEC}，修改后需要重启才生效。
 */
public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_MULTITOOL = BUILDER
            .comment("Whether the six multitool items are registered: wooden, stone, iron, golden, diamond and netherite.",
                    "Requires a restart of the game or server to take effect.",
                    "Client and server must use the same value, otherwise their item registries will differ.")
            .define("enableMultitool", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private static final boolean MULTITOOL_ENABLED = readMultitoolFlag();

    private Config() {
    }

    public static boolean isMultitoolEnabled() {
        return MULTITOOL_ENABLED;
    }

    /**
     * 在配置系统加载之前读取配置文件中的开关。文件不存在（首次启动）或读取失败时按默认值开启处理。
     */
    private static boolean readMultitoolFlag() {
        Path path = FMLPaths.CONFIGDIR.get().resolve(MoreUsefulQualityofLifeStuff.MOD_ID + "-common.toml");
        if (!Files.isRegularFile(path)) {
            return true;
        }

        try {
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String trimmed = line.strip();
                if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("[")) {
                    continue;
                }
                int separator = trimmed.indexOf('=');
                if (separator < 0) {
                    continue;
                }
                if ("enableMultitool".equals(trimmed.substring(0, separator).strip())) {
                    return !"false".equalsIgnoreCase(stripComment(trimmed.substring(separator + 1)));
                }
            }
        } catch (IOException e) {
            MoreUsefulQualityofLifeStuff.LOGGER.warn("Could not read the multitool setting early from {}", path, e);
        }
        return true;
    }

    /**
     * 去掉值后面可能存在的行内注释（例如手工编辑出来的 {@code enableMultitool = false # 关闭}）。
     */
    private static String stripComment(String value) {
        int comment = value.indexOf('#');
        return (comment < 0 ? value : value.substring(0, comment)).strip();
    }
}
