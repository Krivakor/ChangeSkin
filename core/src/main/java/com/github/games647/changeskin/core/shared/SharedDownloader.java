package com.github.games647.changeskin.core.shared;

import com.github.games647.changeskin.core.ChangeSkinCore;
import com.github.games647.changeskin.core.model.SkinData;

import java.util.UUID;

public abstract class SharedDownloader implements Runnable, MessageReceiver {

    protected final ChangeSkinCore core;
    protected final boolean keepSkin;
    protected final UUID targetUUID;

    protected final UUID receiverUUID;

    public SharedDownloader(ChangeSkinCore core, boolean keepSkin, UUID targetUUID, UUID receiverUUID) {
        this.core = core;
        this.keepSkin = keepSkin;
        this.targetUUID = targetUUID;
        this.receiverUUID = receiverUUID;
    }

    @Override
    public void run() {
        SkinData storedSkin = core.getStorage().getSkin(targetUUID);
        storedSkin = core.checkAutoUpdate(storedSkin);

        if (targetUUID.equals(receiverUUID)) {
            sendMessageInvoker("reset");
        }

        scheduleApplyTask(storedSkin);
    }

    protected abstract void scheduleApplyTask(SkinData skinData);
}