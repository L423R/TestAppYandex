package ru.asntl.callsharklibrary;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import ru.asntl.callsharklibrary.audio.AudioActivity;
import ru.asntl.callsharklibrary.camera.CameraActivity;

public class SelectionDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        View audioSelect = view.findViewById(R.id.audioSelect);
        View videoSelect = view.findViewById(R.id.videoSelect);

        audioSelect.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AudioActivity.class);
            getActivity().startActivity(intent);
        });

        videoSelect.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CameraActivity.class);
            getActivity().startActivity(intent);
        });

        builder.setView(view);
        return builder.create();
    }
}
