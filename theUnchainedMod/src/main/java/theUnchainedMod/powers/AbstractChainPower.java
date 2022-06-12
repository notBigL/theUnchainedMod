package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.actions.ChainAction;
import theUnchainedMod.util.TextureLoader;

public class AbstractChainPower extends AbstractPower {
    private static final Texture attackTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/AttackChain_power48.png");
    private static final Texture attackTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/AttackChain_power128.png");
    private static final Texture skillTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/SkillChain_power48.png");
    private static final Texture skillTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/SkillChain_power128.png");
    private static final Texture powerTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/PowerChain_power48.png");
    private static final Texture powerTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/PowerChain_power128.png");
    private static int chainIdOffset;
    public final AbstractGameAction finishedChainAction;
    public final AbstractCard.CardType cardType;
    private final String powerIdWithoutOffset;


    public AbstractChainPower(String powerID, AbstractCreature player, int chainLength, AbstractGameAction finishedChainAction, AbstractCard.CardType cardType) {
        this.ID = powerID + chainIdOffset;
        chainIdOffset++;
        this.owner = player;
        this.amount = chainLength;
        this.finishedChainAction = finishedChainAction;
        this.cardType = cardType;
        loadTextures(this.cardType);
        this.powerIdWithoutOffset = powerID;
        instantFinishCheck();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (!AbstractDungeon.player.hasRelic("theUnchainedMod:Carabiner")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }


    public void onUseCard(AbstractCard c, UseCardAction action) {
        switch (c.cardID) {
            case "theUnchainedMod:DiamondSaw":
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                break;
            case "theUnchainedMod:Liberation":
                AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID, "liberation"));
                this.flash();
                break;
            case "theUnchainedMod:RoutinePunch":
            case "theUnchainedMod:RoutineDodge":
                AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID, "link"));
                this.flash();
                break;
            default:
                AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID));
                if(this.cardType == c.type) {
                    this.flash();
                }
                break;
        }
    }

    private void loadTextures(AbstractCard.CardType type) {
        if (type == AbstractCard.CardType.ATTACK) {
            this.region128 = new TextureAtlas.AtlasRegion(attackTexture128, 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(attackTexture48, 0, 0, 48, 48);
        } else if (type == AbstractCard.CardType.SKILL) {
            this.region128 = new TextureAtlas.AtlasRegion(skillTexture128, 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(skillTexture48, 0, 0, 48, 48);
        } else {
            this.region128 = new TextureAtlas.AtlasRegion(powerTexture128, 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(powerTexture48, 0, 0, 48, 48);
        }
    }

    private void instantFinishCheck() {
        AbstractPlayer player = (AbstractPlayer) owner;
        String knotID = "theUnchainedMod:KnotPower";
        String instantFinishID = "theUnchainedMod:FreeFormPower";
        if (player.hasPower(instantFinishID) || player.hasPower(knotID)) {
            if (player.hasPower(knotID)) {
                player.getPower(knotID).reducePower(1);
            }
            if (powerIdWithoutOffset.equals("theUnchainedMod:RelentlessBatteryPower")) {
                return;
            }
            AbstractDungeon.actionManager.addToBottom(this.finishedChainAction);
            if(player.hasPower("theUnchainedMod:LawOfInertiaPower")) {
                player.getPower("theUnchainedMod:LawOfInertiaPower").onSpecificTrigger();
            }
            if (player.hasPower("theUnchainedMod:FluidMovementPower")) {
                int momentumGain = player.getPower("theUnchainedMod:FluidMovementPower").amount;
                if (momentumGain > 0) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MomentumPower(player, momentumGain)));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, this));
        }
    }
}
