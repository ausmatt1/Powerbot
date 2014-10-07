package org.ausmatt.org.powerbot.scripts.FishOR.utils;

import org.ausmatt.powerbot.rt4.script.FishOR.Places.ShiloVillage;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.Area;

/**
 * Created by Matt on 5/10/2014.
 */
public abstract class Fishing extends ClientAccessor {
    protected int caught;
    protected Tile fishTile;
    protected Area fishArea;
    protected Area bankArea;

    public Fishing(ClientContext arg0) {
        super(arg0);
        caught = 0;
        getFishTile();
        setFishTile();
        foundFish();
        fishTile();
        setFishArea();
        getFishArea();
        setBankArea();
        getBankArea();
    }

    public Tile fishTile() {
        return fishTile;
    }

    public Area fishArea() {
        return fishArea;
    }

    public Area bankArea() {
        return bankArea;
    }

    public abstract boolean foundFish();

    protected abstract void setFishTile();

    protected void addfishTile(Tile fishingTile) {
        fishTile = fishingTile;
    }

    protected void addFishArea(Area fishingArea) {
        fishArea = fishingArea;
    }

    protected void addBankArea(Area bankingArea) {
        bankArea = bankingArea;
    }

    protected abstract Tile getFishTile();

    protected abstract void setFishArea();

    protected abstract Area getFishArea();

    protected abstract void setBankArea();

    protected abstract Area getBankArea();

    public void runToFishTile(){
        if ((ctx.movement.energyLevel() > 30) && (!ctx.movement.running())) {
            ctx.movement.running(true);
        }
        ctx.movement.findPath(fishTile);
        ctx.camera.turnTo(fishTile);
        ctx.movement.step(fishTile);
    }



}
