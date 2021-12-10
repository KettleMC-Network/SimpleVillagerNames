package net.kettlemc.villagernames;

import net.kettlemc.villagernames.configuration.Configuration;
import net.kettlemc.villagernames.listener.CreatureSpawnListener;
import net.kettlemc.villagernames.listener.EntityBreedListener;
import net.kettlemc.villagernames.listener.PlayerInteractEntityListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class VillagerNamePlugin extends JavaPlugin {

    public static final String CONSOLE_PREFIX = "[SimpleVillagerNames] ";

    private boolean legacy;

    private Configuration configuration;

    private List<String> firstNames;
    private List<String> lastNames;

    public void onEnable() {
        if (legacy = checkLegacy())
            this.getLogger().info("Couldn't find EntityBreedEvent. Please use at least 1.10.2 in order to use the Baby Name feature.");

        copyDefaults();

        this.configuration = new Configuration();

        firstNames = this.getConfiguration().getFirstNames();
        lastNames = this.getConfiguration().getLastNames();

        Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
        if (!legacy) Bukkit.getPluginManager().registerEvents(new EntityBreedListener(this), this);
    }

    private boolean checkLegacy() {
        try {
            // This event only exists in Spigot 1.10.2 and above.
            Class.forName("org.bukkit.event.entity.EntityBreedEvent");
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

    private void copyDefaults() {
        this.saveResource("firstnames.txt", false);
        this.saveResource("lastnames.txt", false);
        this.saveResource("config.yml", false);
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public String generateRandomName(boolean onlyFirstName) {
        if (firstNames.isEmpty() && lastNames.isEmpty())
            return this.getConfiguration().getStandardName();
        String firstName = firstNames.get(new Random().nextInt(firstNames.size() - 1));
        String lastName = onlyFirstName ? "" : lastNames.get(new Random().nextInt(lastNames.size() - 1));
        return firstName + " " + lastName;
    }
}
