package theUnchainedMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.ShatteringSwingAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.CustomTags;

import static theUnchainedMod.DefaultMod.makeCardPath;

@AutoAdd.Ignore
public class Ruse extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Ruse.class.getSimpleName());
    public static final String IMG = makeCardPath("Ruse.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Ruse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        tags.add(CustomTags.SWING);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }
  //Targete einen Gegner, der kriegt Block. Hitte alle anderen Gegner, wenn du niemanden hittest, draw ne karte oder so
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ShatteringSwingAction(damage, m, p));
    }
}
