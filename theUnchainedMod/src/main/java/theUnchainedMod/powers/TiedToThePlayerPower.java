package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.util.TextureLoader;

public class TiedToThePlayerPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("TiedToThePlayerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/TiedToThePlayerPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/TiedToThePlayerPower_power128.png");
    private final AbstractPlayer player;

    public TiedToThePlayerPower(final AbstractCreature owner, final AbstractCreature source, AbstractPlayer player) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.source = source;
        type = PowerType.DEBUFF;
        isTurnBased = false;
        this.player = player;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0];
    }

    public void damageEnemyWhenPlayerIsHit(int damageAmount, AbstractCreature player) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(player, damageAmount, DamageInfo.DamageType.HP_LOSS)));
    }

    public void onDeath() {
        if (!AbstractDungeon.getCurrRoom().isBattleEnding()) {
            if (player.hasPower("theUnchainedMod:TiedToAnEnemyPower")) {
                TiedToAnEnemyPower playerPower = (TiedToAnEnemyPower) player.getPower("theUnchainedMod:TiedToAnEnemyPower");
                playerPower.removeMe(this);
            }
        }
    }

    public void onRemove() {
        if (player.hasPower("theUnchainedMod:TiedToAnEnemyPower")) {
            TiedToAnEnemyPower playerPower = (TiedToAnEnemyPower) player.getPower("theUnchainedMod:TiedToAnEnemyPower");
            playerPower.removeMe(this);
        }
    }
}
