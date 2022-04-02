package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class HeartOfTheUnderdog extends CustomRelic {

    public static final String ID = DefaultMod.makeID("HeartOfTheUnderdog");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HeartOfTheUnderdog_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HeartOfTheUnderdog_relic.png"));

    public HeartOfTheUnderdog() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }


    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, false)));
        this.flash();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
