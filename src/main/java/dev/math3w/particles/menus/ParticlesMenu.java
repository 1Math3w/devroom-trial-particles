package dev.math3w.particles.menus;

import dev.math3w.particles.ParticlesPlugin;
import dev.math3w.particles.config.MenuConfig;
import dev.math3w.particles.config.MessagePlaceholder;
import dev.math3w.particles.config.MessagesConfig;
import dev.math3w.particles.particles.PlayerParticleManager;
import dev.math3w.particles.particles.SelectableParticle;
import me.zort.containr.Component;
import me.zort.containr.Element;
import me.zort.containr.GUI;
import me.zort.containr.internal.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class ParticlesMenu {
    private final ParticlesPlugin plugin;
    private final Element glassElement;

    public ParticlesMenu(ParticlesPlugin plugin) {
        this.plugin = plugin;
        glassElement = Component.element(ItemBuilder.newBuilder(Material.BLACK_STAINED_GLASS_PANE).withName(ChatColor.WHITE.toString()).build()).build();
    }

    public void open(Player player) {
        getGui(player).thenAccept(gui -> Bukkit.getScheduler().runTask(plugin, () -> gui.open(player)));
    }

    public CompletableFuture<GUI> getGui(Player player) {
        return plugin.getPlayerParticleManager().getSelectedParticle(player).thenApply(this::getGui).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public GUI getGui(SelectableParticle selectedParticle) {
        return Component.gui()
                .title(plugin.getMenuConfig().getTitle())
                .rows(plugin.getMenuConfig().getRows())
                .prepare((gui, player) -> {
                    for (SelectableParticle particle : plugin.getParticlesConfig().getParticles()) {
                        ItemBuilder iconBuilder = ItemBuilder.newBuilder(particle.material())
                                .withName(particle.displayName())
                                .appendLore(particle.description())
                                .appendLore("")
                                .appendLore(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$" + particle.price());

                        appendCallToActionLore(player, iconBuilder, particle, selectedParticle);

                        if (particle.equals(selectedParticle)) {
                            iconBuilder.enchanted();
                        }

                        gui.setElement(particle.slot(), Component.element(iconBuilder.build())
                                .click(clickInfo -> {
                                    PlayerParticleManager playerParticleManager = plugin.getPlayerParticleManager();
                                    MessagesConfig messagesConfig = plugin.getMessagesConfig();

                                    if (particle.equals(selectedParticle)) {
                                        messagesConfig.sendMessage(player, "already-selected");
                                        return;
                                    }

                                    if (playerParticleManager.ownsParticle(player, particle)) {
                                        playerParticleManager.selectParticle(player, particle);
                                        messagesConfig.sendMessage(player, "select", new MessagePlaceholder("particle", particle.displayName()));
                                        getGui(particle).open(player);
                                        return;
                                    }

                                    if (playerParticleManager.canPurchaseParticle(player, particle)) {
                                        playerParticleManager.purchaseParticle(player, particle);
                                        messagesConfig.sendMessage(player,
                                                "buy.success",
                                                new MessagePlaceholder("particle", particle.displayName()),
                                                new MessagePlaceholder("price", String.valueOf(particle.price())));
                                        open(player);
                                        return;
                                    }

                                    messagesConfig.sendMessage(player, "buy.not-enough");
                                })
                                .build());
                    }
                }).build();
    }

    private void appendCallToActionLore(Player player, ItemBuilder iconBuilder, SelectableParticle particle, SelectableParticle selectedParticle) {
        if (particle.equals(selectedParticle)) {
            return;
        }

        iconBuilder.appendLore("");

        PlayerParticleManager playerParticleManager = plugin.getPlayerParticleManager();
        MenuConfig menuConfig = plugin.getMenuConfig();

        if (playerParticleManager.ownsParticle(player, particle)) {
            iconBuilder.appendLore(menuConfig.getString("click-to-select"));
            return;
        }

        if (playerParticleManager.canPurchaseParticle(player, particle)) {
            iconBuilder.appendLore(menuConfig.getString("click-to-purchase"));
            return;
        }

        iconBuilder.appendLore(menuConfig.getString("not-enough"));
    }
}
