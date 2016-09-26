package com.quickblox.q_municate_core.legacy.qb.commands.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.quickblox.chat.model.QBDialog;
import com.quickblox.q_municate_core.legacy.core.command.ServiceCommand;
import com.quickblox.q_municate_core.legacy.qb.helpers.QBPrivateChatHelper;
import com.quickblox.q_municate_core.legacy.service.QBService;
import com.quickblox.q_municate_core.legacy.service.QBServiceConsts;
import com.quickblox.q_municate_db.models.User;

public class QBCreatePrivateChatCommand extends ServiceCommand {

    private QBPrivateChatHelper chatHelper;

    public QBCreatePrivateChatCommand(Context context, QBPrivateChatHelper chatHelper, String successAction,
            String failAction) {
        super(context, successAction, failAction);
        this.chatHelper = chatHelper;
    }

    public static void start(Context context, User friend) {
        Intent intent = new Intent(QBServiceConsts.CREATE_PRIVATE_CHAT_ACTION, null, context,
                QBService.class);
        intent.putExtra(QBServiceConsts.EXTRA_FRIEND, friend.getUserId());
        context.startService(intent);
    }

    @Override
    protected Bundle perform(Bundle extras) throws Exception {
        Integer friendId = (Integer) extras.getSerializable(QBServiceConsts.EXTRA_FRIEND);

        QBDialog privateDialog = chatHelper.createPrivateDialogIfNotExist(friendId);
        extras.putSerializable(QBServiceConsts.EXTRA_DIALOG, privateDialog);
        return extras;
    }
}