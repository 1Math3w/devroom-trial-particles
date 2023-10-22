package dev.math3w.particles.particles;

import dev.math3w.particles.ParticlesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public abstract class PlayerParticleManager {
    protected final ParticlesPlugin plugin;

    protected PlayerParticleManager(ParticlesPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract CompletableFuture<SelectableParticle> getSelectedParticle(Player player);


    public abstract void selectParticle(Player player, SelectableParticle particle);

    public boolean ownsParticle(Player player, SelectableParticle particle) {
        return player.hasPermission(particle.permission());
    }

    public boolean canPurchaseParticle(Player player, SelectableParticle particle) {
        return plugin.getEconomy().has(player, particle.price());
    }

    public void purchaseParticle(Player player, SelectableParticle particle) {
        plugin.getEconomy().withdrawPlayer(player, particle.price());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("lp user %s permission set %s", player.getName(), particle.permission()));
    }
}
