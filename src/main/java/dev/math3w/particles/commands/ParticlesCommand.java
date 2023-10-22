package dev.math3w.particles.commands;

import dev.math3w.particles.ParticlesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParticlesCommand implements CommandExecutor {
    private final ParticlesPlugin plugin;

    public ParticlesCommand(ParticlesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        plugin.getParticlesMenu().open(player);
        return true;
    }
}
