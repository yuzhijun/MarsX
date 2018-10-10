package com.winning.mars_security.core.strategy.mail;

import android.support.annotation.NonNull;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.winning.mars_security.core.ActionData;
import com.winning.mars_security.core.module.BaseAction;
import com.winning.mars_security.util.MailUtils;

import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import androidx.work.Worker;

public class MailWorker extends Worker{
    @NonNull
    @Override
    public WorkerResult doWork() {
        getImapEmail();
        return WorkerResult.SUCCESS;
    }

    private void getImapEmail() {
        String user = "sharkchao123@163.com";// 邮箱的用户名
        String password = "15235174661lcc1"; // 邮箱的密码

        Properties prop = System.getProperties();
        prop.put("mail.store.protocol", "imap");
        prop.put("mail.imap.host", "imap.163.com");

        Session session = Session.getInstance(prop);

        IMAPStore store;
        try {
            store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器
            store.connect(user, password);
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
            folder.open(Folder.READ_WRITE);
            // 得到收件箱文件夹信息，获取邮件列表
            Message[] messages = folder.getMessages();
            if (messages.length > 0) {
                Map<String, Object> map;
                MailUtils pmm = null;
                for (int i = 0; i < messages.length; i++) {
                    pmm = new MailUtils((MimeMessage) messages[i]);
                    try {
                        Flags flags = messages[i].getFlags();
                        if (!flags.contains(Flags.Flag.SEEN)) {
                            // 获得邮件内容===============
                            pmm.getMailContent((Part) messages[i]);
                            if (pmm.getFrom().equals("\" 哥只是个传说 \"<827623353@qq.com>")){
                                BaseAction action = (BaseAction) ActionData.map.get(pmm.getBodyText().split("<")[0]);
                                if (action != null){
                                    action.doAction();
                                }
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
