package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Random;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class SharePainEffect extends AbstractGameEffect {
    private final TextureAtlas.AtlasRegion img;
    private final CatmullRomSpline<Vector2> crs = new CatmullRomSpline();
    private final ArrayList<Vector2> controlPoints = new ArrayList();
    private static final int TRAIL_ACCURACY = 60;
    private final Vector2[] points = new Vector2[60];
    private final Vector2 pos;
    private final Vector2 target;
    private float currentSpeed = 0.0F;
    private static final float START_VELOCITY;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    private static final float DST_THRESHOLD;
    private static final float HOME_IN_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise = true;
    private boolean stopRotating = false;
    private float rotationRate;
    private Texture glyph;
    private Texture glyphBG = loadImage(makeVFXPath("glyphs/BG_Glyph.png"));
    private Color glyphColor;

    public SharePainEffect(float sX, float sY, float dX, float dY, int glyphNumber) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.pos = new Vector2(sX, sY);
        this.target = new Vector2(dX, dY);
        this.crs.controlPoints = new Vector2[1];
        this.rotateClockwise = MathUtils.randomBoolean();
        this.rotation = (float)MathUtils.random(0, 359);
        this.controlPoints.clear();
        this.rotationRate = MathUtils.random(300.0F, 350.0F) * Settings.scale;
        this.currentSpeed = START_VELOCITY * MathUtils.random(0.2F, 1.0F);
        this.color = new Color(0.9f, 0.3f, 0.86f, 0.1f); //share pain BG color
        this.duration = 1.3F;
        glyphColor = Color.GOLD.cpy();
        this.glyph = loadImage(makeVFXPath("glyphs/" + glyphNumber + ".png"));
    }

    public void update() {
        this.updateMovement();
    }

    private void updateMovement() {
        Vector2 tmp = new Vector2(this.pos.x - this.target.x, this.pos.y - this.target.y);
        tmp.nor();
        float targetAngle = tmp.angle();
        this.rotationRate += Gdx.graphics.getDeltaTime() * 700.0F;
        if (!this.stopRotating) {
            if (this.rotateClockwise) {
                this.rotation += Gdx.graphics.getDeltaTime() * this.rotationRate;
            } else {
                this.rotation -= Gdx.graphics.getDeltaTime() * this.rotationRate;
                if (this.rotation < 0.0F) {
                    this.rotation += 360.0F;
                }
            }

            this.rotation %= 360.0F;
            if (!this.stopRotating) {
                if (this.target.dst(this.pos) < HOME_IN_THRESHOLD) {
                    this.rotation = targetAngle;
                    this.stopRotating = true;
                } else if (Math.abs(this.rotation - targetAngle) < Gdx.graphics.getDeltaTime() * this.rotationRate) {
                    this.rotation = targetAngle;
                    this.stopRotating = true;
                }
            }
        }

        tmp.setAngle(this.rotation);
        tmp.x *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        tmp.y *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        this.pos.sub(tmp);
        if (this.stopRotating) {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 3.0F;
        } else {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 1.5F;
        }

        if (this.currentSpeed > MAX_VELOCITY) {
            this.currentSpeed = MAX_VELOCITY;
        }

        if ((!(this.target.x < (float)Settings.WIDTH / 2.0F) || !(this.pos.x < 0.0F)) && (!(this.target.x > (float)Settings.WIDTH / 2.0F) || !(this.pos.x > (float)Settings.WIDTH)) && !(this.target.dst(this.pos) < DST_THRESHOLD)) {
            if (!this.controlPoints.isEmpty()) {
                if (!this.controlPoints.get(0).equals(this.pos)) {
                    this.controlPoints.add(this.pos.cpy());
                }
            } else {
                this.controlPoints.add(this.pos.cpy());
            }

            if (this.controlPoints.size() > 3) {
                Vector2[] vec2Array = new Vector2[0];
                this.crs.set(this.controlPoints.toArray(vec2Array), false);

                for(int i = 0; i < 60; ++i) {
                    this.points[i] = new Vector2();
                    this.crs.valueAt(this.points[i], (float)i / 59.0F);
                }
            }

            if (this.controlPoints.size() > 10) {
                this.controlPoints.remove(0);
            }

            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            }

        } else {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            sb.setBlendFunction(770, 1);

            //  Render Trail
            float orbScale = Settings.scale * 3F;
            for(int i = this.points.length - 1; i > 0; --i) {
                if (this.points[i] != null)
                {
                    sb.setColor(this.color);
                    sb.draw(this.img, this.points[i].x, this.points[i].y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, orbScale, orbScale, this.rotation);

                    orbScale *= 0.975F;
                }
            }


            int glyphPos = 59;
            sb.setBlendFunction(GL20.GL_SRC_COLOR, 1);
            if (this.points[glyphPos] != null)
            {
                float glyphScale = Settings.scale * 0.5F;

                // Render BG Glyph

                sb.setColor(Color.PINK);
                sb.draw(glyphBG, this.points[glyphPos].x, this.points[glyphPos].y,32,32, 64,64, glyphScale * 1.75f, glyphScale * 1.75f, this.rotation, 0, 0, 64, 64, false, false);

                // Render Outline

                sb.setColor(Color.PURPLE);
                sb.draw(glyph, this.points[glyphPos].x, this.points[glyphPos].y,32,32, 64,64, glyphScale * 1.25f, glyphScale * 1.25f, this.rotation, 0, 0, 64, 64, false, false);
                sb.draw(glyph, this.points[glyphPos].x, this.points[glyphPos].y,32,32, 64,64, glyphScale * 0.75f, glyphScale * 0.75f, this.rotation, 0, 0, 64, 64, false, false);

                // Glyph
                sb.setColor(glyphColor);
                if (this.points[glyphPos] != null) sb.draw(glyph, this.points[glyphPos].x, this.points[glyphPos].y,32,32, 64,64, glyphScale, glyphScale, this.rotation, 0, 0, 64, 64, false, false);

            }
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose() {
    }

    static {
        START_VELOCITY = 100.0F * Settings.scale;
        MAX_VELOCITY = 5000.0F * Settings.scale;
        VELOCITY_RAMP_RATE = 2000.0F * Settings.scale;
        DST_THRESHOLD = 36.0F * Settings.scale;
        HOME_IN_THRESHOLD = 36.0F * Settings.scale;
    }
}
