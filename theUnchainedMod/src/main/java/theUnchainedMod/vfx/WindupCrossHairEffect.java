package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.DefaultMod.makeVFXPath;

public class WindupCrossHairEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private static final float DUR = 1.5F;
    private static final Texture IMG = loadImage(makeVFXPath("Windup_Cross_Hair.png"));


    public WindupCrossHairEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.startingDuration = this.duration = DUR;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow3Out.apply(0.5F, 1.0F, 1.0F - this.duration / DUR);
        } else {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, 1.0F - this.duration / DUR);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(IMG, this.x - 128.0F, this.y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.scale, this.scale, 0, 0, 0, 256, 256, false, false);
    }

    public void dispose() {
    }
}
