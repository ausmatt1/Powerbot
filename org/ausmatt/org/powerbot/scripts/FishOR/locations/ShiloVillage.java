package org.ausmatt.org.powerbot.scripts.FishOR.locations;

import org.ausmatt.org.powerbot.scripts.FishOR.utils.IBank;
import org.ausmatt.org.powerbot.scripts.FishOR.utils.Fishing;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;
import org.powerbot.script.Area;


/**
 * Created by Matt on 5/10/2014.
 */
public class ShiloVillage extends Fishing {
    static Tile shiloVillageBankTile = new Tile(2852, 2955, 0);
    static Tile fishArea1 = new Tile(2820, 2990, 0);
    static Tile fishArea2 = new Tile(2867, 2966, 0);
    static Area shiloVillageFishArea1 = new Area(fishArea1, fishArea2);
    static Tile fishArea3 = new Tile(2852, 2989, 0);
    static Tile fishArea4 = new Tile(2868, 2965, 0);
    static Area shiloVillageFishArea2 = new Area(fishArea3, fishArea4);
    static Tile fishArea5 = new Tile(2821, 2990, 0);
    static Tile fishArea6 = new Tile(2849, 2964, 0);
    static Area shiloVillageFishArea3 = new Area(fishArea5, fishArea6);
    static Tile bankArea1= new Tile(2850, 2956, 0);
    static Tile bankArea2 = new Tile(2855, 2953, 0);
    static Area shiloVillageBankArea = new Area(bankArea1, bankArea2);
    static Tile fishSpot1 = new Tile(2860, 2970, 0);
    static Tile fishSpot2 = new Tile(2836, 2970, 0);
    int fishSpotID = 1515;
    int bankerID = 399;
    int fishID1 = 331;
    int fishID2 = 335;
    public boolean fishFound = false;

    public ShiloVillage(ClientContext arg0) {
        super(arg0);
        IBank.setBankTile(shiloVillageBankTile);
        IBank.setBankerID(bankerID);
        IBank.addFish(fishID1);
        IBank.addFish(fishID2);
    }

    @Override
    public boolean foundFish() {
        Npc fish = ctx.npcs.select().id(fishSpotID).nearest().poll();
        if (fish != null) {
            if ((ctx.movement.energyLevel() > 30) && (!ctx.movement.running())) {
                ctx.movement.running(true);
            }
            ctx.camera.pitch(99);
            ctx.camera.turnTo(fish);
            fish.interact("Lure");
            fishFound = true;
            Condition.sleep(Random.nextInt(1000, 3000));
            return true;
        }
        while (fish == null) {
            if (!fishFound) {
                if (fish != null) {
                    if ((ctx.movement.energyLevel() > 30) && (!ctx.movement.running())) {
                        ctx.movement.running(true);
                    }
                    ctx.camera.pitch(99);
                    ctx.camera.turnTo(fish);
                    fish.interact("Lure");
                    fishFound = true;
                    Condition.sleep(Random.nextInt(1000, 3000));
                    return true;
                }
                    if (shiloVillageFishArea3.contains(ctx.players.local().tile())) {
                    if ((ctx.movement.energyLevel() > 30) && (!ctx.movement.running())) {
                        ctx.movement.running(true);
                    }
                    ctx.camera.pitch(99);
                    ctx.movement.findPath(fishSpot2);
                    ctx.camera.turnTo(fishSpot2);
                    ctx.movement.step(fishSpot2);
                    fishFound = false;
                    Condition.sleep(6000);
                    return false;
                } else
                        if (shiloVillageFishArea2.contains(ctx.players.local().tile())) {
                            if ((ctx.movement.energyLevel() > 30) && (!ctx.movement.running())) {
                                ctx.movement.running(true);
                            }
                            ctx.camera.pitch(99);
                            ctx.movement.findPath(fishSpot1);
                            ctx.camera.turnTo(fishSpot1);
                            ctx.movement.step(fishSpot1);
                            fishFound = false;
                            Condition.sleep(6000);
                            return false;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    protected void setFishTile(){
        addfishTile(fishSpot1);
    }

    @Override
    protected Tile getFishTile(){
        return fishSpot1;
    }

    @Override
    protected void setFishArea(){
        addFishArea(shiloVillageFishArea1);
    }

    @Override
    protected Area getFishArea(){
        return shiloVillageFishArea1;
    }

    @Override
    protected void setBankArea(){
        addBankArea(shiloVillageBankArea);
    }

    @Override
    protected Area getBankArea(){
        return shiloVillageBankArea;
    }

}