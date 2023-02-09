package theUnchainedMod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.DefaultMod.makeVFXPath;

public class RoyalDecreeEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private final float rotation;
    private float textScale;
    private Color backgroundColor;
    private Color sealColor;
    private boolean alreadyExhausted;
    private static final float DUR = 2.5F;
    private static final Texture IMG_BACKGROUND = loadImage(makeVFXPath("Royal_Decree_Effect_Background.png"));
    private static final Texture IMG_TEXT = loadImage(makeVFXPath("Royal_Decree_Effect_Text.png"));
    private static final Texture IMG_SEAL = loadImage(makeVFXPath("Royal_Decree_Effect_Seal.png"));


    public RoyalDecreeEffect(float x, float y) {
        this.x = x + (100 * Settings.scale);
        this.y = y;
        this.rotation = 0.0F;
        this.scale = 5.0F;
        this.textScale = 2.5F;
        this.backgroundColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.sealColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.startingDuration = this.duration = DUR;
        alreadyExhausted = false;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.backgroundColor.a = Interpolation.pow3Out.apply(0.5F, 1.0F, 1.0F - this.duration / DUR);
            this.sealColor.a = Interpolation.pow3Out.apply(0.0F, 1.0F, 1.0F - this.duration / DUR);
        } else {
            this.backgroundColor.a = Interpolation.pow2In.apply(1.0F, 0.0F, 1.0F - this.duration / DUR);
            this.sealColor.a = Interpolation.pow2In.apply(1.0F, 0.2F, 1.0F - this.duration / DUR);
            if (!alreadyExhausted) {
                alreadyExhausted = true;
                CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
                for (int i = 0; i < 50; ++i) {
                    AbstractDungeon.effectsQueue.add(new ExhaustEmbersForRoyalDecreeEffect(x, y));
                }
            }
        }
        this.scale = Interpolation.exp10Out.apply(6.5F, 0.8F, 1.0F - this.duration / DUR);
        this.textScale = Interpolation.exp10Out.apply(2.5F, 0.8F, 1.0F - this.duration / DUR);

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.backgroundColor);
        sb.draw(IMG_BACKGROUND, this.x - 64.0F, this.y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, rotation, 0, 0, 128, 128, false, false);
        sb.draw(IMG_TEXT, this.x - 64.0F, this.y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.textScale, this.textScale, rotation, 0, 0, 128, 128, false, false);
        sb.setColor(this.sealColor);
        sb.draw(IMG_SEAL, this.x - 64.0F, this.y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, rotation, 0, 0, 128, 128, false, false);
    }

    public void dispose() {
    }
}
