package dev.math3w.particles;

import dev.math3w.particles.commands.ParticlesCommand;
import dev.math3w.particles.commands.ParticlesStopCommand;
import dev.math3w.particles.config.DatabaseConfig;
import dev.math3w.particles.config.MenuConfig;
import dev.math3w.particles.config.MessagesConfig;
import dev.math3w.particles.config.ParticlesConfig;
import dev.math3w.particles.menus.ParticlesMenu;
import dev.math3w.particles.particles.ParticleSpawner;
import dev.math3w.particles.particles.PlayerParticleManager;
import dev.math3w.particles.particles.SQLPlayerParticleManager;
import me.zort.containr.Containr;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticlesPlugin extends JavaPlugin {
    private Economy economy = null;
    private MessagesConfig messagesConfig;
    private DatabaseConfig databaseConfig;
    private MenuConfig menuConfig;
    private ParticlesConfig particlesConfig;
    private PlayerParticleManager playerParticleManager;
    private ParticlesMenu particlesMenu;
    private ParticleSpawner particleSpawner;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getLogger().severe("No registered Vault provider found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Containr.init(this);

        economy = rsp.getProvider();

        getCommand("particles").setExecutor(new ParticlesCommand(this));
        getCommand("particlestop").setExecutor(new ParticlesStopCommand(this));

        messagesConfig = new MessagesConfig(this);
        databaseConfig = new DatabaseConfig(this);
        menuConfig = new MenuConfig(this);
        particlesConfig = new ParticlesConfig(this);
        playerParticleManager = new SQLPlayerParticleManager(this);
        particlesMenu = new ParticlesMenu(this);
        particleSpawner = new ParticleSpawner(this);
    }

    public Economy getEconomy() {
        return economy;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public MenuConfig getMenuConfig() {
        return menuConfig;
    }

    public ParticlesConfig getParticlesConfig() {
        return particlesConfig;
    }

    public PlayerParticleManager getPlayerParticleManager() {
        return playerParticleManager;
    }

    public ParticlesMenu getParticlesMenu() {
        return particlesMenu;
    }

    public ParticleSpawner getParticleSpawner() {
        return particleSpawner;
    }
}