# 💰 SimpleMoney: The Advanced Economy
**(Mod ID: `simplemoney`)**

**SimpleMoney** creates a bridge between resource gathering, exploration, and a high-tier villager economy. It introduces the **Money Bill**, a currency that is significantly more valuable than Emeralds. You can't just farm it easily—you must craft it using rare materials, find it in hidden treasures, or earn it by selling Diamonds to master smiths.

## ✨ Key Features
* **Complex Crafting Chain:** Money isn't free. Creating a bill requires Diamonds, Amethyst, Phantom Membranes, and a Smithing Table.
* **Weighted Enchantment System:** Villagers now offer enchanted gear and books based on a custom probability pool. Rare enchantments like **Mending** or **Protection IV** are harder to find but guaranteed at specific trading levels.
* **Black Market Trader:** The Wandering Trader is no longer useless! He has a chance to sell non-renewable or rare resources like **Netherite Scrap** and **Shulker Shells** using a randomized pool system.
* **Loot Integration:** Find bills in Shipwrecks, Strongholds, and Buried Treasure.
* **Balance Tweak:** Vanilla Firework Rocket stack size is reduced to **16** to balance Elytra flight (compensated by bulk rocket trades).
* **Quality of Life:** Vault cooldowns prevent allows players to loot Vaults only multiple times over long periods.

![Icon](https://cdn.modrinth.com/data/cached_images/3b69876c2b04f4296ea0189f56919edb57b67a8e.png)

---

## ⚙️ Configuration & Customization
SimpleMoney is fully configurable! You can tweak the balancing to fit your modpack or server needs.
*(Requires **Cloth Config API** and **Mod Menu** to edit in-game)*.

* **🚀 Rocket Balancing:** Don't like the nerf? You can change the **Firework Rocket stack size** limit back to **64** (Default: 16).
* **⚖️ Economy Control:** You can individually **enable or disable** the custom Villager Trades or the Wandering Trader overhaul if they conflict with other mods.
* **⏱️ Vault Cooldown:** Define how many in-game days must pass before a player can loot a Vault again (Default: **100 Days**).

![Mod Config Main](https://cdn.modrinth.com/data/cached_images/c090086540b9afe809413b1526cf36312c6ced35_0.webp)

---

## 🏗️ The Production Chain (Crafting)
The creation of a **Money Bill** is an industrial process involving chemistry, precision, and heat.

| Step | Item | Method | Description |
| :--- | :--- | :--- | :--- |
| **1. Materials** | **Special Paper** | Crafting | Paper reinforced with **Phantom Membranes**. |
| **2. Composite** | **Special Fiber** | Crafting | A raw alloy of **Gold, Iron, Copper**, and **Diamond**. |
| **3. Assembly** | **Banknote Blank** | **Smithing Table** | Combine Paper and Fiber using an **Iron Ingot** as the template. |
| **4. Printing** | **Raw Bill** | Crafting | Dye the blank note using **Green Dye** and **Ink Sacs**. |
| **5. Curing** | **Money Bill** | **Blast Furnace** | Fire the wet bill to finalize it. The result is **Fireproof & Glowing**. |

![Production Chain](https://cdn.modrinth.com/data/cached_images/bd50a185ed5f1cb8a7584fd20430588ece071766.png)

---

## 🤝 The Economy Overhaul

The **Money Bill** is worth approximately **5 Emeralds**, but it buys things Emeralds cannot easily obtain.

### 🏛️ Villager Professions
Villagers now use a **weighted system** for enchantments. Instead of random noise, trades are curated for their profession level.

![Trades](https://cdn.modrinth.com/data/cached_images/8493703719c030f6f07c6634426611685e6fae6e.png)

* **Armorer (Expert/Master):** Sells **Diamond Armor** with high-tier enchants (e.g., 40% chance for *Protection II*, rare chance for *Protection IV*). **Buys Diamonds** for Bills.
* **Toolsmith (Master):** Sells **Enchanted Diamond Tools**. Chance for *Efficiency V* or *Fortune III*. **Buys Diamonds** for Bills.
* **Librarian (All Levels):** Sells **Enchanted Books**. Higher levels unlock pools containing *Mending*, *Infinity*, and *Swift Sneak*. Also sells **Name Tags**.
* **Cleric (Novice):** Sells **Ender Pearls** in bulk (8-12 per Bill).
* **Fletcher (Apprentice):** Sells **Firework Rockets** in bulk (16-32 per Bill).
* **Farmer (Apprentice):** Sells **Golden Carrots** and **Cakes**.
* **Mason (Novice):** Sells **Building Blocks** (Stone/Deepslate) in stacks.

### 🦙 The Wandering Trader (RNG)
The Wandering Trader has random pools of items. He doesn't always sell the same thing!
* **Common Pool (~30%):** Chorus Fruits, Apples.
* **Rare Pool (~10%):** **Netherite Scrap**, Diamond Ores.
* **Legendary Pool (~5%):** **Shulker Shells** (Expensive!).

![Wandering Trades](https://cdn.modrinth.com/data/cached_images/b6bc20613ad2f057506fa8b1d3c79bf7e5d36c92.png)

---

## 🌍 World Generation & Loot
Exploration is a valid way to get rich. Money Bills have been injected into the loot tables of:

* **Common:** Igloos, Shipwrecks.
* **Uncommon:** Simple Dungeons, Mineshafts.
* **Rare:** End Cities, Buried Treasure.
* **Legendary:** **Stronghold Libraries** (High yield).

## 📥 Installation
1.  Install **Fabric Loader**.
2.  Install **Fabric API**.
3.  Install **Cloth Config API** (Required for configuration).
4.  Install **Mod Menu** (Optional, but recommended for editing settings in-game).
5.  Place the `.jar` in your `mods` folder.

*(This mod is balanced for Survival Multiplayer and Modpacks.)*