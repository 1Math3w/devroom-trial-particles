package dev.math3w.particles.config;

import dev.math3w.particles.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuConfig extends CustomConfig {
    public MenuConfig(JavaPlugin plugin) {
        super(plugin, "menu");
    }

    @Override
    protected void addDefaults() {
        addDefault("title", "Particles");
        addDefault("rows", 1);
        addDefault("click-to-select", "&eClick to select this particle!");
        addDefault("click-to-purchase", "&eClick to purchase this particle!");
        addDefault("not-enough", "&cYou don't have enough money!");
    }

    public String getTitle() {
        return Utils.colorize(getConfig().getString("title"));
    }

    public int getRows() {
        return getConfig().getInt("rows");
    }

    public String getString(String path, MessagePlaceholder... placeholders) {
        return MessagesConfig.formatMessage(getConfig().getString(path, "&cMissing message: " + getFile().getName() + "/" + path), placeholders);
    }
}
