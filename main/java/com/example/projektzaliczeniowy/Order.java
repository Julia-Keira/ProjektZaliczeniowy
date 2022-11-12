package com.example.projektzaliczeniowy;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Order {
    List<String> items;
    String personalData;
    String totalPrice;
    String data;
    String number;
    SmsManager smsManager;
    PendingIntent sentIntent = null;
    PendingIntent deliveryIntent = null;
    List<PendingIntent> sentIntents = null;
    List<PendingIntent> deliveryIntents = null;
    String scAdress = null;
    String itemsString;
    long messageid = 0;
    static ArrayList<Order> orderArrayList = new ArrayList<>();


    public Order(String personalData, String data, String items) {
        this.personalData = personalData;
        this.data = data;
        this.itemsString = items;
    }

    public Order(List<String> items, String personalData, String totalPrice, String data, String number, SmsManager smsManager) {
        this.items = items;
        this.personalData = personalData;
        this.totalPrice = totalPrice;
        this.data = data;
        this.number = number;
        this.smsManager = smsManager;
    }

    public void send(Context context) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
        sqLiteManager.addOrderToDatabase(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            smsManager.sendTextMessage(
                    number,
                    scAdress,
                    "Dzien dobry "+personalData+" Twoje zamówienie: ",
                    sentIntent,
                    deliveryIntent,
                    messageid
            );
            smsManager.sendMultipartTextMessage(
                number,
                scAdress,
                items,
                sentIntents,
                deliveryIntents,
                "test",
                "tag"
            );

            smsManager.sendTextMessage(
                    number,
                    scAdress,
                    "Suma: "+totalPrice+" Złożone: "+data,
                    sentIntent,
                    deliveryIntent,
                    messageid
            );
        }
    }
}
