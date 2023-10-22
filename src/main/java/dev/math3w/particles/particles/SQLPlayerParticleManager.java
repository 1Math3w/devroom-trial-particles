package dev.math3w.particles.particles;

import dev.math3w.particles.ParticlesPlugin;
import dev.math3w.particles.databases.MySQLDatabase;
import dev.math3w.particles.databases.SQLDatabase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.concurrent.CompletableFuture;

public class SQLPlayerParticleManager extends PlayerParticleManager {
    private final SQLDatabase sqlDatabase;

    public SQLPlayerParticleManager(ParticlesPlugin plugin) {
        super(plugin);
        sqlDatabase = new MySQLDatabase(plugin.getDatabaseConfig().getMySQLConfig());
        createTable();
    }

    private void createTable() {
        try (PreparedStatement statement = sqlDatabase.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS player_particles (" +
                "player VARCHAR(36) PRIMARY KEY, " +
                "selected VARCHAR(64) DEFAULT NULL);")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<SelectableParticle> getSelectedParticle(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement statement = sqlDatabase.getConnection().prepareStatement("SELECT (selected) FROM player_particles WHERE player=?")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String particleName = resultSet.getString(1);
                        if (particleName == null) return null;
                        return plugin.getParticlesConfig().getParticle(particleName);
                    }
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void selectParticle(Player player, SelectableParticle particle) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (PreparedStatement statement = sqlDatabase.getConnection().prepareStatement("INSERT INTO player_particles (player, selected) " +
                    "VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE selected = ?;")) {
                statement.setString(1, player.getUniqueId().toString());
                if (particle != null) {
                    statement.setString(2, particle.name());
                    statement.setString(3, particle.name());
                } else {
                    statement.setNull(2, Types.VARCHAR);
                    statement.setNull(3, Types.VARCHAR);
                }
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
