package net.kettlemc.villagernames.configuration;

import net.kettlemc.villagernames.VillagerNamePlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Configuration {

    private BasicConfigurationHandler config;

    private boolean allowRenaming, randomBabyLastname;
    private String standardName;
    private List<String> disabledSpawnReasons;

    private List<String> firstNames, lastNames;

    public Configuration () {
        this.config = new BasicConfigurationHandler("plugins/SimpleVillagerNames/config.yml");
        this.allowRenaming = this.config.getBool("allow-renaming", true);
        this.randomBabyLastname = this.config.getBool("random-baby-lastname", false);
        this.standardName = this.config.getString("standard-name", "Phil Itcher");
        this.disabledSpawnReasons = (List<String>) this.config.getValue("disabled-spawn-reasons", Arrays.asList(new String[] {"CUSTOM"}));

        this.firstNames = getListFromFile("firstnames.txt");
        this.lastNames = getListFromFile("lastnames.txt");

    }

    private List<String> getListFromFile(String fileName) {
        File file = new File("plugins/SimpleVillagerNames/" + fileName);
        List<String> lines = new ArrayList<>();
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (Exception e) {
                System.out.println(VillagerNamePlugin.CONSOLE_PREFIX + "Error while reading the " + fileName + " list.");
                e.printStackTrace();
            }
        }
        return lines;
    }

    public boolean isRenamingAllowed() {
        return this.allowRenaming;
    }

    public boolean generateRandomBabyLastName() {
        return this.allowRenaming;
    }

    public String getStandardName() {
        return this.standardName;
    }

    public List<String> getDisabledSpawnReasons() {
        return this.disabledSpawnReasons;
    }

    public List<String> getFirstNames() {
        return this.firstNames;
    }

    public List<String> getLastNames() {
        return this.lastNames;
    }
}
