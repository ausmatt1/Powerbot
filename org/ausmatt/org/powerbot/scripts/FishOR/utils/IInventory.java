package org.ausmatt.org.powerbot.scripts.FishOR.utils;

import org.powerbot.script.rt4.Inventory;

/**
 * Created by matt on 1/10/2014.
 */

public class IInventory extends Inventory {

    public IInventory(IClientContext arg0) {
        super(arg0);

    }

    public boolean isFull() {
        return ctx.inventory.select().count() == 28;
    }

    public int count(int itemID) {
        return ctx.inventory.select().id(itemID).count();
    }

    public boolean contains(int itemID) {
        return count(itemID) > 0;
    }

}