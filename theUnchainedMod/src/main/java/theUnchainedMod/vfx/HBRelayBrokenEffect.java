package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.DefaultMod.makeUIPath;

public class HBRelayBrokenEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.0F;

    private static final Texture BROKEN_RELAY_OUTSIDE_1 = loadImage(makeUIPath("BrokenRelay_1.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_2 = loadImage(makeUIPath("BrokenRelay_3.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_3 = loadImage(makeUIPath("BrokenRelay_5.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_4 = loadImage(makeUIPath("BrokenRelay_4.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_5 = loadImage(makeUIPath("BrokenRelay_12.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_6 = loadImage(makeUIPath("BrokenRelay_7.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_7 = loadImage(makeUIPath("BrokenRelay_8.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_8 = loadImage(makeUIPath("BrokenRelay_9.png"));
    private static final Texture BROKEN_RELAY_OUTSIDE_9 = loadImage(makeUIPath("BrokenRelay_11.png"));
    private static final Texture BROKEN_RELAY_INSIDE_1 = loadImage(makeUIPath("BrokenRelay_2.png"));
    private static final Texture BROKEN_RELAY_INSIDE_2 = loadImage(makeUIPath("BrokenRelay_6.png"));
    private static final Texture BROKEN_RELAY_INSIDE_3 = loadImage(makeUIPath("BrokenRelay_10.png"));

    private static final float DEST_X_OUTSIDE_1;
    private static final float DEST_Y_OUTSIDE_1;

    private static final float DEST_X_OUTSIDE_2;
    private static final float DEST_Y_OUTSIDE_2;

    private static final float DEST_X_OUTSIDE_3;
    private static final float DEST_Y_OUTSIDE_3;

    private static final float DEST_X_OUTSIDE_4;
    private static final float DEST_Y_OUTSIDE_4;

    private static final float DEST_X_OUTSIDE_5;
    private static final float DEST_Y_OUTSIDE_5;

    private static final float DEST_X_OUTSIDE_6;
    private static final float DEST_Y_OUTSIDE_6;

    private static final float DEST_X_OUTSIDE_7;
    private static final float DEST_Y_OUTSIDE_7;

    private static final float DEST_X_OUTSIDE_8;
    private static final float DEST_Y_OUTSIDE_8;

    private static final float DEST_X_OUTSIDE_9;
    private static final float DEST_Y_OUTSIDE_9;

    private static final float DEST_X_INSIDE_1;
    private static final float DEST_Y_INSIDE_1;

    private static final float DEST_X_INSIDE_2;
    private static final float DEST_Y_INSIDE_2;

    private static final float DEST_X_INSIDE_3;
    private static final float DEST_Y_INSIDE_3;

    private final float x;
    private final float y;

    private float offsetXOutside1;
    private float offsetYOutside1;

    private float offsetXOutside2;
    private float offsetYOutside2;

    private float offsetXOutside3;
    private float offsetYOutside3;

    private float offsetXOutside4;
    private float offsetYOutside4;

    private float offsetXOutside5;
    private float offsetYOutside5;

    private float offsetXOutside6;
    private float offsetYOutside6;

    private float offsetXOutside7;
    private float offsetYOutside7;

    private float offsetXOutside8;
    private float offsetYOutside8;

    private float offsetXOutside9;
    private float offsetYOutside9;

    private float offsetXInside1;
    private float offsetYInside1;

    private float offsetXInside2;
    private float offsetYInside2;

    private float offsetXInside3;
    private float offsetYInside3;

    public HBRelayBrokenEffect(float x, float y) {
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.duration = EFFECT_DUR;
        this.x = x;
        this.y = y;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside1 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_1, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside1 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_1, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside2 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_2, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside2 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_2, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside3 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_3, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside3 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_3, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside4 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_4, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside4 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_4, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside5 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_5, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside5 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_5, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside6 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_6, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside6 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_6, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside7 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_7, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside7 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_7, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside8 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_8, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside8 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_8, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXOutside9 = Interpolation.pow3Out.apply(0.0F, DEST_X_OUTSIDE_9, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYOutside9 = Interpolation.pow3Out.apply(0.0F, DEST_Y_OUTSIDE_9, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXInside1 = Interpolation.pow2Out.apply(0.0F, DEST_X_INSIDE_1, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYInside1 = Interpolation.pow2Out.apply(0.0F, DEST_Y_INSIDE_1, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXInside2 = Interpolation.pow2Out.apply(0.0F, DEST_X_INSIDE_2, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYInside2 = Interpolation.pow2Out.apply(0.0F, DEST_Y_INSIDE_2, 1.0F - this.duration / EFFECT_DUR);

        this.offsetXInside3 = Interpolation.pow2Out.apply(0.0F, DEST_X_INSIDE_3, 1.0F - this.duration / EFFECT_DUR);
        this.offsetYInside3 = Interpolation.pow2Out.apply(0.0F, DEST_Y_INSIDE_3, 1.0F - this.duration / EFFECT_DUR);

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(BROKEN_RELAY_OUTSIDE_1, this.x - 32.0F + this.offsetXOutside1, this.y - 32.0F + this.offsetYOutside1, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_2, this.x - 32.0F + this.offsetXOutside2, this.y - 32.0F + this.offsetYOutside2, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_3, this.x - 32.0F + this.offsetXOutside3, this.y - 32.0F + this.offsetYOutside3, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_4, this.x - 32.0F + this.offsetXOutside4, this.y - 32.0F + this.offsetYOutside4, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_5, this.x - 32.0F + this.offsetXOutside5, this.y - 32.0F + this.offsetYOutside5, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_6, this.x - 32.0F + this.offsetXOutside6, this.y - 32.0F + this.offsetYOutside6, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_7, this.x - 32.0F + this.offsetXOutside7, this.y - 32.0F + this.offsetYOutside7, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_8, this.x - 32.0F + this.offsetXOutside8, this.y - 32.0F + this.offsetYOutside8, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_OUTSIDE_9, this.x - 32.0F + this.offsetXOutside9, this.y - 32.0F + this.offsetYOutside9, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);

        sb.draw(BROKEN_RELAY_INSIDE_1, this.x - 32.0F + this.offsetXInside1, this.y - 32.0F + this.offsetYInside1, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_INSIDE_2, this.x - 32.0F + this.offsetXInside2, this.y - 32.0F + this.offsetYInside2, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);
        sb.draw(BROKEN_RELAY_INSIDE_3, this.x - 32.0F + this.offsetXInside3, this.y - 32.0F + this.offsetYInside3, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0, 0, 0, 64, 64, false, false);

    }

    public void dispose() {
    }

    static {
        DEST_X_OUTSIDE_1 = -20.0F * Settings.scale;
        DEST_Y_OUTSIDE_1 = 20.0F * Settings.scale;

        DEST_X_OUTSIDE_2 = 0.0F * Settings.scale;
        DEST_Y_OUTSIDE_2 = 20.0F * Settings.scale;

        DEST_X_OUTSIDE_3 = 20.0F * Settings.scale;
        DEST_Y_OUTSIDE_3 = 20.0F * Settings.scale;

        DEST_X_OUTSIDE_4 = 20.0F * Settings.scale;
        DEST_Y_OUTSIDE_4 = 0.0F * Settings.scale;

        DEST_X_OUTSIDE_5 = 20.0F * Settings.scale;
        DEST_Y_OUTSIDE_5 = -20.0F * Settings.scale;

        DEST_X_OUTSIDE_6 = 0.0F * Settings.scale;
        DEST_Y_OUTSIDE_6 = -20.0F * Settings.scale;

        DEST_X_OUTSIDE_7 = -20.0F * Settings.scale;
        DEST_Y_OUTSIDE_7 = -20.0F * Settings.scale;

        DEST_X_OUTSIDE_8 = -20.0F * Settings.scale;
        DEST_Y_OUTSIDE_8 = 0.0F * Settings.scale;

        DEST_X_OUTSIDE_9 = -20.0F * Settings.scale;
        DEST_Y_OUTSIDE_9 = 5.0F * Settings.scale;

        DEST_X_INSIDE_1 = 0.0F * Settings.scale;
        DEST_Y_INSIDE_1 = 10.0F * Settings.scale;

        DEST_X_INSIDE_2 = 10.0F * Settings.scale;
        DEST_Y_INSIDE_2 = -10.0F * Settings.scale;

        DEST_X_INSIDE_3 = -10.0F * Settings.scale;
        DEST_Y_INSIDE_3 = 0.0F * Settings.scale;

    }
}
