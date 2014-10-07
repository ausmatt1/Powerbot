package org.ausmatt.org.powerbot.scripts.FishOR.utils;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;
import org.powerbot.script.rt4.Bank;

import java.util.ArrayList;

/**
 * Created by Matt on 5/10/2014.
 */
public class IBank extends Bank {
    //Tile tile1 = new Tile(2834,2976, 0);
    static Tile bankTile;
    static int bankerID;
    public static ArrayList<Integer> fishes	= new ArrayList<Integer>();

    public IBank(IClientContext arg0) {
        super(arg0);
    }

    public boolean openBank() {
        Npc bank = ctx.npcs.select().id(bankerID).nearest().poll();
        if (bank != null) {
            while (!opened()) {
                ctx.camera.pitch(99);
                ctx.camera.turnTo(bank);
                bank.interact("Bank");
                Condition.sleep(1000);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean bankAll() {
        if (opened()) {
            for(int i=0; i<fishes.size(); i++){
                deposit(fishes.get(i), Amount.ALL);
                Condition.sleep(1000);
                }
            close();
            return true;
            }
        return false;
        }

    public void drop() {
            for(int i=0; i<fishes.size(); i++) {
                while (ctx.inventory.select().id(fishes.get(i)).count() >= 0) {
                ctx.inventory.select().id(fishes.get(i));
                ctx.inventory.poll().interact("Drop");
            }
        }
    }

    public void runToBankTile(){
        if ((ctx.movement.energyLevel() > 30) && (!ctx.movement.running())) {
            ctx.movement.running(true);
        }
        ctx.camera.pitch(99);
        ctx.movement.findPath(bankTile);
        ctx.camera.turnTo(bankTile);
        ctx.movement.step(bankTile);
    }


    public static void addFish(int id) {
        fishes.add(id);
    }

    public static void setBankTile(Tile bankerTile) {
        bankTile = bankerTile;
    }

    public static Tile getBankTile() {
        return bankTile;
    }

    public static void setBankerID(int bankID) {
        bankerID = bankID;
    }

    }
