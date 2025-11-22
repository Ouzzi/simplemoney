package com.simplemoney;

import com.simplemoney.entity.ModEntities;
import com.simplemoney.items.ModItemGroups;
import com.simplemoney.items.ModItems;
import com.simplemoney.recipe.ModRecipes;
import com.simplemoney.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;

import java.util.List;
import java.util.Map;


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



	/**
	 * Die Hauptmethode, die beim Start des Mods von Fabric aufgerufen wird.
	 * Registriert alle Mod-Komponenten und Handelsangebote.
	 */
	@Override
	public void onInitialize() {
		LOGGER.info("Starting Simplemoney initialization...");

        // Registriert alle benutzerdefinierten Entitäten des Mods.
        ModEntities.registerModEntities();

        // Registriert alle Item Gruppen (Creative Tabs)
        ModItemGroups.registerItemGroups();

        // Registriert alle Custom Items des Mods.
		ModItems.registerModItems();

		// Registriert alle Crafting- und Schmelzrezepte des Mods.
		ModRecipes.registerRecipes();

        // Registriert alle Loot Table Modifikationen des Mods.
        ModLootTableModifiers.modifyLootTables();

        // Registriert benutzerdefinierte Handelsangebote für Dorfbewohner
        registerTrades();

	}

    /**
     * Fügt eine Reihe von Standard-Trades zur Liste der Handelsangebote hinzu.
     * Diese Trades dienen hauptsächlich dazu, die Custom Currency (MONEY_BILL)
     * gegen Smaragde zu tauschen und so den Geldwert zu definieren.
     *
     * @param factories Die Liste von TradeOffers.Factory, zu der die Trades hinzugefügt werden sollen.
     */
    public static void addTrades(List<TradeOffers.Factory> factories) {
        // Fügt den variablen Tausch-Trade (Money Bill gegen 2 bis 6 Smaragde) hinzu.
        factories.add((entity, random) -> {
            // Definiert die zufällige Anzahl der Smaragde (Output)
            int minEmeralds = 2;
            int maxEmeralds = 10;

            // Die tatsächliche Anzahl wird beim Initialisieren des Trades zufällig bestimmt
            int emeraldAmount = random.nextInt(maxEmeralds - minEmeralds + 1) + minEmeralds;

            // Trade: 1 MONEY_BILL gegen 2-6 Smaragde
            return new TradeOffer(
                    new TradedItem(ModItems.MONEY_BILL, 1), // Kauf: 1 Money Bill
                    new ItemStack(Items.EMERALD, emeraldAmount), // Verkauf: Variable Smaragd-Menge
                    8, // Max. Verwendungen
                    2, // XP
                    0.05f // Preis-Multiplikator
            );
        });
    }

    public static void registerTrades() {
        // --- CUSTOM VILLAGER TRADES REGISTRIERUNG ---

        // 1. FLETCHER (Pfeilmacher) - Level 1 & 3 (Utility)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FLETCHER, 1, factories -> {
            addTrades(factories);
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.FIREWORK_ROCKET, 8), 6, 5, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FLETCHER, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.FIREWORK_ROCKET, 16), 12, 10, 0.05f));
        });


        // 2. LIBRARIAN (Bibliothekar) - Level 1 & 2 (Bulk Bücher) //TODO: Verzauberte Bücher hinzufügen
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1, factories -> {
            addTrades(factories);
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.BOOK, 32), 4, 20, 0.1f));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.BOOK, 48), 2, 40, 0.1f));
        });


        // 4. CLERIC (Kleriker) - Level 1 & 3 (Erhöhte Progression)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1, factories -> {
            addTrades(factories);
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.ENDER_PEARL, 8), 5, 10, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.ENDER_PEARL, 16), 3, 20, 0.05f));
        });


        // 5. MASON (Steinmetz) - Level 1, 2, 3 (Materialien)
        // Level 1 (Novice): Basis-Baumaterialien
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 1, factories -> {
            addTrades(factories);
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.SMOOTH_STONE, 64), 5, 3, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.STONE_BRICKS, 64), 5, 3, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.DEEPSLATE_BRICKS, 64), 5, 3, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.MOSSY_COBBLESTONE, 32), 10, 3, 0.05f));
        });
        // Level 2 (Apprentice): Leichte Erhöhung der XP/Verwendungen
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.SMOOTH_STONE, 64), 5, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.STONE_BRICKS, 64), 10, 6, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.PRISMARINE_BRICKS, 32), 5, 6, 0.05f));
        });


        // 6. FARMER (Bauer) - Level 1 & 2 (Nahrung)
        // Level 1 (Novice): Bulk-Pflanzen (32er Stacks für 1 Bill)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            addTrades(factories);
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.WHEAT_SEEDS, 64), 16, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.CARROT, 32), 6, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.POTATO, 32), 6, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.APPLE, 32), 4, 10, 0.05f));
        });
        // Level 2 (Apprentice): Komfort-Nahrungsmittel
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 4), new ItemStack(Items.CAKE, 1), 2, 15, 0.1f) );
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 6), new ItemStack(Items.GOLDEN_CARROT, 16), 4, 20, 0.1f) );
        });


        // 3. ARMORER (Rüstungsschmied) - Level 2 & 4 (Rüstung)
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), new ItemStack(Items.DIAMOND_CHESTPLATE, 1), 1, 25, 0.1f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.DIAMOND_LEGGINGS, 1), 1, 25, 0.1f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.DIAMOND_HELMET, 1), 2, 15, 0.1f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.DIAMOND_BOOTS, 1), 2, 15, 0.1f));
        });
        // Level 4 (Expert): Stark verzauberte Rüstung //TODO: Verzauberungen hinzufügen
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 7), new ItemStack(Items.DIAMOND_CHESTPLATE, 1), 1, 50, 0.2f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 6), new ItemStack(Items.DIAMOND_LEGGINGS, 1),1, 50, 0.2f));
        });


        // TOOLSMITH - Level 3 (Journeyman): Mittlere Tools //TODO: Verzauberungen hinzufügen
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 4), new ItemStack(Items.DIAMOND_PICKAXE),2, 30, 0.1f));
        });
        // TOOLSMITH - Level 5 (Master): End-Game-Tools //TODO: Verzauberungen hinzufügen
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 5, factories -> {
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 9), new ItemStack(Items.DIAMOND_PICKAXE),1, 80, 0.25f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 6), new ItemStack(Items.DIAMOND_SHOVEL),1, 60, 0.2f));
            factories.add((entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.DIAMOND, 3),5, 10, 0.02f));
        });

        // TODO: Wandering Trader Trades mit MONEY_BILL hinzufügen
    }


}