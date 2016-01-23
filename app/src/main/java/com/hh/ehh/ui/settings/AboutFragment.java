package com.hh.ehh.ui.settings;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;

/**
 * Created by mpifa on 17/1/16.
 */
public class AboutFragment extends Fragment {

    private TextView name, mail, phone, link, address;
    private ImageView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        name = (TextView) view.findViewById(R.id.enterprise_name);
        mail = (TextView) view.findViewById(R.id.enterprise_email);
        phone = (TextView) view.findViewById(R.id.enterprise_phone);
        link = (TextView) view.findViewById(R.id.enterprise_link);
        address = (TextView) view.findViewById(R.id.enterprise_address);
        logo = (ImageView) view.findViewById(R.id.enterprise_image);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Resources res = getActivity().getResources();
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
        logo.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.mipmap.ic_ehh));
    }

}