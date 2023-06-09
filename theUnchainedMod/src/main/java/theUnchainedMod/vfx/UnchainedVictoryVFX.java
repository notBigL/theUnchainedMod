package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
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
    private float scaleX;
    private float scaleY;
    private final static Texture MaskR = loadImage(makeVFXPath("Mask_Shatter_Effect_1.png"));
    private final static Texture MaskL = loadImage(makeVFXPath("Mask_Shatter_Effect_2.png"));
    private final static Texture MaskEye = loadImage(makeVFXPath("Mask_Shatter_Effect_3.png"));
    private final static Texture MaskEyeGlow = loadImage(makeVFXPath("Mask_Shatter_Effect_4.png"));
    private static final float DUR = 2.5F;
    private float timeForEyeGlowToFade;
    private float maskBreakSpeed;

    public UnchainedVictoryVFX() {
        this.startX = 960;
        this.startY = 540;
        this.color = Color.WHITE.cpy();
        this.scaleX = 1F;
        this.scaleY = 1F;
        this.rotation = 0;
        this.duration = DUR;
        timeForEyeGlowToFade = 1;
        leftMaskPieceX = leftMaskPieceY = rightMaskPieceX = rightMaskPieceY = 0;
        maskBreakSpeed = 5;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if(timeForEyeGlowToFade > 0)
        {
            timeForEyeGlowToFade -= Gdx.graphics.getDeltaTime();
            if(timeForEyeGlowToFade < 0) {
                timeForEyeGlowToFade = 0;
                //  TODO: play breaking SFX
            }
        }
        if(this.duration <= 1.5f)
        {
            leftMaskPieceX -= maskBreakSpeed;
            leftMaskPieceY += maskBreakSpeed;

            rightMaskPieceX += maskBreakSpeed;
            rightMaskPieceY -= maskBreakSpeed;
        }
        if(this.duration <= 0)
        {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(MaskR, this.startX + rightMaskPieceX, this.startY + rightMaskPieceY, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        sb.draw(MaskL, this.startX + leftMaskPieceX, this.startY + leftMaskPieceY, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        sb.draw(MaskEye, this.startX, this.startY, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);

        // TODO: first draw a glow effect like in share pain that also fades out

        sb.setColor(new Color(this.color.r, this.color.g, this.color.b, timeForEyeGlowToFade));
        sb.draw(MaskEyeGlow, this.startX, this.startY, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
    }
    public void dispose() {
    }
}
