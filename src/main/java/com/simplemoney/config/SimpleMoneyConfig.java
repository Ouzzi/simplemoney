package com.simplemoney.config;

import com.simplemoney.Simplemoney;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Simplemoney.MOD_ID)
public class SimpleMoneyConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public Trades trades = new Trades();

    public static class Trades {
        @ConfigEntry.Gui.Tooltip // Keine Argumente nötig, sucht automatisch in .json
        public boolean enableVillagerTrades = true;

        @ConfigEntry.Gui.Tooltip
        public boolean enableWanderingTrades = true;
    }
}