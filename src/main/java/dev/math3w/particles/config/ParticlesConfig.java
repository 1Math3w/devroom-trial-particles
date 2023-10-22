package dev.math3w.particles.config;

import dev.math3w.particles.particles.SelectableParticle;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ParticlesConfig extends CustomConfig {
    public ParticlesConfig(JavaPlugin plugin) {
        super(plugin, "particles");
    }

    @Override
    protected void addDefaults() {
        addDefault("", new SelectableParticle("&6Flame", Material.BLAZE_POWDER, List.of("particle description"), 0, "particles.flame", 5, Particle.FLAME).toConfigSection(getConfig()));
        addDefault("", new SelectableParticle("&cHeart", Material.REDSTONE, List.of("particle description"), 1, "particles.heart", 10, Particle.HEART).toConfigSection(getConfig()));
        addDefault("", new SelectableParticle("&8Soul", Material.SOUL_SAND, List.of("particle description"), 2, "particles.soul", 10, Particle.SOUL).toConfigSection(getConfig()));
    }

    public SelectableParticle getParticle(String name) {
        ConfigurationSection configurationSection = getConfig().getConfigurationSection(name);
        if (configurationSection == null) return null;
        return SelectableParticle.fromConfig(configurationSection);
    }
}
