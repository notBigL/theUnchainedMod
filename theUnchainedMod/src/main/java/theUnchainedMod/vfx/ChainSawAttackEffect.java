package theUnchainedMod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ChainSawAttackEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private final boolean isVertical;

    public ChainSawAttackEffect(float x, float y, boolean isVertical) {
        this.x = x;
        this.y = y;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.isVertical = isVertical;
    }

    public void update() {
        CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.4F);
        CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.4F);
        if (this.isVertical) {
            AbstractDungeon.effectsQueue.add(new ChainSawSlashEffect(this.x, this.y - 30.0F * Settings.scale, 0.0F, -500.0F, 180.0F, 3.0F, Color.GOLD, Color.GOLD));
        } else {
            AbstractDungeon.effectsQueue.add(new ChainSawSlashEffect(this.x, this.y - 30.0F * Settings.scale, -500.0F, -500.0F, 135.0F, 2.0F, Color.GOLD, Color.GOLD));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
