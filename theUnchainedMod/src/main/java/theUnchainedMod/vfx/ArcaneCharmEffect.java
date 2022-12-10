package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class ArcaneCharmEffect extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private static final float DUR = 0.5F;

    public ArcaneCharmEffect(float x, float y) {
        this.img = ImageMaster.CRYSTAL_IMPACT;
        this.x = x - (float) (this.img.packedWidth / 2);
        this.y = y - (float) (this.img.packedHeight / 2);
        this.color = new Color(1.0f, 0.4f, 0.96f, 1.0f);
        this.duration = DUR;
        this.scale = 0.01F;
    }

    public void update() {
        if (this.duration == DUR) {
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PINK.cpy(), true));
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.duration = 0.0F;
        }

        this.color.a = Interpolation.pow3Out.apply(0.0F, 1.0F, this.duration / 2.0F);
        this.scale += Gdx.graphics.getDeltaTime() * 8.0F;
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0f, 0.4f, 0.96f, this.color.a));
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * MathUtils.random(0.8F, 1.2F), this.scale * MathUtils.random(0.8F, 1.2F), this.rotation);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * MathUtils.random(0.8F, 1.2F), this.scale * MathUtils.random(0.8F, 1.2F), this.rotation);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * MathUtils.random(0.8F, 1.2F), this.scale * MathUtils.random(0.8F, 1.2F), this.rotation);
    }

    public void dispose() {
    }
}
