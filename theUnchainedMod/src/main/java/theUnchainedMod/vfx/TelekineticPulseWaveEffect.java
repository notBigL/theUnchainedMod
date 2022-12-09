package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TelekineticPulseWaveEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 2.0F;
    private float x;
    private final float y;
    private float speed;
    private final float speedStart;
    private final float speedTarget;
    private static final float ROTATION = 270.0F;
    private final TextureAtlas.AtlasRegion img;

    public TelekineticPulseWaveEffect(float x, float y, Color color, float chosenSpeed) {
        this.img = ImageMaster.BLUR_WAVE;
        this.rotation = MathUtils.random(360.0F);
        this.scale = MathUtils.random(0.9F, 1.3F);
        this.x = x - (float) this.img.packedWidth / 2.0F;
        this.y = y - (float) this.img.packedHeight / 2.0F;
        this.duration = 2.0F;
        this.color = color;
        this.renderBehind = MathUtils.randomBoolean();
        this.speedStart = chosenSpeed;
        this.speedTarget = 2000.0F * Settings.scale;
        this.speed = this.speedStart;
        color.g -= MathUtils.random(0.1F);
        color.b -= MathUtils.random(0.2F);
        color.a = 0.0F;
    }

    public TelekineticPulseWaveEffect(float x, float y, float chosenSpeed) {
        this(x, y, new Color(0.5F, 0.67F, 0.96F, 1.0F), chosenSpeed);
    }

    public void update() {
        this.speed = Interpolation.fade.apply(this.speedStart, this.speedTarget, 1.0F - this.duration / 2.0F);
        this.x += this.speed * Gdx.graphics.getDeltaTime();
        this.scale *= 1.0F + Gdx.graphics.getDeltaTime() * 2.0F;
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration > 1.5F) {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, (2.0F - this.duration) * 2.0F);
        } else if (this.duration < 0.5F) {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 2.0F);
        }


    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale + MathUtils.random(-0.08F, 0.08F), this.scale + MathUtils.random(-0.08F, 0.08F), ROTATION);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
