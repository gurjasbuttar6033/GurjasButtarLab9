package gurjas.buttar.n01562033.js;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Buttar2Fragment extends Fragment {
    private ToggleButton fileType;
    private EditText fileName, fileContents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buttar2, container, false);

        fileName = view.findViewById(R.id.activity_internalstorage_filename);
        fileContents = view.findViewById(R.id.activity_internalstorage_filecontents);
        fileType = view.findViewById(R.id.activity_internalstorage_filetype);
        fileType.setChecked(true);

        view.findViewById(R.id.activity_internalstorage_create).setOnClickListener(v -> createFile(requireContext(), fileType.isChecked()));
        view.findViewById(R.id.activity_internalstorage_delete).setOnClickListener(v -> deleteFile(requireContext(), fileType.isChecked()));
        view.findViewById(R.id.activity_internalstorage_write).setOnClickListener(v -> writeFile(requireContext(), fileType.isChecked()));
        view.findViewById(R.id.activity_internalstorage_read).setOnClickListener(v -> readFile(requireContext(), fileType.isChecked()));

        return view;
    }

    private void createFile(Context context, boolean isPersistent) {
        File file = isPersistent ? new File(context.getFilesDir(), fileName.getText().toString()) : new File(context.getCacheDir(), fileName.getText().toString());

        if (!file.exists()) {
            try {
                file.createNewFile();
                Toast.makeText(context, getString(R.string.file) + fileName.getText().toString() + getString(R.string.has_been_created), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, getString(R.string.file_creation_failed), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context,getString(R.string.file_already_exists), Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFile(Context context, boolean isPersistent) {
        try (FileOutputStream fileOutputStream = isPersistent ? context.openFileOutput(fileName.getText().toString(), Context.MODE_PRIVATE) : new FileOutputStream(new File(context.getCacheDir(), fileName.getText().toString()))) {
            fileOutputStream.write(fileContents.getText().toString().getBytes(Charset.forName(getString(R.string.utf_8))));
            Toast.makeText(context, getString(R.string.write_successful), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, getString(R.string.write_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile(Context context, boolean isPersistent) {
        try (FileInputStream fileInputStream = isPersistent ? context.openFileInput(fileName.getText().toString()) : new FileInputStream(new File(context.getCacheDir(), fileName.getText().toString()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, Charset.forName("UTF-8")))) {

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            fileContents.setText(TextUtils.join("\n", lines));
            Toast.makeText(context, getString(R.string.read_successful), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, getString(R.string.read_failed), Toast.LENGTH_SHORT).show();
            fileContents.setText(R.string.comma);
        }
    }

    private void deleteFile(Context context, boolean isPersistent) {
        File file = isPersistent ? new File(context.getFilesDir(), fileName.getText().toString()) : new File(context.getCacheDir(), fileName.getText().toString());

        if (file.exists() && file.delete()) {
            Toast.makeText(context, getString(R.string.file_deleted), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, getString(R.string.file_doesn_t_exist), Toast.LENGTH_SHORT).show();
        }
    }
}
