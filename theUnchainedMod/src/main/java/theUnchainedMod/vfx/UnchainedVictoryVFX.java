package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.sun.glass.ui.Screen;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class UnchainedVictoryVFX extends AbstractGameEffect {
    private float x;
    private float y;
    private float scaleX;
    private float scaleY;
    private final static Texture Mask1 = loadImage(makeVFXPath("Mask_Shatter_Effect_1.png"));
    private final static Texture Mask2 = loadImage(makeVFXPath("Mask_Shatter_Effect_2.png"));
    private final static Texture Mask3 = loadImage(makeVFXPath("Mask_Shatter_Effect_3.png"));
    private final static Texture Mask4 = loadImage(makeVFXPath("Mask_Shatter_Effect_4.png"));
    private static final float DUR = 2.5F;

    public UnchainedVictoryVFX() {
        this.x = 960;
        this.y = 540;
        this.color = Color.WHITE.cpy();
        this.scaleX = 1F;
        this.scaleY = 1F;
        this.rotation = 0;
        this.duration = DUR;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0)
        {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        //sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
        sb.setColor(this.color);
        sb.draw(Mask1, this.x, this.y, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        sb.draw(Mask2, this.x, this.y, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        sb.draw(Mask3, this.x, this.y, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        //sb.draw(Mask4, this.x, this.y, 112, 112, 224, 224, this.scaleX * Settings.scale, this.scaleY * Settings.scale, this.rotation, 0, 0, 224, 224, false, false);
        //sb.setBlendFunction(770, 771);
    }
    public void dispose() {
    }
}
