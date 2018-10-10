package com.winning.mars_security.core.strategy.mail;

import android.support.annotation.NonNull;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.winning.mars_security.core.ActionData;
import com.winning.mars_security.core.module.BaseAction;
import com.winning.mars_security.util.MailUtils;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import androidx.work.Worker;

public class MailWorker extends Worker{
    private static final String EMAIL_ACCOUNT = "sharkchao123@163.com";// 邮箱的用户名
    private static final String EMAIL_PASSWORD = "15235174661lcc1"; // 邮箱授权码(允许第三方访问)
    private static final String FORM_EMAIL = "\" 哥只是个传说 \"<827623353@qq.com>";
    @NonNull
    @Override
    public WorkerResult doWork() {
        getImapEmail();
        return WorkerResult.SUCCESS;
    }

    private void getImapEmail() {
        Properties prop = System.getProperties();
        prop.put("mail.store.protocol", "imap");
        prop.put("mail.imap.host", "imap.163.com");

        Session session = Session.getInstance(prop);
        IMAPStore store;
        try {
            store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器
            store.connect(EMAIL_ACCOUNT, EMAIL_PASSWORD);
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
            folder.open(Folder.READ_WRITE);//如果需要在取得邮件数后将邮件置为已读则这里需要使用READ_WRITE,否则READ_ONLY就可以
            /**
             * Flag 类型列举如下
             * Flags.Flag.ANSWERED 邮件回复标记，标识邮件是否已回复。
             * Flags.Flag.DELETED 邮件删除标记，标识邮件是否需要删除。
             * Flags.Flag.DRAFT 草稿邮件标记，标识邮件是否为草稿。
             * Flags.Flag.FLAGGED 表示邮件是否为回收站中的邮件。
             * Flags.Flag.RECENT 新邮件标记，表示邮件是否为新邮件。
             * Flags.Flag.SEEN 邮件阅读标记，标识邮件是否已被阅读。
             * Flags.Flag.USER 底层系统是否支持用户自定义标记，只读。
             */
            if (folder.getUnreadMessageCount() > 0){
                FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);//false代表未读，true代表已读

                Message[] messages = folder.search(ft);// 获取所有未读邮件
                for (Message message : messages){
                    try {
                        MailUtils pmm = new MailUtils((MimeMessage) message);
                        pmm.getMailContent(message);
                        if (pmm.getFrom().equals(FORM_EMAIL)){
                            BaseAction action = (BaseAction) ActionData.map.get(pmm.getBodyText().split("<")[0]);
                            if (action != null){
                                action.doAction();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
