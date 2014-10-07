package org.ausmatt.org.powerbot.scripts.FishOR.utils;

import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Skills;

import java.text.DecimalFormat;

/**
 * Created by matt on 2/10/2014.
 */

public class Tracker implements MessageListener {

    private ClientContext ctx;
    private int startXPFishing;
    private int fishingXPGained;
    private int nextLevelXP;
    private int XPTillNextLevel;
    private String TTL;
    private int totalCaughtPerHour;
    private int startLevel;
    private int levelsGained;
    public static long startTime;
    private int fishingXPPerHour;
    private String timer;
    private int currentLevel;
    private long TimeTillLevel;

    public static int caught = 0;

    public Tracker(ClientContext arg0) {
        ctx = arg0;
        startXPFishing = curFishingXP();
        startLevel = curLevel();
        startTime = System.currentTimeMillis();
    }

    final int[] XP_TABLE =
            {
                    0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154,
                    1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018,
                    5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
                    16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224,
                    41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721,
                    101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254,
                    224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428,
                    496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
                    1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068,
                    2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294,
                    4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614,
                    8771558, 9684577, 10692629, 11805606, 13034431, 200000000
            };

    public void update() {
        long time = System.currentTimeMillis() - startTime;
        long secs = (time) / 1000 % 60;
        long mins = (time / (1000 * 60)) % 60;
        long hours = (time / (1000 * 60 * 60)) % 24;
        timer = String.format("%02d:%02d:%02d", hours, mins, secs);
        currentLevel = ctx.skills.realLevel(Skills.FISHING);
        fishingXPGained = curFishingXP() - startXPFishing;
        fishingXPPerHour = (int)(fishingXPGained / ((System.currentTimeMillis() - startTime) / 3600000.0D));
        totalCaughtPerHour = (int)((caught * 3600000D) / time);
        levelsGained = curLevel() - startLevel;
        nextLevelXP = XP_TABLE[curLevel() + 1];
        XPTillNextLevel = nextLevelXP - curFishingXP();
        TimeTillLevel = (long)(((double) XPTillNextLevel * 3600000.0) / (double) fishingXPPerHour);
        long secs2 = (TimeTillLevel) / 1000 % 60;
        long mins2 = (TimeTillLevel / (1000 * 60)) % 60;
        long hours2 = (TimeTillLevel / (1000 * 60 * 60)) % 24;
        TTL = String.format("%02d:%02d:%02d", hours2, mins2, secs2);
    }

    private int curFishingXP() {
        return ctx.skills.experience(Skills.FISHING);
    }

    public int curLevel(){
        return ctx.skills.realLevel(Skills.FISHING);
    }

    public int getFishingXPPerHour() {
        return fishingXPPerHour;
    }

    public int getFishingXPGained() {
        return fishingXPGained;
    }

   public String getTime() {
        return timer;
    }

    public String getTTL(){
        return TTL;
    }

    public String getCaughtPerHour(){
        DecimalFormat df = new DecimalFormat("#");
        return df.format(totalCaughtPerHour);
    }

    public int levelsGained(){
        return levelsGained;
    }

    public int fishCaught(){
        return caught;
    }



    @Override
    public void messaged(MessageEvent e) {
        if (e.text().contains("You catch a")) {
            caught++;
        }
    }
}

