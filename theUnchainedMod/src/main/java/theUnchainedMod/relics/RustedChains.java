package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.powers.OiledChainsPower;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class RustedChains extends CustomRelic {

    public static final String ID = TheUnchainedMod.makeID("RustedChains");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RustedChains_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RustedChains_relic.png"));

    public RustedChains() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
        this.grayscale = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        ++this.counter;
        if (this.counter == 6) {
            this.beginLongPulse();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new OiledChainsPower(AbstractDungeon.player, AbstractDungeon.player,1)));
        } else if (this.counter == 7) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
            this.stopPulse();
            this.grayscale = true;
        }
    }

    public void onVictory() {
        this.counter = -1;
        this.stopPulse();
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
