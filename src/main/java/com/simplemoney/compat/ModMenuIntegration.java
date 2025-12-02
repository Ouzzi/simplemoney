package com.simplemoney.compat;

import com.simplemoney.config.SimpleMoneyConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        // Erzeugt das GUI automatisch basierend auf deiner Config-Klasse
        return parent -> AutoConfig.getConfigScreen(SimpleMoneyConfig.class, parent).get();
    }
}