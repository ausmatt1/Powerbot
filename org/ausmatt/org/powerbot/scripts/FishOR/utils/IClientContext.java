package org.ausmatt.org.powerbot.scripts.FishOR.utils;

import org.powerbot.script.rt4.Player;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by matt on 1/10/2014.
 */

public class IClientContext extends ClientContext {
    public static IClientContext current;
    public final IInventory inventory;
    public final Player local;
    public final IBank bank;

    public IClientContext(ClientContext ctx) {
        super(ctx);
        current = this;
        this.inventory = new IInventory(this);
        this.local = players.local();
        this.bank = new IBank(this);
    }
}