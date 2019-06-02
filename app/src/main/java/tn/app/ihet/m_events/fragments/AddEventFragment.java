package tn.app.ihet.m_events.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import tn.app.ihet.m_events.BuildConfig;
import tn.app.ihet.m_events.R;
import tn.app.ihet.m_events.db.EventManager;
import tn.app.ihet.m_events.model.Event;
import tn.app.ihet.m_events.utility.FileCompressor;




public class AddEventFragment extends DialogFragment {

    public static AddEventFragment getInstance() {

        return new AddEventFragment();
    }

    public AddEventFragment() {
        // Required empty public constructor
    }

    private EditText title, description, info, prix;
    private Button  add ;
    private ImageView image;
    public static final String TAG = "ADD_EVENT";
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;
    Event event = new Event();





    @Override
    public void onResume() {
        super.onResume();
        Display display = Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) (size.x * 0.9);
        int height = (int) (size.y * 0.8);

        Objects.requireNonNull(getDialog().getWindow()).setLayout(width, height);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        title = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        info = view.findViewById(R.id.info);
        prix = view.findViewById(R.id.prix);
        add = view.findViewById(R.id.add);

        image = view.findViewById(R.id.image);

        ButterKnife.bind(getActivity());
        mCompressor = new FileCompressor(getContext());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        final EventManager eventManager = new EventManager(getContext());





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setName(title.getText().toString());
                event.setDescription(description.getText().toString());
                try {
                    event.setPrix(Double.valueOf(prix.getText().toString()));
                }catch (NumberFormatException ex)
                {
                    Log.e(TAG, "onClick: " + ex.toString() );
                    event.setPrix(0.0);

                }
                event.setDate_debut(info.getText().toString());
                event.setId(eventManager.getAllEvents().get(eventManager.getAllEvents().size()-1).getId()+1);
                eventManager.addEvent(event);
                HomeFragment.eventAdapter.setData(eventManager.getAllEvents());

                getActivity().finish();
                startActivity(getActivity().getIntent());
             //   getInstance().dismiss();
                Fragment prev = getFragmentManager().findFragmentByTag(TAG);
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }



            }
        });




        View v = Objects.requireNonNull(getDialog().getWindow()).getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        return view;
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(final boolean isCamera) {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                .onSameThread()
                .check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                openSettings();

            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")) {
                    requestStoragePermission(true);
                } else if (items[which].equals("Choose from Library")) {
                    requestStoragePermission(false);
                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", Objects.requireNonNull(getActivity()).getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }




    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        event.setName(storageDir+mFileName);
        Log.e(TAG, "createImageFile: " + storageDir+mFileName+".jpg" );
        return File.createTempFile(mFileName, ".jpg", storageDir);
    }

    /**
     * Capture image from camera
     */
    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "onActivityResult: " + mPhotoFile.getAbsolutePath() );

                Picasso.with(getContext()).load(mPhotoFile).into(image);

              //  Glide.with().load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.default_profile_pic)).into(imageViewProfilePic);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    event.setImageString(mPhotoFile.getAbsolutePath());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Picasso.with(getContext()).load(mPhotoFile).into(image);

            }

    }



    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



}
