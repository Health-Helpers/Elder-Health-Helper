package com.hh.ehh.ui.customdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;


public class CustomDialogs {

    public static Builder noInternetConnection(final Context context) {
        final Builder licenceDialog = new Builder(context);
        licenceDialog.setTitle(context.getResources().getString(R.string.internet_tittle));
        licenceDialog.setMessage(context.getResources().getString(R.string.internet_body));
        return licenceDialog;
    }

    public static Builder closeDialog(Context context, DialogInterface.OnClickListener accept, DialogInterface.OnClickListener cancel) {
        final Builder closeDialog = new Builder(context);
        closeDialog.setTitle(context.getResources().getString(R.string.close));
        closeDialog.setMessage(context.getResources().getString(R.string.close_body));
        closeDialog.setPositiveButton(context.getResources().getString(R.string.Accept), accept);
        closeDialog.setNegativeButton(context.getResources().getString(R.string.cancel), cancel);
        return closeDialog;
    }

    public static Builder wrongDateDialog(final Context context) {
        return new Builder(context)
                .setTitle(context.getResources().getString(R.string.wrong_date))
                .setMessage(context.getResources().getString(R.string.wrong_date_text))
                .setNeutralButton(context.getResources().getString(R.string.accept),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
    }

    public static Builder registrationError(final Context context) {
        return new Builder(context)
                .setTitle(context.getResources().getString(R.string.wrong_register_header))
                .setMessage(context.getResources().getString(R.string.wrong_register))
                .setNeutralButton(context.getResources().getString(R.string.close),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });

    }

    public static Dialog aboutDialog(final Context context) {
        TextView name, mail, phone, link, address;
        ImageView logo;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.about_fragment);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();

            }
        });
        name = (TextView) dialog.findViewById(R.id.enterprise_name);
        mail = (TextView) dialog.findViewById(R.id.enterprise_email);
        phone = (TextView) dialog.findViewById(R.id.enterprise_phone);
        link = (TextView) dialog.findViewById(R.id.enterprise_link);
        address = (TextView) dialog.findViewById(R.id.enterprise_address);
        logo = (ImageView) dialog.findViewById(R.id.enterprise_image);
        Resources res = context.getResources();
        name.setText(res.getString(R.string.app_name));
        mail.setText(res.getString(R.string.enterprise_email));
        mail.setClickable(true);
        mail.setMovementMethod(LinkMovementMethod.getInstance());
        mail.setText(Html.fromHtml("<a href='mailto:" + res.getString(R.string.enterprise_email) + "'>" + res.getString(R.string.enterprise_email) + "</a>"));

        phone.setText(res.getString(R.string.enterprise_phone));
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml("<a href='" + res.getString(R.string.enterprise_link) + "'> " + res.getString(R.string.enterprise_link) + "</a>"));

        address.setText(res.getString(R.string.enterprise_address));
        logo.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_ehh));
        return dialog;
    }
}