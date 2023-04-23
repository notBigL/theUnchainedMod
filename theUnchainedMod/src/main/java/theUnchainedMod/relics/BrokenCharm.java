package theUnchainedMod.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class BrokenCharm extends CustomRelic {

    public static final String ID = TheUnchainedMod.makeID("BrokenCharm");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BrokenCharm_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BrokenCharm_relic.png"));
    private static final int RELAY = 5;


    public BrokenCharm() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[2].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[2].toLowerCase())
        ));
    }

    @Override
    public void atTurnStart() {
        if (!this.grayscale) {
            ++this.counter;
            if (this.counter < 4) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new GainRelayAction(AbstractDungeon.player, RELAY));
            } else {
                this.counter = -1;
                this.grayscale = true;
            }
        }

    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + RELAY + DESCRIPTIONS[1];
    }

}
