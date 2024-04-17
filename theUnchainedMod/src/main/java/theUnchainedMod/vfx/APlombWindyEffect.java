package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class APlombWindyEffect extends AbstractGameEffect {
    private int count;
    private float timer;

    public APlombWindyEffect(Color setColor) {
        this.count = 0;
        this.timer = 0.0F;
        this.color = setColor.cpy();
    }

    public APlombWindyEffect() {
        this(new Color(0.95F, 0.67F, 0.26F, 1.0F));
    }

    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += 0.05F;

            AbstractDungeon.effectsQueue.add(new APlombWindyParticlesEffect(this.color));
            ++this.count;
            if (this.count == 7) {
                this.isDone = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
