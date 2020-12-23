package me.meowso.simpleautosell;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.io.IOException;

public class Listeners implements Listener {
    private final SellHandler sellHandler;

    public Listeners(SimpleAutoSell simpleAutoSell) {
        sellHandler = new SellHandler(simpleAutoSell);
    }

    @EventHandler
    public void OnPlayerPickup(EntityPickupItemEvent e) throws IOException {
        if (e.getEntity() instanceof Player) sellHandler.handleItemPickup((Player) e.getEntity());
    }
}
