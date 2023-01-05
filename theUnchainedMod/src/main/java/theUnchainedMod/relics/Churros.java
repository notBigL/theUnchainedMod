package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class Churros extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Churros");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Churros_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Churros_relic.png"));

    private boolean eaten;

    public Churros() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
        eaten = false;
    }


    @Override
    public void atBattleStart() {
        eaten = false;
    }

    @Override
    public void atTurnStart() {
        eaten = false;
        this.beginLongPulse();
    }

    public boolean isEaten() {
        return eaten;
    }

    public void onVictory() {
        eaten = false;
        this.stopPulse();
    }

    public void eat() {
        eaten = true;
        this.stopPulse();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
