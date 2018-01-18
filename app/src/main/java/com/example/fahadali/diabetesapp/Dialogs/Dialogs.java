package com.example.fahadali.diabetesapp.Dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.fahadali.diabetesapp.R;

/**
 * Created by fahadali on 17/01/2018.
 */

public class Dialogs {


        public static AlertDialog forgotLogin(Context context){

            LayoutInflater inflater = LayoutInflater.from(context);

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            View mView = inflater.inflate(R.layout.forgot_login, null);
            final EditText mEmail = (EditText) mView.findViewById(R.id.email_dialog);




                    mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();


            return dialog;
        }


}
