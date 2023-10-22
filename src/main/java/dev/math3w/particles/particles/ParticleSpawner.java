package dev.math3w.particles.particles;

import dev.math3w.particles.ParticlesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ParticleSpawner {
    private final ParticlesPlugin plugin;

    public ParticleSpawner(ParticlesPlugin plugin) {
        this.plugin = plugin;

        startSpawningParticles();
    }

    private void startSpawningParticles() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                plugin.getPlayerParticleManager().getSelectedParticle(player).thenAccept(particle -> {
                    if (particle == null) return;

                    player.getWorld().spawnParticle(particle.particle(), player.getLocation(), 1, 0, 0, 0, 0);
                });
            }
        }, 0, 10);
    }
}
