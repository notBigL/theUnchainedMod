package theUnchainedMod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SwordOfDamoclesEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    public static boolean SwordIsHanging = false;
    public static SwordHangingEffect sword;

    public SwordOfDamoclesEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }
    public void update() {
        AbstractDungeon.effectsQueue.add(new UnchainedVictoryVFX());
        if(!SwordIsHanging) {
            sword = new SwordHangingEffect(this.x, this.y - 30.0F * Settings.scale, 0.0F,-500.0f, Color.WHITE);
            sword.isDone = false;
            AbstractDungeon.effectsQueue.add(sword);
            SwordIsHanging = true;
        }
        this.isDone = true;
    }
    public static void SwordFallsDown() {
        SwordIsHanging = false;
        sword.InitiateFallDown();
    }
    public static void CutByChainSaw() {
        SwordIsHanging = false;
        sword.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
