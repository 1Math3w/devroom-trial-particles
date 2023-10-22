package dev.math3w.particles.config;

import dev.math3w.particles.databases.SQLDatabase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseConfig extends CustomConfig {
    public DatabaseConfig(JavaPlugin plugin) {
        super(plugin, "database");
    }

    @Override
    protected void addDefaults() {
        addDefault("type", "mysql");
        addDefault("mysql.hostname", "");
        addDefault("mysql.port", 3306);
        addDefault("mysql.database", "");
        addDefault("mysql.username", "");
        addDefault("mysql.password", "");
    }

    public ConfigurationSection getMySQLConfig() {
        return getConfig().getConfigurationSection("mysql");
    }

    public SQLDatabase.Type getSqlType() {
        for (SQLDatabase.Type type : SQLDatabase.Type.values()) {
            if (!getConfig().getString("type").equalsIgnoreCase(type.getConfigName())) continue;
            return type;
        }
        throw new RuntimeException("Plugin doesn't support " + getConfig().getString("type") + " as sql database. Please use mysql");
    }
}
