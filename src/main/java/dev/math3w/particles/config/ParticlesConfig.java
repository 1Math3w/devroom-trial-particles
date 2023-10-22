package dev.math3w.particles.config;

import dev.math3w.particles.particles.SelectableParticle;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ParticlesConfig extends CustomConfig {
    public ParticlesConfig(JavaPlugin plugin) {
        super(plugin, "particles");
    }

    @Override
    protected void addDefaults() {
        addDefault("", new SelectableParticle("flame", "&6Flame", Material.BLAZE_POWDER, List.of("particle description"), 0, "particles.flame", 5, Particle.FLAME).toConfigSection(getConfig()));
        addDefault("", new SelectableParticle("heart", "&cHeart", Material.REDSTONE, List.of("particle description"), 1, "particles.heart", 10, Particle.HEART).toConfigSection(getConfig()));
        addDefault("", new SelectableParticle("soul", "&8Soul", Material.SOUL_SAND, List.of("particle description"), 2, "particles.soul", 10, Particle.SOUL).toConfigSection(getConfig()));
    }

    public SelectableParticle getParticle(String name) {
        ConfigurationSection configurationSection = getConfig().getConfigurationSection(name);
        if (configurationSection == null) return null;
        return SelectableParticle.fromConfig(configurationSection);
    }

    public Set<SelectableParticle> getParticles() {
        return getConfig().getKeys(false).stream()
                .map(name -> SelectableParticle.fromConfig(Objects.requireNonNull(getConfig().getConfigurationSection(name))))
                .collect(Collectors.toSet());
    }
}
