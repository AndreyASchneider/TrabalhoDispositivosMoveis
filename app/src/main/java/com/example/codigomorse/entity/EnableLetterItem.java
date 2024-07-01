package com.example.codigomorse.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class EnableLetterItem implements Parcelable {

    private Integer userId;
    private String codeType;
    private Character letter;
    private String translation;
    private boolean enabled;

    public EnableLetterItem(Integer userId, String codeType, Character letter, String translation, boolean enabled) {
        this.userId = userId;
        this.codeType = codeType;
        this.letter = letter;
        this.translation = translation;
        this.enabled = enabled;
    }

    protected EnableLetterItem(Parcel in) {
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        codeType = in.readString();
        letter = (Character) in.readSerializable();
        translation = in.readString();
        enabled = in.readByte() != 0;
    }

    public static final Creator<EnableLetterItem> CREATOR = new Creator<EnableLetterItem>() {
        @Override
        public EnableLetterItem createFromParcel(Parcel in) {
            return new EnableLetterItem(in);
        }

        @Override
        public EnableLetterItem[] newArray(int size) {
            return new EnableLetterItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        dest.writeString(codeType);
        dest.writeSerializable(letter);
        dest.writeString(translation);
        dest.writeByte((byte) (enabled ? 1 : 0));
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
