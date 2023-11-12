package theUnchainedMod;

import basemod.AutoAdd;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.interfaces.EditPacksSubscriber;
import theUnchainedMod.cards.AbstractDefaultCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.packs.AbstractUnchainedPack;
import theUnchainedMod.packs.UnchainedChainPack;

public class PackLoader implements EditPacksSubscriber {
    @Override
    public void receiveEditPacks() {
        SpireAnniversary5Mod.allowCardClass(AbstractDefaultCard.class);
        SpireAnniversary5Mod.allowCardColor(TheUnchained.Enums.UNCHAINED_COLOR);
        new AutoAdd("TheUnchainedMod")
            .packageFilter(UnchainedChainPack.class)
            .any(AbstractUnchainedPack.class, (info, pack) -> SpireAnniversary5Mod.declarePack(pack));
    }
}