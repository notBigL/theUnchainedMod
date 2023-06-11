package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.awt.*;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class ChainAcrossScreenEffect extends AbstractGameEffect {
    private float scaleY;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float rotation;
    private Texture chainTexture = loadImage(makeVFXPath("Chain.png"));
    private float distance;
    public ChainAcrossScreenEffect(Color setColor, float rotation) {
        //this.x = (float)Settings.WIDTH + MathUtils.random(400.0F, 100.0F) * Settings.scale - 128.0F;
        this.x = 500;
        this.vX = MathUtils.random(-700.0F, -1400.0F) * Settings.scale;
        //this.y = MathUtils.random(0.15F, 0.85F) * (float)Settings.HEIGHT - 128.0F;
        this.y = 500;
        this.vY = MathUtils.random(-100.0F, 100.0F) * Settings.scale;
        this.duration = 1.5F;
        this.distance = 1F;
        this.scale = MathUtils.random(1.5F, 3.0F);
        this.vX *= this.scale;
        this.scale *= Settings.scale;
        this.scaleY = MathUtils.random(0.5F, 2.0F) * Settings.scale;
        this.color = setColor.cpy();
        this.color.a = MathUtils.random(0.5F, 1.0F);
        this.rotation = rotation;
        if (this.scaleY < Settings.scale) {
            this.renderBehind = true;
        }
    }

    public void update() {
        this.distance += Gdx.graphics.getDeltaTime();
        this.x += distance * Math.cos(rotation);
        this.y += distance * Math.sin(rotation);

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        //sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(chainTexture, this.x, this.y, 512, 64, 1024, 128, 1, 1, this.rotation, 0, 0, 1024, 128, false, false);
        //sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
