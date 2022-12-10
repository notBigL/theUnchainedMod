package theUnchainedMod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;

public class TetheredMindIgniteEffect extends AbstractGameEffect {
    private static final int COUNT = 25;
    private float x;
    private float y;

    public TetheredMindIgniteEffect(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        for (int i = 0; i < COUNT; ++i) {
            AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, new Color(1.0f, 0.4f, 0.96f, 1.0f)));
        }
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }

}
