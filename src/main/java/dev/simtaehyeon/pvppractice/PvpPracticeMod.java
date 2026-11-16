package dev.simtaehyeon.pvppractice;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PvpPracticeMod implements ModInitializer {
    public static final String MOD_ID = "pvp_practice";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("PVP Practice loaded. Practice modes are coming soon!");
    }
}
