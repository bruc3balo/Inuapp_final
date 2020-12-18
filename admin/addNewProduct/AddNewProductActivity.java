package com.example.inuapp.admin.addNewProduct;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.inuapp.R;
import com.example.inuapp.models.Products;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.example.inuapp.models.Products.COMPUTING;
import static com.example.inuapp.models.Products.COUNT;
import static com.example.inuapp.models.Products.ELECTRONICS;
import static com.example.inuapp.models.Products.FASHION;
import static com.example.inuapp.models.Products.GAMING;
import static com.example.inuapp.models.Products.HOME;
import static com.example.inuapp.models.Products.LITRES;
import static com.example.inuapp.models.Products.NO_IMAGE;
import static com.example.inuapp.models.Products.PRODUCTS;
import static com.example.inuapp.models.Products.PRODUCT_IMAGES;
import static com.example.inuapp.models.Products.PRODUCT_SUR;
import static com.example.inuapp.models.Products.SPORTING;
import static com.example.inuapp.models.Products.WEIGHT;


public class AddNewProductActivity extends AppCompatActivity {

    private String categoryS = "";
    private String unitS = "";
    private Uri file = null;
    private Uri dataUri = null;
    public static final int GET_FROM_GALLERYA = 2;

    private Button getImageButton;
    private ImageView productPreview;

    private final Products product = new Products();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        //Image
        productPreview = findViewById(R.id.productPreview);
        getImageButton = findViewById(R.id.getImageButton);
        getImageButton.setEnabled(true);

        getImageButton.setOnClickListener(v -> galleryPermission());

        //Name
        EditText nameOfProductField = findViewById(R.id.nameOfProductField);

        //Category
        RadioGroup categoryGroup = findViewById(R.id.categoryGroup);
        categoryGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                default:
                    break;
                case R.id.electronicRadio:
                    categoryS = ELECTRONICS;
                    break;

                case R.id.homeRadio:
                    categoryS = HOME;
                    break;

                case R.id.gamingRadio:
                    categoryS = GAMING;
                    break;

                case R.id.fashionRadio:
                    categoryS = FASHION;
                    break;

                case R.id.computingRadio:
                    categoryS = COMPUTING;
                    break;

                case R.id.sportingRadio:
                    categoryS = SPORTING;
                    break;
            }
            Toast.makeText(AddNewProductActivity.this, "" + checkedId, Toast.LENGTH_SHORT).show();
        });

        //Unit
        RadioGroup unitGroup = findViewById(R.id.unitGroup);
        unitGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                default:
                    break;
                case R.id.weightUnit:
                    unitS = WEIGHT;
                    break;

                case R.id.litresUnit:
                    unitS = LITRES;
                    break;

                case R.id.countUnit:
                    unitS = COUNT;
                    break;
            }
        });

        //Price
        EditText priceField = findViewById(R.id.priceField);

        //Description
        EditText descriptionField = findViewById(R.id.descriptionField);

        //Post
        Button postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(v -> {
            if (validateForm(nameOfProductField, priceField, descriptionField)) {
                new UploadProduct().execute(product, null, null);
                finish();
            } else {
                Toast.makeText(AddNewProductActivity.this, "Check Details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(EditText name, EditText price, EditText description) {
        boolean valid = false;

        if (name.getText().toString().isEmpty()) {
            name.setError("Fill");
            name.requestFocus();
        } else if (price.getText().toString().isEmpty()) {
            price.setError("Fill");
            price.requestFocus();
        } else if (description.getText().toString().isEmpty()) {
            description.setError("Fill");
            description.requestFocus();
        } else if (unitS.equals("")) {
            Toast.makeText(this, "Set Unit", Toast.LENGTH_SHORT).show();
        } else if (categoryS.equals("")) {
            Toast.makeText(this, "Pick category", Toast.LENGTH_SHORT).show();
        } else if (file == null) {
            Toast.makeText(this, "Pick an image", Toast.LENGTH_SHORT).show();
        } else {
            product.setProductName(name.getText().toString());
            product.setProductMarketPricePerUnit(Double.valueOf(price.getText().toString()));
            product.setProductDescription(description.getText().toString());

            product.setProductCategory(categoryS);
            product.setProductUnit(unitS);
            product.setPostedAt(truncate(Calendar.getInstance().getTime().toString(), 16));

            product.setProductSellingPricePerUnit(Double.valueOf(price.getText().toString()));
            product.setProductSellCount(0);
            product.setProductImageUrl(NO_IMAGE);

            product.setProductId(name.getText().toString() + "-" + truncate(Calendar.getInstance().getTime().toString(), 16) + "-" + PRODUCT_SUR);

            valid = true;
        }

        return valid;
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraDemo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    public static String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length);
        } else {
            return value;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERYA) {
            new Handler().postDelayed(() -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {

                        assert data != null;
                        // bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), data.getData());

                        //upload image
                        // pickImage.setImageBitmap(bitmap);
                        productPreview.setImageURI(data.getData());
                        dataUri = data.getData();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Failed to get Image");
                }
            }, 2000);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void galleryPermission() {
        // checkSelfPermission(Manifest.permission_group.STORAGE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Not granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            file = Uri.fromFile(getOutputMediaFile());
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).putExtra(MediaStore.EXTRA_OUTPUT, file), GET_FROM_GALLERYA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            getImageButton.setEnabled(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class UploadProduct extends AsyncTask<Products, Void, Void> {

        public UploadProduct () {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Products... products) {
            uploadProduct(dataUri);
            return null;
        }

        private void uploadProduct(Uri files) {
            StorageReference productBucket = FirebaseStorage.getInstance().getReference().child(PRODUCT_IMAGES).child(product.getProductId());
            //  Uri file = Uri.fromFile(new File(String.valueOf(files)));
            //Todo Fix images link
            productBucket.putFile(files).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri generatedFilePath = Objects.requireNonNull(task.getResult()).getUploadSessionUri();
                    productBucket.getDownloadUrl().addOnSuccessListener(uri -> {
                        String url = uri.toString();
                        product.setProductImageUrl(url);
                        saveToDocs(product);
                        System.out.println("Stored link uri" + url);
                    });
                    System.out.println("Upload link uri" + Objects.requireNonNull(task.getResult()).getUploadSessionUri());
                } else {
                    System.out.println("Failed to upload " + files.toString());
                    saveToDocs(product);
                }

            });

        }

        private void saveToDocs(Products product) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(PRODUCTS).document(product.getProductId()).set(product).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    System.out.println(product.getProductName() + " has been saved");
                } else {
                    System.out.println(product.getProductName() + " has not been saved");
                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}