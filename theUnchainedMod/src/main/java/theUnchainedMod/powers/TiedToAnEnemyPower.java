package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.patches.RelayedDamageField;
import theUnchainedMod.util.TextureLoader;

public class TiedToAnEnemyPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("TiedToAnEnemyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public final TiedToThePlayerPower tiedToThePlayerPower;
    public final AbstractMonster monster;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/TiedToAnEnemyPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/TiedToAnEnemyPower_power128.png");

    public TiedToAnEnemyPower(final AbstractCreature owner, final AbstractCreature source, final int amount, TiedToThePlayerPower TttPP, AbstractMonster m) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        type = PowerType.BUFF;
        isTurnBased = false;
        tiedToThePlayerPower = TttPP;
        this.monster = m;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void damageEnemyWhenHit(DamageInfo info, int damageAmount) {
        if (tiedToThePlayerPower != null && !monster.isDead && monster.hasPower("theUnchainedMod:TiedToThePlayerPower")) {
            if (!RelayedDamageField.relayed.get(info)) {
                tiedToThePlayerPower.damageEnemyWhenPlayerIsHit(damageAmount, this.owner);
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void atStartOfTurn() {
        if (tiedToThePlayerPower == null || tiedToThePlayerPower.owner == null || tiedToThePlayerPower.owner.isDead || !monster.hasPower("theUnchainedMod:TiedToThePlayerPower")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

}
