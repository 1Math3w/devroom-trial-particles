package dev.math3w.particles.config;

import dev.math3w.particles.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MessagesConfig extends CustomConfig {
    public MessagesConfig(JavaPlugin plugin) {
        super(plugin, "messages");
    }

    public static String formatMessage(String message, MessagePlaceholder... placeholders) {
        for (MessagePlaceholder placeholder : placeholders) {
            message = message.replaceAll("%" + placeholder.getPlaceholder() + "%", placeholder.getValue());
        }

        return Utils.colorize(message);
    }

    public String getMessage(String path, MessagePlaceholder... placeholders) {
        return formatMessage(getConfig().getString(path, "&cMissing message: " + getFile().getName() + "/" + path), placeholders);
    }

    public void sendMessage(Player player, String path, MessagePlaceholder... placeholders) {
        String message = getMessage(path, placeholders);
        if (message.isEmpty()) return;
        player.sendMessage(message);
    }

    @Override
    protected void addDefaults() {
        addDefault("buy.not-enough", "&cYou don't have enough money to purchase this particle!");
        addDefault("buy.success", "&aYou have successfully purchased a particle %particle% &afor $%price%!");
        addDefault("select", "&aYou have selected particle %particle%");
        addDefault("already-selected", "&cYou have already selected this particle");
        addDefault("stop.not-selected", "&aYou don't have selected any particle");
        addDefault("stop.success", "&aYour particle was disabled");
    }
}
