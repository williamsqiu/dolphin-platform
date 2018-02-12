package com.canoo.dolphin.samples.security;

import com.canoo.platform.remoting.Property;
import com.canoo.platform.remoting.RemotingBean;

@RemotingBean
public class UserBean {

    private Property<String> userName;

    private Property<String> mailAddress;

    public String getUserName() {
        return userName.get();
    }

    public Property<String> userNameProperty() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName.set(userName);
    }

    public String getMailAddress() {
        return mailAddress.get();
    }

    public Property<String> mailAddressProperty() {
        return mailAddress;
    }

    public void setMailAddress(final String mailAddress) {
        this.mailAddress.set(mailAddress);
    }
}
