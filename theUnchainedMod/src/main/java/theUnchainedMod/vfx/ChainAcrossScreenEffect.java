package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makeVFXPath;

public class ChainAcrossScreenEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float startX;
    private float startY;
    private float speed;
    private float rotation;
    private Texture chainTexture = loadImage(makeVFXPath("Chain.png"));
    private float distance;
    float xChainOffset;
    float yChainOffset;
    public ChainAcrossScreenEffect(Color setColor, float xCrossingPoint, float yCrossingPoint, float chainSpeed, float rotation) {
        speed = chainSpeed;
        this.duration = 6F;
        this.distance = 1F;
        this.scale = 0.5f;
        this.color = setColor.cpy();
        this.rotation = rotation;
        xChainOffset = (float) ((1024-180) * scale * Math.cos(Math.toRadians(rotation)));
        yChainOffset = (float) ((1024-180) * scale * Math.sin(Math.toRadians(rotation)));

        //  calculate position on the screen where the chain starts depending on the angle
        startX = xCrossingPoint - xChainOffset - 512;
        startY = yCrossingPoint - yChainOffset - 64;
    }

    public void update() {
        this.distance += speed;
        this.x = startX + (float) (distance * Math.cos(Math.toRadians(rotation)));
        this.y = startY + (float) (distance * Math.sin(Math.toRadians(rotation)));

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 1.0F) {
            color.a = Math.max(0, duration);
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sb.setColor(this.color);
        for(int i = 0; i < 5; i++)
        {
            sb.draw(chainTexture, this.x - i * xChainOffset, this.y - i * yChainOffset, 512, 64, 1024, 128, scale, scale, this.rotation, 0, 0, 1024, 128, false, false);
        }
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
