package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class TetheredMindChainEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private final float sX;
    private final float sY;
    private final float dX;
    private final float dY;
    private final float dst;
    private static final float DUR = 0.5F;
    private static final Texture IMG = loadImage(makeVFXPath("Tethered_Mind_Effect.png"));

    public TetheredMindChainEffect(float sX, float sY, float dX, float dY) {
        this.x = (dX + sX) / 2.0F;
        this.y = (dY + sY) / 2.0F;
        this.sX = sX;
        this.sY = sY;
        this.dX = dX;
        this.dY = dY;
        this.dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.startingDuration = this.duration = DUR;
        this.rotation = MathUtils.atan2(dX - sX, dY - sY);
        this.rotation *= 57.295776F;
        this.rotation = -this.rotation + 90.0F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
        } else {
            this.color.a = Interpolation.bounceIn.apply(0.0F, 1.0F, this.duration * 4.0F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(IMG, this.x - (this.dst / 2), this.y - 61.5F, 965.0F, 61.5F, this.dst, 123.0F/(1930/dst), this.scale, this.scale, this.rotation, 0, 0, 1930, 123, false, false);
    }

    public void dispose() {
    }
}
