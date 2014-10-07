package org.ausmatt.org.powerbot.scripts.FishOR;

import org.ausmatt.org.powerbot.scripts.FishOR.locations.ShiloVillage;
import org.ausmatt.org.powerbot.scripts.FishOR.utils.*;

import org.powerbot.script.*;
import org.powerbot.script.rt4.Player;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;



/**
 * Created by Matt on 4/10/2014.
 */
@Script.Manifest(name = "FishOR", description = "Fishes in Shilo Village")
public class FishOR extends PollingScript<IClientContext> implements PaintListener, BotMenuListener, MessageListener {
    private State currentState;
    private boolean selectedOption = false;
    private static boolean banking = true;
    private static String currentLocation = "Shilo Village";
    private HashMap<String, Fishing> fishLocation = new HashMap();
    private Tracker	stats = new Tracker(ctx);

    private Player player() {
        return ctx.players.local();
    }

    @Override
    public void start() {
        fishLocation.put("Shilo Village", new ShiloVillage(ctx));
    }

    private State getState() {
        if (!selectedOption || !ctx.game.loggedIn()) {
            return State.LOADING;
        } else if (ctx.inventory.isFull()) {
            if (banking) {
                if (fishLocation.get(currentLocation).bankArea().contains(player().tile())) {
                    return State.BANKING;
                } else
                    return State.WALKING_TO_BANK;
            } else
                return State.DROPPING;
        } else if (player().animation() != -1) {
            return State.FISHING;
        } else
        if (fishLocation.get(currentLocation).fishArea().contains(player().tile())) {
            return State.SEARCHING_FOR_FISH;
        } else
            return State.WALKING_TO_FISH;
        }

    enum State {
        LOADING,
        BANKING,
        DROPPING,
        FISHING,
        WALKING_TO_BANK,
        WALKING_TO_FISH,
        SEARCHING_FOR_FISH
    }

    @Override
    public void poll() {
        stats.update();
        ctx.camera.pitch(99);
        currentState = getState();
        switch (currentState) {
            case LOADING:
                break;
            case BANKING:
                ctx.bank.openBank();
                if (ctx.bank.bankAll()) {
                    currentState = State.WALKING_TO_FISH;
                }
                break;
            case DROPPING:
                ctx.bank.drop();
                break;
            case FISHING:
                if (ctx.players.local().animation() != -1)
                break;
            case WALKING_TO_BANK:
                ctx.bank.runToBankTile();
                break;
            case WALKING_TO_FISH:
                fishLocation.get(currentLocation).runToFishTile();
                break;
            case SEARCHING_FOR_FISH:
           if (fishLocation.get(currentLocation).foundFish());
                break;
        }

    }

    @Override
    public void menuSelected(MenuEvent e) {
        final JMenu menu = (JMenu) e.getSource();
        final JMenu shiloVillage = new JMenu("Shilo Village");
        final JMenu lureFishing = new JMenu("Lure Fishing");
        menu.add(shiloVillage);
        shiloVillage.add(lureFishing);
        final JMenuItem isBanking = new JMenuItem("Banking");
        isBanking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedOption = true;
                banking = true;
                currentLocation = "Shilo Village";
                log.info("Shilo Village - Lure Fishing - Banking");
            }
        });
        lureFishing.add(isBanking);
        final JMenuItem isDropping = new JMenuItem("Dropping");
        isDropping.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedOption = true;
                banking = false;
                currentLocation = "Shilo Village";
                log.info("Shilo Village - Lure Fishing - Dropping");
            }
        });
        lureFishing.add(isDropping);
    }

    @Override
    public void messaged(MessageEvent e) {
        String message = e.text();
        if (message.contains("You catch a")) {
            stats.caught++;
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }

    @Override
    public void repaint(Graphics g) {
        //if (selectedOption) {
            //if (ctx.game.loggedIn()) {
                Color backgroundFill = new Color(0, 0, 0, 101);
                Font textFont = new Font("Arial", 0, 12);
                Color textColor = new Color(255, 255, 255);
                Graphics2D gr = (Graphics2D) g;
                gr.setColor(backgroundFill);
                gr.fillRect(370, 183, 146, 155);
                gr.setFont(textFont);
                gr.setColor(textColor);
                gr.drawString("FishOR BETA", 375, 205); // Name of bot
                gr.drawString("" + currentState, 375, 230);
                gr.drawString("Time Ran: " + stats.getTime(), 375, 245);
                gr.drawString("Fish Caught: " + stats.fishCaught(), 375, 260);
                gr.drawString("Fish /Hr " + stats.getCaughtPerHour(), 375, 275);
                gr.drawString("EXP Gained: " + stats.getFishingXPGained(), 375, 290);
                gr.drawString("EXP/Hr: " + stats.getFishingXPPerHour(), 375, 305);
                gr.drawString("Current Level: " + stats.curLevel() + " " + " (" + stats.levelsGained() + ")", 375, 320);
                gr.drawString("TTL: " + stats.getTTL(), 375, 335);
            //}
        //}
    }

}
