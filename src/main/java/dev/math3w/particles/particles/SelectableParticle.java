package dev.math3w.particles.particles;

import dev.math3w.particles.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public record SelectableParticle(String name, Material material, List<String> description, int slot, String permission, int price, Particle particle) {
    public static SelectableParticle fromConfig(ConfigurationSection configurationSection) {
        return new SelectableParticle(Utils.colorize(configurationSection.getString("name")),
                Material.valueOf(configurationSection.getString("material", "").toUpperCase()),
                configurationSection.getStringList("description").stream().map(Utils::colorize).toList(),
                configurationSection.getInt("slot"),
                configurationSection.getString("permission"),
                configurationSection.getInt("price"),
                Particle.valueOf(configurationSection.getString("particle", "").toUpperCase()));
    }

    public ConfigurationSection toConfigSection(ConfigurationSection parent) {
        ConfigurationSection configurationSection = parent.createSection(ChatColor.stripColor(Utils.colorize(name)).toLowerCase().replaceAll(" ", "-"));

        configurationSection.set("name", name);
        configurationSection.set("material", material.name().toLowerCase());
        configurationSection.set("description", description);
        configurationSection.set("slot", slot);
        configurationSection.set("permission", permission);
        configurationSection.set("price", price);
        configurationSection.set("particle", particle.name().toLowerCase());

        return configurationSection;
    }
}
