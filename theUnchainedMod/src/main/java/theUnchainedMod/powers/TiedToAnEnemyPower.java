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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.patches.RelayedDamageField;
import theUnchainedMod.util.TextureLoader;

import java.util.ArrayList;

public class TiedToAnEnemyPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("TiedToAnEnemyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public final ArrayList<TiedToThePlayerPower> tiedToThePlayerPowerList = new ArrayList<>();
    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/TiedToAnEnemyPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/TiedToAnEnemyPower_power128.png");

    public TiedToAnEnemyPower(final AbstractCreature owner, final AbstractCreature source, TiedToThePlayerPower tiedToThePlayerPower, AbstractMonster m) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.source = source;
        type = PowerType.BUFF;
        isTurnBased = false;
        if (owner.hasPower("theUnchainedMod:TiedToAnEnemyPower")) {
            TiedToAnEnemyPower ttAEP = (TiedToAnEnemyPower) owner.getPower("theUnchainedMod:TiedToAnEnemyPower");
            ttAEP.tiedToThePlayerPowerList.add(tiedToThePlayerPower);
        } else {
            tiedToThePlayerPowerList.add(tiedToThePlayerPower);
        }
        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void damageEnemyWhenHit(DamageInfo info, int damageAmount) {
        if (tiedToThePlayerPowerList.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
            return;
        }

        ArrayList<TiedToThePlayerPower> removeList = new ArrayList<>();
        for (TiedToThePlayerPower tttPP : tiedToThePlayerPowerList) {
            if (!tttPP.owner.isDeadOrEscaped() && tttPP.owner.hasPower("theUnchainedMod:TiedToThePlayerPower")) {
                if (!RelayedDamageField.relayed.get(info)) {
                    tttPP.damageEnemyWhenPlayerIsHit(damageAmount, this.owner);
                }
            } else {
                removeList.add(tttPP);
                if (tiedToThePlayerPowerList.size() == removeList.size()) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                    return;
                }
            }
        }
        for (TiedToThePlayerPower tttPP : removeList) {
            tiedToThePlayerPowerList.remove(tttPP);
        }
    }

    public void removeMe(TiedToThePlayerPower tttPP) {
        tiedToThePlayerPowerList.remove(tttPP);
        if (tiedToThePlayerPowerList.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

}
