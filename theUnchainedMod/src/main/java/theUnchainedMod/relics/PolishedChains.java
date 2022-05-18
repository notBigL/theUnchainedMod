package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.powers.OiledChainsPower;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class PolishedChains extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PolishedChains");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PolishedChains_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PolishedChains_relic.png"));

    public PolishedChains() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
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
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new OiledChainsPower(AbstractDungeon.player, AbstractDungeon.player,1)));
        } else if (this.counter == 7) {
            this.stopPulse();
            this.counter = 0;
        }
    }

    public void onVictory() {
        this.counter = 0;
        this.stopPulse();
        this.grayscale = false;
    }

    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, false)));
        this.flash();
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("theUnchainedMod:OiledChains");
    }

    public void obtain() {
        if (AbstractDungeon.player.hasRelic("theUnchainedMod:OiledChains")) {
            for(int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals("theUnchainedMod:OiledChains")) {
                    this.instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
