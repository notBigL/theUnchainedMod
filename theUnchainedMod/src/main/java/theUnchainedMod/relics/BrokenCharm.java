package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.powers.RelayPower;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class BrokenCharm extends CustomRelic {

    public static final String ID = DefaultMod.makeID("BrokenCharm");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BrokenCharm_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BrokenCharm_relic.png"));
    private static final int RELAY = 5;


    public BrokenCharm() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        if (!this.grayscale) {
            ++this.counter;
        }
        if (this.counter < 4) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainRelayAction(AbstractDungeon.player, RELAY));
        } else {
            this.counter = -1;
            this.grayscale = true;
        }
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + RELAY + DESCRIPTIONS[1];
    }

}
