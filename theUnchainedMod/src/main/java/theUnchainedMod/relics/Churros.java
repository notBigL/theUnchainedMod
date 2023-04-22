package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class Churros extends CustomRelic {

    public static final String ID = TheUnchainedMod.makeID("Churros");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Churros_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Churros_relic.png"));

    public Churros() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }


    public void onSpecificTrigger() {
        counter++;
        if (counter == 6) {
            this.flash();
            this.beginLongPulse();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DeliciousChurroPower(AbstractDungeon.player)));
        } else if (counter == 8) {
            counter = 0;
            this.stopPulse();
        }
    }

    public void onVictory() {
        this.stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
