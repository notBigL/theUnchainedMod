package theUnchainedMod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;

public class FractalShieldBlocksAllDamagePower extends AbstractPower implements InvisiblePower {

    public static final String POWER_ID = TheUnchainedMod.makeID("FractalShieldBlocksAllDamagePower");
    public FractalShieldBlocksAllDamagePower(final AbstractCreature owner) {
        ID = POWER_ID;
        this.owner = owner;
        type = PowerType.BUFF;
        isTurnBased = false;
    }
}
