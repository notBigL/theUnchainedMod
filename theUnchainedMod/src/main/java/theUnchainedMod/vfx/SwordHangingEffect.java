package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class SwordHangingEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float scaleX;
    private float scaleY;
    private Color color2;
    private final static Texture DamoclesGlyph = loadImage(makeVFXPath("Sword_of_Damocles_Effect_1.png"));
    private final static Texture DamoclesSword = loadImage(makeVFXPath("Sword_of_Damocles_Effect_2.png"));
    private float timeSinceSwinging;
    private float swordRotation;
    private float rotationFactor = 0.0333f;

    public SwordHangingEffect(float x, float y, float dX, float dY, Color color1, Color color2) {
        this.x = x - 64.0F - dX / 2.0F * Settings.scale;
        this.y = y - 64.0F - dY / 2.0F * Settings.scale;
        this.color = color1.cpy();
        this.color2 = color2.cpy();
        this.color.a = 0.0F;
        this.scaleX = 0.01F;
        this.scaleY = 0.01F;
        this.rotation = 0;
        timeSinceSwinging = 0;
    }

    public void update() {
        this.color.a = 1;
        this.scaleX = 1;
        this.scaleY = this.scaleX;

        timeSinceSwinging += Gdx.graphics.getDeltaTime() * 2;
        swordRotation += Math.cos(timeSinceSwinging) * rotationFactor;
    }

    public void render(SpriteBatch sb) {
        this.rotation = 0;
        sb.setColor(this.color2);
        sb.setBlendFunction(770, 1);
        sb.draw(DamoclesGlyph, this.x - 15f, this.y, 64.0F, 64.0F, 128.0F, 368.0F, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 128, 368, false, false);

        this.rotation = swordRotation;
        sb.setColor(this.color2);
        sb.setBlendFunction(770, 1);
        sb.draw(DamoclesSword, this.x - 15f, this.y-48, 64.0F, 348.0F, 128.0F, 368.0F, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 128, 368, false, false);

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
