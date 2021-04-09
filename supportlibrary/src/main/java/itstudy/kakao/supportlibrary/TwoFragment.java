package itstudy.kakao.supportlibrary;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TwoFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("DialogFragment");
        builder.setMessage("DialogFragment 내용이 잘 보이지요");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog=builder.create();
        return dialog;
    }

}