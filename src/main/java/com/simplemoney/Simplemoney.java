package com.simplemoney;

import com.simplemoney.config.SimpleMoneyConfig;
import com.simplemoney.entity.ModEntities;
import com.simplemoney.items.ModItemGroups;
import com.simplemoney.items.ModItems;
import com.simplemoney.recipe.ModRecipes;
import com.simplemoney.util.ModLootTableModifiers;
import com.simplemoney.util.ModTradeOffers;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Die Hauptklasse für den Simplemoney Mod.
 * Diese Klasse initialisiert alle Custom Items, Rezepte und registriert
 * die benutzerdefinierten Handelsangebote für Dorfbewohner (Villager Trades),
 * wobei die Custom Currency (MONEY_BILL) verwendet wird.
 * * Implementiert das Fabric ModInitializer Interface.
 */
public class Simplemoney implements ModInitializer {
	/** Die eindeutige Mod-ID, verwendet für Registrierungen und Logger. */
	public static final String MOD_ID = "simplemoney";
	/** Der Logger für die Protokollierung von Mod-Ereignissen und Debugging. */
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static SimpleMoneyConfig CONFIG;

	/**
	 * Die Hauptmethode, die beim Start des Mods von Fabric aufgerufen wird.
	 * Registriert alle Mod-Komponenten und Handelsangebote.
	 */
	@Override
	public void onInitialize() {
		LOGGER.info("Starting Simplemoney initialization...");

        // 1. WICHTIG: Config ZUERST laden!
        AutoConfig.register(SimpleMoneyConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(SimpleMoneyConfig.class).getConfig();

        ModEntities.registerModEntities();
        ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModRecipes.registerRecipes();
        ModLootTableModifiers.modifyLootTables();
        ModTradeOffers.registerModTradeOffers();

	}

    public static SimpleMoneyConfig getConfig() {
        return CONFIG;
    }
}