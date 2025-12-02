package com.simplemoney.config;

import com.simplemoney.Simplemoney;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Simplemoney.MOD_ID)
public class SimpleMoneyConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public Trades trades = new Trades();

    @ConfigEntry.Gui.CollapsibleObject
    public Balancing balancing = new Balancing();

    @ConfigEntry.Gui.CollapsibleObject
    public Vaults vaults = new Vaults();

    public static class Trades {
        @ConfigEntry.Gui.Tooltip // Keine Argumente nötig, sucht automatisch in .json
        public boolean enableVillagerTrades = true;

        @ConfigEntry.Gui.Tooltip
        public boolean enableWanderingTrades = true;
    }

    public static class Balancing {
        // count = 2 bedeutet, der Tooltip hat 2 Zeilen in der .json
        @ConfigEntry.Gui.Tooltip(count = 2)
        public int rocketStackSize = 16;
    }

    public static class Vaults {
        @ConfigEntry.Gui.Tooltip
        public int vaultCooldownDays = 100;
    }
}