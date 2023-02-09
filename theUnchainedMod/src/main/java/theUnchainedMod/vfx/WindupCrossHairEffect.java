package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.DefaultMod.makeVFXPath;

public class WindupCrossHairEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private float rotation;
    private Color crossColor;
    private static final float DUR = 1.5F;
    private static final Texture IMG_CIRCLE = loadImage(makeVFXPath("Wind_Up_Cross_Hair_Effect_Circle.png"));
    private static final Texture IMG_CROSS = loadImage(makeVFXPath("Wind_Up_Cross_Hair_Effect_Cross.png"));


    public WindupCrossHairEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.rotation = 0.0F;
        this.scale = 5.0F;
        this.crossColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.startingDuration = this.duration = DUR;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow3Out.apply(0.5F, 1.0F, 1.0F - this.duration / DUR);
            this.crossColor.a = Interpolation.pow3Out.apply(0.0F, 1.0F, 1.0F - this.duration / DUR);
        } else {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, 1.0F - this.duration / DUR);
            this.crossColor.a = Interpolation.pow2In.apply(1.0F, 0.0F, 1.0F - this.duration / DUR);
        }
        this.scale = Interpolation.exp10Out.apply(4.5F, 0.8F, 1.0F - this.duration / DUR);
        rotation++;

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(IMG_CIRCLE, this.x - 128.0F, this.y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, Settings.scale, Settings.scale, rotation, 0, 0, 256, 256, false, false);
        sb.setColor(this.crossColor);
        sb.draw(IMG_CROSS, this.x - 128.0F, this.y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.scale, this.scale, 0, 0, 0, 256, 256, false, false);
    }

    public void dispose() {
    }
}
