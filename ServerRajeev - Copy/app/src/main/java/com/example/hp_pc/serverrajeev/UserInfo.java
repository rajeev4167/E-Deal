package com.example.hp_pc.serverrajeev;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hp-Pc on 10/12/2017.
 */


public class UserInfo {

    @SerializedName("name")
    private String name ;

    @SerializedName("user_email")
    private String useremail;

    @SerializedName("user_pass")
    private String userpass;

    @SerializedName("mobile_num")
    private String mobilenum;

    @SerializedName("profile_pic")
    private String profilepic;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
