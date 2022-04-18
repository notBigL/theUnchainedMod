package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.patches.RelayedDamageField;
import theUnchainedMod.util.TextureLoader;

public class RelayedDamagePower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("RelayedDamagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/RelayedDamagePower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/RelayedDamagePower_power128.png");

    public RelayedDamagePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        int amountAfterToughness = amount;
        if (this.owner.hasPower("theUnchainedMod:ToughnessPower")) {
            int toughnessAmount = this.owner.getPower("theUnchainedMod:ToughnessPower").amount;
            amountAfterToughness -= toughnessAmount;
        }
        if (amountAfterToughness < 0) {
            amountAfterToughness = 0;
        }
        this.amount = amountAfterToughness;
        this.source = source;
        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        if (stackAmount > 0) {
            this.amount += stackAmount;
        }
    }

    public void atStartOfTurn() {
        if(this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            DamageInfo damageInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
            RelayedDamageField.relayed.set(damageInfo, true);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, damageInfo, AbstractGameAction.AttackEffect.POISON));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}
