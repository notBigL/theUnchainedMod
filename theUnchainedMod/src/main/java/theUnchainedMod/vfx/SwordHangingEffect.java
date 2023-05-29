package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class SwordHangingEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float scaleX;
    private float scaleY;
    private Color color2;
    private final static Texture IMG = loadImage(makeVFXPath("Sword_of_Damocles_Effect.png"));

    public SwordHangingEffect(float x, float y, float dX, float dY, float angle, Color color1, Color color2) {
        this.x = x - 64.0F - dX / 2.0F * Settings.scale;
        this.y = y - 64.0F - dY / 2.0F * Settings.scale;
        this.sX = this.x;
        this.sY = this.y;
        this.tX = this.x + dX / 2.0F * Settings.scale;
        this.tY = this.y + dY / 2.0F * Settings.scale;
        this.color = color1.cpy();
        this.color2 = color2.cpy();
        this.color.a = 0.0F;
        this.startingDuration = 0.4F;
        this.duration = this.startingDuration;
        this.scaleX = 0.01F;
        this.scaleY = 0.01F;
        this.rotation = angle;
    }

    public void update() {
        this.color.a = 1;
        this.scaleX = 1;
        this.scaleY = this.scaleX;

        //this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color2);
        sb.setBlendFunction(770, 1);
        sb.draw(IMG, this.x - 15f, this.y, 64.0F, 64.0F, 128.0F, 368.0F, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 128, 368, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
