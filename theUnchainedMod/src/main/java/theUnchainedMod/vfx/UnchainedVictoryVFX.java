package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class UnchainedVictoryVFX extends AbstractGameEffect {
    private float startX;
    private float startY;
    private float leftMaskPieceX;
    private float leftMaskPieceY;
    private float rightMaskPieceX;
    private float rightMaskPieceY;
    private final static Texture MaskR = loadImage(makeVFXPath("Mask_Shatter_Effect_1.png"));
    private final static Texture MaskL = loadImage(makeVFXPath("Mask_Shatter_Effect_2.png"));
    private final static Texture MaskEye = loadImage(makeVFXPath("Mask_Shatter_Effect_3.png"));
    private final static Texture MaskEyeGlow = loadImage(makeVFXPath("Mask_Shatter_Effect_4.png"));
    private static final float DUR = 2.5F;
    private float glowingEyeOpacity;
    private float restOfMaskOpacity;
    private float maskBreakMovementSpeed;
    private TextureAtlas.AtlasRegion glowImage;

    public UnchainedVictoryVFX() {
        this.glowImage = ImageMaster.GLOW_SPARK_2;
        this.startX = Settings.WIDTH/2f - 112;
        this.startY = Settings.HEIGHT/2f - 112;
        this.color = Color.WHITE.cpy();
        this.rotation = 0;
        this.duration = DUR;
        glowingEyeOpacity = 1;
        restOfMaskOpacity = 1;
        leftMaskPieceX = leftMaskPieceY = rightMaskPieceX = rightMaskPieceY = 0;
        maskBreakMovementSpeed = 4.5f;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if(glowingEyeOpacity > 0)
        {
            glowingEyeOpacity -= Gdx.graphics.getDeltaTime();
            if(glowingEyeOpacity < 0) {
                glowingEyeOpacity = 0;
                CardCrawlGame.sound.play("normalChainAttack"); //chain breaking sound effect
                AbstractDungeon.effectsQueue.add(new ChainAcrossScreenEffect(color, 0,Settings.HEIGHT * 0.72f, MathUtils.random(4, 6), MathUtils.random(55,75)));
                AbstractDungeon.effectsQueue.add(new ChainAcrossScreenEffect(color, Settings.WIDTH * 0.21f,Settings.HEIGHT, MathUtils.random(4, 6), MathUtils.random(230,250)));
                AbstractDungeon.effectsQueue.add(new ChainAcrossScreenEffect(color, Settings.WIDTH * 0.25f,0, MathUtils.random(4, 6), MathUtils.random(130,150)));
                AbstractDungeon.effectsQueue.add(new ChainAcrossScreenEffect(color, Settings.WIDTH * 0.83f,Settings.HEIGHT, MathUtils.random(4, 6), MathUtils.random(290,310)));
                AbstractDungeon.effectsQueue.add(new ChainAcrossScreenEffect(color, Settings.WIDTH * 0.86f,0, MathUtils.random(4, 6), MathUtils.random(45,65)));
            }
        }
        if(this.duration <= 1.5f)
        {
            leftMaskPieceX -= maskBreakMovementSpeed;
            leftMaskPieceY += 0.1f * maskBreakMovementSpeed;

            rightMaskPieceX += maskBreakMovementSpeed;
            rightMaskPieceY -= 0.1f * maskBreakMovementSpeed;

            maskBreakMovementSpeed -= 4 * Gdx.graphics.getDeltaTime();
        }
        if(this.duration <= 0.75f)
        {
            restOfMaskOpacity -= (1/0.75f) * Gdx.graphics.getDeltaTime();
            if(restOfMaskOpacity < 0) {
                restOfMaskOpacity = 0;
            }
        }
        if(this.duration <= 0)
        {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        //sb.setColor(this.color);
        sb.setColor(new Color(this.color.r, this.color.g, this.color.b, restOfMaskOpacity));

        sb.draw(MaskR, this.startX + rightMaskPieceX, this.startY + rightMaskPieceY, 112, 112, 224, 224, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        sb.draw(MaskL, this.startX + leftMaskPieceX, this.startY + leftMaskPieceY, 112, 112, 224, 224, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        sb.draw(MaskEye, this.startX, this.startY, 112, 112, 224, 224, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);

        sb.setColor(new Color(this.color.r, this.color.g, this.color.b, glowingEyeOpacity));
        sb.draw(MaskEyeGlow, this.startX, this.startY, 112, 112, 224, 224, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);

        // TODO: first draw a glow effect like in share pain that also fades out
        sb.setColor(new Color(0.9f, 0.3f, 0.86f, glowingEyeOpacity));
        sb.draw(glowImage, this.startX, this.startY, (float)glowImage.packedWidth / 2.0F, (float)glowImage.packedHeight / 2.0F, (float)glowImage.packedWidth, (float)glowImage.packedHeight, 5* scale, 5* scale, this.rotation);

    }
    public void dispose() {
    }
}
