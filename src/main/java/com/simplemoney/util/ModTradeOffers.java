package com.simplemoney.util;

import com.simplemoney.Simplemoney;
import com.simplemoney.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

import java.util.List;

public class ModTradeOffers {
    public record WeightedEnchantment(RegistryKey<Enchantment> key, int level, int weight) {}

    //register trade offers here
    public static void registerModTradeOffers() {
        Simplemoney.LOGGER.info("Registering Custom Trade Offers for " + Simplemoney.MOD_ID);
        registerVillagerTrades();
        registerWanderingTraderTrades();
    }

    /**
     * Fügt eine Reihe von Standard-Trades zur Liste der Handelsangebote hinzu.
     * Diese Trades dienen hauptsächlich dazu, die Custom Currency (MONEY_BILL)
     * gegen Smaragde zu tauschen und so den Geldwert zu definieren.
     *
     * @param factories Die Liste von TradeOffers.Factory, zu der die Trades hinzugefügt werden sollen.
     */
    public static void addTrades(List<TradeOffers.Factory> factories) {
        // FIX: (world, entity, random)
        factories.add((world, entity, random) -> {
            int emeraldAmount = random.nextInt(35) + 3; // 3 bis 7 Smaragde (Durchschnitt 5)
            return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.EMERALD, emeraldAmount), 8, 5, 0.05f);
        });
    }

    public static void registerVillagerTrades() {
        if (Simplemoney.getConfig().trades.enableVillagerTrades) {
            // 1. FLETCHER (Pfeilmacher)
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.FLETCHER, 1, factories -> {
                addTrades(factories);
                // FIX: (world, entity, random)
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.FIREWORK_ROCKET, 10), 4, 5, 0.05f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.FLETCHER, 3, factories -> {
                // FIX: (world, entity, random)
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.FIREWORK_ROCKET, 16), 6, 10, 0.05f));
            });

            // 2. LIBRARIAN (Bibliothekar)
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1, factories -> {
                List<WeightedEnchantment> level2Books = List.of(
                        new WeightedEnchantment(Enchantments.EFFICIENCY, 5, 20),
                        new WeightedEnchantment(Enchantments.UNBREAKING, 3, 20),
                        new WeightedEnchantment(Enchantments.FIRE_ASPECT, 2, 10),
                        new WeightedEnchantment(Enchantments.FORTUNE, 3 , 10),
                        new WeightedEnchantment(Enchantments.KNOCKBACK, 2, 5),
                        new WeightedEnchantment(Enchantments.FEATHER_FALLING, 4, 10),
                        new WeightedEnchantment(Enchantments.MENDING, 1, 2),
                        new WeightedEnchantment(Enchantments.SILK_TOUCH, 1, 10)
                );
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), createRandomEnchantedBook(entity, random, level2Books, 10), 2, 10, 0.5f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 2, factories -> {
                List<WeightedEnchantment> level2Books = List.of(
                        new WeightedEnchantment(Enchantments.SHARPNESS, 5, 20),
                        new WeightedEnchantment(Enchantments.PROTECTION, 4, 20),
                        new WeightedEnchantment(Enchantments.FEATHER_FALLING, 4, 10)
                );
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), createRandomEnchantedBook(entity, random, level2Books, 10), 2, 10, 0.5f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 3, factories -> {
                List<WeightedEnchantment> masterBooks = List.of(
                        new WeightedEnchantment(Enchantments.RESPIRATION, 3, 10),
                        new WeightedEnchantment(Enchantments.IMPALING, 5, 10),
                        new WeightedEnchantment(Enchantments.POWER, 5, 15)

                );
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 4), createRandomEnchantedBook(entity, random, masterBooks, 10), 1, 50, 0.7f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 4, factories -> {
                List<WeightedEnchantment> masterBooks = List.of(
                        new WeightedEnchantment(Enchantments.DEPTH_STRIDER, 3, 10),
                        new WeightedEnchantment(Enchantments.FROST_WALKER, 2, 10),
                        new WeightedEnchantment(Enchantments.LOOTING, 3, 15),
                        new WeightedEnchantment(Enchantments.PIERCING, 4, 10),
                        new WeightedEnchantment(Enchantments.POWER, 5, 15)

                );
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 4), createRandomEnchantedBook(entity, random, masterBooks, 10), 1, 50, 0.7f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 5, factories -> {
                List<WeightedEnchantment> masterBooks = List.of(
                        new WeightedEnchantment(Enchantments.SWIFT_SNEAK, 1, 2),
                        new WeightedEnchantment(Enchantments.SOUL_SPEED, 1, 2)
                );
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 5), createRandomEnchantedBook(entity, random, masterBooks, 10), 1, 50, 0.7f));
            });

            // 3. CLERIC (Kleriker)
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1, factories -> {
                addTrades(factories);
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.ENDER_PEARL, 8), 4, 10, 0.05f));
            });

            // 4. MASON (Steinmetz)
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 1, factories -> {
                addTrades(factories);
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.SMOOTH_STONE, 64), 4, 3, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.STONE_BRICKS, 64), 4, 3, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.DEEPSLATE_BRICKS, 52), 4, 3, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.MOSSY_COBBLESTONE, 32), 4, 3, 0.05f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 2, factories -> {
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.SMOOTH_STONE, 64), 6, 5, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.STONE_BRICKS, 64), 6, 6, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.PRISMARINE_BRICKS, 16), 3, 6, 0.05f));
            });

            // 5. FARMER (Bauer)
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
                addTrades(factories);
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.WHEAT_SEEDS, 52), 16, 5, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.CARROT, 20), 6, 5, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 1), new ItemStack(Items.POTATO, 24), 6, 5, 0.05f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.APPLE, 28), 4, 10, 0.05f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.CAKE, 1), 2, 15, 0.1f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), new ItemStack(Items.GOLDEN_CARROT, 16), 4, 20, 0.1f));
            });

            // 6. ARMORER
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 2, factories -> {
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(Items.DIAMOND, 7), new ItemStack(ModItems.MONEY_BILL, 2), 2, 10, 0.2f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 3, factories -> {
                List<WeightedEnchantment> enchantmentPoolHelmet = List.of(
                        new WeightedEnchantment(Enchantments.PROTECTION, 1, 40),
                        new WeightedEnchantment(Enchantments.THORNS, 1, 20),
                        new WeightedEnchantment(Enchantments.AQUA_AFFINITY, 1, 20),
                        new WeightedEnchantment(Enchantments.RESPIRATION, 2, 20)
                );
                List<WeightedEnchantment> enchantmentPoolBoots = List.of(
                        new WeightedEnchantment(Enchantments.FEATHER_FALLING, 1, 30),
                        new WeightedEnchantment(Enchantments.DEPTH_STRIDER, 1, 20),
                        new WeightedEnchantment(Enchantments.PROTECTION, 1, 30),
                        new WeightedEnchantment(Enchantments.FROST_WALKER, 1, 20)
                );

                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), createRandomEnchantedItem(entity, random, Items.DIAMOND_HELMET, enchantmentPoolHelmet, 5), 2, 15, 0.2f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), createRandomEnchantedItem(entity, random, Items.DIAMOND_BOOTS, enchantmentPoolBoots, 5), 2, 15, 0.2f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 4, factories -> {
                List<WeightedEnchantment> enchantmentPoolChestplate = List.of(
                        new WeightedEnchantment(Enchantments.PROTECTION, 2, 40),
                        new WeightedEnchantment(Enchantments.FIRE_PROTECTION, 3, 25),
                        new WeightedEnchantment(Enchantments.PROJECTILE_PROTECTION, 3, 15),
                        new WeightedEnchantment(Enchantments.UNBREAKING, 2, 20)
                );

                List<WeightedEnchantment> enchantmentPoolLeggings = List.of(
                        new WeightedEnchantment(Enchantments.PROTECTION, 2, 40),
                        new WeightedEnchantment(Enchantments.BLAST_PROTECTION, 3, 30),
                        new WeightedEnchantment(Enchantments.SWIFT_SNEAK, 1, 5)
                );

                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 5), createRandomEnchantedItem(entity, random, Items.DIAMOND_CHESTPLATE, enchantmentPoolChestplate, 10), 1, 50, 0.7f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 4), createRandomEnchantedItem(entity, random, Items.DIAMOND_LEGGINGS, enchantmentPoolLeggings, 10), 1, 50, 0.7f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 5, factories -> {
                List<WeightedEnchantment> enchantmentPoolMasterChestplate = List.of(
                        new WeightedEnchantment(Enchantments.PROTECTION, 4, 50),
                        new WeightedEnchantment(Enchantments.THORNS, 3, 30),
                        new WeightedEnchantment(Enchantments.UNBREAKING, 3, 20)
                );

                List<WeightedEnchantment> enchantmentPoolMasterBoots = List.of(
                        new WeightedEnchantment(Enchantments.FEATHER_FALLING, 4, 40),
                        new WeightedEnchantment(Enchantments.DEPTH_STRIDER, 3, 30),
                        new WeightedEnchantment(Enchantments.SOUL_SPEED, 1, 10)
                );

                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 5), createRandomEnchantedItem(entity, random, Items.DIAMOND_CHESTPLATE, enchantmentPoolMasterChestplate, 10), 1, 50, 0.7f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), createRandomEnchantedItem(entity, random, Items.DIAMOND_BOOTS, enchantmentPoolMasterBoots, 10), 2, 15, 0.7f));
            });

            // 7. TOOLSMITH
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3, factories -> {
                List<WeightedEnchantment> toolPoolLevel3 = List.of(
                        new WeightedEnchantment(Enchantments.EFFICIENCY, 1, 50),
                        new WeightedEnchantment(Enchantments.UNBREAKING, 1, 30),
                        new WeightedEnchantment(Enchantments.EFFICIENCY, 2, 10)
                );

                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), createRandomEnchantedItem(entity, random, Items.DIAMOND_HOE, toolPoolLevel3, 10), 2, 15, 0.7f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 2), createRandomEnchantedItem(entity, random, Items.DIAMOND_SHOVEL, toolPoolLevel3, 10), 2, 10, 0.7f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 4, factories -> {
                List<WeightedEnchantment> toolPoolLevel4 = List.of(
                        new WeightedEnchantment(Enchantments.EFFICIENCY, 2, 40),
                        new WeightedEnchantment(Enchantments.UNBREAKING, 2, 30),
                        new WeightedEnchantment(Enchantments.FORTUNE, 1, 15),
                        new WeightedEnchantment(Enchantments.SILK_TOUCH, 1, 10)
                );

                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), createRandomEnchantedItem(entity, random, Items.DIAMOND_PICKAXE, toolPoolLevel4, 10), 1, 15, 0.7f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 3), createRandomEnchantedItem(entity, random, Items.DIAMOND_AXE, toolPoolLevel4, 10), 1, 15, 0.7f));
            });
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 5, factories -> {
                List<WeightedEnchantment> toolPoolLevel5Tool = List.of(
                        new WeightedEnchantment(Enchantments.EFFICIENCY, 4, 30),
                        new WeightedEnchantment(Enchantments.EFFICIENCY, 5, 10),
                        new WeightedEnchantment(Enchantments.FORTUNE, 2, 20),
                        new WeightedEnchantment(Enchantments.FORTUNE, 3, 10),
                        new WeightedEnchantment(Enchantments.MENDING, 1, 5),
                        new WeightedEnchantment(Enchantments.UNBREAKING, 3, 20)
                );
                List<WeightedEnchantment> toolPoolLevel5Weapon = List.of(
                        new WeightedEnchantment(Enchantments.SHARPNESS, 5, 30),
                        new WeightedEnchantment(Enchantments.SMITE, 5, 20),
                        new WeightedEnchantment(Enchantments.BANE_OF_ARTHROPODS, 5, 20),
                        new WeightedEnchantment(Enchantments.UNBREAKING, 3, 20),
                        new WeightedEnchantment(Enchantments.SWEEPING_EDGE, 3, 10),
                        new WeightedEnchantment(Enchantments.MENDING, 1, 5)
                );

                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 4), createRandomEnchantedItem(entity, random, Items.DIAMOND_PICKAXE, toolPoolLevel5Tool, 10), 1, 30, 0.7f));
                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(ModItems.MONEY_BILL, 4), createRandomEnchantedItem(entity, random, Items.DIAMOND_SWORD, toolPoolLevel5Weapon, 10), 1, 30, 0.7f));

                factories.add((world, entity, random) -> new TradeOffer(new TradedItem(Items.DIAMOND, 7), new ItemStack(ModItems.MONEY_BILL, 2), 5, 10, 0.1f));
            });
        }
    }


    public static void registerWanderingTraderTrades() {
        if (Simplemoney.getConfig().trades.enableWanderingTrades) {
            TradeOfferHelper.registerWanderingTraderOffers(factory -> {
                // FIX: (world, entity, random) für alle Wandering Trader Angebote
                factory.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL, (world, entity, random) -> {
                    int moneyBillAmount = random.nextInt(4) + 6;
                    int chorusFruitAmount = random.nextInt(8) + 16;
                    return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, moneyBillAmount), new ItemStack(Items.CHORUS_FRUIT, chorusFruitAmount), 3, 10, 0.1f);
                });
                factory.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL, (world, entity, random) -> {
                    int moneyBillAmount = random.nextInt(2) + 2;
                    int appleAmount = random.nextInt(20) + 30;
                    return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, moneyBillAmount), new ItemStack(Items.APPLE, appleAmount), 3, 10, 0.1f);
                });
                factory.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL, (world, entity, random) -> {
                    int moneyBillAmount = random.nextInt(20) + 30;
                    int netheriteScrapAmount = random.nextInt(1) + 3;
                    return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, moneyBillAmount), new ItemStack(Items.NETHERITE_SCRAP, netheriteScrapAmount), 1, 100, 0.5f);
                });
                factory.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL, (world, entity, random) -> {
                    int moneyBillAmount = random.nextInt(30) + 30;
                    return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, moneyBillAmount), new ItemStack(Items.SHULKER_SHELL, 1), 1, 200, 0.5f);
                });
                factory.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL, (world, entity, random) -> {
                    int moneyBillAmount = random.nextInt(2) + 5;
                    int oreAmount = random.nextInt(1) + 6;
                    return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, moneyBillAmount), new ItemStack(Items.DIAMOND_ORE, oreAmount), 5, 150, 0.5f);
                });
                factory.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL, (world, entity, random) -> {
                    int moneyBillAmount = random.nextInt(2) + 5;
                    int oreAmount = random.nextInt(1) + 4;
                    return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, moneyBillAmount), new ItemStack(Items.DEEPSLATE_DIAMOND_ORE, oreAmount), 7, 150, 0.5f);
                });
                factory.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL, (world, entity, random) -> {
                    int moneyBillAmount = random.nextInt(15) + 50;
                    return new TradeOffer(new TradedItem(ModItems.MONEY_BILL, moneyBillAmount), new ItemStack(Items.BUDDING_AMETHYST, 3), 5, 150, 0.5f);
                });
            });
        }
    }

    /**
     * Hilfsmethode: Zieht eine zufällige Verzauberung basierend auf dem Gewicht.
     */
    private static WeightedEnchantment pickWeighted(List<WeightedEnchantment> pool, Random random) {
        int totalWeight = 0;
        for (WeightedEnchantment e : pool) totalWeight += e.weight();

        int pick = random.nextInt(totalWeight);
        int currentWeight = 0;

        for (WeightedEnchantment e : pool) {
            currentWeight += e.weight();
            if (pick < currentWeight) {
                return e;
            }
        }
        return pool.get(0); // Fallback
    }

    /**
     * Wählt zufällig Verzauberungen aus.
     * @param chanceForSecond Wahrscheinlichkeit (0-100), dass eine zweite Verzauberung hinzugefügt wird.
     */
    private static ItemStack createRandomEnchantedItem(
            Entity entity,
            Random random,
            Item item,
            List<WeightedEnchantment> pool,
            int chanceForSecond
    ) {
        ItemStack stack = new ItemStack(item);

        // Builder initialisieren
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);

        // 1. Erste Verzauberung (Garantiert)
        WeightedEnchantment firstPick = pickWeighted(pool, random);
        addEnchantmentToBuilder(entity, builder, firstPick);

        // 2. Zweite Verzauberung (Chance)
        // Wir prüfen auch, ob der Pool überhaupt mehr als 1 Option hat.
        if (pool.size() > 1 && random.nextInt(100) < chanceForSecond) {
            WeightedEnchantment secondPick = pickWeighted(pool, random);

            // Stelle sicher, dass wir nicht zweimal exakt dieselbe Verzauberung (Key) wählen
            int attempts = 0;
            while (secondPick.key().equals(firstPick.key()) && attempts < 10) {
                secondPick = pickWeighted(pool, random);
                attempts++;
            }

            // Nur hinzufügen, wenn sie unterschiedlich sind
            if (!secondPick.key().equals(firstPick.key())) {
                addEnchantmentToBuilder(entity, builder, secondPick);
            }
        }

        stack.set(DataComponentTypes.ENCHANTMENTS, builder.build());
        return stack;
    }

    // Kleine Hilfsmethode um den Builder-Code nicht doppelt zu schreiben
    private static void addEnchantmentToBuilder(Entity entity, ItemEnchantmentsComponent.Builder builder, WeightedEnchantment selection) {
        RegistryEntry<Enchantment> enchantmentEntry = entity.getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(selection.key());

        builder.add(enchantmentEntry, selection.level());
    }

    private static ItemStack createRandomEnchantedBook(
            Entity entity,
            Random random,
            List<WeightedEnchantment> pool,
            int chanceForSecond
    ) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);

        // 1. Erste Verzauberung
        WeightedEnchantment firstPick = pickWeighted(pool, random);
        addEnchantmentToBuilder(entity, builder, firstPick);

        // 2. Zweite Verzauberung
        if (pool.size() > 1 && random.nextInt(100) < chanceForSecond) {
            WeightedEnchantment secondPick = pickWeighted(pool, random);
            int attempts = 0;
            while (secondPick.key().equals(firstPick.key()) && attempts < 10) {
                secondPick = pickWeighted(pool, random);
                attempts++;
            }
            if (!secondPick.key().equals(firstPick.key())) {
                addEnchantmentToBuilder(entity, builder, secondPick);
            }
        }

        // WICHTIG: Bei Büchern nutzen wir STORED_ENCHANTMENTS
        stack.set(DataComponentTypes.STORED_ENCHANTMENTS, builder.build());
        return stack;
    }

}