package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.util.TextureLoader;

public class MaladyPower extends AbstractPower {


    public static final String POWER_ID = DefaultMod.makeID("MaladyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MaladyPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MaladyPower_power128.png");


    public MaladyPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public MaladyPower(final AbstractCreature owner) {
        this(owner, 1);
    }

    @Override
    public void atStartOfTurn() {
        if (this.amount > 2) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new FrailPower(owner, 1, false)));
            if (this.amount > 4) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new WeakPower(owner, 1, false)));
                if (this.amount > 6) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new VulnerablePower(owner, 1, false)));
                }
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
