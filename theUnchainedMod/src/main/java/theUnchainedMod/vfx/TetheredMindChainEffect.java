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
import static theUnchainedMod.DefaultMod.makeVFXPath;

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
        //sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(IMG, this.x - (this.dst / 2), this.y - 100.0F, 500.0F, 100.0F, this.dst, 200.0F, this.scale, this.scale, this.rotation, 0, 0, 1000, 200, false, false);
        //sb.setColor(new Color(1.F, 0.2F, 0.4F, this.color.a));
        //sb.draw(IMG,this.sX - 465.0F, this.sY - 56.5F, 465.0F, 56.5F, 930.0F, 113.0F, this.scale, this.scale, 0, 0, 0, 930, 113, false, false);
        //sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
