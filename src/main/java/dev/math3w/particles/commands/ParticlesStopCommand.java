package dev.math3w.particles.commands;

import dev.math3w.particles.ParticlesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParticlesStopCommand implements CommandExecutor {
    private final ParticlesPlugin plugin;

    public ParticlesStopCommand(ParticlesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        plugin.getPlayerParticleManager().getSelectedParticle(player).thenAccept(particle -> {
            if (particle == null) {
                plugin.getMessagesConfig().sendMessage(player, "stop.not-selected");
                return;
            }
            
            plugin.getPlayerParticleManager().selectParticle(player, null);
            plugin.getMessagesConfig().sendMessage(player, "stop.success");
        });
        return true;
    }
}
