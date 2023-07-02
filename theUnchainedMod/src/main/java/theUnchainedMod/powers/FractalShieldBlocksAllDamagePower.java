package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.ApplyFullSpinsAction;
import theUnchainedMod.actions.RevertSwirlToBaseValuesAction;
import theUnchainedMod.util.TextureLoader;

public class FractalShieldBlocksAllDamagePower extends AbstractPower implements InvisiblePower {

    public static final String POWER_ID = TheUnchainedMod.makeID("FractalShieldBlocksAllDamagePower");
    public FractalShieldBlocksAllDamagePower(final AbstractCreature owner) {
        ID = POWER_ID;
        this.owner = owner;
        type = PowerType.BUFF;
        isTurnBased = false;
    }
}
